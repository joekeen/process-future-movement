package au.id.keen.pfm.config;

import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.enums.TransactionFieldV1Enum;
import au.id.keen.pfm.mapper.RecordFieldSetMapper;
import au.id.keen.pfm.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.EnumSet;

@Configuration
@EnableBatchProcessing
public class FutureTransactionBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public FutureTransactionBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository pJobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(pJobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public Job uploadJob(ItemReader<Transaction> pItemReader, ItemWriter<Transaction> pItemWriter) {
        return jobBuilderFactory.get("uploadJob")
                .start(processFile(pItemReader, pItemWriter))
                .build();
    }

    @Bean
    public Step processFile(ItemReader<Transaction> pItemReader, ItemWriter<Transaction> pItemWriter) {
        return stepBuilderFactory.get("processFile")
                .<Transaction, Transaction>chunk(20)
                .reader(pItemReader)
                .writer(pItemWriter)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Transaction> itemWriter(TransactionRepository pTransactionRepository) {
        RepositoryItemWriter<Transaction> writer = new RepositoryItemWriter<>();
        writer.setRepository(pTransactionRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    @StepScope // object will share lifetime with StepExecution, allows us to inject dynamic values at runtime
    public FlatFileItemReader<Transaction> itemReader(@Value("#{jobParameters['file.path']}") String pPath,
                                                      @Value("#{stepExecution.jobExecutionId}") Long pJobId) throws UnexpectedInputException, ParseException {

        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(pPath));

        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        EnumSet<TransactionFieldV1Enum> fields = EnumSet.allOf(TransactionFieldV1Enum.class);
        tokenizer.setColumns(fields.stream()
                .map(r -> new Range(r.getRangeStart(), r.getRangeEnd())).toArray(Range[]::new));
        tokenizer.setNames(fields.stream()
                .map(TransactionFieldV1Enum::getFieldName).toArray(String[]::new));

        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper(pJobId));

        reader.setLineMapper(lineMapper);

        return reader;
    }

}

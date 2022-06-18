package au.id.keen.pfm.config;

import au.id.keen.pfm.mapper.RecordFieldSetMapper;
import au.id.keen.pfm.enums.TransactionFieldV1Enum;
import au.id.keen.pfm.domain.Transaction;
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
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class FutureTransactionBatchConfig {

    @Value("src/test/resources/input/Input.txt")
    private Resource input;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TransactionRepository transactionRepository;

    public FutureTransactionBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, TransactionRepository transactionRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.transactionRepository = transactionRepository;
    }

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public Job uploadJob(ItemReader<Transaction> itemReader) {
        return jobBuilderFactory.get("uploadJob")
//                .incrementer(new RunIdIncrementer()0)
                .start(step1(itemReader))
                .build();
    }

    @Bean
    public Step step1(ItemReader<Transaction> itemReader) {
        return stepBuilderFactory.get("step1")
                .<Transaction, Transaction> chunk(20)
                .reader(itemReader)
//                .processor(processor())
                .writer(writer())
                .build();
    }

    // Creates the Writer, configuring the repository and the method that will be used to save the data into the database
    @Bean
    public RepositoryItemWriter<Transaction> writer() {
        RepositoryItemWriter<Transaction> iwriter = new RepositoryItemWriter<>();
        iwriter.setRepository(transactionRepository);
        iwriter.setMethodName("save");
        return iwriter;
    }

    @Bean
    @StepScope // object will share lifetime with StepExecution, allows us to inject dynamic values at runtime
    public FlatFileItemReader<Transaction> itemReader(@Value("#{jobParameters['file.path']}") String pPath,
                                                      @Value("#{stepExecution.jobExecutionId}") Long pJobExecutionId) throws UnexpectedInputException, ParseException {

        //FlatFileItemReaderBuilder<Transaction> builder = new FlatFileItemReaderBuilder<>();

        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();

        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setStrict(false);
        tokenizer.setColumns(Arrays.stream(TransactionFieldV1Enum.values())
                .map(r -> new Range(r.getRangeStart(), r.getRangeEnd())).toArray(Range[]::new));
        tokenizer.setNames(Arrays.stream(TransactionFieldV1Enum.values())
                .map(TransactionFieldV1Enum::getFieldName).toArray(String[]::new));

        reader.setResource(new FileSystemResource(pPath));

        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper(pJobExecutionId));

        reader.setLineMapper(lineMapper);

        return reader;
    }

/*    @Bean
    public FixedLengthTokenizer tokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(Arrays.stream(ProcessedFutureMovementRecordEnum.values())
                .map(r -> new Range(r.getRangeStart(), r.getRangeEnd())).toArray(Range[]::new));
        tokenizer.setNames(Arrays.stream(ProcessedFutureMovementRecordEnum.values())
                .map(ProcessedFutureMovementRecordEnum::getFieldName).toArray(String[]::new));
        return tokenizer;
    }*/
}

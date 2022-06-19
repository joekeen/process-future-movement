package au.id.keen.pfm.batch;

import au.id.keen.pfm.ProcessedFutureMovementApplication;
import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {ProcessedFutureMovementApplication.class})
//@ContextConfiguration(classes = {BatchConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UploadJobIT {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("input/Input.txt")
    private Resource input;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void testSummariseInputFile() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        ExitStatus exitStatus = jobExecution.getExitStatus();

        assertEquals("COMPLETED", exitStatus.getExitCode());

        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(717, transactions.size());

        List<DailySummary> records = transactionRepository.getDailySummary(jobExecution.getJobId());
        assertEquals(5, records.size());

        DailySummary summary1 = records.get(0);
        assertThat(summary1.getClientInformation()).isEqualTo("CL123400020001");
        assertThat(summary1.getProductInformation()).isEqualTo("SGXFUNK20100910");
        assertThat(summary1.getTotalTransactionAmount()).isEqualTo("-52");

        DailySummary summary2 = records.get(1);
        assertThat(summary2.getClientInformation()).isEqualTo("CL123400030001");
        assertThat(summary2.getProductInformation()).isEqualTo("CMEFUN120100910");
        assertThat(summary2.getTotalTransactionAmount()).isEqualTo("285");

        DailySummary summary3 = records.get(2);
        assertThat(summary3.getClientInformation()).isEqualTo("CL123400030001");
        assertThat(summary3.getProductInformation()).isEqualTo("CMEFUNK.20100910");
        assertThat(summary3.getTotalTransactionAmount()).isEqualTo("-215");

        DailySummary summary4 = records.get(3);
        assertThat(summary4.getClientInformation()).isEqualTo("CL432100020001");
        assertThat(summary4.getProductInformation()).isEqualTo("SGXFUNK20100910");
        assertThat(summary4.getTotalTransactionAmount()).isEqualTo("46");

        DailySummary summary5 = records.get(4);
        assertThat(summary5.getClientInformation()).isEqualTo("CL432100030001");
        assertThat(summary5.getProductInformation()).isEqualTo("CMEFUN120100910");
        assertThat(summary5.getTotalTransactionAmount()).isEqualTo("-79");
    }

    private JobParameters defaultJobParameters() throws IOException {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("file.path", input.getFile().getAbsolutePath());
        return builder.toJobParameters();
    }
}

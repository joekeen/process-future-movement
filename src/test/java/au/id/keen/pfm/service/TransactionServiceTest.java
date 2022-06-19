package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.DailySummaryDto;
import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    private static final String TRANSACTION1 = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private Job uploadJob;

    @MockBean
    private JobExplorer jobExplorer;

    @MockBean
    private JobExecution jobExecution;

    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        transactionService = new TransactionServiceImpl(transactionRepository, jobLauncher, uploadJob, jobExplorer);
    }

    @Test
    public void getSummaryRecords() {
        // given
        long jobId = 1L;
        List<DailySummary> expectedSummaries = List.of(new DailySummaryDto("clientInfo1", "productInfo1", "10"));
        given(transactionRepository.getDailySummary(jobId))
                .willReturn(expectedSummaries);

        // when
        List<DailySummary> actualSummaries = transactionService.getSummaryRecords(jobId);

        // then
        assertThat(actualSummaries).isEqualTo(expectedSummaries);
    }

    @Test
    public void processFile() throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        // given
        MockMultipartFile inputFile = new MockMultipartFile("input.txt", TRANSACTION1.getBytes());
        given(jobLauncher.run(eq(uploadJob), any())).willReturn(jobExecution);
        given(jobExecution.getJobId()).willReturn(22L);
        given(jobExecution.getStatus()).willReturn(BatchStatus.COMPLETED);

        // when
        JobStatusDto status = transactionService.processFile(inputFile);

        // then
        assertThat(status.getJobId()).isEqualTo(22L);
        assertThat(status.getJobStatus()).isEqualTo("COMPLETED");
    }

    @Test
    public void processFileWithError() throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        // given
        MockMultipartFile inputFile = new MockMultipartFile("input.txt", TRANSACTION1.getBytes());
        given(jobLauncher.run(eq(uploadJob), any())).willThrow(new JobExecutionAlreadyRunningException("detailed error message"));

        // when
        JobStatusDto status = transactionService.processFile(inputFile);

        // then
        assertThat(status.getJobId()).isEqualTo(null);
        assertThat(status.getJobStatus()).isEqualTo("ERROR");
        assertThat(status.getMessage()).isEqualTo("An error occurred.");
        assertThat(status.getDetailedMessage()).isEqualTo("detailed error message");
    }

}
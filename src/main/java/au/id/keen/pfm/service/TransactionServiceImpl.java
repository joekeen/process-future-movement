package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final JobLauncher jobLauncher;
    private final Job uploadJob;
    private final JobExplorer jobExplorer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, JobLauncher jobLauncher, Job uploadJob, JobExplorer jobExplorer) {
        this.transactionRepository = transactionRepository;
        this.jobLauncher = jobLauncher;
        this.uploadJob = uploadJob;
        this.jobExplorer = jobExplorer;
    }

    @Override
    public JobStatusDto processFile(String pPath) {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("file.path", pPath);
        try {
            JobExecution execution = jobLauncher.run(uploadJob, builder.toJobParameters());
            return new JobStatusDto(execution.getJobId(), execution.getStatus().toString(), null, null);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            return new JobStatusDto(null, null, "An error occurred.", e.getMessage());
        }
    }

    @Override
    public JobStatusDto getJobStatus(Long pJobId) {
        JobExecution execution = jobExplorer.getJobExecution(pJobId);
        if (execution != null) {
            return new JobStatusDto(pJobId, execution.getStatus().toString(), null, null);
        }
        return new JobStatusDto(pJobId, null, "Job ID not found.", null);
    }

    @Override
    public List<DailySummary> getSummaryRecords(Long pJobId) {
        return transactionRepository.getDailySummary(pJobId);
    }

}

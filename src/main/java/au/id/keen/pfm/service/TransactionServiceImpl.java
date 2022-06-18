package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummaryDto;
import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final JobLauncher jobLauncher;
    private final Job uploadJob;

    public TransactionServiceImpl(TransactionRepository transactionRepository, JobLauncher jobLauncher, Job uploadJob) {
        this.transactionRepository = transactionRepository;
        this.jobLauncher = jobLauncher;
        this.uploadJob = uploadJob;
    }

    @Override
    public JobStatusDto upload(String pPath) {
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
    public List<DailySummaryDto> getRecords(Long pJobId) {
        return transactionRepository.getDailySummary(pJobId)
                .stream().map(DailySummaryDto::new).collect(Collectors.toList());
    }

}

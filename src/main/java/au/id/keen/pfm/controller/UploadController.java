package au.id.keen.pfm.controller;

import au.id.keen.pfm.dto.JobStatusDto;
import au.id.keen.pfm.util.FileUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/api/v1/")
public class UploadController {

    private final JobLauncher jobLauncher;
    private final JobLauncher asyncJobLauncher;
    private final Job uploadJob;
    private final JobExplorer jobExplorer;

    public UploadController(JobLauncher jobLauncher, JobLauncher asyncJobLauncher, Job uploadJob, JobExplorer jobExplorer) {
        this.jobLauncher = jobLauncher;
        this.asyncJobLauncher = asyncJobLauncher;
        this.uploadJob = uploadJob;
        this.jobExplorer = jobExplorer;
    }

    @GetMapping("/job/{id}")
    public JobStatusDto job(@PathVariable Long id) {
        JobExecution execution = jobExplorer.getJobExecution(id);
        if (execution != null) {
            return new JobStatusDto(id, execution.getStatus().toString(), null);
        }
        return new JobStatusDto(id, null, "Job ID not found");
    }

    @PostMapping("/upload")
    public JobStatusDto upload(@RequestParam MultipartFile file) throws IOException {

/*        File tempFile = File.createTempFile("upl", null);

        tempFile.deleteOnExit();

        file.transferTo(tempFile);*/

        /*file.getResource().getFile().getAbsolutePath();

        // async run job and delete temp file
        Files.createTempFile()*/

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("file.path", FileUtils.getTempFile(file).getAbsolutePath());
        // add date time stamp
//        builder.addString("dateTime", LocalDateTime.now());

        try {
            JobExecution execution = jobLauncher.run(uploadJob, builder.toJobParameters());
            return new JobStatusDto(execution.getJobId(), execution.getStatus().toString(), null);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

        /*while (job ! finished) {

        }*/

//        tempFile.delete();

        return new JobStatusDto(null, null, null);
    }

}

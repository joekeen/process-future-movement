package au.id.keen.pfm.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {

    private final JobLauncher jobLauncher;
    private final Job uploadJob;

    public UploadController(JobLauncher jobLauncher, Job uploadJob) {
        this.jobLauncher = jobLauncher;
        this.uploadJob = uploadJob;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam MultipartFile file) throws IOException {

        File tempFile = File.createTempFile("upl", null);

        tempFile.deleteOnExit();

        file.transferTo(tempFile);

        /*file.getResource().getFile().getAbsolutePath();

        // async run job and delete temp file
        Files.createTempFile()*/

        Map<String, JobParameter> parameterMap = new HashMap<>();
//        parameterMap.put("file.path", new JobParameter(tempFile.getAbsolutePath()));
        parameterMap.put("file.path", new JobParameter(getTempFile(file).getAbsolutePath()));

        /*while (job ! finished) {

        }*/

        tempFile.delete();

        return null;
    }

    public File getTempFile(MultipartFile multipartFile) throws IOException {
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
        FileItem fileItem = commonsMultipartFile.getFileItem();
        DiskFileItem diskFileItem = (DiskFileItem) fileItem;
        String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
        File file = new File(absPath);

        //trick to implicitly save on disk small files (<10240 bytes by default)
        if (!file.exists()) {
            file.createNewFile();
            multipartFile.transferTo(file);
        }

        return file;
    }
}

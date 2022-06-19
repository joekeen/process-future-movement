package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.JobStatusDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TransactionService {
    List<DailySummary> getSummaryRecords(Long pJobId);

    JobStatusDto processFile(MultipartFile pFile) throws IOException;

    JobStatusDto getJobStatus(Long pJobId);
}

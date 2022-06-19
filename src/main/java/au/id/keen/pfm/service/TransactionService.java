package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.JobStatusDto;

import java.util.List;

public interface TransactionService {
    List<DailySummary> getSummaryRecords(Long pJobId);

    JobStatusDto processFile(String pPath);

    JobStatusDto getJobStatus(Long pJobId);
}

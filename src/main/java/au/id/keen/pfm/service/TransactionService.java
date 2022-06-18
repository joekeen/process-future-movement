package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummary;
import au.id.keen.pfm.dto.JobStatusDto;

import java.util.List;

public interface TransactionService {
    List<DailySummary> getRecords(Long pJobId);

    JobStatusDto upload(String pPath);

    JobStatusDto queryJob(Long pJobId);
}

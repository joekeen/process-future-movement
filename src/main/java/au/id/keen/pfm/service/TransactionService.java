package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummaryDto;
import au.id.keen.pfm.dto.JobStatusDto;

import java.util.List;

public interface TransactionService {
    List<DailySummaryDto> getRecords(Long pJobId);

    JobStatusDto upload(String pPath);
}

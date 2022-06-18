package au.id.keen.pfm.service;

import au.id.keen.pfm.dto.DailySummaryDto;
import au.id.keen.pfm.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<DailySummaryDto> getRecords(Long pJobId) {
        return transactionRepository.getDailySummary(pJobId)
                .stream().map(DailySummaryDto::new).collect(Collectors.toList());
    }

}

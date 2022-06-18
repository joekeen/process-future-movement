package au.id.keen.pfm.repository;

import au.id.keen.pfm.constant.Constants;
import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.dto.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT concat(clientType, clientNumber, accountNumber, subaccountNumber) as clientInformation " +
            ", concat(exchangeCode, productGroupCode, symbol, function('to_char', expirationDate, '" + Constants.DATE_FORMAT + "')) as productInformation " +
            ", SUM(quantityLong-quantityShort) as totalTransactionAmount " +
            "FROM Transaction " +
            "WHERE jobId = ?1 " +
            "GROUP BY concat(clientType, clientNumber, accountNumber, subaccountNumber)" +
            ", concat(exchangeCode, productGroupCode, symbol, function('to_char', expirationDate, '" + Constants.DATE_FORMAT + "')) ")
    List<DailySummary> getDailySummary(Long pJobId);
}

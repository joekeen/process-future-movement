package au.id.keen.pfm.repository;

import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.dto.DailySummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetDailySummary() {
        // given
        transactionRepository.save(givenTransaction(1L, "CT", "CN", "AN", "SAN", "EC", "PGC1", "S", LocalDate.of(2020, 1, 22), 10L, 1L));
        transactionRepository.save(givenTransaction(1L, "CT", "CN", "AN", "SAN", "EC", "PGC1", "S", LocalDate.of(2020, 1, 22), 5L, 2L));
        // different product
        transactionRepository.save(givenTransaction(1L, "CT", "CN", "AN", "SAN", "EC", "PGC2", "S", LocalDate.of(2020, 1, 22), 8L, 3L));
        // save another record for a different job
        transactionRepository.save(givenTransaction(2L, "CT", "CN", "AN", "SAN", "EC", "PGC", "S", LocalDate.of(2020, 1, 22), 10L, 1L));

        // when
        List<DailySummary> summary = transactionRepository.getDailySummary(1L);

        // then
        DailySummary summary1 = summary.get(0);
        assertThat(summary1.getClientInformation()).isEqualTo("CTCNANSAN");
        assertThat(summary1.getProductInformation()).isEqualTo("ECPGC1S20200122");
        assertThat(summary1.getTotalTransactionAmount()).isEqualTo("12");

        DailySummary summary2 = summary.get(1);
        assertThat(summary2.getClientInformation()).isEqualTo("CTCNANSAN");
        assertThat(summary2.getProductInformation()).isEqualTo("ECPGC2S20200122");
        assertThat(summary2.getTotalTransactionAmount()).isEqualTo("5");
    }

    private Transaction givenTransaction(Long jobId, String clientType, String clientNumber, String accountNumber, String subaccountNumber, String exchangeCode, String productGroupCode, String symbol, LocalDate expirationDate, long quantityLong, long quantityShort) {
        Transaction t = new Transaction();
        t.setJobId(jobId);
        t.setClientType(clientType);
        t.setClientNumber(clientNumber);
        t.setAccountNumber(accountNumber);
        t.setSubaccountNumber(subaccountNumber);
        t.setExchangeCode(exchangeCode);
        t.setProductGroupCode(productGroupCode);
        t.setSymbol(symbol);
        t.setExpirationDate(expirationDate);
        t.setQuantityLong(quantityLong);
        t.setQuantityShort(quantityShort);
        return t;
    }
}
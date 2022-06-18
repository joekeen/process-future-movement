package au.id.keen.pfm.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long jobId;

    private String recordCode;
    private String clientType;
    private String clientNumber;
    private String accountNumber;
    private String subaccountNumber;
    private String oppositePartyCode;
    private String productGroupCode;
    private String exchangeCode;
    private String symbol;
    private LocalDate expirationDate;
    private String currencyCode;
    private String movementCode;
    private String buySellCode;
    private String quantityLongSign;
    private String quatityLong;
    private String quantityShortSign;
    private String quantityShort;
    private String exchBrokerFeeDec;
    private String exchBrokerFeeDC;
    private String exchBrokerFeeCurCode;
    private String clearingFeeDec;
    private String clearingFeeDC;
    private String clearingFeeCurCode;
    private String commission;
    private String commissionDC;
    private String commissionCurCode;
    private LocalDate transactionDate;
    private String futureReference;
    private String ticketNumber;
    private String externalNumber;
    private String transactionPriceDec;
    private String traderInitials;
    private String oppositeTraderId;
    private String openCloseCode;

}

package au.id.keen.pfm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionFieldV1Enum {

    RECORD_CODE("recordCode", 3, 1, 3),
//    CLIENT_TYPE("clientType", 4, 4, 7),
//    CLIENT_NUMBER("clientNumber", 4, 8, 11),
//    ACCOUNT_NUMBER("accountNumber", 4, 12, 15),
//    SUBACCOUNT_NUMBER("subaccountNumber", 4, 16, 19),
//    OPPOSITE_PARTY_CODE("oppositePartyCode", 6, 20, 25),
    EXPIRATION_DATE("expirationDate", 8, 38, 45),
    FILLER("filler", 278, 26, 303);

    private final String fieldName;
    private final int length;
    private final int rangeStart;
    private final int rangeEnd;

}

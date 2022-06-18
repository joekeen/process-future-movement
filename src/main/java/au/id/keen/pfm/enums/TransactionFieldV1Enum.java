package au.id.keen.pfm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionFieldV1Enum {

    RECORD_CODE("recordCode", 3, 1, 3),
    CLIENT_TYPE("clientType", 4, 4, 7),
    CLIENT_NUMBER("clientNumber", 4, 8, 11),
    ACCOUNT_NUMBER("accountNumber", 4, 12, 15),
    SUBACCOUNT_NUMBER("subaccountNumber", 4, 16, 19),
    OPPOSITE_PARTY_CODE("oppositePartyCode", 6, 20, 25),
    PRODUCT_GROUP_CODE("productGroupCode", 2, 26,27),
    EXCHANGE_CODE("exchangeCode", 4, 28, 31),
    SYMBOL("symbol", 6, 32, 37),
    EXPIRATION_DATE("expirationDate", 8, 38, 45),
    CURRENCY_CODE("currencyCode", 3, 46, 48),
    MOVEMENT_CODE("movementCode", 2, 49, 50),
    BUY_SELL_CODE("buySellCode", 1, 51, 51),
    QUANTITY_LONG_SIGN("quantityLongSign", 1, 52, 52),
    QUANTITY_LONG("quantityLong", 10, 53, 62),
    QUANTITY_SHORT_SIGN("quantityShortSign", 1, 63, 63),
    QUANTITY_SHORT("quantityShort", 10, 64, 73),
    EXCH_BROKER_FEE_DEC("exchBrokerFeeDec", 12, 74,85),
    EXCH_BROKER_FEE_DC("exchBrokerFeeDC", 1, 86,86),
    EXCH_BROKER_FEE_CUR_CODE("exchBrokerFeeCurCode", 3, 87,89),
    CLEARING_FEE_DEC("clearingFeeDec", 12, 90, 101),
    CLEARING_FEE_DC("clearingFeeDC", 1, 102, 102),
    CLEARING_FEE_CUR_CODE("clearingFeeCurCode", 3, 103, 105),
    COMMISSION("commission", 12, 106, 117),
    COMMISSION_DC("commissionDC", 1, 118, 118),
    COMMISSION_CUR_CODE("commissionCurCode", 3, 119, 121),
    TRANSACTION_DATE("transactionDate", 8, 122, 129),
    FUTURE_REFERENCE("futureReference", 6, 130, 135),
    TICKET_NUMBER("ticketNumber", 6, 136, 141),
    EXTERNAL_NUMBER("externalNumber", 6, 142, 147),
    TRANSACTION_PRICE_DEC("transactionPriceDec", 15, 148, 162),
    TRADER_INITIALS("traderInitials", 6, 163, 168),
    OPPOSITE_TRADER_ID("oppositeTraderId", 7, 169, 175),
    OPEN_CLOSE_CODE("openCloseCode", 1, 176, 176);

    private final String fieldName;
    private final int length;
    private final int rangeStart;
    private final int rangeEnd;

}

package au.id.keen.pfm.mapper;

import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.enums.TransactionFieldV1Enum;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {

    private final Long jobExecutionId;

    public RecordFieldSetMapper(Long pJobExecutionId) {
        this.jobExecutionId = pJobExecutionId;
    }

    public Transaction mapFieldSet(FieldSet fieldSet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Transaction transaction = new Transaction();
        transaction.setJobId(jobExecutionId);
        transaction.setRecordCode(fieldSet.readString(TransactionFieldV1Enum.RECORD_CODE.getFieldName()));
        transaction.setClientType(fieldSet.readString(TransactionFieldV1Enum.CLIENT_TYPE.getFieldName()));
        transaction.setClientNumber(fieldSet.readString(TransactionFieldV1Enum.CLIENT_NUMBER.getFieldName()));
        transaction.setAccountNumber(fieldSet.readString(TransactionFieldV1Enum.ACCOUNT_NUMBER.getFieldName()));
        transaction.setSubaccountNumber(fieldSet.readString(TransactionFieldV1Enum.SUBACCOUNT_NUMBER.getFieldName()));
        transaction.setOppositePartyCode(fieldSet.readString(TransactionFieldV1Enum.OPPOSITE_PARTY_CODE.getFieldName()));
        transaction.setProductGroupCode(fieldSet.readString(TransactionFieldV1Enum.PRODUCT_GROUP_CODE.getFieldName()));
        transaction.setExchangeCode(fieldSet.readString(TransactionFieldV1Enum.EXCHANGE_CODE.getFieldName()));
        transaction.setSymbol(fieldSet.readString(TransactionFieldV1Enum.SYMBOL.getFieldName()));
        String dateString = fieldSet.readString(TransactionFieldV1Enum.EXPIRATION_DATE.getFieldName());
        transaction.setExpirationDate(LocalDate.parse(dateString, formatter));
        transaction.setCurrencyCode(fieldSet.readString(TransactionFieldV1Enum.CURRENCY_CODE.getFieldName()));
        transaction.setMovementCode(fieldSet.readString(TransactionFieldV1Enum.MOVEMENT_CODE.getFieldName()));
        transaction.setBuySellCode(fieldSet.readString(TransactionFieldV1Enum.BUY_SELL_CODE.getFieldName()));
        transaction.setQuantityLongSign(fieldSet.readString(TransactionFieldV1Enum.QUANTITY_LONG_SIGN.getFieldName()));
        transaction.setQuantityLong(fieldSet.readLong(TransactionFieldV1Enum.QUANTITY_LONG.getFieldName()));
        transaction.setQuantityShortSign(fieldSet.readString(TransactionFieldV1Enum.QUANTITY_SHORT_SIGN.getFieldName()));
        transaction.setQuantityShort(fieldSet.readLong(TransactionFieldV1Enum.QUANTITY_SHORT.getFieldName()));
        transaction.setExchBrokerFeeDec(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DEC.getFieldName()));
        transaction.setExchBrokerFeeDC(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DC.getFieldName()));
        transaction.setExchBrokerFeeCurCode(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_CUR_CODE.getFieldName()));
        transaction.setClearingFeeDec(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DEC.getFieldName()));
        transaction.setClearingFeeDC(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DC.getFieldName()));
        transaction.setClearingFeeCurCode(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_CUR_CODE.getFieldName()));
        transaction.setCommission(fieldSet.readString(TransactionFieldV1Enum.COMMISSION.getFieldName()));
        transaction.setCommissionDC(fieldSet.readString(TransactionFieldV1Enum.COMMISSION_DC.getFieldName()));
        transaction.setCommissionCurCode(fieldSet.readString(TransactionFieldV1Enum.COMMISSION_CUR_CODE.getFieldName()));
        dateString = fieldSet.readString(TransactionFieldV1Enum.TRANSACTION_DATE.getFieldName());
        transaction.setTransactionDate(LocalDate.parse(dateString, formatter));
        transaction.setFutureReference(fieldSet.readString(TransactionFieldV1Enum.FUTURE_REFERENCE.getFieldName()));
        transaction.setTicketNumber(fieldSet.readString(TransactionFieldV1Enum.TICKET_NUMBER.getFieldName()));
        transaction.setExternalNumber(fieldSet.readString(TransactionFieldV1Enum.EXTERNAL_NUMBER.getFieldName()));
        transaction.setTransactionPriceDec(fieldSet.readString(TransactionFieldV1Enum.TRANSACTION_PRICE_DEC.getFieldName()));
        transaction.setTraderInitials(fieldSet.readString(TransactionFieldV1Enum.TRADER_INITIALS.getFieldName()));
        transaction.setOppositeTraderId(fieldSet.readString(TransactionFieldV1Enum.OPPOSITE_TRADER_ID.getFieldName()));
        transaction.setOpenCloseCode(fieldSet.readString(TransactionFieldV1Enum.OPEN_CLOSE_CODE.getFieldName()));

        return transaction;
    }
}

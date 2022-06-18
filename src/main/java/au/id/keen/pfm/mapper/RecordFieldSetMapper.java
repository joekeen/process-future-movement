package au.id.keen.pfm.mapper;

import au.id.keen.pfm.constant.Constants;
import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.enums.TransactionFieldV1Enum;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {

    private final Long jobId;

    public RecordFieldSetMapper(Long pJobId) {
        this.jobId = pJobId;
    }

    public Transaction mapFieldSet(FieldSet pFieldSet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

        Transaction transaction = new Transaction();

        transaction.setJobId(jobId);
        transaction.setRecordCode(pFieldSet.readString(TransactionFieldV1Enum.RECORD_CODE.getFieldName()));
        transaction.setClientType(pFieldSet.readString(TransactionFieldV1Enum.CLIENT_TYPE.getFieldName()));
        transaction.setClientNumber(pFieldSet.readString(TransactionFieldV1Enum.CLIENT_NUMBER.getFieldName()));
        transaction.setAccountNumber(pFieldSet.readString(TransactionFieldV1Enum.ACCOUNT_NUMBER.getFieldName()));
        transaction.setSubaccountNumber(pFieldSet.readString(TransactionFieldV1Enum.SUBACCOUNT_NUMBER.getFieldName()));
        transaction.setOppositePartyCode(pFieldSet.readString(TransactionFieldV1Enum.OPPOSITE_PARTY_CODE.getFieldName()));
        transaction.setProductGroupCode(pFieldSet.readString(TransactionFieldV1Enum.PRODUCT_GROUP_CODE.getFieldName()));
        transaction.setExchangeCode(pFieldSet.readString(TransactionFieldV1Enum.EXCHANGE_CODE.getFieldName()));
        transaction.setSymbol(pFieldSet.readString(TransactionFieldV1Enum.SYMBOL.getFieldName()));
        String dateString = pFieldSet.readString(TransactionFieldV1Enum.EXPIRATION_DATE.getFieldName());
        transaction.setExpirationDate(LocalDate.parse(dateString, formatter));
        transaction.setCurrencyCode(pFieldSet.readString(TransactionFieldV1Enum.CURRENCY_CODE.getFieldName()));
        transaction.setMovementCode(pFieldSet.readString(TransactionFieldV1Enum.MOVEMENT_CODE.getFieldName()));
        transaction.setBuySellCode(pFieldSet.readString(TransactionFieldV1Enum.BUY_SELL_CODE.getFieldName()));
        transaction.setQuantityLongSign(pFieldSet.readString(TransactionFieldV1Enum.QUANTITY_LONG_SIGN.getFieldName()));
        transaction.setQuantityLong(pFieldSet.readLong(TransactionFieldV1Enum.QUANTITY_LONG.getFieldName()));
        transaction.setQuantityShortSign(pFieldSet.readString(TransactionFieldV1Enum.QUANTITY_SHORT_SIGN.getFieldName()));
        transaction.setQuantityShort(pFieldSet.readLong(TransactionFieldV1Enum.QUANTITY_SHORT.getFieldName()));
        transaction.setExchBrokerFeeDec(pFieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DEC.getFieldName()));
        transaction.setExchBrokerFeeDC(pFieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DC.getFieldName()));
        transaction.setExchBrokerFeeCurCode(pFieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_CUR_CODE.getFieldName()));
        transaction.setClearingFeeDec(pFieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DEC.getFieldName()));
        transaction.setClearingFeeDC(pFieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DC.getFieldName()));
        transaction.setClearingFeeCurCode(pFieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_CUR_CODE.getFieldName()));
        transaction.setCommission(pFieldSet.readString(TransactionFieldV1Enum.COMMISSION.getFieldName()));
        transaction.setCommissionDC(pFieldSet.readString(TransactionFieldV1Enum.COMMISSION_DC.getFieldName()));
        transaction.setCommissionCurCode(pFieldSet.readString(TransactionFieldV1Enum.COMMISSION_CUR_CODE.getFieldName()));
        dateString = pFieldSet.readString(TransactionFieldV1Enum.TRANSACTION_DATE.getFieldName());
        transaction.setTransactionDate(LocalDate.parse(dateString, formatter));
        transaction.setFutureReference(pFieldSet.readString(TransactionFieldV1Enum.FUTURE_REFERENCE.getFieldName()));
        transaction.setTicketNumber(pFieldSet.readString(TransactionFieldV1Enum.TICKET_NUMBER.getFieldName()));
        transaction.setExternalNumber(pFieldSet.readString(TransactionFieldV1Enum.EXTERNAL_NUMBER.getFieldName()));
        transaction.setTransactionPriceDec(pFieldSet.readString(TransactionFieldV1Enum.TRANSACTION_PRICE_DEC.getFieldName()));
        transaction.setTraderInitials(pFieldSet.readString(TransactionFieldV1Enum.TRADER_INITIALS.getFieldName()));
        transaction.setOppositeTraderId(pFieldSet.readString(TransactionFieldV1Enum.OPPOSITE_TRADER_ID.getFieldName()));
        transaction.setOpenCloseCode(pFieldSet.readString(TransactionFieldV1Enum.OPEN_CLOSE_CODE.getFieldName()));

        return transaction;
    }
}

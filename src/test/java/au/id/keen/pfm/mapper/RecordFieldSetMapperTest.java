package au.id.keen.pfm.mapper;

import au.id.keen.pfm.domain.Transaction;
import au.id.keen.pfm.enums.TransactionFieldV1Enum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
class RecordFieldSetMapperTest {
    
    @MockBean
    private FieldSet fieldSet;

    @Test
    void testMapFieldSet() {
        // given
        given(fieldSet.readString(TransactionFieldV1Enum.RECORD_CODE.getFieldName())).willReturn("RC");
        given(fieldSet.readString(TransactionFieldV1Enum.CLIENT_TYPE.getFieldName())).willReturn("CT");
        given(fieldSet.readString(TransactionFieldV1Enum.CLIENT_NUMBER.getFieldName())).willReturn("CN");
        given(fieldSet.readString(TransactionFieldV1Enum.ACCOUNT_NUMBER.getFieldName())).willReturn("AN");
        given(fieldSet.readString(TransactionFieldV1Enum.SUBACCOUNT_NUMBER.getFieldName())).willReturn("SAN");
        given(fieldSet.readString(TransactionFieldV1Enum.OPPOSITE_PARTY_CODE.getFieldName())).willReturn("OPC");
        given(fieldSet.readString(TransactionFieldV1Enum.PRODUCT_GROUP_CODE.getFieldName())).willReturn("PGC");
        given(fieldSet.readString(TransactionFieldV1Enum.EXCHANGE_CODE.getFieldName())).willReturn("EC");
        given(fieldSet.readString(TransactionFieldV1Enum.SYMBOL.getFieldName())).willReturn("S");
        given(fieldSet.readString(TransactionFieldV1Enum.EXPIRATION_DATE.getFieldName())).willReturn("20220113");
        given(fieldSet.readString(TransactionFieldV1Enum.CURRENCY_CODE.getFieldName())).willReturn("CC");
        given(fieldSet.readString(TransactionFieldV1Enum.MOVEMENT_CODE.getFieldName())).willReturn("MC");
        given(fieldSet.readString(TransactionFieldV1Enum.BUY_SELL_CODE.getFieldName())).willReturn("BSC");
        given(fieldSet.readString(TransactionFieldV1Enum.QUANTITY_LONG_SIGN.getFieldName())).willReturn("QLS");
        given(fieldSet.readLong(TransactionFieldV1Enum.QUANTITY_LONG.getFieldName())).willReturn(30L);
        given(fieldSet.readString(TransactionFieldV1Enum.QUANTITY_SHORT_SIGN.getFieldName())).willReturn("QSS");
        given(fieldSet.readLong(TransactionFieldV1Enum.QUANTITY_SHORT.getFieldName())).willReturn(31L);
        given(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DEC.getFieldName())).willReturn("EBFD");
        given(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_DC.getFieldName())).willReturn("EBFDC");
        given(fieldSet.readString(TransactionFieldV1Enum.EXCH_BROKER_FEE_CUR_CODE.getFieldName())).willReturn("EBFCC");
        given(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DEC.getFieldName())).willReturn("CFD");
        given(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_DC.getFieldName())).willReturn("CFDC");
        given(fieldSet.readString(TransactionFieldV1Enum.CLEARING_FEE_CUR_CODE.getFieldName())).willReturn("CFCC");
        given(fieldSet.readString(TransactionFieldV1Enum.COMMISSION.getFieldName())).willReturn("C");
        given(fieldSet.readString(TransactionFieldV1Enum.COMMISSION_DC.getFieldName())).willReturn("CDC");
        given(fieldSet.readString(TransactionFieldV1Enum.COMMISSION_CUR_CODE.getFieldName())).willReturn("CCC");
        given(fieldSet.readString(TransactionFieldV1Enum.TRANSACTION_DATE.getFieldName())).willReturn("20220312");
        given(fieldSet.readString(TransactionFieldV1Enum.FUTURE_REFERENCE.getFieldName())).willReturn("FR");
        given(fieldSet.readString(TransactionFieldV1Enum.TICKET_NUMBER.getFieldName())).willReturn("TN");
        given(fieldSet.readString(TransactionFieldV1Enum.EXTERNAL_NUMBER.getFieldName())).willReturn("EN");
        given(fieldSet.readString(TransactionFieldV1Enum.TRANSACTION_PRICE_DEC.getFieldName())).willReturn("TPD");
        given(fieldSet.readString(TransactionFieldV1Enum.TRADER_INITIALS.getFieldName())).willReturn("TI");
        given(fieldSet.readString(TransactionFieldV1Enum.OPPOSITE_TRADER_ID.getFieldName())).willReturn("OTID");
        given(fieldSet.readString(TransactionFieldV1Enum.OPEN_CLOSE_CODE.getFieldName())).willReturn("OCC");
        
        // when
        RecordFieldSetMapper mapper = new RecordFieldSetMapper(22L);
        Transaction transaction = mapper.mapFieldSet(fieldSet);

        // then
        assertThat(transaction.getJobId()).isEqualTo(22L);
        assertThat(transaction.getRecordCode()).isEqualTo("RC");
        assertThat(transaction.getClientType()).isEqualTo("CT");
        assertThat(transaction.getClientNumber()).isEqualTo("CN");
        assertThat(transaction.getAccountNumber()).isEqualTo("AN");
        assertThat(transaction.getSubaccountNumber()).isEqualTo("SAN");
        assertThat(transaction.getOppositePartyCode()).isEqualTo("OPC");
        assertThat(transaction.getProductGroupCode()).isEqualTo("PGC");
        assertThat(transaction.getExchangeCode()).isEqualTo("EC");
        assertThat(transaction.getSymbol()).isEqualTo("S");
        assertThat(transaction.getExpirationDate()).isEqualTo(LocalDate.of(2022, 1, 13));
        assertThat(transaction.getCurrencyCode()).isEqualTo("CC");
        assertThat(transaction.getMovementCode()).isEqualTo("MC");
        assertThat(transaction.getBuySellCode()).isEqualTo("BSC");
        assertThat(transaction.getQuantityLongSign()).isEqualTo("QLS");
        assertThat(transaction.getQuantityLong()).isEqualTo(30L);
        assertThat(transaction.getQuantityShortSign()).isEqualTo("QSS");
        assertThat(transaction.getQuantityShort()).isEqualTo(31L);
        assertThat(transaction.getExchBrokerFeeDec()).isEqualTo("EBFD");
        assertThat(transaction.getExchBrokerFeeDC()).isEqualTo("EBFDC");
        assertThat(transaction.getExchBrokerFeeCurCode()).isEqualTo("EBFCC");
        assertThat(transaction.getClearingFeeDec()).isEqualTo("CFD");
        assertThat(transaction.getClearingFeeDC()).isEqualTo("CFDC");
        assertThat(transaction.getClearingFeeCurCode()).isEqualTo("CFCC");
        assertThat(transaction.getCommission()).isEqualTo("C");
        assertThat(transaction.getCommissionDC()).isEqualTo("CDC");
        assertThat(transaction.getCommissionCurCode()).isEqualTo("CCC");
        assertThat(transaction.getTransactionDate()).isEqualTo(LocalDate.of(2022, 3, 12));
        assertThat(transaction.getFutureReference()).isEqualTo("FR");
        assertThat(transaction.getTicketNumber()).isEqualTo("TN");
        assertThat(transaction.getExternalNumber()).isEqualTo("EN");
        assertThat(transaction.getTransactionPriceDec()).isEqualTo("TPD");
        assertThat(transaction.getTraderInitials()).isEqualTo("TI");
        assertThat(transaction.getOppositeTraderId()).isEqualTo("OTID");
        assertThat(transaction.getOpenCloseCode()).isEqualTo("OCC");
    }
}
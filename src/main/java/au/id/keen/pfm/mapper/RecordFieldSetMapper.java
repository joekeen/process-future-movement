package au.id.keen.pfm.mapper;

import au.id.keen.pfm.domain.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {

    public Transaction mapFieldSet(FieldSet fieldSet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Transaction transaction = new Transaction();
        transaction.setRecordCode(fieldSet.readString("recordCode"));
        String dateString = fieldSet.readString("expirationDate");
        transaction.setExpirationDate(LocalDate.parse(dateString, formatter));
        transaction.setFiller(fieldSet.readString("filler"));

        return transaction;
    }
}

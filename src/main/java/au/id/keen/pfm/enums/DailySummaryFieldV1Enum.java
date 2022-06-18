package au.id.keen.pfm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DailySummaryFieldV1Enum {

    CLIENT_INFORMATION("Client_Information"),
    PRODUCT_INFORMATION("Product_Information"),
    TOTAL_TRANSACTION_AMOUNT("Total_Transaction_Amount");

    private final String header;
}

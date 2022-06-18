package au.id.keen.pfm.dto;

import lombok.Value;

@Value
public class DailySummaryDto implements DailySummary {

    String clientInformation;
    String productInformation;
    String totalTransactionAmount;

    public DailySummaryDto(DailySummary pSummary) {
        this.clientInformation = pSummary.getClientInformation();
        this.productInformation = pSummary.getProductInformation();
        this.totalTransactionAmount = pSummary.getTotalTransactionAmount();
    }
}

package au.id.keen.pfm.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"clientInformation", "productInformation", "totalTransactionAmount"})
public interface DailySummary {

    String getClientInformation();

    String getProductInformation();

    String getTotalTransactionAmount();
}

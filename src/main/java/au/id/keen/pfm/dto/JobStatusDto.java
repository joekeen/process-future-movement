package au.id.keen.pfm.dto;

import lombok.Value;

@Value
public class JobStatusDto {

    Long jobId;
    String jobStatus;
    String message;
    String detailedMessage;

}

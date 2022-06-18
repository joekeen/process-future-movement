package au.id.keen.pfm.dto;

import lombok.Value;

@Value
public class JobStatusDto {

    private Long jobId;
    private String jobStatus;
    private String message;

}

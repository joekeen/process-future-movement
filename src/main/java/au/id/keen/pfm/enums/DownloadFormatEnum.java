package au.id.keen.pfm.enums;

import au.id.keen.pfm.dto.DailySummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DownloadFormatEnum {

    csv(MediaType.TEXT_PLAIN_VALUE,
            list -> Stream.concat(
                    Stream.of(EnumSet.allOf(DailySummaryFieldV1Enum.class).stream()
                            .map(DailySummaryFieldV1Enum::getHeader)
                            .collect(Collectors.joining(","))),
                    list.stream()
                            .map(s -> String.join(",",
                                    s.getClientInformation(),
                                    s.getProductInformation(),
                                    s.getTotalTransactionAmount()))
            )
                    .collect(Collectors.joining("\n"))
                    .getBytes());

    private final String contentType;
    private final Function<List<DailySummary>, byte[]> toByteArray;
}

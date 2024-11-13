package nbc_final.gathering.domain.matching.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc_final.matching_service.enums.InterestType;


@NoArgsConstructor
@Getter
@AllArgsConstructor
public class MatchingRequestDto {

    private Long userId;
    private InterestType interestType;
    private String location;

}

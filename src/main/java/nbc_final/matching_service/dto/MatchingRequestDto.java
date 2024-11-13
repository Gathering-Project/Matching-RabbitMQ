package nbc_final.matching_service.dto;

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

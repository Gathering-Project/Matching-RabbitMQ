//package nbc_final.matching_service.service;
//
//import nbc_final.matching_service.dto.MatchingRequestDto;
//import nbc_final.matching_service.entity.Matching;
//import nbc_final.matching_service.enums.InterestType;
//import nbc_final.matching_service.repository.MatchingRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MatchingServiceTest {
//
//    private final Logger log = LoggerFactory.getLogger(MatchingService.class);
//
//    @Mock
//    private MatchingRepository matchingRepository;
//
//    @InjectMocks
//    private MatchingService matchingService;
//
//    private MatchingRequestDto user1;
//    private MatchingRequestDto user2;
//    private MatchingRequestDto user3;
//    private MatchingRequestDto user4;
//    private MatchingRequestDto user5;
//
//    @BeforeEach
//    void setUp() {
//        user1 = new MatchingRequestDto(1L, InterestType.EXERCISE, "서울시");
//        user2 = new MatchingRequestDto(2L, InterestType.EXERCISE, "부산시");
//        user3 = new MatchingRequestDto(3L, InterestType.GAMING, "인천시");
//        user4 = new MatchingRequestDto(4L, InterestType.DRAWING, "대전시");
//        user5 = new MatchingRequestDto(5L, InterestType.EXERCISE, "서울시");
//    }
//
//    @Test
//    void 관심사와_지역이_같은_유저들끼리는_매칭에_성공한다() {
//        // 유저 1 ~ 5까지 각각 매칭 신청 -> 관심사, 지역이 같은 유저는 1번과 5번 유저가 매칭되어야 한다.
//
//        // when
//        matchingService.add(user1);
//        matchingService.add(user2);
//        matchingService.add(user3);
//        matchingService.add(user4);
//        matchingService.add(user5);
//        matchingService.matching();
//
//        // then & given
//        verify(matchingRepository, times(1)).save(any(Matching.class));
//    }
//
//    @Test
//    void 유저가_매칭_신청을_취소하면_매칭_대기열에서_삭제된다() {
//        // Given
//        matchingService.add(user1); // 유저1 매칭 대기열에 추가
//        matchingService.add(user2); // 유저2 매칭 대기열에 추가
//        matchingService.add(user3); // 유저3 매칭 대기열에 추가
//        matchingService.add(user4); // 유저4 매칭 대기열에 추가
//        matchingService.add(user5); // 유저5 매칭 대기열에 추가
//
//        // When
//        matchingService.cancelMatching(user5.getUserId()); // 유저5의 매칭 신청 취소
//        matchingService.matching();
//
//        // Then
//        assertThat(matchingService.matchingList.contains(user5)).isFalse(); // 매칭 대기열에서 유저1이 삭제되었는지 검증
//        // 매칭 조건 성립되는 1과 5 중 5가 삭제되었으니까 매칭 저장 0번 되야 함
//        verify(matchingRepository, times(0)).save(any(Matching.class));
//    }
//
//
//
//}
package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
            },
            MOVING_FORWARD, STOP
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 입력 기능 구현 테스트
    @Test
    void 정상_입력_시_프롬프트_출력_테스트() {
        assertSimpleTest(() -> {
            run("pobi,woni,jun", "2");

            // 1. "입력 기능"에서 요구하는 프롬프트가 모두 출력되었는지 확인
            assertThat(output()).contains(
                    "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)",
                    "시도할 횟수는 몇 회인가요?"
            );

            // 2. "출력 기능"의 일부(실행 결과, 최종 우승자)까지 정상적으로 도달했는지 확인
            assertThat(output()).contains(
                    "실행 결과",
                    "최종 우승자 : "
            );
        });
    }

    // 유효성 검증 및 예외 처리 기능 구현 테스트
    @Test
    void 이름이_쉼표로_끝나는_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,woni,jun,", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("자동차 이름은 쉼표(,)로 끝날 수 없습니다.")
        );
    }

    @Test
    void 이름이_5자를_초과하는_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,woni,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("자동차 이름은 5자 이하만 가능합니다.")
        );
    }

    @Test
    void 이름이_공백인_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi, ,woni", "1")) // " " (공백)
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("자동차 이름은 공백이거나 비어있을 수 없습니다.")
        );
    }

    @Test
    void 이름이_비어있는_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,,woni", "1")) // "" (빈 문자열)
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("자동차 이름은 공백이거나 비어있을 수 없습니다.")
        );
    }

    @Test
    void 이름이_중복되는_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,woni,pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("자동차 이름은 중복될 수 없습니다.")
        );
    }

    @Test
    void 시도_횟수가_숫자가_아닌_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,woni", "a")) // "a" (숫자 아님)
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("시도 횟수는 숫자여야 합니다.")
        );
    }

    @Test
    void 시도_횟수가_1보다_작은_경우_예외_발생() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> run("pobi,woni", "0")) // "0" (1 미만)
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("시도 횟수는 1 이상이어야 합니다.")
        );
    }

    // 핵심 로직 구현 테스트
    @Test
    void 단독_우승자_판별_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "2");
                    assertThat(output()).contains("최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP, // 1라운드
                MOVING_FORWARD, STOP  // 2라운드
        );
    }

    @Test
    void 공동_우승자_판별_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "2");
                    assertThat(output()).contains("최종 우승자 : pobi, woni");
                },
                MOVING_FORWARD, MOVING_FORWARD, // 1라운드
                STOP, STOP  // 2라운드
        );
    }

    @Test
    void 단일_라운드_진행_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output()).contains("최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP // 1라운드
        );
    }

    @Test
    void 모든_자동차가_정지하는_경우_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni,jun", "1");
                    assertThat(output()).contains("최종 우승자 : pobi, woni, jun");
                },
                STOP, STOP, STOP // 1라운드
        );
    }

    // 출력 기능 구현 테스트
    @Test
    void 라운드별_결과_출력_테스트() {
        // pobi: 4(전진) -> pobi : -
        // woni: 3(정지) -> woni :
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");

                    // "실행 결과" 문구 출력 검증
                    assertThat(output()).contains("실행 결과");

                    // 라운드별 결과 검증
                    assertThat(output()).contains("pobi : -", "woni : ");
                },
                MOVING_FORWARD, STOP // 1라운드
        );
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}

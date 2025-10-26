package racingcar;

import camp.nextstep.edu.missionutils.Console;

/**
 * 자동차 경주 게임의 주 로직을 담당하는 클래스
 */
public class RacingGame {

    //게임을 시작하는 메인 메서드
    public void start() {
        // 1. 입력 기능 구현
        String carNamesInput = getCarNamesInput();
        // 2. 유효성 검증 기능 구현
        validateCarNamesInputFormat(carNamesInput);

        String tryCountInput = getTryCountInput();


        // (임시) 입력 확인 출력
        System.out.println("입력된 차 이름: " + carNamesInput);
        System.out.println("입력된 횟수: "+ tryCountInput);

    }

    // 자동차 이름 입력 기능
    private String getCarNamesInput() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }

    // 시도 횟수 입력 기능
    private String getTryCountInput() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        return Console.readLine();
    }

    // 자동차 이름 입력 형식 검증 기능
    private void validateCarNamesInputFormat(String carNamesInput) {
        if (carNamesInput.endsWith(",")) {
            throw new IllegalArgumentException("자동차 이름은 쉼표(,)로 끝날 수 없습니다.");
        }
    }
}

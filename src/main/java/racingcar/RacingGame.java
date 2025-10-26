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

        // (임시) 입력 확인 출력
        System.out.println("입력된 차 이름: " + carNamesInput);

    }

    // 자동차 이름 입력 기능
    private String getCarNamesInput() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }
}

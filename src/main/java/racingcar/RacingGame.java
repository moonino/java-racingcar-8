package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 자동차 경주 게임의 주 로직을 담당하는 클래스
 */
public class RacingGame {

    private static final int MAX_NAME_LENGTH = 5; // 이름 최대 길이 상수
    // 전진 판단 기능 - 상수 추가
    private static final int MOVE_FORWARD_CONDITION = 4; // 전진 조건
    private static final int MIN_RANDOM_NUMBER = 0; // 랜덤 범위 최소값
    private static final int MAX_RANDOM_NUMBER = 9; // 랜덤 범위 최대값


    //게임을 시작하는 메인 메서드
    public void start() {
        // 1. 입력 기능
        String carNamesInput = getCarNamesInput();
        // 2. 유효성 검증 기능
        validateCarNamesInputFormat(carNamesInput);//자동차 이름 형식 유효성 검증 기능
        validateCarNameLengths(carNamesInput);//자동차 이름 길이 유효성 검증 기능

        String tryCountInput = getTryCountInput();
        int tryCount = validateTryCount(tryCountInput);//시도 횟수 검증 기능

        // 3. 핵심 로직 (자동차 객체 생성)
        List<Car> cars = createCars(carNamesInput);

        // 4. 결과 출력 (실행 결과 메시지)
        System.out.println("\n실행 결과");
        // 경주 진행 기능
        runRace(cars, tryCount);

        // 우승자 판별 기능
        List<Car> winners = getWinners(cars);

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

    // 자동차 이름 길이 검증 기능
    private void validateCarNameLengths(String carNamesInput) {
        String[] carNames = carNamesInput.split(",");
        for (String name : carNames) {
            if (name.length() > MAX_NAME_LENGTH) {
                throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다.");
            }
        }
    }

    // 시도 횟수 검증 기능
    private int validateTryCount(String tryCountInput) {
        int tryCount;
        try {
            tryCount = Integer.parseInt(tryCountInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도 횟수는 숫자여야 합니다.");
        }

        if (tryCount < 1) {
            throw new IllegalArgumentException("시도 횟수는 1 이상이어야 합니다.");
        }

        return tryCount;
    }

    // 자동차 객체 생성 기능
    private List<Car> createCars(String carNamesInput) {
        List<Car> cars = new ArrayList<>();
        String[] carNames = carNamesInput.split(",");

        for (String name : carNames) {
            cars.add(new Car(name));
        }
        return cars;
    }

    // 전진 판단 기능
    private void tryMoveCar(Car car) {
        int randomNumber = Randoms.pickNumberInRange(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
        if (randomNumber >= MOVE_FORWARD_CONDITION) {
            car.move();
        }
    }

    // 경주 진행 기능 (전체 라운드)
    private void runRace(List<Car> cars, int tryCount) {
        for (int i = 0; i < tryCount; i++) {
            runSingleRound(cars);
            printRoundResult(cars);
        }
    }

    // 경주 진행 기능 (단일 라운드)
    private void runSingleRound(List<Car> cars) {
        for (Car car : cars) {
            tryMoveCar(car);
        }
    }

    // 우승자 판별 기능
    private List<Car> getWinners(List<Car> cars) {
        int maxPosition = getMaxPosition(cars);

        return cars.stream()
                .filter(car -> car.getPosition() == maxPosition)
                .collect(Collectors.toList());
    }

    // 우승자 판별 기능 (자동차 리스트에서 가장 큰 position 값을 찾는 메소드)
    private int getMaxPosition(List<Car> cars) {
        int maxPosition = 0;
        for (Car car : cars) {
            if (car.getPosition() > maxPosition) {
                maxPosition = car.getPosition();
            }
        }
        return maxPosition;
    }

    // 결과 출력 기능
    private void printRoundResult(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + car.getPositionString());
        }
        System.out.println();
    }
}


package racingcar;

//자동차의 이름과 현재 위치를 관리하는 클래스

public class Car {
    private final String name;
    private int position;

    public Car(String name) {
        this.name = name.trim(); // 이름 앞뒤 공백 제거
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    // 자동차를 한 칸 전진하는 메소드
    public void move() {
        this.position++;
    }

    // 현재 position 값을 "-" 문자열로 변환하여 반환하는 메소드
    // return 예: position이 3이면 "---" 반환
    public String getPositionString() {
        return "-".repeat(this.position);
    }
}

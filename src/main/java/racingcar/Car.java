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

    public void move() {
        this.position++;
    }
}

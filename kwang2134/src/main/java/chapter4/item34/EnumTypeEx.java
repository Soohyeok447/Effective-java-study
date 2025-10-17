package chapter4.item34;

public class EnumTypeEx {
    /**
     * 정수 상수 (static final int) 방식 -> 타입 안전성, 유지보수, 네임스페이스, 확장성 모두 취약
     * enum 상수 방식 -> 강력한 타입 안전성, 관련 데이터, 동작(메서드), 인터페이스 구현 등 다양한 기능 지원
     *
     * 열거 타입 사용 시, 상수 집합을 독립적인 타입으로 선언 가능
     * 잘못된 값의 혼용 방지, 모든 switch-case exhaustiveness, value iteration 등 가능
     */

    // 정수 상수 방식
    // 타입 안전성 없음: int로 관리 -> Apple과 Orange 상수 섞여도 컴파일 오류 발생 X
    // new 그룹의 상수 추가/삭제/순서 변경 시 클라이언트 영향 미침
    class FruitType {
        public static final int APPLE_FUJI = 0;
        public static final int APPLE_GRANNY_SMITH = 1;
        public static final int ORANGE_NAVEL = 0;
        public static final int ORANGE_TEMPLE = 1;
    }

    // 열거 타입
    // 타입 안전성: 잘못된 타입간 대입/비교 모두 컴파일 타임 오류
    enum Apple {FUJI, GRANNY_SMITH}
    enum Orange {NAVEL, TEMPLE}

    // 값과 행동을 함께 가지는 enum
    // 각 상수별 필드, 생성자, 메서드 정의 가능
    enum Fruit {
        APPLE("red", 52), ORANGE("orange", 47);

        private final String color;
        private final int calories;

        Fruit(String color, int calories) {
            this.color = color;
            this.calories = calories;
        }

        public String getColor() {
            return color;
        }

        public int getCalories() {
            return calories;
        }
    }

    // enum이 아닌 상수만 있는 경우 문제
    public void oldWayProblems() {
        int a = FruitType.APPLE_FUJI;
        int o = FruitType.ORANGE_TEMPLE;
        // 오류 없이 혼용 가능
        if (a == o) {
            // 논리적 오류, 의미 없는 비교인데 컴파일러는 모름
        }
    }

    // enum 예시
    public void enumUsage() {
        Apple apple = Apple.FUJI;
        Orange orange = Orange.NAVEL;
        Fruit fruit = Fruit.APPLE;

        System.out.println(apple);         // FUJI
        System.out.println(orange);        // NAVEL
        System.out.println(fruit.getColor());     // red
        System.out.println(fruit.getCalories());  // 52

        // 아래처럼 잘못된 대입 불가 (컴파일 오류)
        // apple = Orange.NAVEL;

        // 열거형 반복
        for (Fruit f : Fruit.values()) {
            System.out.println(f + ": color=" + f.getColor() + ", cal=" + f.getCalories());
        }

        // switch-case 지원
        switch (fruit) {
            case APPLE:
                System.out.println("사과!");
                break;
            case ORANGE:
                System.out.println("오렌지!");
                break;
        }
    }

}

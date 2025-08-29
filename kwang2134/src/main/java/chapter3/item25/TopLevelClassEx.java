package chapter3.item25;

public class TopLevelClassEx {
    /**
     * 톱레벨(top-level) 클래스: public / default 접근 제한자를 갖는 최상위 클래스
     *
     * 한 소스 파일에 여러 톱레벨 클래스를 두면 컴파일/빌드 결과가 예측 불가능해 질 수 있음
     *
     * 이유
     * - 같은 파일 내에 여러 톱레벨 클래스가 있으면 클래스 이름으로 접근 시 어떤 클래스가 컴파일되는지 모호
     * - 소스 & 빌드 아티팩트가 꼬여서 디버깅과 유지보수가 매우 어려워짐
     *
     * -> 톱레벨 클래스는 반드시 한 소스 파일에 하나만 담을 것
     */

    // 잘못된 예시 - 하나의 파일에 두 개의 톱레벨 클래스 정의
    // 파일 이름 Utensil.java
    class Utensil {
        static final String NAME = "Pan";
    }

    class Dessert {
        static final String NAME = "Cake";
    }

    // javac Utensil.java로 컴파일하면 "Utensil"만 컴파일
    // javac Dessert.java로 컴파일하면 같은 파일을 읽어 "Dessert"만 클래스 파일로 생성

    // 빌드 환경에 따라 다른 결과물이 나와 예기치 못한 버그 발생


     // 올바른 예시 - 파일마다 하나의 톱레벨 클래스만 둠

     //Utensil.java 파일
    /*
    public class Utensil {
        static final String NAME = "Pan";
    }
    */


    // Dessert.java 파일
    /*
    public class Dessert {
        static final String NAME = "Cake";
    }
    */
}

package chapter3.item24;

public class MemberClassEx {
    /**
     * 중첩 클래스 종류
     * 1. 정적 멤버 클래스 (static member class)
     * 2. 비정적 멤버 클래스 (non-static member class)
     * 3. 익명 클래스 (anonymous class)
     * 4. 지역 클래스 (local class)
     *
     * 비정적 멤버 클래스는 바깥 클래스(instance)의 숨은 참조를 가짐
     * -> 메모리 누수, 성능 저하, 직렬화/역직렬화 문제 발생 가능
     *
     * 중첩 클래스에서 바깥 인스턴스에 접근할 필요가 없으면 무조건 static을 붙여 정적 멤버 클래스로 생성
     *
     * static 멤버 클래스는 바깥 클래스와 독립적으로 존재 가능
     */

    // 비정적 멤버 클래스 (불필요한 참조 문제 발생 가능)
    // NonStaticMember는 외부클래스 Outer의 인스턴스를 항상 숨은 참조로 가지고 있음
    class NonStaticMember {
        private int value = 10;

        public void print() {
            System.out.println("Inner Value: " + value + ", Outer value: " + outerValue);
        }
    }

    private int outerValue = 100;

    // 정적 멤버 클래스
    // 바깥 클래스 인스턴스에 접근할 필요가 전혀 없는 경우
    static class StaticMember {
        private int value = 10;

        public void print() {
            System.out.println("Static member value: " + value);
        }
    }

    // Enum도 사실상 정적 멤버 클래스 -> 바깥 클래스 인스턴스에 종속되지 않는 정적 멤버 클래스의 일종
    enum Suit {CLUBS, DIAMONDS, HEARTS};

    // 지역 클래스(Local class)
    // 메서드 내부에서만 필요한 특수 타입 정의 시 사용
    public void processLocal() {
        class LocalClass {
            void run() {
                System.out.println("Local class instance running");
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.run();
    }

    // 익명 클래스 - 1회성 임시 구현체 필요 시 사용
    public void processAnonymous() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명 클래스 실행");
            }
        };
        r.run();
    }
}

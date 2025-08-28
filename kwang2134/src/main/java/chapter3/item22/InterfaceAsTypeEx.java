package chapter3.item22;

public class InterfaceAsTypeEx {
    /**
     * 인터페이스의 목적
     * -> 타입을 정의하는 것
     * -> 어떤 클래스가 그 인터페이스를 구현한다 = 그 타입의 인스턴스다 라고 보장
     *
     * 잘못된 사용 예시 -> 상수를 모아놓는 상수 인터페이스
     * -> 인터페이스를 타입 정의가 아닌 상수 집합으로 활용
     * -> 구현 클래스의 내부 API에 불필요한 상수가 노출
     */


     // 잘못된 사용 예시: 상수 인터페이스
     interface PhysicalConstants {
        static final double AVOGADROS_NUMBER = 6.022_140_857e23;
        static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
        static final double ELECTRON_MASS = 9.109_383_56e-31;
    }

    // 상수 인터페이스를 구현하는 클래스는 의도치 않게 이 상수들이 public API로 노출
    static class BadClass implements PhysicalConstants {}

    // 대안 1: 특정 클래스 내부에 상수를 정의 -> 클래스의 의미와 밀접하게 관련된 상수만 위치
    static class Planet {
        private final double mass;           // 질량 (kg)
        private final double radius;         // 반지름 (m)

        // 행성별 상수
        public static final Planet EARTH = new Planet(5.972e24, 6.371e6);
        public static final Planet MARS  = new Planet(6.417e23, 3.389e6);

        public Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double surfaceGravity() {
            return GRAVITATIONAL_CONSTANT * mass / (radius * radius);
        }

        // 중력 상수를 "해당 클래스 내부"에 둔다 (외부에 불필요하게 노출하지 않음)
        private static final double GRAVITATIONAL_CONSTANT = 6.674e-11;
    }

    // 대안 2: 성수 유틸리티 클래스 사용 -> final 클래스 + private 생성자로 인스턴스화 시키지 못하게 제공
    public final class PhysicalConstantsUtil {
        private PhysicalConstantsUtil() {} // 인스턴스화 방지

        public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
        public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
        public static final double ELECTRON_MASS = 9.109_383_56e-31;
    }
}

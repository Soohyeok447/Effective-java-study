package chapter3.item23;

public class TaggedClassEx {
    /**
     * 태그 달린 클래스란?
     * 한 클래스 안에 여러 타입을 한데 묶어 표현하고, 타입 태그 피드를 두어 현재 객체가 어떤 타입인지 구분하는 방식
     * <p>
     * 문제점
     * 1. 불필요한 필드 존재 (태그 값에 따라 미사용 필드 존재)
     * 2. switch/if 문 분기를 통해 동작 -> 캡슐화가 깨짐
     * 3. 새로운 타입 추가 어려움 -> 코드 전체 수정 필요
     * 4. 가독성이 떨어지고 유지보수 어려움
     */

    // 잘못된 설계: 태그 달린 클래스
    static class FigureBad {
        enum Shape {RECTANGLE, CIRCLE}

        final Shape shape;

        // Rectangle 용 -> 원이라면 필요 없음
        double length;
        double width;

        // Circle 용 -> 사각형이라면 필요 없음
        double radius;

        FigureBad(double length, double width) {
            shape = Shape.RECTANGLE;
            this.length = length;
            this.width = width;
        }

        FigureBad(double radius) {
            shape = Shape.CIRCLE;
            this.radius = radius;
        }

        // 동작: 넓이를 구하는 메서드
        // switch문으로 타입 분기 -> 동작 책임이 객체가 아니라 switch문에 있음
        double area() {
            switch (shape) {
                case RECTANGLE:
                    return length * width; // 직사각형 넓이
                case CIRCLE:
                    return Math.PI * (radius * radius); // 원 넓이
                default:
                    throw new AssertionError(shape); // 새로운 타입 미처리 위험
            }
        }

        /**
         * 개선된 설계: 계층 구조 활용
         * - 공통 성질은 추상 클래스에 정의
         * - 각 구체 타입은 하위 클래스로 분리
         * - 다형성을 이용하여 분기문 제거
         */
        abstract static class Figure {
            abstract double area();
        }

        static class Rectangle extends Figure {
            private final double length;
            private final double width;

            Rectangle(double length, double width) {
                this.length = length;
                this.width = width;
            }

            @Override
            double area() {
                return length * width;
            }
        }

        static class Circle extends Figure {
            private final double radius;

            Circle(double radius) {
                this.radius = radius;
            }

            @Override
            double area() {
                return Math.PI * (radius * radius);
            }
        }

        // 새로운 타입(Triangle) 추가 시
        // Figure 계층 하위 클래스만 추가하면 됨
        static class Triangle extends Figure {
            private final double base;
            private final double height;

            Triangle(double base, double height) {
                this.base = base;
                this.height = height;
            }

            @Override
            double area() {
                return 0.5 * base * height;
            }
        }
    }
}

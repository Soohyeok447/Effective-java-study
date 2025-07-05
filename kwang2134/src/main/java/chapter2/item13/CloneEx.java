package chapter2.item13;

import java.util.ArrayList;
import java.util.List;

public class CloneEx {

    /**
     * Cloneable 인터페이스와 Object.clone()
     * Cloneable 인터페이스는 마커 인터페이스로 메서드가 존재하지 않음
     * Cloneable 을 구현한다 해도 clone() 메서드가 자동으로 생기지 않음
     * 즉 Cloneable 메서드를 구현해도 Object.clone()을 통해 클론이 생성됨
     */
    interface Cloneable {
        // 메서드 존재 x
    }

    /**
     * Object에 구현된 clone 메서드
     * 내부 구현으로 Cloneable 인터페이스를 구현하지 않았으면 예외를 던지도록 구현되어 있음
     * Cloneable 인터페이스는 단순 마킹만 해주고 실제 클론 처리는 Object에서 수행
     * clone 메서드는 native 메서드로 메모리 레벨에서 직접 객체를 복사함 -> 생성자 호출이 일어나지 않음
     *
     * 설계 이유
     * 1. 명시적 의도 표현
     *  - 인터페이스를 통해 clone 에 의해 복사될 수 있음을 표시
     * 2. 무분별한 객체 복사 방지
     *  - clone 메서드의 경우 생성자를 호출하지 않고 객체를 생성할 수 있게 되는 것으로 이는 매우 위험한 동작
     *  - 개발자가 의도적으로 허용해야만 작동하도록 설계
     * 3. 기존 코드와 호환성 유지
     *  - Object 클래스에 clone() 메서드가 존재하기 떄문에 새로운 인터페이스에 clone() 메서드를 추가하면 충돌 가능성이 있음
     */
    protected native Object clone() throws CloneNotSupportedException;

    /**
     * 잘못된 clone 구현으로 인한 문제
     *
     * 얕은 복사 문제
     * 클래스 내 객체를 참조 값 그대로 복사
     * clone을 통해 별도의 객체가 되었으나 내부 필드가 같은 곳을 참조하게 되는 문제
     */
    class Stack implements Cloneable {
        private Object[] elements;
        private int size;
        private static final int INITIAL_CAPACITY = 16;

        /*@Override
        protected Stack clone() {
            try {
                // 문제 발생 지점
                // 얕은 복사로 인해 clone으로 생성된 새로운 객체도 같은 elements의 참조 값을 사용하게 됨
                // 원본과 clone은 서로 다른 객체지만 내부 필드인 elements가 동기화(?) 되어 있는 문제
                return (Stack) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
        */

        @Override
        protected Stack clone() {
            try {
                Stack clone = (Stack) super.clone();
                // 깊은 복사를 통해 독립적으로 만들어 해결
                clone.elements = elements.clone();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * final 키워드를 사용하며 생기는 충돌 문제
     * 깊은 복사를 통해 clone과 원본 객체를 독립적으로 유지 시켜야 함
     * final로 선언된 필드는 재할당 불가로 충돌 발생
     */
    class Problem implements Cloneable {
        private final List<String> ls;

        public Problem() {
            this.ls = new ArrayList<>();
        }

        @Override
        protected Problem clone() {
            try {
                Problem clone = (Problem) super.clone();
                // final 필드는 재할당 불가
                //clone.ls = new ArrayList<>();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    /**
     * 상속과 clone에서 발생하는 문제
     * Parent.clone()을 호출하면 Child 타입이 아닌 Parent 타입 반환
     * 또한 childData의 깊은 복사도 이루어지지 않음
     */
    class Parent implements Cloneable {
        protected int val;

        @Override
        protected Parent clone(){
            try {
                return (Parent) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    class Child extends Parent {
        private List<String> childData = new ArrayList<>();

        // 상속을 받는 자식 타입에서 재정의 하여 사용 필요
        @Override
        protected Child clone() {
            Child clone = (Child) super.clone();
            clone.childData = new ArrayList<>(childData);
            return clone;
        }
    }

    /**
     * clone 대신 권장 방법
     *
     * 복사 생성자
     * clone 메서드 대신 객체를 받는 복사 생성자를 만들어 복사
     * 값 자체를 복사하여 할당하기 때문에 clone 보다 안전하고 명확함
     *
     * 정적 팩토리 메서드
     * 복사를 수행하는 정적 메서드를 제공하는 방법
     */
    static class Person {
        private String name;
        private int age;
        private List<String> hobbies;

        public Person(String name, int age, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.hobbies = new ArrayList<>(hobbies);
        }

        // 복사 생성자
        public Person(Person original) {
            this.name = original.name;
            this.age = original.age;
            this.hobbies = new ArrayList<>(original.hobbies);
        }

        // 정적 팩토리 메서드
        public static Person copyOf(Person original) {
            return new Person(original.name, original.age, new ArrayList<>(original.hobbies));
        }
    }

}

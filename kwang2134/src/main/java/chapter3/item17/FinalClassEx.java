package chapter3.item17;

import java.util.List;

public class FinalClassEx {
    /**
     * 클래스를 불변으로 만드는 5가지 규칙
     * - 객체 상태를 변경하는 메서드(변경자)를 제공하지 않는다
     * - 클래스를 확장할 수 없도록 한다
     * - 모든 필드를 final로 선언한다
     * - 모든 필드를 private로 선언한다
     * - 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다
     */

    final class User {
        // 모든 필드를 final로 선언한다
        private final String name;
        private final int age;
        // 자신 외에는 내부 가변 컴포넌트에 접근할 수 없도록 한다
        private final List<String> hobbies;

        public User(String name, int age, List<String> hobbies) {
            this.name = name;
            this.age = age;
            // 방어적 복사
            this.hobbies = List.copyOf(hobbies);
        }

        // 객체 상태를 변경할 수 없는 메서드(변경자)를 제공하지 않는다

        public List<String> getHobbies() {
            /**
             * 만약 필드에 리스트가 불변이 아니라면 외부에서 변경할 수 없게 불변리스트로 변환하여 반환
             * 생성자에서 이미 불변 리스트로 초기화를 했기 때문에 불변 리스트 상태를 그대로 반환
             */
            return hobbies;
        }
    }
}

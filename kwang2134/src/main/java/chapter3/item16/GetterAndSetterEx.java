package chapter3.item16;

public class GetterAndSetterEx {

    /**
     * public으로 내부 필드를 선언하는 경우
     * user.name 과 같이 직접 필드 접근으로 사용하게 됨
     * 그럴경우 클래스의 데이터 필드에 직접 접근할 수 있어 캡슐화의 이점을 제공할 수 없음
     */
    class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    /**
     * 접근자 메서드 (getter, setter)를 사용하여 직접 필드 접근 대신 메서드를 통해 접근
     * 클래스는 접근자 메서드를 제공함으로써 클래스 내부 표현 방식을 언제든 바꿀 수 있음
     */
    class UserPrivate {
        private String name;
        private int age;

        public UserPrivate(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}

package item2;

public class UserWithBuilder {
    private String name;
    private int age;
    private String gender;

    /**
     * Builder 패턴
     * 클래스 내부에 빌더 클래스를 두고 필수 매개변수만으로 생성자를 호출해 빌더 객체를 얻음
     * 그 다음 빌더 객체가 제공하는 세터 메서드로 원하는 선택 매개변수 설정
     */
    static class Builder {
        // 필수 매개변수
        private final String name;

        // 선택 매개변수
        private int age;
        private String gender;

        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserWithBuilder build() {
            return new UserWithBuilder(this);
        }
    }

    private UserWithBuilder(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.gender = builder.gender;
    }
}

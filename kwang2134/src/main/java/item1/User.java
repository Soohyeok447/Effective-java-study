package item1;

public class User {
    private String name;
    private int age;
    private String gender;

    public User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * 1. 이름을 가질 수 있다
     * of: 일반적으로 새 유저를 만드는 것을 명시
     */
    public static User of(String name, int age, String gender) {
        return new User(name, age, gender);
    }

    /**
     * 2. 호출될 때마다 인스턴스를 생성하지 않아도 된다
     *  - 동일한 값이면 같은 인스턴스를 반환하는 캐싱 (싱글톤/ 플라이웨이트)
     *  - 게스르 유저를 얻는 것을 이름을 통해 명시(1. 이름을 가질 수 있다)
     */
    public static final User guest = new User("Guest", 0, "unknown");

    public static User getGuest() {
        return guest;
    }

    /**
     * 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다
     *  - Manager는 User의 하위 타입 + department 필드 추가
     */
    static class Manager extends User {
        private String department;

        public Manager(String name, int age, String gender, String department) {
            super(name, age, gender);
            this.department = department;
        }

        public void manage() {
            System.out.println("매니저용 추가 메서드");
        }
    }

    public static User createManager(String name, int age, String gender, String department) {
        return new Manager(name, age, gender, department);
    }


    /**
     * 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다
     *  - department가 null이 아니면 Manager, null이면 일반 User
     */
    public static User ofType(String name, int age, String gender, String department) {
        if(department != null && !department.isBlank()) {
            return new Manager(name, age, gender, department);
        } else {
            return new User(name, age, gender);
        }
    }

    /**
     * 5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다
     *  - 외부 라이브러리에서 제공하는 User의 하위 타입을 나중에 반환하도록 할 수 있다
     *  - 아래는 인터페이스 기반으로 팩터리 메서드만 정의하고 실제 구현체는 나중에 동적으로 주입
     *
     *  정리: 팩터리 메서드를 선언할 때는 보통 인터페이스나 상위 클래스(User)로 반환 타입을 정의하나
     *       실제 사용 시 User가 아닌 Manager 등 하위 클래스가 반환될 수 있으므로 팩터리 메서드 작성 시에는
     *       하위 클래스가 존재하지 않고 고려하지 않아도 나중에 얼마든지 확장할 수 있음
     */
    interface UserFactory {
        User create(String name, int age, String gender);
    }

    class UserFactoryRegistry {
        private static UserFactory factory = User::new;

        public static void registerFactory(UserFactory newFactory) {
            factory = newFactory;
        }

        public static User create(String name, int age, String gender) {
            return factory.create(name, age, gender);
        }
    }



}

package item3;

public class User_static_factory_singleton {
    /**
     * 정적 팩터리 방식 싱글톤
     * private 생성자로 INSTANCE가 초기화 될 때 딱 한 번만 호출을 보장
     * 생성된 INSTANCE를 private로 외부 접근을 막음
     * 정적 메서드를 통해 동일한 인스턴스 반환
     */
    private static final User_static_factory_singleton INSTANCE = new User_static_factory_singleton();

    private static boolean initialized = false;

    private User_static_factory_singleton() {
        if(initialized) {
            throw new RuntimeException("이미 인스턴스가 생성됨 - 싱글톤 위반");
        }
        initialized = true;
    }

    public static User_static_factory_singleton getInstance() {
        return INSTANCE;
    }
}

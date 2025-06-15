package item3;

import item1.User;

public class User_private_static_final_singleton {
    /**
     * private static final 필드를 통해 정적 인스턴스를 생성하고
     * User_private_static_final_singleton.INSTANCE 를 통해 싱글톤 객체를 얻는 방식
     * private 생성자로 INSTANCE가 생성될 때 한 번의 생성자 호출을 보장
     */
    public static final User_private_static_final_singleton INSTANCE = new User_private_static_final_singleton();

    private static boolean isInitialized = false;

    private User_private_static_final_singleton() {
        if(isInitialized) {
            throw new RuntimeException("이미 인스턴스가 생성됨 - 싱글톤 위반");
        }
        isInitialized = true;
    }
}

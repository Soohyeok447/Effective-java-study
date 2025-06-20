package chapter1.item3;

public enum User_enum_singleton {
    /**
     * enum 방식의 싱글톤
     * 자바의 enum은 기본적으로 싱글톤을 보장
     * 직렬화/역직렬화, 리플렉션 공격에도 안전
     * User_enum_singleton.INSTANCE.doSomething(); 형태로 사용
     */
    INSTANCE;

    public void doSomething() {
        System.out.println("doSomething");
    }
}

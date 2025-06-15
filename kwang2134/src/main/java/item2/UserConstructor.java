package item2;

public class UserConstructor {
    private String name;
    private int age;
    private String gender;

    /**
     * 생성자로 객체를 생성하는 방법
     * 단점
     *  - 파라미터가 많아질수록 복잡해짐
     */
    public UserConstructor(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * 점층적 생성자 패턴
     * 받고 싶은 값으로만 생성하기 위해 종류별 생성자를 다 만드는 방법
     * 단점
     *  - 필드가 많아지면 수 많은 생성자가 필요
     *  - 아래의 {이름, 나이}를 받는 생성자와 {이름, 성별}을 받는 생성자가 파라미터 개수가 똑같아 어떤 생성자가 호출되는지 명시적으로 알기 어려움
     */
    public UserConstructor(String name) {
        this.name = name;
    }

    public UserConstructor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserConstructor(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    /**
     * 자바 빈즈 패턴
     * 빈 객체를 생성 후 setter 를 통해 값을 설정하는 방식
     * 단점
     *  - 객체를 하나 만들기 위해 여러 메서드(setter)를 호출해야 함
     *  - 객체가 완전 생성되기 전에는 일관성이 깨진 상태로 존재하게 됨
     *  - 일관성이 무너지기 때문에 불변으로 생성이 불가능
     */
    public UserConstructor() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

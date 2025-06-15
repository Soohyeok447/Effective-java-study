package item4;


public class UserUtility {

    /**
     * 인스턴스화를 막기 위해 private 생성자 사용
     * 리플렉션이나 추가 호출 발생을 막기 위해 생성자 호출 시 에러 발생
     * 오로지 정적 메서드 제공용 유틸리티 클래스의 경우 사용
     */
    private UserUtility() {
        throw new RuntimeException("생성 불가 클래스");
    }

    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 25;
    }
}

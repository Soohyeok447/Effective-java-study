package chapter1.item6;

import java.util.regex.Pattern;

public class EmailValidator {

    /**
     * 매번 객체를 생성
     * 매번 Pattern.compile() 호출로 새로운 Pattern 객체 생성
     */
    public static boolean isValidEmailBad(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    /**
     * 정적 불변 객체를 미리 생성해두고 재사용 하는 방식
     * 여러 번 사용되는 메서드라면 객체 생성 비용을 줄일 수 있음
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    public static boolean isValidEmailGood(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 불필요한 참조 타입 사용
     * Long 타입 객체의 인스턴스 메서드 사용이 필요하지 않다면 불필요한 박싱으로 인해 성능 저하 발생
     * 매번 sum을 위해 새로운 Long 객체가 오토 박싱으로 생성
     */
    public static long sumBad() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * 불필요한 참조 타입 사용을 제거하고 객체 생성 없이 기본 타입으로 사용
     */
    public static long sumGood() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * 매번 새로운 문자열 객체를 생성
     * 문자열 객체가 생성될 때마다 힙 메모리에 새로운 String 객체가 할당
     */
    public static String createGreetingBad(String name) {
        return new String("Hello, " + name + "!");
    }

    /**
     * 문자열 리터럴 사용
     * 문자열 상수풀에 저장된 기존 객체를 재사용하여 객체 생성 비용을 막음
     */
    public static String createGreetingGood(String name) {
        return "Hello, " + name + "!";
    }
}

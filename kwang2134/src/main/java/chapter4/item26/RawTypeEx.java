package chapter4.item26;

import java.util.ArrayList;
import java.util.List;

public class RawTypeEx {
    /**
     * 로 타입(raw type)이란?
     * -> 제네릭 클래스나 인터페이스의 타입 매개변수를 지정하지 않은 경우
     *    ex) List<E> 가 아닌 List
     *
     * 문제점
     * 1. 타입 안정성 상실
     *    -> 컴파일러가 타입 검사를 못 해 런타임 예외(ClassCastException) 발생 가능
     * 2. 제네릭 도입의 의미를 해침 (컴파일러 안전성 보장 무력화)
     *
     * 원칙
     *  - raw type 사용 금지
     *  - 비한정 와일드 카드 (List<?>) 사용으로 대체 가능
     *  - 클래스 리터럴 (Class 객체)에서는 로 타입 대신 비한정 와일드 카드 사용
     *  - instanceof 검사에서는 로 타입 허용, 보통 캐스팅 시 와일드카드가 더 안전
     */

    // raw 타입 사용 (타입 안전성 깨짐)
    public static void rawType() {
        List rawList = new ArrayList();
        rawList.add("hello");
        rawList.add(111); // 다른 타입도 허용 -> 컴파일러가 막지 않음

        for (Object o : rawList) {
            // String만 있다고 가정후 캐스팅 시 ClassCastException 발생 가능
            String s = (String) o; // 런타임 오류 발생
        }
    }

    // 제네릭 타입 사용 (타입 안정성 보장)
    public static void genericType() {
        List<String> list = new ArrayList();
        list.add("hello");
        // list.add(1); 컴파일 오류 발생

        for (String s : list) {
            // 안전하게 String 만 사용 가능
        }
    }

    // 와일드카드 타입 사용 (타입 안정성 + 유연성)
    public static void wildcard(List<?> list) {
        // list에 어떤 타입이 들어있는지 몰라 추가(add)는 불가(null) 제외 -> 읽기 전용
        // list.add("hello"); 컴파일 오류

        for (Object o : list) {
            // Object 타입으로 안전하게 꺼낼순 있음
        }
    }

    // instanceof 검사에서만 로 타입 허용 -> 제네릭 타입 소거 때문
    public static void instanceofEx(Object o) {
        if (o instanceof List<?>) { // raw 타입 허용 (제네릭 정보는 런타임에 소거)
            List<?> list = (List<?>) o;
        }
    }
}

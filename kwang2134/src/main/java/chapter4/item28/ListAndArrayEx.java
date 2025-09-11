package chapter4.item28;

import java.util.ArrayList;
import java.util.List;

public class ListAndArrayEx {
    /**
     * 배열과 리스트는 타입 안전성 원칙과 동작 방식에서 차이가 있다
     *
     * 1. 배열은 공변(covariant)
     *  - Sub가 Super의 하위타입이면 Sub[]는 Super[]의 하위타입이 된다
     *  - 잘못된 타입을 대입하는 경우는 런타임 예외가 발생
     *
     * 2. 제네릭(리스트 등)은 불공변(invariant)
     *  - List<Super>와 List<Sub>는 아무런 상하 관계가 없다
     *  - 타입 불일치 시 컴파일 오류 발생 -> 강력한 타입 안전성 제공
     *
     * 3. 배열은 런타임에도 자신의 원소 타입을 인식(실체와 reified)
     *  - 잘못된 타입 저장 시 ArrayStoreException 발생
     *  - 제네릭은 타입 정보가 런타임에 소거된다
     *  - 제네릭 컬렉션에는 컴파일 타입에만 타입 체크 가능, 런타임 확인 불가
     *
     * 4. 제네릭과 배열 혼용은 많은 불일치 초래 (컴파일 오류, 경고, 안전성 결여)
     */

    // 배열의 공변성으로 런타임 예외 위험
    public static void badArrayStore() {
        Object[] array = new String[2]; // 컴파일 OK
        array[0] = "hello";
        // array[1] = 123; // 런타임 ArrayStoreException 발생 (타입불일치)
    }

    // 리스트 불공변성으로 컴파일 타임에 타입 안전성 확보
    public static void goodListUsage() {
        List<String> strings = new ArrayList<>();
        strings.add("hello");
        // strings.add(123); // 컴파일 에러: 타입 안전!
        for (String s : strings) {
            System.out.println(s.toUpperCase());
        }
    }

    // 제네릭 생성 불가 예시
    //List<String>[] stringLists = new List<String>[1];

    // 리스트 배열 대체
    public static void listOfListsExample() {
        List<List<String>> listOfLists = new ArrayList<>();
        listOfLists.add(new ArrayList<>(List.of("A", "B")));
    }
}

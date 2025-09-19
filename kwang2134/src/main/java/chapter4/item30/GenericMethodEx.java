package chapter4.item30;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class GenericMethodEx {
    /**
     * 메서드도 제네릭 타입처럼 타임 매개변수로 선언이 가능하다
     * - 한 메서드에서 다양한 타입의 매개변수, 반환값 지원
     * <p>
     * 제네렉 메서드로 만들면
     * 1. 형변환 필요 없이 다양한 타입 지원
     * 2. 컴파일 타임에 타입 안정성 확보
     * 3. API의 명확성과 재사용성 증가
     */

    // 일반 메서드 - 형변환 필요, 타입 안전성 떨어짐
    public static Set unionRaw(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }

    public static void rawUnionEx() {
        Set<String> set1 = Set.of("a", "b");
        Set<String> set2 = Set.of("a", "b");
        Set result = unionRaw(set1, set2); // 형변환 필요
        for (Object o : result) {
            String str = (String) o; // 런타임 오류 위험
        }
    }

    // 제네릭 메서드 - 타입 매개변수 E 선언
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    public static void genericUnionEx() {
        Set<String> set1 = Set.of("a", "b");
        Set<String> set2 = Set.of("c", "d");
        Set<String> result = union(set1, set2); // 타입 안전, 형변환 불필요
        for (String s : result) {
            System.out.println(s);
        }
    }

    // 제네릭 싱글톤 팩토리
    private static final UnaryOperator<Object> IDENTITY_FN = t -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        // 타입 소거 덕분에 모든 타입별로 단일 객체 사용가능
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    public static void singletonFactoryExample() {
        UnaryOperator<String> op1 = identityFunction();
        System.out.println(op1.apply("hello")); // "hello"

        UnaryOperator<Integer> op2 = identityFunction();
        System.out.println(op2.apply(123));     // 123
    }

    // 타입 한정 제네릭 메서드 예시
    public static <T extends Comparable<T>> T max(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException();
        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    public static void boundedMethodExample() {
        List<Integer> nums = List.of(1, 9, 3, 7);
        System.out.println("Max: " + max(nums)); // 9
    }
}

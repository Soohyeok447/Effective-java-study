package chapter4.item31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundedWildcardEx {
    /**
     * 제네릭은 기본적으로 불공변 -> List<Number> != List<Integer>
     * 다양한 타입 인자를 유연하게 처리하려면 한정적 와일드카드 (extends, super) 활용
     * PECS 원칙: 생산자(Producer)는 extends, 소비자(Customer)는 super
     */

    // 생산자 역할: 데이터 읽기, <? extends 타입> 적용
    public static double sumNums(List<? extends Number> nums) {
        double total = 0.0;
        for (Number n : nums)
            total += n.doubleValue();
        return total;
    }

    // 소비자 역할: 데이터 추가/변경, <? super 타입> 적용
    public static void fillWithInts(List<? super Integer> list, int count) {
        for (int i = 0; i < count; i++)
            list.add(i);
    }

    // 한정적 와일드카드 사용 예: 서로 다른 상속 관계의 타입들도 올 수 있게 유연함 제공
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(2.1, 3.5);

        double sum1 = sumNums(intList);    // Integer 리스트도 처리 가능
        double sum2 = sumNums(doubleList); // Double 리스트도 처리 가능
        System.out.println("총합 = " + (sum1 + sum2));

        List<Number> numList = new ArrayList<>();
        fillWithInts(numList, 3); // Number 타입 리스트에 Integer 데이터 추가
        System.out.println("채운 리스트 = " + numList);

        // 소비자 역할: Object 타입 리스트에도 Integer 삽입 가능
        List<Object> objList = new ArrayList<>();
        fillWithInts(objList, 2);
        System.out.println("Object type 리스트: " + objList);

        // 생산자 역할: 읽기만 가능 (쓰기 불가)
        // intList.add(10); // 가능
        // doubleList.add(5.5); // 불가능 (불변 리스트)
        // sumNums(Arrays.asList(10, 20, 30)); // 가능
    }

    // 보조 메서드 활용 -> 와일드카드 타입을 실제 타입으로 바꿀 때
    public static void swapFirstLast(List<?> list) {
        swapHelper(list, 0, list.size()-1);
    }
    private static <T> void swapHelper(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

}

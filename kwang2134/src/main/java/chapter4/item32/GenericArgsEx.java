package chapter4.item32;

import java.util.ArrayList;
import java.util.List;

public class GenericArgsEx {
    /**
     * 가변인수 varargs 는 내부적으로 배열을 사용해서 파라미터를 처리
     * 제네릭 타입과 배열은 궁합이 좋지 않음 -> 배열은 실체화, 제네릭은 소거
     * 가변인수에 제네릭(매개변수화) 타입이 들어오면 자동으로 힙 오염의 가능성이 생김
     */

    /**
     * 유용성 때문에 허용되었으니 안전하지 않은 코드 패턴은 피해야 함
     * 안전할 때만 @SafeVarargs 어노테이션 사용
     */

    // 안전하지 않은 제네릭 가변인수 메서드
    @SafeVarargs // 실제 안전보장 없으면 붙이면 안됨
    static <T> void addToAllLists(T item, List<T>... listArray) {
        // listArray는 T타입 정보가 없는 List[]
        for (List<T> list : listArray) {
            list.add(item);
        }
    }
    // 외부에서 Object[]로 변조할 경우 타입 안전성이 깨짐

    // 안전한 제네릭 가변인수 메서드 -> 외부 노출, 저장 없이 소비만 사용
    @SafeVarargs
    static <T> void printAllFirsts(List<? extends T>... lists) {
        for (List<? extends T> list : lists) {
            if (!list.isEmpty())
                System.out.println(list.get(0));
        }
    }

    // 안전하게 가변인수 대신 리스트 사용
    static <T> List<T> safeToList(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }
}

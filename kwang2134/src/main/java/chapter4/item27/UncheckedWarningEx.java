package chapter4.item27;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UncheckedWarningEx {
    /**
     * 비검사 경고(unchecked warning)란?
     * 제네릭 타입 관련 코드에서 타입 정보가 완전히 확인되지 않아
     * 컴파일러가 '타입 안정성(type safety)'을 보장할 수 없을 때 발생하는 경고
     *
     * 원칙
     * 1. 할 수 있는 한 모든 비검사 경고를 제거
     *    -> 경고 없이 컴파일되면 해당 코드는 타입 안전성이 보장
     * 2. 정말로 제거 불가(라이브러리 구조적 한계 등)인 경우에만
     *    - 타입 안전함을 직접 증명
     *    - @SuppressWarnings("unchecked") 어노테이션으로 감싼다
     *    - 반드시 가능한 좁은 범위(변수, 작은 메서드 등)에 한정
     *    - 경고를 숨겨도 안전하다는 근거를 반드시 주석으로 남길 것
     */

    // 비검사 경고 발생 예시
    public void oldApi() {
        List rawList = new ArrayList(); // raw 타입 비검사 경고 발생
        rawList.add("hello");
        String s = (String) rawList.get(0);
    }

    // 타입 매개변수를 명시하여 경고 제거
    public void generic() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        String s = list.getFirst();
    }

    // 불가피한 비검사 형변환 & 범위 제한 SuppressWarnings 사용
    public <T> T[] toArray(Collection<T> c, T[] a) {
        if (a.length < c.size()) {
            // 배열 복사 시 비검사 형변환 불가피
            // 올바른 형변환임을 주석 남김
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Arrays.copyOf(c.toArray(), c.size(), a.getClass());
            // 해당 변수에만 어노테이션 적용
            return result;
        }
        int i = 0;
        for (T e : c)
            a[i++] = e;
        if (a.length > c.size())
            a[c.size()] = null;
        return a;
    }

    // 어노테이션을 넓은 범위에 적용하면 진짜 에러를 놓칠 수 있음
    @SuppressWarnings("unchecked") // 메서드 전체에 남용 X
    public void badSuppressExample(List list) {
        List<String> stringList = list; // 타입 안전하지 않음
    }
}

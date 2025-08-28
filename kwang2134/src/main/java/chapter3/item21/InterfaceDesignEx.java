package chapter3.item21;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class InterfaceDesignEx {
    /**
     * 자바 8부터 인터페이스에 "default" 메서드를 넣을 수 있음
     * -> 기존 구현 클래스들의 소스 수정을 강제하지 않고 새로운 메서드 추가가 가능
     *
     * 하지만 default 메서드는 안전한 변경이 아님
     * -> 기존 동작이 모든 구현체에 적합하지 않을 수 있음
     * -> 기존 코드에 예기치 못한 버그 발생 가능
     *
     * 인터페이스는 한번 공개하면 쉽게 고칠 수 없는 API 계약이므로 default 메서드까지 포함해서 신중한 설계 필요
     */

    /**
     * 인터페이스 설계 유의점
     * 1. 모든 기존 및 미래의 구현체에 기본 구현이 합리적인지 고민
     * 2. default 메서드는 최소한으로만 제공, 문서화 철저히
     * 3. 변경 가능한 가능성은 "추상 골격 구현(skeletal implementation) 쪽에서 처리하는 편이 안전
     */

    /**
     * 잘못된 default 메서드 예시
     * - removeIf()는 Collection 인터페이스에 자바 8부터 ㅜ가
     * - default 구현으로는 forEach + remove() 조합 제공
     * - 특정 구현체에선 이 기본 구현이 적절하지 않을 수 있음
     */
    interface BadCollection<E> extends Collection<E> {

        // 기존 구현체에 갑자기 추가된 메서드
        default boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Iterator<E> it = iterator(); it.hasNext(); ) {
                if (filter.test(it.next())) {
                    it.remove();  // 일부 구현체에서는 지원하지 않거나 성능 문제 발생 가능
                    removed = true;
                }
            }
            return removed;
        }
    }

    /**
     * 더 나은 설계 방식
     * - 인터페이스 자체는 계약만 정의
     * - 실제 구현 기본 동작은 골격 구현 클래스에서 제공
     */
    interface SafeCollection<E> extends Collection<E> {
        boolean removeIf(Predicate<? super E> filter);
    }

    abstract static class AbstractSafeCollection<E> extends AbstractCollection<E> implements SafeCollection<E> {
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Iterator<E> it = iterator(); it.hasNext(); ) {
                if (filter.test(it.next())) {
                    it.remove();
                    removed = true;
                }
            }
            return removed;
        }
    }
}

package chapter3.item19;

import java.util.ArrayList;
import java.util.Collection;

public class InheritanceEx {

    /**
     * 상속용으로 설계되지 않은 클래스를 상속하면 하위 클래스가 깨지기 쉬움
     * 클래스 내부 구현 방식에 지나치게 의존하는 경우, 향후 상위 클래스 변경 시 하위 클래스가 오작동 가능
     *
     * 상속을 허용할 클래스라면
     * 1. 문서(API)에서 어떤 메서드를 오버라이딩할 수 있는지 명확시 명시해야 함
     * 2. 메서드 호출 순서와 결과(호출하는 다른 메서드까지)도 기록해야 함
     * 3. 클래스 내부 훅(hook) 메서드를 잘 설계하여 하위 클래스가 원하는 동작을 안전하게 확장할 수 있도록 해야 함
     *
     * 상속을 고려하지 않았다면 기본적으로 final 클래스로 만들어 상속을 금지하는 것이 안전
     */

    /**
     * 상속을 허용하지 않을 경우
     *
     * - final 클래스 선언
     * - 생성자 private 정의 후 정적 팩토리 메서드 사용
     */


    // 잘못된 상속 설계 예시 - 내부적으로 다른 메서드를 호출하나 문서에 기재하지 않은 경우
    static class WrongList<E> extends ArrayList<E> {
        private int addCount = 0;

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }

        /**
         * ArrayList의 addAll()은 내부적으로 add()를 호출
         * 개발자는 문서에 이런 내부 동작이 쓰여 있지 않음
         * addAll() 호출하면 addCount 이중 증가 -> 의도치 않은 동작 발생
         */
    }


    // 상속을 의도적으로 허용하는 설계 방식
    // protected Hook 메서드 제공
    // 오버라이딩 가능한 지점을 문서화
    static class CorrectList<E> extends ArrayList<E> {
        /**
         * add가 호출될 때마다 동작할 hook 메서드
         * 하위 클래스가 원하는 동작을 정의하고 끼워 넣을 수 있음
         */
        protected void onAdd(E e) {
            // 기본 구현 없음
        }

        @Override
        public boolean add(E e) {
            onAdd(e); //Hook 메서드 호출
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            for (E e : c) {
                onAdd(e);
            }
            return super.addAll(c);
        }

        /**
         * 하위 클래스는 hook 메서드를 안전하게 오버라이딩만 하면 됨
         */
    }

    static class ChildList<E> extends CorrectList<E> {
        @Override
        protected void onAdd(E e) {
            System.out.println("추가된 원소: " + e);
        }
    }
}

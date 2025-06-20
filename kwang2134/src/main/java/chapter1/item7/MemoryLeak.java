package chapter1.item7;

import java.util.EmptyStackException;

public class MemoryLeak {
    /**
     * 사용자 입장에서는 다 썼지만 가비지 컬렉터는 알 수 없는 부분에서 메모리 누수가 발생
     */

    static class Stack {
        private Object[] elements;
        private int size;
        private static final int DEFAULT_CAPACITY = 16;

        public Stack() {
            elements = new Object[DEFAULT_CAPACITY];
        }

        /**
         * size 값을 줄이며 현재 스택의 top에 존재하는 객체를 반환
         * 배열의 크기가 16이고 가득찼던 (15번 인덱스까지 객체가 존재했던) 상태에서 pop 수행 시
         * 배열의 참조 자체는 제거되지 않고 있음
         * 예를 들어 popBad()를 3 번 사용해 top 을 13 까지 내리는 경우
         * 자료구조 특성상 위에서 부터 3개의 객체가 빠져나가 존재하지 않는 상태여야 하지만
         * 실제 배열 내에는 참조가 그대로 존재하고 자료구조의 특성을 살려 접근만 하지 못하게 한 상태
         * 실제 사용되지 않는 비활성 영역이지만 참조 값 유지로 메모리 누수 발생
         */
        public Object popBad() {
            if(size == 0)
                throw new EmptyStackException();
            return elements[--size];
        }

        /**
         * 실제 사용되지 않는 비활성 영역에 존재하는 객체 참조를 해제하여 메모리 누수를 막음
         */
        public Object popGood() {
            if(size == 0)
                throw new EmptyStackException();
            Object element = elements[--size];
            elements[size] = null;
            return element;
        }


    }
}

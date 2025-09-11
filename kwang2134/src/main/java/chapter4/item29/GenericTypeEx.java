package chapter4.item29;

import java.util.EmptyStackException;

public class GenericTypeEx {
    /**
     * Object 기반(raw type) 타입보다 제네릭 타입이 안전하고 편함
     * 제네릭 타입을 사용하면
     *  1. 컴파일 타임 타입 안정성 확보
     *  2. 불필요한 형변환 없이 사용 가능
     *  3. API 사용이 명확해지고 버그 발생 가능성 감소
     */


    // Object 기반 타입 스택 -> 클라이언트가 직접 형변환 해야하고 안전하지 않음
    static class ObjectStack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public ObjectStack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0) throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null;
            return result;
        }
    }

    // 사용 예시 (런타임 예외 위험)
    public void objectStackExample() {
        ObjectStack stack = new ObjectStack();
        stack.push("hello");
        stack.push(123);
        String s = (String) stack.pop(); // 형변환 필요, 실패시 ClassCastException 가능
    }

    // 제네릭 타입으로 개선
    static class GenericStack<E> {
        private E[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        // 제네릭 배열 생성에는 형변환 필요 (바로 E[] 생성을 막음)
        @SuppressWarnings("unchecked")
        public GenericStack() {
            elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(E e) {
            elements[size++] = e;
        }

        public E pop() {
            if (size == 0) throw new EmptyStackException();
            E result = elements[--size];
            elements[size] = null;
            return result;
        }
    }

    // 사용 예시 (타입 안전)
    public void genericStackExample() {
        GenericStack<String> strStack = new GenericStack<>();
        strStack.push("hello");
        // strStack.push(123); // 컴파일 에러!
        String s = strStack.pop(); // 형변환 불필요, 타입 안전
    }
}

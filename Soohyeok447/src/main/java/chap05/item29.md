# item29. 이왕이면 제네릭 타입으로 만들라

### 선요약

새로운 타입을 설계할 때, 그 타입이 객체를 다룬다면 처음부터 제네릭 타입으로 만들것

### 제네릭 타입 만드는 법

#### 제네릭 타입 없이 구현한 Stack class

```java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object [DEFAULT_INITIAL_CAPACITY];
    }

    public void push (Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size = 0)
        throw new EmptyStackException();

        Object result = elements[--size];
        elements[size]= null; // 다 쓴 참조 해제
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    ｝

    private void ensureCapacity() i
        if (elements.length = size)
        elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}

```

-   이 Stack을 이용하는 클라이언트는 스택에서 꺼낸 객체를 형변환해야하는데 (Object) 이 때, 런타임 오류가 날 위험이 있음
-   클래스 선언에 타입 매개변수를 추가함으로써 제네릭 클래스로 만들 수 있음

#### 타입 매개변수를 추가한 Stack class

```java
public class Stack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new E[DEFAULT_INITIAL_CAPACITY];
    }

    public void push (E e) {
        ensureCapacity();
        elements [size++] = e;
    }

    public E pop() {
        if (size == 0) throw new EmptyStackException();

        E result = elements[--size];
        elements[size]= null; // 다 쓴 참조 해제
        return result;
    }

    ...// isEmpty와 ensureCapacity 메서드는 그대로
}
```

-   이 때, E같이 실체화가 불가한 타입으로는 배열을 만들 수 없다는 오류가 하나 발생함
-   제네릭으로 전환할 때 가장 까다로운 부분은 제네릭 배열을 생성할 수 없다는 점 (`new E[]` 같은 코드는 문법적으로 금지됨)

### `new E[]` 문제 해결법 2가지

#### 해결책 1: Object 배열 생성 후 형변환

```java
public class Stack<E> {
    private E[] elements;
    private int size = 0;

    public Stack() {
        // 비검사 형변환 경고 발생
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }
    // ...
}
```

-   이 코드는 "비검사 형변환" 경고를 발생시킴. 하지만 이 방식은 타입 안전성이 보장되므로, @SuppressWarnings("unchecked") 애너테이션으로 경고를 숨기는 것이 정당함
-   elements 배열은 private 필드이며, 외부로 노출되지 않기 때문에 안전이 보장됨
    -   오직 push()로만 E타입의 인스턴스가 저장되기 때문 (원소 타입이 항상 E타입임이 보장됨)

#### 이런식으로 경고 제거 가능

```java
@SuppressWarnings("unchecked")
public Stack() {
    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
}
```

#### 해결책 2: elements 필드를 Object[]로 유지

elements 필드의 타입을 Object[]로 두고, 원소를 꺼내는 pop 메서드에서만 E 타입으로 형변환하는 방법

```java
public class Stack<E> {
    private Object[] elements;
    // ...

    public E pop() {
        // ...
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];
        return result;
    }
}
```

-   이 방법도 가능은한데 배열 필드 자체가 E[]이 아니라서 가독성이 떨어짐
-   형변환 코드가 여러 메서드에 흩어질 수 있기 때문에 첫 번째 방법을 더 권장함

### 정리

-   클라이언트 코드의 타입 안전성과 편의를 위해 새로운 타입은 제네릭으로 만들것 (형변환 없이도 사용할 수 있도록 하라)
-   Object를 직접 사용하는 것보다 제네릭이 항상 더 나음

### 제네릭이란?

제네릭은 코드에서 자료형(데이터 타입)을 미리 정하지 않고, 나중에 필요한 자료형을 넣을 수 있게 만드는 방법이다. 제네릭을 사용하면, 어떤 자료형이든 코드에 재사용할 수 있다.

### 예시: `Stack` 클래스

우리가 다루는 `Stack` 클래스는 책 더미처럼 데이터를 쌓아 올리고, 나중에 하나씩 꺼내 쓰는 자료구조이다. 데이터를 저장했다가 마지막에 넣은 데이터를 제일 먼저 꺼내는 방식이다.

### 제네릭을 사용하는 이유

기존에는 `Object`라는 자료형을 사용해서 어떤 자료형이든 담을 수 있었다. 그런데 `Object`로 데이터를 다루면 나중에 값을 꺼내 쓸 때 어떤 자료형인지 일일이 확인해야 했다!! (잘못하면 프로그램 오류 발생) 그래서 `Stack` 같은 자료구조를 제네릭으로 만들면 어떤 자료형을 쓸지 미리 정해놓고, 그 자료형에 맞게 데이터를 안전하게 다룰 수 있다!!

### 제네릭을 사용한 `Stack` 클래스

`Stack` 클래스에 제네릭을 추가해보자. (제네릭 타입으로 `E`를 사용)

```java
public class Stack<E> {
    private E[] elements;  // 제네릭 배열
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];  // Object 배열을 제네릭 배열로 변환
    }

    public void push(E e) {  // E 타입 데이터를 스택에 추가
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {  // 스택에서 E 타입 데이터를 꺼냄
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;  // 참조 해제
        return result;
    }

    // 스택이 비어있는지 확인
    public boolean isEmpty() {
        return size == 0;
    }

    // 용량을 확인하고 필요하면 배열 크기를 늘림
    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}
```

1. 제네릭 배열 생성:
    - `E[]`로 배열을 만들고 싶은데, 컴파일러는 우리가 어떤 타입의 배열을 만들지 알 수 없어서 안전하지 않다고 경고한다. 그래서 배열을 만들 때는 `Object[]`로 만들고, 나중에 제네릭 타입으로 변환한다.
2. push 메서드:
    - `push(E e)`는 스택에 데이터를 추가하는 함수이다. 데이터를 배열에 넣을 때 제네릭 타입인 `E`로 지정된 타입만 넣을 수 있다.
3. pop 메서드:
    - `pop()`은 스택에서 데이터를 꺼내는 함수이다. 제네릭 덕분에 데이터를 꺼낼 때 형변환 없이 바로 꺼낼 수 있다.
- 왜 제네릭이 유용할까?
    
    제네릭을 사용하면 스택에 들어가는 데이터 타입을 미리 정할 수 있어서 나중에 타입 오류가 나는 걸 막아준다. 예를 들어, 문자열만 들어갈 수 있는 스택을 만들면, 다른 타입(숫자 같은)을 넣으려고 할 때 컴파일 단계에서 오류를 알준다!
    

### 문제: 제네릭 배열 생성의 어려움

우리가 `Stack`을 제네릭으로 만들 때 제네릭 배열을 생성하는 부분에서 문제가 생긴다. 왜냐하면 자바에서 제네릭 타입으로 배열을 직접 만들 수 없기 때문!!

```java
elements = new E[DEFAULT_INITIAL_CAPACITY];  // 컴파일 오류 발생
```

왜 이런 문제가 생길까?

- 제네릭은 컴파일할 때 타입 정보를 확인하는데, 런타임에는 이 타입 정보가 사라진다(소거). 그래서 컴파일러는 런타임에 제네릭 타입이 뭔지 알 수 없기 때문에, 제네릭 타입으로 배열을 만들 수 없다고 경고한다.

### 해결 방법 1: `Object` 배열을 사용하고 형변환하기

첫 번째 해결책은 제네릭 배열을 `Object[]`로 만들고 형변환하는 방법이다. `E[]` 배열을 만들려고 할 때, 먼저 `Object[]`로 배열을 만들고 나중에 그 배열을 제네릭 타입으로 형변환하자.

```java
elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];  // Object 배열을 E[]로 형변환
```

**문제점과 경고**
이 방법은 컴파일러가 안전하다고 판단하지 못해 경고가 뜰 수 있다. 하지만, 우리가 배열에 저장하는 데이터는 항상 제네릭 타입인 `E`이기 때문에 직접 안전성을 확인하고, 경고를 무시해도 되는 경우가 있다. (이렇게 할 때는 경고를 숨기기 위해 `@SuppressWarnings("unchecked")` 애너테이션을 사용)

```java
@SuppressWarnings("unchecked")  // 비검사 형변환 경고를 무시
public Stack() {
    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
}
```

### 해결 방법 2: `List<E>` 사용하기

두 번째 해결책은 배열 대신 `List<E>`를 사용하는 것이다. 자바의 `List`는 제네릭을 제대로 지원하니까, 배열 대신 리스트를 사용하면 타입 안전성을 유지할 수 있다. 이 방법을 사용하면 컴파일러 경고가 없다.

```java
private final List<E> elements;

public Stack() {
    elements = new ArrayList<>(DEFAULT_INITIAL_CAPACITY);  // List를 사용해 배열 대신 저장
}
```

그리고 `push()`나 `pop()` 메서드에서 배열을 사용하는 대신 리스트를 사용해 데이터를 추가하고 꺼내면 된다.

```java
public void push(E e) {
    elements.add(e);  // 리스트에 데이터를 추가
}

public E pop() {
    if (elements.isEmpty()) {
        throw new EmptyStackException();
    }
    return elements.remove(elements.size() - 1);  // 리스트에서 마지막 데이터를 꺼냄
}
```

- **쉬운 설명!**
    
    ### 해결 방법 1: 다른 상자(Object)로 만들고 변신시키기
    
    첫 번째 방법은 상자를 다른 모양의 상자(Object)로 먼저 만든 다음에, 우리가 원하는 제네릭 상자로 변신시키는 방법이다. 예를 들어, 사과만 넣고 싶은 상자를 못 만들지만, 일단 "어떤 물건이든 넣을 수 있는 상자(Object)"를 만들고, 나중에 "이 상자는 사과만 넣을 거야!"라고 변신시킬 수 있다.
    
    ```java
    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];  // 일반 상자를 사과 상자로 변신!
    ```
    
    하지만 이 방법은 "경고"가 뜰 수 있어. 컴퓨터가 "정말 안전한지 확신할 수 없어!"라고 알려주는 것. 하지만 우리가 "이 상자에는 정말 사과만 넣을 거니까 괜찮아"라고 알고 있다면, 그 경고를 무시해도 된다.
    
    ```java
    @SuppressWarnings("unchecked")  // 경고를 무시!
    public Stack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }
    ```
    
    ### 해결 방법 2: 리스트(List) 사용하기
    
    두 번째 방법은 배열 대신 리스트를 사용하는 거야. 배열과 리스트는 둘 다 물건을 넣을 수 있는 상자지만, 리스트는 제네릭을 쉽게 사용할 수 있어. 그래서 배열 대신 리스트로 상자를 만들면 안전하고 경고도 없다!
    
    ```java
    private final List<E> elements;
    
    public Stack() {
        elements = new ArrayList<>(DEFAULT_INITIAL_CAPACITY);  // 리스트로 상자를 만듦
    }
    ```
    
    그리고 사과를 넣고 꺼낼 때도 리스트를 사용해서 안전하게 할 수 있다.
    
    ```java
    public void push(E e) {
        elements.add(e);  // 리스트에 사과 넣기
    }
    
    public E pop() {
        return elements.remove(elements.size() - 1);  // 리스트에서 사과 꺼내기
    }
    ```
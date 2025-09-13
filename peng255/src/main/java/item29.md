## 29. 이왕이면 제네릭 타입으로 만들라
### 요약 정리

- 제네릭이 아닌 타입 (Object 기반 타입)은 안전성 문제 // 매번 캐스팅해야 한다는 문제 / 런타임오류 발생가능 등의 문제가 있다
- 제네릭 타입으로 만들면

  컴파일 시점에 타입을 보장할 수 있고

  불필요한 캐스팅이 사라지며

  코드 가독성과 재사용성이 높아진다.

- 이미 Object 기반 타입이 있다면, 소스 호환성도 거의 깨지지 않으니 기존 타입도 언제든 제네릭으로 변경하는 것이 좋다.

### Object (제네릭 x) 로 만든 예시

```java
public class Stack {
    private Object[] elements;
    private int size = 0;

    public void push(Object e) { elements[size++] = e; }
    public Object pop() { return elements[--size]; }
}

// 사용 예
Stack s = new Stack();
s.push("hello");
s.push(42);

String str = (String) s.pop(); // 매번 캐스팅 필요
```

예시처럼 String, Integer 등이 들어갈 수 있는데…

혹시나 잘못된 캐스팅이 수행되면 런타임에 ClassCastException이 발생할 수 있다.

→ 되도록 쓰지 말자…

### 제네릭 타입으로 개선한 예시

```java
public class Stack<E> {
    private Object[] elements;
    private int size = 0;

    public void push(E e) { elements[size++] = e; }
    @SuppressWarnings("unchecked")
    public E pop() { return (E) elements[--size]; }
}

// 사용 예
Stack<String> s = new Stack<>();
s.push("hello");
// s.push(42); // 컴파일 에러!

String str = s.pop(); // 캐스팅 필요 없음, 안전함
```

Stack 객체를 만드는 시점에 타입을 하나로 고정하기 때문에 의도한 타입이 아닌 다른 타입이 들어갈 걱정 xㅌ

하나의 타입으로 통일 → 값을 꺼낼때 처음부터 (E)로 캐스팅해서 돌려줄 수 있다 → 런타임에러 x
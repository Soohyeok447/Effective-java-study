객체를 사용한 후에는 명시적으로 참조를 해제하지 않으면 메모리 누수가 발생할 수 있다. 자바의 가비지 컬렉터는 사용되지 않는 객체를 자동으로 메모리에서 해제하지만, 여전히 참조가 남아 있는 객체는 해제되지 않는다.

### **다 쓴 객체 참조를 해제하지 않아 메모리 누수 발생**

```java
public class Stack {
    private Object[] elements;
    private int size = 0;

    public Stack(int capacity) {
        elements = new Object[capacity];
    }

    public void push(Object item) {
        elements[size++] = item;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }
}
```

이 코드에서 `pop()` 메서드는 스택에서 객체를 꺼내지만, 꺼낸 후에도 배열에 객체 참조가 남아 있어 메모리 누수가 발생. 꺼낸 후에는 참조를 명시적으로 해제해주어야 한다.

<aside>

**해결**

```java
public Object pop() {
    if (size == 0)
        throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // 다 쓴 객체 참조 해제
    return result;
}
```

</aside>

→ 스택에서 객체를 꺼낸 후 배열에서 해당 참조를 null로 명시적으로 해제하여, 가비지 컬렉터가 이를 수거할 수 있게 한다.

### 정리

> 자바의 가비지 컬렉터는 참조가 없는 객체만 회수 : 참조를 유지하고 있는 객체는 메모리에서 해제되지 않기 때문에, 다 쓴 객체의 참조를 해제하여 메모리 누수를 방지해야 한다.
>
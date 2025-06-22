# item7. 다 쓴 객체 참조를 해제하라

- GC가 있다고 해서 메모리 누수가 절대 안 생기는 건 아님
- **필요 없는 객체를 계속 참조하고 있으면 GC의 수거 대상이 되지 않음**
- 사용이 끝난 객체 참조는 **명시적으로 null 처리**하거나 **컬렉션에서 제거**할 것

### 객체를 계속 참고하는 상황
```java
public class Stack {
    private Object[] elements = new Object[100]; // 객체 참조
    private int size = 0;

    public void push(Object e) {
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) throw new EmptyStackException();
        
        return elements[--size]; // null 처리를 안해서 요소는 줄었지만 참조는 남아 있음
    }
}
```
- pop()을 해도 elements 배열에는 내부적으로 이전 객체 참조가 남아 있음
  - 프로그램에서 Stack을 사용하지 않더라도 Stack이 다 쓴 참조(obsolete reference)를 여전히 가지고 있음
  - 가바지 콜렉터가 스택에서 꺼내진 객체들을 회수하지 않음
- GC는 이 객체를 "아직 사용 중"이라고 판단하고 회수하지 않음
- 객체 참조 하나를 살려두면 GC는 그 객체를 포함해서 그 객체가 참조하는 모든 객체들을 회수하지 못함 (스노우볼 굴러감)
- 결국 쌓여서 메모리 누수 발생

```java
public Object pop() {
    if (size == 0) throw new EmptyStackException();
    
    Object result = elements[--size];
    
    elements[size] = null; // 참조 해제 (GC 회수가 가능해짐)
    
    return result;
}
```

### 무조건 null 처리하지 마라 – scope 밖으로 밀어내는 게 더 좋다
- 지역 변수의 참조는 해당 범위를 벗어나면 자동으로 사라짐
```java
void process() {
    BigObject obj = new BigObject(); // 지역 변수로 객체 참조
    obj.doSomething();
    // obj = null; 이렇게 안 해도 scope 끝나면 GC 대상 됨
}
```
- 괜히 모든 객체를 null로 만드는 데 혈안이 되지않아도 됨
- 참조 자체가 scope에서 사라지게 구조화하는 게 더 안전하고 깔끔함

### null 처리는 자기 메모리를 직접 관리하는 클래스만
#### 자기 메모리 관리 클래스?
- 컬렉션, 배열, 큐, 스택처럼 내부에 **객체 참조를 직접 유지**하는 구조 
- 내부 배열이나 리스트에 사용자 객체를 담고 유지하는 구조 
- 해당 구조가 객체를 참조하고 있으면 외부에서 아무리 scope를 벗어나도 GC 대상이 아님 
- 따라서 이 경우는 명시적인 null 처리 or remove() 가 예외적으로 꼭 필요함

### 캐시도 메모리 누수의 주범
- 객체를 Map 등에 저장하고 사용이 끝난 후에도 제거하지 않으면 계속 남아있음
```java
Map<String, Object> cache = new HashMap<>();
cache.put("user:1", new User("Soohyeok", 28, "+82")); // 사용 안 해도 계속 유지됨
```

- WeakHashMap를 써서 캐시를 구현하면 됨
  - 키가 GC되면 항목도 자동 제거
```java
Map<Object, String> cache = new WeakHashMap<>();
```

### 리스너, 콜백도 메모리 누수 주범
- 어떤 객체가 리스너나 콜백에 자신(this)을 등록하고 
- 그 후 명시적으로 제거하지 않으면, 해당 객체는 계속 참조됨 -> GC 대상이 아님


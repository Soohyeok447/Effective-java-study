## 8. finalizer와 cleaner 사용을 피하라

- finalizer와 cleaner는 예측할 수 없고, 성능 저하와 버그, 보안 문제를 일으킬 수 있음 → 자바에서 사용을 피해야 한다.

  자원 해제는 명확하게 직접 처리하는 것이 좋다

- 자원을 명확하게 해제하려면 AutoCloseable을 구현하고, 클라이언트가 명시적으로 close()를 호출하도록 한다.
- 예외 상황에서도 자원이 해제되도록 try-with-resources를 사용한다.
- close() 메서드는 객체가 이미 닫혔는지 상태를 기록하고, 닫힌 후에는 다른 메서드 호출 시 IllegalStateException을 던지도록 구현한다

### 잘못된 예시 (finalizer 사용)

```java
public class Resource {
    @Override
    protected void finalize() {
        System.out.println("자원 해제!");
    }
}
```

- 이 코드는 객체가 가비지 컬렉션될 때 자원을 해제하려고 finalizer를 사용한다.
- 하지만 finalize()가 언제 실행될지, 혹은 실행이 보장되는지도 알 수 없다.
- 자원 해제가 지연되거나 아예 실행되지 않을 수 있다.

### 권장 예시 (**AutoCloseable과 try-with-resources 사용)**

```java
public class Resource implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("자원 해제!");
    }
}

// 사용 예시
try (Resource res = new Resource()) {
    // 자원 사용
}
// try 블록을 벗어나면 자동으로 close()가 호출되어 자원이 즉시 해제된다.
```

- 예외가 발생해도 자원이 안전하게 해제된다.
- 실행 시점이 예측 가능하다.
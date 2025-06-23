자바는 객체가 더 이상 사용되지 않을 때 자동으로 메모리를 회수하는 가비지 컬렉터를 제공한다. 하지만, 객체가 파괴될 때 finalizer나 Cleaner를 통해 특별한 동작을 정의할 수 있다. 문제는, finalizer와 Cleaner는 성능에 악영향을 미치고, 예측 불가능한 동작을 일으킬 수 있다는 점!!

### 왜 finalizer와 Cleaner를 피해야 하는가?

1. **성능 저하** : `finalizer`는 가비지 컬렉션이 발생할 때 실행되기 때문에, 실행 시점이 예측 불가능하고 지연될 수 있다.
2. **신뢰성 문제** : `finalizer`는 정상적으로 실행되지 않을 수 있다. 프로그램이 종료되기 전까지 `finalizer`가 호출되지 않거나, 심지어 호출되지 않은 채로 객체가 파괴될 수 있다.

### 해결 : 명시적인 자원 해제

대부분의 경우, 명시적으로 자원을 해제하는 방식이 더 안전하고 효율적!! 자바에서는 `try-with-resources` 구문을 사용하여 자원을 명시적으로 해제할 수 있다.

### 명시적인 자원 해제

```java
public class FileProcessor {
    public void processFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
```

`try-with-resources` 구문을 사용하면, 자원을 자동으로 해제할 수 있다. `BufferedReader`는 `AutoCloseable` 인터페이스를 구현하고 있기 때문에, `try` 블록이 끝나면 자동으로 자원이 반환

### 정리

> `finalizer`와 `Cleaner`는 성능 문제와 불확실한 동작이 발생하므로 사용을 자제
대신 명시적인 자원 해제나 try-with-resources를 통해 자원을 관리하자
>
## 26. raw 타입은 사용하지 말라

### 요약 정리

- raw type : 제네릭 클래스나 인터페이스를 타입 파라미터 없이 사용하는 것!
    - 예: `List list = new ArrayList();` (`List<String> list`과 다르다)
- **왜 쓰지 말아야 하나?**
    - 타입 안정성 사라짐 → 아무 타입이나 넣을 수 있음

      → 컴파일러가 타입오류를 잡아줄 수 없음

      → 잘못된 타입이 들어가면 나중에 꺼내서 casting 할 때 런타임 에러가 발생한다..

- **Generic을 써야 하는 이유는?**
    - 컴파일 시점에 타입체크 → 버그 미리 방지 가능
    - 명확하게 어떤 타입을 담는지 코드를 문서화할 수 있다

- **예외적으로 raw type을 써도 되는 때가 있다**
    - 클래스 literal 같은 거! (ex. `List.class`)
    - `if (o instanceof List)` 이런 식으로 검사할 떄
    - 하지만 이런 경우 외에는 항상 타입 파라미터를 명시하자

### 잘못된 예시 (raw type 사용)

```java
List list = new ArrayList();  // 로 타입 사용
list.add("hello");
list.add(42);

// 꺼낼 때 뭔지 알 수 없어서 캐스팅 필요
String str = (String) list.get(0); // 괜찮다
String str2 = (String) list.get(1); // 런타임에 ClassCastException 발생!
```

- **문제점**: 컴파일은 통과하지만 실행시 예외 터짐, 코드의 의미도 불명확

### 올바른 예시 (Generic 사용)

```java
List<String> list = new ArrayList<>(); // 타입 명시
list.add("hello");
// list.add(42); // 컴파일 에러 → 미리 방지

String str = list.get(0); // 안전, 캐스팅 불필요
```

- **장점**: 컴파일러가 타입을 체크해주고, 잘못된 사용을 사전에 막아줌
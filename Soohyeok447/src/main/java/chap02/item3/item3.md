# item3. private 생성자나 열거 타입으로 싱글턴임을 보증하라

-   **싱글턴(singleton)**: 인스턴스를 단 하나만 생성할 수 있도록 보장하는 패턴임
-   **2가지 방법 추천**:
    1. `private 생성자 + public static 필드/메서드`
    2. `enum` 타입

### private 생성자 방식

```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis(); // 클래스가 처음 로딩될 때 딱 한 번만 실행됨

    private Elvis() {} // 외부에서 생성 불가, 초기화 할 때 한 번만 호출됨

    public static Elvis getInstance() { return INSTANCE; }

    public void eat() {
        System.out.println("밥은 먹고 하자");
    }
}
```

-   간단하고 getInstance()를 통해 전역 접근이 가능함
-   리플렉션으로 생성자 접근하면 인스턴스 추가 생성이 되긴 함..

### enum 방식

```java
public enum Elvis {
    INSTANCE;

    public void eat() {
        System.out.println("밥은 먹고 하자");
    }
}
```

-   코드가 public 필드 방식과 비슷하지만 더 간결하고 쉽게 직렬화 가능함
-   리플렉션에도 안전함

### 직렬화 / 역직렬화

직렬화 (Serialization)

-   **객체**를 **바이트 스트림**으로 변환하는 과정
-   주로 파일 저장, 네트워크 전송, 캐싱 할 때 사용함

역직렬화 (Deserialization)

-   **바이트 스트림**을 다시 **객체**로 복원하는 과정
-   직렬화된 데이터를 읽어서 원래 객체처럼 다시 사용 가능함

여기서 문제

-   역직렬화할 때, new를 호출하지 않지만 JVM 내부적으로 새로운 객체가 생성됨
    => 싱글턴이 깨진다

---

### 부자연스러워 보여도 대부분 원소가 하나뿐인 enum 방식이 싱글턴을 만드는 가장 좋은 방식임

## 19. 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라

### 요점 정리

- item 18에서 봤듯이 상속은 부모클래스 구현에 종속돼서 깨지기 쉽다
- 그러니 상속을 안전하게 허용하려면 처음부터!! 확장까지 고려해서 설계하고 문서화하는 것이 필요하다
- 상속을 허용하려면
    - `@implSpec`으로 self-use 상세히 문서화하기
    - 생성자에서 오버라이드 가능한 메서드 호출 절대 금지
- 상속을 막으려면
    - 클래스에 final 붙이기
    - private 생성자 + static factory로 설계

### 상속을 고려해서 설계하는 게 어떻게 하는 건데?

**1. self-use를 문서화하기**

   이 메서드가 내부적으로 어떤 overridable 메서드를 언제 호출하는지를 Javadoc에 명시해야 한다.

   ex. `remove()` → 내부적으로 iterator().remove()를 사용합니다

**2. 생성자에서 오버라이드 가능 메서드를 호출하지 말기!!**

    ```java
    class Super {
        public Super() {
            overrideMe(); // ❌ 위험
        }
        public void overrideMe() {}
    }
    
    class Sub extends Super {
        private final Instant instant;
        Sub() { instant = Instant.now(); }
        @Override public void overrideMe() {
            System.out.println(instant); // instant는 아직 초기화 전
        }
    }
    
    public static void main(String[] args) {
        new Sub(); // null 출력 -> 깨짐
    }
    ```

   위 예시에서 Super 클래스의 생성자가 overrideMe()라는 overridable 메서드를 호출하고 있다. overrideMe() 메서드는 Sub 클래스에서 `@Override` 되고 있다

    - 생성자는 자식의 초기화보다 먼저 실행된다! 즉 자식의 필드는 초기화되지 않은 시점이다.

      → 생성자에서는 private, final, static 메서드만 호출해야 안전하다

- 어떤 메서드를 protected로 제공해야 할지 신중히 선택
    - 너무 많이 열면: 내부 구현을 외부에 드러내는 것
    - 너무 적게 열면: 자식 클래스가 제대로 동작하기 힘듦

  → 항상 직접 서브클래스를 작성해 테스트해야 적절한 공개 수준 확인 가능


### 상속을 막는 법과 언제 막아야 하는지

**1. final 선언하기**

    ```java
    public final class Complex { ... }
    ```

**2. 모든 생성자를 private 또는 package-private 처리하고 static factory를 제공하기**

    ```java
    public class Complex {
        private Complex(...) { ... }
        public static Complex valueOf(...) { ... }
    }
    ```


**- 언제 상속을 금지해야 하냐?**
    - 불변 클래스 (예: String, BigInteger…) → 반드시 금지해야 한다
    - 일반 구체 클래스는 대부분 상속 필요없으니 `final`을 붙이자
    - 인터페이스 기반 컴포넌트 (Set, List, Map 등)
        - 상속 금지해도 문제없음
        - 기능 확장을 하고 싶다면 wrapper, Decorator등의 컴포지션으로 대체하자
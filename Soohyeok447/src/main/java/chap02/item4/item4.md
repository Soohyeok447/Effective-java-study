# item4. 인스턴스화를 막으려거든 private 생성자를 사용하라

- 어떤 클래스는 **인스턴스로 만들 목적이 전혀 없음**
- 예: `Math`, `Collections`, `Arrays` 같은 **유틸리티 클래스** 같은 녀석들 (굳이 생성할 필요 X)
- 이런 클래스는 **생성자를 `private`으로 만들어** 인스턴스화를 막아야 함

### private이 없으면요?
- 생성자를 명시하지 않으면 컴파일러가 **자동으로 기본 생성자를 추가**함
- `new` 키워드로 사용자가 인스턴스를 생성할 수 있게 됨 
  - 의도된 것인지 자동 생성된 것인지 구분할 수 없음

### 그래서 private 붙이는 겁니다
- **private 생성자를 추가**해주기만 하면 클래스의 인스턴스화를 막을 수 있음
```java
public class UtilityClass {
    // 생성자를 private으로 막음
    private UtilityClass() {
        // 굳이 던질 필요는 없는데 클래스 내부에서 실수로 생성자 호출 못하게 개발자 실수 방지하는 역할
        throw new RuntimeException("인스턴스화 금지!"); 
    }

    public static void doSomething() {
        // ...
    }
}
```
- 얘는 인스턴스로 생성하지 않을 녀석이라는 의도를 명확하게 표현 가능함
- 생성자가 private이기 때문에 상속이 안된다는 점은 있음
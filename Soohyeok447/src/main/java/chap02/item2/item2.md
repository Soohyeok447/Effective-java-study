# item2. 생성자에 매개변수가 많다면 빌더를 고려해라

-   생성자의 매개변수가 많아지면 호출 코드의 **가독성/안정성**이 심각하게 떨어짐
-   점층적 생성자 패턴이나 자바빈 패턴은 한계가 있음
-   **Builder 패턴**은 복잡한 객체 생성 시 가장 유연하고 명확한 대안임

#### 점층적 생성자 패턴

-   생성자를 계속 늘려가는 어찌보면 무식한 방법..
-   배개변수 순서가 바뀌면 버그 지옥이 발생함
-   인자의 의미를 파악하기 어려움

```java
    NutritionFacts facts = new NutritionFacts(240, 8, 100, 0, 35, 5); // 숫자가 각각 뭐를 뜻하는거지?
```

#### 자바빈 패턴

-   매개변수 없는 기본 생성자로 객체를 생성하고 setter를 사용하는 방식
-   setter를 쓰기 때문에 불변성을 보장할 순 없다 (수정가능성 때문에 필요할 때 적용못함)
-   setter로 모든 인자가 저장되기전에 객체가 사용될 수 있는 위험이 있음

#### 빌더 패턴

-   불변 객체를 안전하게 생성할 수 있음
-   named parameter를 쓰는 것처럼 객체를 생성할 수 있음

```java
public class User {
    private final String name;
    private final int age;

    public static class Builder {
        private String name = "";
        private int age = 0;

        public Builder name(String val) {
            this.name = val;
            return this;
        }

        public Builder age(int val) {
            this.age = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }
}

// 무슨 인자에 어떤 값이 들어가는지 명확함
User user = new User.Builder()
    .name("홍길동")
    .age(28)
    .build();
```

---

### 매개변수가 많다면 빌더 패턴을 쓰는게 낫다.

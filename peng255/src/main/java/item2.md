## 2. 생성자에 매개변수가 많다면 빌더를 고려 
- **매개변수 개수가 많아질 때의 문제점**
    - 각 값의 의미가 무엇인지 헷갈린다
    - 매개변수 개수를 신경써야 한다
    - 타입이 같은 매개변수가 연달아 있으면 찾기 어려운 버그로 이어질 수 있다.

- **빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기 좋다.**
    - 각 계층의 클래스 관련 빌더를 멤버로 정의하고, 추상 클래스는 추상 빌더를, concrete 클래스는 concrete 빌더를 가지게 하는 구조이다.

  ### 예시

  추상 클래스 / 추상 빌더

    ```java
    public abstract class Furniture {
        public enum Material { MDF, SOFTWOOD, HARDWOOD }
    
        final Set<Material> materials;
    
        // 추상 빌더: 제네릭 self 타입 사용
        abstract static class Builder<T extends Builder<T>> {
            EnumSet<Material> materials = EnumSet.noneOf(Material.class);
    
            public T addMaterial(Material material) {
                materials.add(Objects.requireNonNull(material));
                return self();
            }
    
            // 하위 클래스에서 반환 타입을 맞추기 위한 추상 메서드
            protected abstract T self();
    
            public abstract Furniture build();
        }
    
        // 추상 클래스 생성자는 빌더를 받음
        Furniture(Builder<?> builder) {
            this.materials = builder.materials.clone();
        }
    }
    
    ```


**self()** 메서드로 메서드 체이닝을 하위 타입에서도 자연스럽게 사용할 수 있게 한다

- **구체 클래스 / 구체 빌더**

```java
public class Dresser extends Furniture {
    private final int level;

    public static class Builder extends Furniture.Builder<Builder> {
        private int level = 3; // 기본값

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        @Override
        protected Builder self() { return this; }

        @Override
        public Dresser build() {
            return new Dresser(this);
        }
    }

    private Dresser(Builder builder) {
        super(builder);
        this.level = builder.level;
    }

    @Override
    public String toString() {
        return "Dresser(level=" + level + ", materials=" + materials + ")";
    }
}

```

- **Builder**는 상위 빌더를 상속받고, 자기만의 필드(level)와 세터(level())를 가진다
- build()는 실제 Dresser 객체를 생성한다.

- **사용 예시**
```java
Dresser dresser = new Dresser.Builder()
    .addMaterial(Furniture.Material.HARDWOOD)
    .level(5)
    .build();

System.out.println(dresser);
// 출력 예: Dresser(level=5, materials=[HARDWOOD])

```

**addMaterial**(상위 빌더 메서드)와 **level**(하위 빌더 메서드)을 메서드 체이닝으로 자연스럽게 사용한다
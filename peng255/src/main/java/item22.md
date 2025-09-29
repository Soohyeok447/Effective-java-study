## 22. 인터페이스는 타입을 정의하는 용도로만 사용하라

### 요점 정리

- 인터페이스는  “이 객체가 무엇을 할 수 있는지” 타입으로서 역할을 정의하는 용도여야 한다!
- 인터페이스를 만들 때 상수를 모아 두기만을 위한 목적으로 만들면 안된다
- 상수를 담기 위해서 유틸리티 클래스나 enum을 이용하자

### 잘못된 상수 인터페이스(Constant Interface) 패턴

```java
// ❌ 상수를 위해 만든 인터페이스 (지양)
public interface PhysicalConstants {
    static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    static final double BOLTZMANN_CONST = 1.380_648_52e-23;
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```

- **왜 이렇게 쓰면 안됨?**

  이 인터페이스를 구현하면 하위 클래스까지 모든 상수 이름이 scope에 포함되는 일이 생긴다

  "이 타입을 구현한다"는 인터페이스의 의미를 왜곡한다

  내부 구현 디테일이 외부 공개 API로 ‘새나감’ (정보은닉 원칙 위반)

  상수가 필요 없어져도 호환성 때문에 구현을 유지해야 한다
<br/><br/>
- **그러면 상수를 어떻게 공개해야함?**
    - 관련 클래스나 Enum에 넣거나.. 별도의 static 유틸리티 클래스에서 관리할 수 있다.
    - **utility 클래스 예시**

        ```java
        public class PhysicalConstants {
            private PhysicalConstants() {} // 인스턴스화 방지
            public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
            public static final double BOLTZMANN_CONST = 1.380_648_52e-23;
            public static final double ELECTRON_MASS = 9.109_383_56e-31;
        }
        ```

        ```java
        사용할 때는 이렇게 사용한다
        
        import static com.example.PhysicalConstants.*;
        
        public double atoms(double mols) {
            return AVOGADROS_NUMBER * mols;
        }
        ```

      static import를 하면 클래스 이름이 없어도 상수를 쓸 수 있다!

    - **그러면 Enum을 쓰는 경우는?**

      상수들 각각이 의미 있는 값이면 enum을 쓰는 게 더 적절하다. (ex. 단위, 요일, 방향 등)
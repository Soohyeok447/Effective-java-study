## 정수 열거 패턴의 단점

과거에는 **int enum pattern**  이라 해서 **public static final int APPLE_FUJI = 0;**   같은 상수를 사용했다. 이 방식은 여러 단점이 있다.

1. 타입 안전성이 없다.

   서로 다른 그룹의 상수(예: **APPLE_FUJI**  , **ORANGE_NAVEL**  )를 비교하거나 잘못된 값을 전달해도 컴파일 오류가 발생하지 않는다.

2. 의미 없는 숫자 출력

   디버깅하거나 출력할 때 숫자(예: 0, 1)만 보여서 어떤 상수인지 식별하기 어렵다.

3. 순회 불가 및 하드코딩 문제

   **values()**   같은 기능이 없으므로 상수 그룹 전체를 순회하거나 크기를 알기 어렵다.

4. 클라이언트 코드 재컴파일 문제

   상수의 값이 바뀌면 해당 값을 사용하는 모든 코드를 다시 컴파일해야 하며, 그렇지 않으면 잘못된 동작이 발생한다.

5. 문자열 상수 패턴은 더 나쁨

   오타를 컴파일 타임에 잡을 수 없고 비교 비용도 크다.


---

## Enum 타입의 특징

자바의 **enum**  은 단순한 상수가 아니라 **클래스**다.

각 열거 상수는 단 하나의 인스턴스로 존재하며, **public static final**  필드로 노출된다.

사용자가 직접 생성할 수 없으므로 사실상 **Singleton**  패턴의 집합과 같다.

- 서로 다른 enum 간 비교 불가 → 타입 안전 보장

  예: **Apple.FUJI == Orange.NAVEL** 은 컴파일 오류.

- 각 enum은 고유한 네임스페이스를 가진다.

  같은 이름의 상수도 서로 다른 enum 안에서는 충돌하지 않는다.

- 상수 순서나 개수를 변경해도 클라이언트 재컴파일 불필요.

---

## 데이터와 메서드를 가진 Enum

**enum**  은 필드, 생성자, 메서드를 가질 수 있다.

예를 들어 행성 정보를 나타내는 **Planet**   열거형은 질량(mass), 반지름(radius)을 속성으로 가지고, 이 두 값을 이용해 표면중력(surfaceGravity)을 계산할 수 있다.

```java
public enum Planet {
    EARTH(5.975e+24, 6.378e6);
    private final double mass;
    private final double radius;
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }
    public double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }
}
```

이런 식으로 데이터를 상수와 함께 묶어두면 객체지향적으로 표현할 수 있다.

---

## 상수별 메서드 구현

각 enum 상수마다 다른 동작이 필요할 때는 **상수별 메서드 구현(Constant-specific method implementation)** 을 사용한다.

예를 들어 사칙연산을 표현하는 **Operation** 열거형은 다음과 같이 정의할 수 있다.

```java
public enum Operation {
    PLUS { double apply(double x, double y){ return x + y; } },
    MINUS { double apply(double x, double y){ return x - y; } };
    abstract double apply(double x, double y);
}
```

이렇게 하면 새로운 상수를 추가할 때 반드시 **apply** 를 구현해야 하므로 실수를 막을 수 있다.

---

## 전략적 열거 타입 패턴

상수별 메서드 구현은 중복 코드가 늘어날 수 있다.

이 경우 **전략적 열거 타입 패턴(Strategy Enum Pattern)** 을 사용한다.

예를 들어 **PayrollDay**   열거형에서 평일/주말 근무 방식을 **PayType** 이라는 내부 enum에 위임하면, 각 상수의 급여 계산 방식을 깔끔하게 분리할 수 있다 :

```java
enum PayrollDay {
    MONDAY(PayType.WEEKDAY), SATURDAY(PayType.WEEKEND);
    private final PayType payType;
    enum PayType {
        WEEKDAY { int overtimePay(...){...} },
        WEEKEND { int overtimePay(...){...} };
    }
}
```

---

## 요약

- **int enum pattern** 대신 **enum**  을 사용한다.
- **enum** 은 타입 안전하며, 의미 있는 문자열 표현과 순회 기능(**values()**)을 제공한다.
- 상수별 데이터나 행위를 추가할 수 있고, 객체지향적 설계가 가능하다.
- 상수마다 다른 동작이 필요할 경우 상수별 메서드를 쓰고, 일부 상수만 다른 경우에는 전략적 열거 타입 패턴을 사용한다.
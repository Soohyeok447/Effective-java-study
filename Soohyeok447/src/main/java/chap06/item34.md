# item34. int 상수 대신 열거 타입을 사용하라

## 정수 열거 패턴

enum 키워드가 없던 시절에 프로그래머들은 public static final int를 사용해 상수 집합을 표현했음. <br>
=> 정수 열거 패턴

```java

public static final int APPLE_FUJI = 0;
public static final int APPLE_PIPPIN = 1;
public static final int APPLE_GRANNY_SMITH = 2;

public static final int ORANGE_NAVEL = 0;
public static final int ORANGE_TEMPLE = 1;
public static final int ORANGE_BLOOD = 2;
```

### 단점

#### 타입 안전성을 보장할 수 없음

-   eatApple(APPLE_FUJI)라는 메서드가 있다고 해도, eatApple(ORANGE_NAVEL)을 인자로 넘겨도 컴파일러는 오류를 못잡음. (둘 다 숫자 int이기 때문)

-   심지어 APPLE_FUJI == ORANGE_NAVEL이 true가 되면서 전혀 다른 상수임여도 같다고 판단하는 논리 오류가 발생할 수 있음.

#### 표현력이 안 좋음

-   네임스페이스 부재: 이름 충돌을 피하기 위해 APPLE\_ , ORANGE\_ 같은 지저분한 접두사를 붙여야 함.

-   디버깅 어려움: 디버거로 값을 봐도 0, 1 같은 숫자만 보여서 이게 "FUJI"인지 "NAVEL"인지 알기 어려움.

## Enum을 쓰자

자바는 이 모든 문제를 해결하는 **열거 타입(Enum)** 을 제공함.<br> enum은 관련된 상수들을 모아두는 완전한 클래스임.

```java

public enum Apple { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }
```

### 장점

#### 컴파일타임 타입 안정성을 보장함

-   Apple 타입과 Orange 타입은 완벽히 구분됨. <br>
-   eatApple(Apple apple) 메서드에 Orange.NAVEL을 넘기려는 시도는 **컴파일 타임** 에 즉시 에러가 발생됨.
-   Apple.FUJI == Orange.NAVEL 같은 비교는 아예 불가능.

#### 각자의 네임스페이스가 있음

Apple.FUJI, Orange.NAVEL처럼 소속이 명확해져서 이상한 접두사가 필요없음.

#### 싱글턴

열거 타입의 각 상수는 그 자체로 public static final로 선언된 단 하나의 인스턴스라서 JVM이 인스턴스 생성을 보장함<br>
=> 완벽한 싱글턴으로 사용가능.

#### 임의의 메서드나 필드도 추가 가능

-   enum은 단순한 상수가 아니라 클래스인데 그래서 내부에 필드, 생성자, 메서드를 자유롭게 추가해서 상수와 관련된 데이터 및 동작을 함께 캡슐화할 수 있음.

-   toString()이 자동으로 상수의 이름("FUJI", "NAVEL")을 반환해 줌

-   values() 메서드로 모든 상수를 배열로 받아 순회할 수 있음.

## Enum안에 메서드와 필드는 언제 추가하는데?

1. 상수에 연관된 데이터를 저장할 때 (필드 + 생성자)

```java

public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6);
    // ...

    private final double mass;      // 질량 (필드)
    private final double radius;    // 반지름 (필드)

    // 생성자
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    // 필드를 이용한 메서드
    public double surfaceGravity() {
        return mass / (radius * radius);
    }
}
```

2. 각 상수마다 동작이 달라야 할 때 (추상 메서드)

```java

public enum Operation {
    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x - y; }
    };
    // ...

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract double apply(double x, double y);
}
```

## Enum과 switch case문

-   Enum은 switch 문과 함께 사용할 때 매우 깔끔하고 가독성이 좋음.

-   case 문에서 Apple.FUJI가 아닌 FUJI처럼 상수 이름만 사용해야 함.

```java

Apple apple = Apple.FUJI;
String description;

switch (apple) {
    case FUJI:
        description = "부사는 아삭";
        break;
    case PIPPIN:
        description = "피핀은 새콤";
        break;
    case GRANNY_SMITH:
        description = "그래니 스미스는 시다";
        break;
    default:
        throw new AssertionError("알 수 없는 사과: " + apple);
}
```

## 그래서 Enum 타입을 언제 쓰라는거지?

서로 연관된 상수들의 고정된 집합이 필요할 때는 항상 enum을 사용해야 함

#### int 상수를 써야 할 이유는 사실상 없음

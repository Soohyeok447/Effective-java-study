### 문제점

소스 파일 하나에 여러 개의 톱레벨 클래스를 선언하는 것은 위험하다. 컴파일 순서에 따라 어느 클래스를 사용할지 달라질 수 있기 때문이다. 이렇게 하면 의도치 않은 동작을 유발할 수 있으며, 중복 정의에 따른 컴파일 에러를 발생시키기도 한다.

### 예시:

```java
// Utensil.java (잘못된 방법)
class Utensil {
    static final String NAME = "pan";
}

class Dessert {
    static final String NAME = "cake";
}
```

```java
// Dessert.java (잘못된 방법)
class Utensil {
    static final String NAME = "pot";
}

class Dessert {
    static final String NAME = "pie";
}
```

→ Utensil과 Dessert 클래스가 중복 정의되었고, 컴파일 순서에 따라 어떤 클래스가 사용될지 달라질 수 있다. 따라서, 컴파일러가 어느 파일을 먼저 처리하느냐에 따라 결과가 달라질 수 있는 상황이 발생한다.

### 해결 방법

- 톱레벨 클래스는 반드시 하나의 파일에 하나만 선언해야 한다. 이렇게 하면 각 클래스가 독립적으로 관리되고, 컴파일 순서에 따라 달라지는 문제를 방지할 수 있다.

### 올바른 방법

클래스를 서로 다른 소스 파일로 분리하면 문제가 해결된다. 아래와 같이 정적 멤버 클래스를 사용하면 여러 클래스를 한 파일에 담고 싶을 때 좋은 대안이 될 수 있다.

### 정적 멤버 클래스를 사용하는 방법

```java
public class Test {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }

    private static class Utensil {
        static final String NAME = "pan";
    }

    private static class Dessert {
        static final String NAME = "cake";
    }
}
```

이렇게 하면 클래스 이름 충돌 문제를 방지하면서 여러 클래스를 한 파일에 담을 수 있다. 하지만 정적 멤버 클래스는 바깥 클래스와 함께 쓰일 때만 사용해도 된다.
**클래스의 필드를 직접 노출하는 대신, 접근자 메서드(getter, setter)를 사용하는 것이 더 좋다.** **기본적으로는 캡슐화 원칙을 따르는 것이 객체 지향 설계에서 중요하다.**

### 1. **캡슐화 문제점**

```java
class Point {
    public double x;
    public double y;
}
```

위와 같은 public 필드를 가진 클래스는 몇 가지 문제가 있다:

- 캡슐화의 장점을 제공하지 못함: 필드에 직접 접근할 수 있으니, 내부 구현이 외부에 드러난다.
- 내부 표현을 바꿀 때 API 수정 필요: 필드가 직접 공개되면, 필드를 바꾸는 순간 API도 수정 필요.
- 불변성 보장 불가능: 외부에서 필드 값을 직접 바꿀 수 있으니, 필드의 불변성을 보장할 수 없다.
- 부수 작업 수행 불가능: 필드에 접근할 때 특정 작업을 수행하려면, 이런 부가 작업을 구현할 방법이 없다.

### 2. 접근자 메서드 방식

이러한 문제를 해결하기 위해 필드를 `private`로 선언하고, getter와 setter를 제공하는 방식:

```java
class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
```

→ 캡슐화를 유지하면서 내부 표현을 바꿀 수 있는 유연성을 제공. 즉, 내부 필드를 바꾸더라도 API는 유지할 수 있다. 또한, 필드를 읽거나 쓸 때 부가 작업(예: 값 검증)도 추가 가능.

### 3. 패키지 내부(private, package-private) 클래스

하지만 `package-private`이나 `private` 중첩 클래스처럼, 외부에 노출되지 않는 클래스라면 굳이 이렇게 복잡하게 할 필요는 없다. 내부적으로 사용하는 클래스라면 필드를 직접 노출해도 괜찮다. 내부 구현을 바꾸더라도, 패키지 외부 코드와 관련이 없기 때문에 더 단순한 코드로 유지할 수 있다.

### 4. 불변 필드

불변성을 보장하려면 필드를 `final`로 선언하는 방법도 있다.
Ex)  `Time` 클래스처럼 불변 필드를 사용하면 외부에서 값을 수정할 수 없어서 불변성을 보장할 수 있다:

```java
public final class Time {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    public final int hour;
    public final int minute;

    public Time(int hour, int minute) {
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("Invalid hour: " + hour);
        }
        if (minute < 0 || minute >= MINUTES_PER_HOUR) {
            throw new IllegalArgumentException("Invalid minute: " + minute);
        }
        this.hour = hour;
        this.minute = minute;
    }
}
```

→ 필드가 불변이라면 값이 변경되지 않기 때문에 안정성이 높아지고, 값이 직접 노출되더라도 큰 문제가 없다. 하지만 여전히 내부 표현을 바꾸려면 API 수정이 필요하고, 부가 작업을 추가할 수 없다는 단점이 있다.
**불변 클래스(Immutable Class)의 중요성과 구현 방법
(불변 클래스 : 그 인스턴스가 생성된 후, 내부 상태가 절대 변하지 않는 클래스)**

### 불변 클래스의 특징

1. 인스턴스 수정 불가: 불변 클래스의 인스턴스는 한 번 생성되면 수정할 수 없다. (`String`, `BigInteger`, `BigDecimal` 등이 자바에서 불변 클래스로 제공)
2. 클래스를 확장할 수 없게 설계: 불변 클래스를 상속하면 내부 상태를 바꿀 수 있는 위험이 생기므로, `final` 키워드를 사용해 상속을 막아야 한다.
3. 모든 필드가 `final`: 모든 필드는 한 번만 설정되고, 그 이후에는 변경되지 않아야 한다.
4. 모든 필드가 `private`: 필드를 외부에서 직접 접근할 수 없도록 `private`로 선언해서 정보 은닉
5. 가변 객체는 방어적 복사: 만약 필드에 가변 객체가 포함된다면, 방어적 복사를 사용해 외부에서 해당 객체를 수정하지 못하게 해야 한다.

### 불변 클래스의 장점

1. 안전성: 불변 클래스는 Thread-safe(스레드 안전)하기 때문에 여러 스레드에서 동시에 사용해도 안전(동기화 처리가 필요 X)
2. 단순한 설계: 불변 객체는 상태가 변하지 않으므로 디버깅과 테스트가 쉽다
3. 재사용과 공유 가능: 불변 객체는 자유롭게 공유할 수 있다. 한 번 생성된 객체를 여러 곳에서 재사용하면 메모리와 성능을 효율적으로 사용할 수 있다.
4. 캐싱 가능: 불변 객체는 언제나 동일한 상태를 유지하므로, 캐싱을 통해 성능을 최적화할 수 있다.

### 불변 클래스 구현 예시

```java
public final class Complex {
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }
}
```

`Complex` 클래스는 불변성을 유지하면서 사칙연산 메서드를 제공한다. 각 연산은 새로운 `Complex` 인스턴스를 반환하지만, 원래의 객체 상태는 변경되지 않는다.

### 불변 클래스의 단점

- 성능 문제: 값을 변경할 때마다 새로운 객체를 생성해야 하므로, 값이 많이 바뀌는 상황에서는 성능 문제 발생. 특히 다단계 연산을 처리할 때 중간에 생성된 객체들이 메모리를 낭비

→ 해결 : 가변 동반 클래스(e.g., `StringBuffer`)를 함께 제공할 수 있다.

### 정적 팩터리와 방어적 복사

- 불변 객체는 정적 팩터리 메서드를 이용해 인스턴스를 생성하는 방식도 자주 사용된다. 이렇게 하면 나중에 객체 캐싱 등의 최적화 작업을 쉽게 적용 가능.

```java
public final class Complex {
    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }
}
```

- 방어적 복사 : 가변 객체를 외부에서 참조하지 못하도록 복제하는 것
Ex) 아래 코드는 외부로부터 안전하게 `BigInteger` 객체를 복사해 반환

```java
public static BigInteger safeInstance(BigInteger val) {
    return val.getClass() == BigInteger.class ? val : new BigInteger(val.toByteArray());
}
```
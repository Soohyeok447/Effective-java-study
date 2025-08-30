## 17. 변경 가능성을 최소화하라

### 요점 정리

- 객체는 가능하다면 immutable (변경 불가능)하게 설계하자.

  왜?? 불변 클래스를 쓰면 설계, 구현이 단순하다. 오류도 줄어들고 보안과 스레드 안정성도 얻을 수 있음!!

- public 생성자 대신 static factory를 쓰면 캐싱 최적화를 할 수 있다

### 불변 클래스를 만들기 위한 규칙들

1. 상태를 변경하는 메서드들을 제공하지 않기 (setter, mutator 등)
2. 클래스를 확장할 수 없게 만들어야 한다
    - final로 선언하기
    - 생성자를 private / package-private으로 막고 static factory만 제공하는 방식도 가능하다
3. 모든 필드를 final로 선언하기
4. 모든 필드를 private로 선언하기.
    - 내부 표현을 외부에서 직접 변경하기 못하게 보호하기 위함
5. mutable 객체를 참조하는 필드가 있다면 defensive copy를 사용한다

### 불변 객체의 장점

- **단순성 :** 상태가 생성시점부터 변하지 않는다!
- **안전성 :**
    - 스레드 안전하다 → 추가 동기화가 필요 없다
    - 공유 가능 → 여러 쓰레드가 동시에 사용해도 문제가 없다
- **재사용성 :**
    - 자주 쓰이는 값은 `public static final` 상수로 제공할 수 있다

    ```java
    // ex. 불변 클래스 Complex가 있을 때
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE  = new Complex(1, 0);
    public static final Complex I    = new Complex(0, 1);
    ```


### 정적 팩토리 방식으로 불변 클래스 만들기

```java
public class Complex {
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

valueOf과 같은 방식으로 만들 수도 있다

- public 생성자 대신 static factory를 쓰면 캐싱 최적화를 할 수 있다

  →  valueOf로 호출할 때 자주 쓰이는 객체를 미리 만들어두고 돌려줄 수 있다

    ```java
    public static final Complex ZERO = new Complex(0,0);
    public static final Complex ONE  = new Complex(1,0);
    
    public static Complex valueOf(double re, double im) {
        if (re == 0 && im == 0) return ZERO;
        if (re == 1 && im == 0) return ONE;
        return new Complex(re, im);
    }
    ```

- 외부에서 상속 불가 → 불변성 안전

  사실상 final과 같은 효과. 왜냐하면 생성자가 private니까 …extends ImmutableClass 이런 식으로 상속하는 게 불가능하기 때문


- **비유 :**

  public 생성자는 그냥 “객체 만드는 기계 버튼”이고

  static factory는 “객체를 제공하는 창구”라서 내부 사정에 따라 새 걸 줄지, 기존 걸 줄지 / 어떤 구조로 만들어진 구현체를 줄지 내부에서 결정할 수 있는 자유가 생긴다.
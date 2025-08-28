# item16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

-   클래스의 필드를 public으로 공개하면 안 됨 캡슐화의 이점을 모두 포기하는 행위
-   데이터는 반드시 private으로 숨기고 public 접근자(getter) 메서드를 통해 제공해야 함

```java
// 좋은 예 -> 데이터를 캡슐화
class Point {
    private double x;
    private double y;

    // constructor

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```

### public final 필드는 괜찮나

필드를 final로 선언하여 불변으로 만들면 노출해도 덜 위험하지만 좋은 방법이 아님

### 필드를 노출해도 괜찮은 경우

package-private 클래스나 private 중첩 클래스에서는 필드를 노출하는 것이 더 나을 수 있음 - 패키지 바깥 코드는 손대지 않고 데이터 표현 방식 바꿀 수 있음

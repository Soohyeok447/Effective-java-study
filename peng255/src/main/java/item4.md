## 4. 인스턴스화를 막으려면 private 생성자를 써라

- static 메서드와 static 필드만 모아둔 **유틸리티 클래스**(Math, Arrays, Collections 등)는 인스턴스화할 필요가 없다.
- 근데 생성자를 명시하지 않으면 컴파일러가 public 기본 생성자를 자동으로 만들어주기 때문에, 실수로 인스턴스가 생성될 수 있다.
- **추상 클래스로 만드는 것**은 인스턴스화 방지에 **효과적이지 않음.** 상속을 통해 인스턴스를 만들 수 있기 때문이다.
- **가장 확실한 방법은 private 생성자를 추가하는 것**이다. 이렇게 하면 외부에서 new 연산자로 인스턴스를 만들 수 없다.
- 내부에서 실수로 생성자를 호출하는 것을 막으려면 **생성자에서 예외**를 던지면 된다.
- private 생성자를 사용하면 서브클래싱도 막을 수 있다. 생성자를 호출할 수 없으니 하위 클래스도 인스턴스화할 수 없다.

---

## **예시와 설명**

### **1. 기본 유틸리티 클래스 (잘못된 예시)**

```java
public class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }
}
```

이렇게 작성하면, 아래와 같이 인스턴스를 만드는 것이 허용돼버린다

```java
MathUtils utils = new MathUtils(); // 인스턴스가 생성됨 (의도하지 않음)
```

### **2. private 생성자를 이용한 인스턴스화 방지**

```java
public class MathUtils {
    private MathUtils() {
        throw new AssertionError(); // 내부에서 실수로 호출해도 예외 발생
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
```

이렇게 하면 외부에서`new MathUtils()`를 호출하면 **컴파일 에러**가 발생하고, 내부에서 실수로 생성자를 호출해도 런타임에 예외가 발생한다.

### **3. 실제 사용 예시**

```java
int sum = MathUtils.add(5, 3); // 정상 사용
// MathUtils utils = new MathUtils(); // 컴파일 에러
```

### **4. 왜 추상 클래스로 하면 안 되는가?**

```java
public abstract class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }
}
class MyMathUtils extends MathUtils {}
MyMathUtils utils = new MyMathUtils(); // 인스턴스 생성 가능
```

추상 클래스로 만들면 직접 인스턴스화는 막을 수 있지만, **상속을 통해 인스턴스를 만들 수** 있으므로 완벽하게 인스턴스화를 막지 못한다
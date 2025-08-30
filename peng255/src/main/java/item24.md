## 24. 멤버 클래스는 되도록 static으로 만들라

### 요점 정리

- 가능한 한 멤버 클래스는 `static`으로 만든다.
- **외부 인스턴스 참조가 꼭 필요한 경우**에만 inner class로 한다.
- static 멤버 클래스는 외부 클래스에 종속적인 독립 클래스로 생각하면 된다.

### 멤버 클래스가 뭔데?

- 멤버 클래스 : 다른 클래스 내부에 정의된 클래스!
- 크게 두 가지가 있다
    1. `static` 멤버 클래스 (static nested class)
    2. `non static` 멤버 클래스 (inner class)

### 왜 static 멤버 클래스를 선호해야 하는데?

- **외부 인스턴스 참조 부재로 인한 공간 및 시간 절감**

  inner 클래스는 내부적으로 **외부 클래스의 인스턴스를 참조하는 필드**를 가진다. 이 참조 때문에 생성 비용, 메모리 차지가 증가한다..

  외부 객체가 GC되더라도 inner 클래스 인스턴스 때문에 메모리 누수가 발생할 가능성이 있다

- **불필요한 종속성 제거**

  static 멤버 클래스는 외부 클래스 인스턴스와 독립적이어서

  분리와 재사용이 용이하고 명확한 역할 구분에 도움이 된다.

- **API 설계 명확화 및 유지보수성 증가**

  외부 객체 참조를 명시적으로 하지 않아도 된다 → 코드 이해도가 높아진다.


### non static 멤버 클래스 (inner class) 예시

```java
public class Outer {
    private int outerValue = 10;

    class Inner {
        void print() {
            System.out.println("Outer value: " + outerValue);
        }
    }

    public Inner createInner() {
        return new Inner();
    }
}
```

- Inner는 Outer 객체에 암묵적 연결
- Inner 인스턴스는 외부 Outer 인스턴스를 반드시 참조해야 생성 가능
- 메모리와 성능 부담, 불필요한 강한 결합

### static 멤버 클래스 예시

```java
public class Outer {
    private static int staticValue = 20;

    static class Nested {
        void print() {
            System.out.println("Static value: " + staticValue);
        }
    }

    public static Nested createNested() {
        return new Nested();
    }
}
```

- `Nested`는 외부 인스턴스가 필요하지 않다
- 무거운 외부 참조 없고 가볍고 독립적

### 어떨 때 뭘 써야하나?

해당 멤버 클래스가 외부 클래스 객체 상태에 의존하는가?

    예 → non-static 멤버 클래스(inner class)
    아니오→ static 멤버 클래스
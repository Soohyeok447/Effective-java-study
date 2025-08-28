# item24. 멤버 클래스는 되도록 static으로 만들라

## 핵심

멤버 클래스가 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여 정적 멤버 클래스로 만들라

### 중첩 클래스(Nested Class)란

-   다른 클래스 내부에 정의된 클래스를 말함
-   자신을 감싸고 있는 바깥 클래스 안에서만 쓰이는 것이 일반적

### 중첩 클래스 종류 4가지

-   정적 멤버 클래스
-   (비정적) 멤버 클래스
-   익명 클래스
-   지역 클래스

여기서 정적 멤버 클래스를 제외한 나머지는 전부 내부 클래스(inner class)에 해당

### 정적 멤버 클래스

다른 클래스 안에 static 키워드를 붙여서 선언한 클래스

바깥 클래스와 완전히 **독립적인** 그저 다른 클래스임 내부에 선언된 일반 클래스와 같게 됨 (인스턴스 차원에서 바깥 인스턴스와 독립적인 객체임)

그러나 클래스 설계 차원에서는 논리적으로 강하게 연결되어 있다는 것이 특징임

바깥 클래스의 전용 부품같은 느낌 (바깥 클래스와 함께해야지만 논리적인 의미가 발생함)

```java
// 바깥 클래스
public class OuterClass {

    // 바깥 클래스의 다른 멤버들 (필드, 메서드)
    private int instanceField;
    private static int staticField;

    // 정적 멤버 클래스
    public static class StaticMemberClass {
        // 자신만의 필드와 메서드를 가질 수 있음
        void show() {
            // 바깥 클래스의 static 멤버에는 접근 가능
            System.out.println("바깥 클래스의 static 필드: " + staticField);

            // 바깥 클래스의 인스턴스 멤버에는 접근 불가
            // System.out.println(instanceField); // 접근 불가
        }
    }
}
```

-   다른 클래스 안에 선언됨
-   바깥 클래스의 private 멤버에도 접근할 수 있음
-   다른 정적 멤버와 똑같은 접근 규칙을 적용받음
-   흔히 바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스로 쓰임

-> 이걸 제외하면 일반 클래스와 똑같음

### 다른 정적 멤버와 똑같은 접근 규칙을 적용받는다는 게 무슨 뜻인가

정적 멤버 클래스를 static 변수나 static 메서드와 똑같은 것으로 취급하면 이해하기 쉬움

-   public static int A; -> 클래스 외부 어디서든 OuterClass.A로 접근 가능
-   private static int B; -> OuterClass 내부에서만 B로 접근 가능

static 필드나 메서드는 접근 제어자(public, private 등)에 따라 접근 범위가 결정되는데 **정적 멤버 클래스도 이 규칙을 그대로 따른다는 뜻**

-   public static class A { ... } -> 클래스 외부 어디서든 new OuterClass.A()로 객체 생성 가능
-   private static class B { ... } -> OuterClass 내부에서만 new OuterClass.B()로 객체 생성 가능

> private으로 선언하면 바깥 클래스에서만 접근할 수 있음 (정적 멤버 클래스의 존재가 외부로 부터 감춰지게 됨)

### public 도우미 클래스란

public 도우미 클래스(헬퍼 클래스)는 바깥 클래스와 논리적으로 밀접하게 연결되어 있지만, 클라이언트(사용자)가 직접 사용해야 할 필요가 있는 클래스

빌더패턴의 Builder 클래스가 대표적인 예시

### 바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스로 쓰임

도우미 클래스를 바깥 클래스와 상관없는 곳에서 단독으로 쓸 이유가 전혀 없다는 뜻

Outer.Helper라는 형태는 **이 Helper는 독립적인 객체지만, 그것의 유일한 존재 이유는 논리적으로 Outer 클래스를 돕기 위함이다**라는 관계를 명확하게 표현하는 매우 효과적인 설계 방식 <br>
-> 그래서 인스턴스가 없어도 Helper 자체는 만들 수 있도록 정적으로 만든다

### 멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들 것

-   static을 생략하면 바깥 인스턴스로의 숨은 외부 참조를 갖게 됨
    -> 시간과 공간이 소비됨
    -   가비지 컬렉션이 바깥 클래스의 인스턴스를 수거하지 못해서 메모리 누수가 생길 수 있음 (item 7)
    -   참조가 눈에 안보여서 원인을 찾기 어려움

### 정적 vs 비정적

중요한 차이는 '바깥 클래스 인스턴스와의 연결고리(숨은 외부 참조)' 유무

#### 정적 멤버 클래스

바깥 클래스를 단순히 이름 공간(namespace)으로만 사용하는 독립적인 클래스

#### (비정적) 멤버 클래스 - static X

바깥 클래스 인스턴스의 일부처럼 동작하는 종속적인 클래스임 바깥 인스턴스로의 숨은 외부 참조를 가져서 이 클래스를 통해 바깥 클래스의 모든 멤버에 접근이 가능해짐 (멤버 클래스의 인스턴스가 살아있으면 GC가 수거를 못해서 메모리 누수 위험)

### GC가 수거를 못하게 되는 메모리 누수 케이스

```java
import java.util.ArrayList;
import java.util.List;

public class LeakExample {

    // 이 static 리스트가 내부 클래스 인스턴스를 붙잡아 두게 됨
    static List<Object> storage = new ArrayList<>();

    // 바깥 클래스
    class Outer {
        // 내부 클래스인 Inner가 이 객체를 참조할 수 있음
        private byte[] bigData = new byte[10 * 1024 * 1024]; // 10MB

        // 비정적 내부 클래스 (static 없음)
        class Inner {}
    }

    public static void main(String[] args) {
        // 세 개의 객체를 연쇄적으로 생성
        // Outer()를 주인으로 삼는 새로운 내부 객체를 생성하게 됨
        Inner innerInstance = new LeakExample().new Outer().new Inner();

        // 내부 객체를 static 리스트에 저장
        storage.add(innerInstance);

        System.gc(); // GC 유도

        // innerInstance가 붙잡고 있기 때문에 Outer 객체는 수거가 안됨
    }
}
```

Inner 클래스를 정적 멤버 클래스로 만들면 독립적으로 생성이 가능하게 됨

```java
// Outer와 Inner가 모두 static이라고 가정
Inner staticInnerInstance = new LeakExample.Outer.Inner();

```

### 정리

중첩 클래스에는 4가지가 있고 쓰임새가 다름

메서드 밖에서도 사용해야 하거나 메서드 안에 정의하기에 너무 길면 멤버 클래스로 만들 것

멤버 클래스의 인스턴스 각각이 바깥 인스턴스를 참조한다면 비정적으로, 그렇지 않으면 정적으로 만들 것

중첩 클래스가 한 메서드 안에서만 쓰이면서 그 인스턴스를 생성하는 지점이 단 한 곳이고 해당 타입으로 쓰기에 적합한 클래스나 인터페이스가 이미 있다면 익명 클래스로 만들 것

그렇지 않으면 지역 클래스로 만들 것

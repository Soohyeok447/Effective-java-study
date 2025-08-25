## 18. 상속보다는 컴포지션을 사용하라

### 요점 정리

- 상속은 코드 재사용을 위한 강력한 방법이지만… 설계/유지보수 면에서 위험할 수 있다
    - 특히 다른 사람이 만든 일반 concrete class를 상속하면, 부모 클래스의 구현에 의존하게 돼서 자식 클래스가 취약해진다.
    - 그러니 특별한 경우를 제외하면 상속 대신 composition과 전달을 활용하는 것이 더 안전하다
- B is A가 진짜로 성립할 때만 extends를 사용하자
- B is A가 성립하지 않는다면 composition으로 감싸서 원하는 기능을 추가하자
- composition을 사용하면 더 유연하고 안정적이고 확장 가능한 API를 만들 수 있다

### 상속의 문제점

부모 클래스의 내부 구현이 변경되면 자식 클래스가 깨질 수 있다.

```java
// 부모 클래스
class Parent {
    public void doSomething() {
        System.out.println("기본 동작");
    }

    // v1 버전에서는 이 메서드가 없음
    // → 나중에 v2 버전에서 추가됨
    public void newFunction() {
        doSomething(); // 내부적으로 doSomething 호출
    }
}

// 자식 클래스
class Child extends Parent {
    @Override
    public void doSomething() {
        System.out.println("자식 클래스 동작");
    }
}

public class InheritanceBreakDemo {
    public static void main(String[] args) {
        Child child = new Child();

        // 1. 원래 의도
        child.doSomething();  // "자식 클래스 동작"

        // 2. 부모가 나중에 추가한 메서드
        child.newFunction();  
        // 의도: "기본 동작"
        // 실제: "자식 클래스 동작"  (== 부모 의도 깨짐!)
    }
}
```

- Parent 클래스에 새 메서드 newFunction()을 추가했다고 생각해보자. 이 메서드에서는 내부적으로 doSomething()을 호출한다

  그런데 Child 클래스가 doSomething()을 오버라이드했기 때문에 `child.newFunction()`을 실행했을 때 newFunction 내부에서 부모의 doSomething이 아닌 Child의 soSomething이 실행된다
  → 예상치 못한 동작을 하게 됨
</br></br>
**- 컴포지션으로 바꿔보자**

```java
// 부모 역할 클래스
class Parent {
    public void doSomething() {
        System.out.println("기본 동작");
    }

    public void newFunction() {
        doSomething(); // 안전하게 기본 doSomething 실행
    }
}

// 자식은 "상속하지 않고 포함(Composition)" 함
class Child {
    private final Parent parent = new Parent();

    // Child만의 독립적인 동작
    public void customDoSomething() {
        System.out.println("자식 클래스 동작");
    }

    // 부모 기능을 그대로 쓰고 싶으면 위임(forwarding)
    public void parentDoSomething() {
        parent.doSomething();
    }

    public void parentNewFunction() {
        parent.newFunction();
    }
}

public class CompositionDemo {
    public static void main(String[] args) {
        Child child = new Child();

        // 자식만의 동작: 부모와 충돌 없음
        child.customDoSomething();   // "자식 클래스 동작"

        // 부모 동작도 그대로 안전히 사용 가능
        child.parentDoSomething();   // "기본 동작"
        child.parentNewFunction();   // "기본 동작"
    }
}
```

- Child가 Parent를 상속하지 않고 내부에 `private final Parent parent` 인스턴스로 두고, 클래스 메서드에서는 parent.doSomething()으로 호출한다
- 이러면 Parent의 변경이 있어도 충돌이 없다
- Child가 customDoSomething()으로 자기만의 기능을 안전하게 추가ㅎ하여 부모 기능과 독립적일 수 있다
- API를 노출할지 말지를 Child가 직접 통제하니 안정적이다

### 그럼 언제 상속을 써도 되나?

- 상속하려는 클래스가 같은 패키지 내부에서 관리되는 경우
- 상속하려는 클래스가 상속을 고려해서 설계/문서화 되어있는 경우
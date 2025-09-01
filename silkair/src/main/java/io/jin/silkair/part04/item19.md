### 상속 설계의 핵심

상속을 지원하려는 클래스는 확장 가능성을 고려해 잘 설계하고, 문서화해야 한다. 특히, 재정의할 수 있는 메서드(public, protected, final이 아닌 메서드)의 동작 방식을 명확히 문서로 남겨야 한다. 이 문서가 없다면 하위 클래스는 상위 클래스의 동작을 오해할 수 있고, 잘못된 방식으로 상속받아 문제가 생길 수 있다.

### @implSpec 태그

`@implSpec` 태그는 메서드의 내부 동작 방식을 설명하는데 사용된다. 이 태그를 활용하면 상속을 통해 재정의할 수 있는 메서드의 구체적인 동작 방식을 문서화할 수 있다.

```java
/**
 * {@inheritDoc}
 *
 * @implSpec
 * This implementation iterates over the collection and removes the element
 * if found using the iterator's remove method.
 */
public boolean remove(Object o) {
    // 메서드 구현
}
```

메서드의 내부 동작을 명확하게 설명하면, 하위 클래스에서 어떻게 재정의해야 할지 이해하기 쉽다.

### 훅(Hook) 메서드

상속을 쉽게 하려면, 하위 클래스가 상위 클래스의 동작에 적절하게 끼어들 수 있는 지점을 만들어야 한다. 이를 위해 상위 클래스에서 protected 메서드(훅 메서드)를 적절히 제공해 하위 클래스에서 중간 과정을 수정할 수 있게 만들어줘야 한다.

### 예시: AbstractList의 removeRange

```java
protected void removeRange(int fromIndex, int toIndex) {
    // 하위 클래스에서 효율적인 clear 메서드를 만들 수 있도록 지원
}
```

`removeRange`  : 하위 클래스에서 성능을 개선하거나 특정 동작을 구현할 수 있도록 고안된 메서드. 
이 메서드를 제공하지 않으면, 하위 클래스는 성능 문제를 겪을 수 있거나 하위 클래스에서 새로 구현해야 할 수도 있다.

### protected 메서드는 신중하게

상속용 클래스에서 protected 메서드는 너무 많이 제공해서도 안 되고, 너무 적게 제공해서도 안된다. 상속의 장점을 살리면서도, 클래스 내부 구현을 외부에 노출하지 않도록 신중하게 선택.

### 상속을 시험하는 방법

상속용 클래스를 테스트하려면 하위 클래스를 직접 구현해 보면서 필요한 protected 메서드를 확인하자. 하위 클래스를 작성하면서 어떤 부분을 수정해야 하는지 알아보는 과정에서 상속 설계의 적절성을 판단할 수 있다.

### 생성자에서 재정의 가능한 메서드를 호출하지 말라

상위 클래스의 생성자가 재정의 가능한 메서드를 호출하면, 하위 클래스의 생성자가 실행되기 전에 그 메서드가 호출될 수 있다. 이때 하위 클래스에서 초기화되지 않은 값을 사용하면 예기치 않은 동작이 발생할 수 있다.

### 잘못된 예시

```java
public class Super {
    public Super() {
        overrideMe(); // 재정의 가능한 메서드 호출
    }

    public void overrideMe() {}
}

public class Sub extends Super {
    private final Instant instant;

    public Sub() {
        instant = Instant.now();
    }

    @Override
    public void overrideMe() {
        System.out.println(instant); // instant가 아직 초기화되지 않음
    }
}
```

→ 상위 클래스의 생성자가 먼저 호출되고, 그 안에서 `overrideMe()`가 실행되면서 아직 초기화되지 않은 `instant`를 사용하려 해서 문제가 발생

### Cloneable과 Serializable 고려사항

만약 상속용 클래스가 `Cloneable`이나 `Serializable` 인터페이스를 구현해야 한다면, `clone`과 `readObject` 메서드에서 재정의 가능한 메서드를 호출하지 않아야 한다. 그렇지 않으면 하위 클래스에서 잘못된 동작이 발생할 수 있다.

### Serializable 예시

- readObject: 하위 클래스의 상태가 역직렬화되기 전에 재정의된 메서드를 호출할 수 있기 때문에, 의도하지 않은 결과를 초래할 수 있다.
- clone: 하위 클래스의 복제본이 생성되기 전에 재정의된 메서드가 호출되면 복제본이나 원본 객체에 문제가 발생할 수 있다.

### 상속 금지 방법

상속을 허용하지 않으려면 클래스에 대해 final 선언을 하거나, 모든 생성자를 private 또는 package-private로 설정해 외부에서 상속을 할 수 없도록 해야 한다. 그리고 정적 팩터리 메서드를 제공해 객체를 생성할 수 있도록 하는 방법도 있다.

### final 클래스로 상속 금지

```java
public final class MyFinalClass {
    // 상속 금지
}
```
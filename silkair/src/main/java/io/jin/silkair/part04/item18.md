### 상속의 문제점

상속은 코드를 재사용하는 좋은 수단이지만, 잘못 사용하면 상속이 캡슐화를 깨뜨린다. 상속을 사용하게 되면 하위 클래스는 상위 클래스의 내부 구현에 의존하게 되고, 상위 클래스의 변경에 따라 하위 클래스도 수정이 필요할 수 있다.

### 상속을 잘못 사용한 예시

```java
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
```

`InstrumentedHashSet`은 `HashSet`을 상속받아 추가된 원소의 수를 추적하려는 목적이었다.
그런데 문제 발생! 
`HashSet`의 `addAll` 메서드가 내부적으로 add 메서드를 호출해서, `addCount`가 두 번 증가하게 된다. 이렇게 상위 클래스의 내부 구현을 모르고 상속을 사용하면, 부작용이 발생!

### 컴포지션(Composition)이란?

컴포지션은 상속 대신 새로운 클래스에서 기존 클래스를 포함시켜 기능을 확장하는 방법. 이를 통해 기존 클래스의 내부 구현에 의존하지 않게 만들어 상위 클래스의 변경에 영향을 받지 않도록 한다.

### 컴포지션을 사용한 예시

```java
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;

    public ForwardingSet(Set<E> s) {
        this.s = s;
    }

    @Override
    public boolean add(E e) {
        return s.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }
    // 나머지 Set 메서드도 모두 위임
}
```

`ForwardingSet` 클래스는 `Set`을 구현하고, `Set` 인스턴스를 필드로 사용해 기능을 위임한다. 이제 상속을 사용하는 대신, `InstrumentedSet`에서 포함된 `Set` 인스턴스에 기능을 추가할 수 있다.

```java
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
```

이제 `InstrumentedSet`은 `ForwardingSet`을 확장해 `Set`의 기능을 활용하면서도, 추가된 기능(addCount)을 안전하게 유지할 수 있다.

### 상속의 한계와 위험성

- 상위 클래스의 변경: 상위 클래스가 업데이트되면, 하위 클래스는 그 변화를 알 수 없다. 새로운 메서드가 추가되면, 하위 클래스가 원하지 않게 그 메서드를 재정의할 수도 있다.
- Self-use(자기 사용): 상위 클래스가 자기 메서드를 호출하는 경우, 하위 클래스에서 그 동작을 오버라이드하면 예상치 못한 결과가 발생할 수 있다.

### 컴포지션의 장점

- 내부 구현에 의존하지 않음: 기존 클래스의 내부 동작 방식에 의존하지 않기 때문에, 상위 클래스의 변화에 영향을 받지 않는다.
- 유연성: 컴포지션은 다양한 클래스의 인스턴스를 조합할 수 있기 때문에, 더 유연하게 코드 재사용이 가능.
- 새로운 기능 추가: 컴포지션을 사용하면 상속 없이도 새로운 기능을 추가할 수 있다.

### 컴포지션의 한계

- 콜백 프레임워크와의 부조화: 컴포지션 방식은 콜백을 사용하는 프레임워크와는 잘 어울리지 않는다. 콜백에서는 보통 `this`를 넘기는데, 래퍼 클래스가 아닌 내부 객체가 콜백으로 호출될 수 있다.

### 상속은 언제 사용해야 할까?

상속은 하위 클래스가 상위 클래스의 "진짜 하위 타입"일 때만 사용해야 한다. 즉, A is B 관계일 때 상속을 고려해야 한다. 만약 두 클래스가 완전히 다른 역할을 가지고 있다면, 상속을 사용하는 것보다는 컴포지션을 사용하는 게 훨씬 안전.
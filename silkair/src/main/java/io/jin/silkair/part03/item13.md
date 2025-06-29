### `Cloneable` 인터페이스와 `clone` 메서드란?

- `Cloneable` 인터페이스는 객체를 복제할 수 있도록 해주는 인터페이스
- 기본적으로 `clone` 메서드는 `Object` 클래스에서 제공되고 이 메서드는 객체의 복사본을 만들어 반환
- 하지만 `Cloneable`을 그냥 구현하면 `clone` 메서드가 자동으로 작동하지 않는다. `clone` 메서드를 재정의해서 사용하자!

### 문제점:

- `clone` 메서드는 `Object`에 `protected`로 선언되어 있다. 때문에 외부에서 쉽게 호출할 수 없고, `Cloneable`을 구현하는 것만으로는 복제 기능을 제대로 사용할 수 없다.
- 상위 클래스에서 `clone` 메서드를 재정의해야 하며, 하위 클래스도 그에 맞춰 올바르게 작동하게 해야 한다.

### 올바르게 `clone` 메서드를 재정의하는 방법

### 1. 간단한 객체 복제

- 만약 클래스가 모든 필드가 기본 타입이거나 불변 객체를 참조하는 경우, `clone` 메서드를 간단히 `super.clone()`으로 구현할 수 있다.

```java
@Override
public PhoneNumber clone() {
    try {
        return (PhoneNumber) super.clone(); // 기본 타입 필드를 복사해 객체를 반환
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(); // 발생하지 않을 예외 처리
    }
}
```

- `super.clone()`을 호출하면 객체의 모든 필드가 값 그대로 복사된다. (간단하고 불변 객체에 적합)

### 2. 가변 상태를 가진 클래스의 복제

- 만약 클래스에 배열 같은 가변 필드가 있는 경우, 단순히 `super.clone()`을 호출하면 원본과 복제본이 같은 배열을 공유하게 된다.
- `Stack` 클래스가 있다고 할 때, 이 클래스의 `elements`라는 배열은 원본과 복제본에서 같은 배열을 가리키게 되면 문제가 발생

```java
@Override
public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        result.elements = elements.clone(); // 배열을 깊은 복사해서 독립적으로 만듦
        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

- 깊은 복사를 통해, 원본과 복제본이 서로 독립적인 배열을 가지도록 만들어야 한다. 이렇게 하면 원본이나 복제본을 수정하더라도 서로 영향을 주지 않는다.

### 3. 더 복잡한 객체의 복제

- 클래스가 연결 리스트나 해시테이블 같은 복잡한 구조를 가지고 있다면, 이 내부 구조까지 모두 깊은 복사해줘야 해.
- 예를 들어, `HashTable` 클래스에서 각 버킷이 연결 리스트로 이루어져 있다면, 각 리스트도 모두 깊은 복사를 해줘야 해.

```java
java
코드 복사
@Override
public HashTable clone() {
    HashTable result = (HashTable) super.clone();
    result.buckets = new Entry[buckets.length];
    for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] != null) {
            result.buckets[i] = buckets[i].deepCopy(); // 연결 리스트를 깊은 복사
        }
    }
    return result;
}

```

- 이 방법은 모든 연결 리스트를 재귀적으로 깊은 복사해주는 방식으로, 복잡하지만 정확히 동작하게 만들 수 있어.

### `clone` 대신 사용하는 더 나은 방법

- 사실, `clone` 메서드와 `Cloneable` 인터페이스는 복잡하고 실수가 발생하기 쉽다. 그래서 더 좋은 대안으로 복사 생성자와 복사 팩터리가 있다.
- 복사 생성자란 : 자기 자신의 클래스 인스턴스를 인수로 받는 생성자

```java
public Yum(Yum yum) {
    // 복사할 필드들 초기화
}
```

- 복사 팩터리는 정적 팩터리 메서드로, 비슷한 기능을 제공

```java
public static Yum newInstance(Yum yum) {
    // 복사할 필드들 초기화
    return new Yum(yum);
}
```

### 복사 생성자와 복사 팩터리의 장점

- 안전하고 직관적
  `clone`처럼 생성자를 사용하지 않는 방식이 아니라, 정상적인 객체 생성 규칙을 따르기 때문
- 불필요한 예외 처리나 형변환이 필요 없다.
  `clone` 메서드와는 다르게 복사 생성자는 검사 예외나 형변환이 없어서 간단하고 사용하기 쉽다
- 유연성
  복사 생성자나 팩터리는 인터페이스 타입의 인스턴스도 인수로 받을 수 있기 때문에, 다양한 형태의 객체를 복제할 수 있다. Ex) `HashSet` 객체를 `TreeSet`으로 변환할 때 사용
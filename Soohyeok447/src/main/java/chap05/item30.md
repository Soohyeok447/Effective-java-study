# item30. 이왕이면 제네릭 메서드로 만들라

## 선요약

클래스처럼 메서드도 제네릭으로 만들 수 있고 장점이 많음.<br>
제네릭 메서드는 타입 안정성을 보장하고 사용하기 훨씬 편리함. <br>

## 제네릭 메서드 장점

### 타입 안정성이 보장됨

잘못된 타입의 객체를 사용하려고 할 때 컴파일 시점에 에러가 발생하기 때문에 안정성이 보장됨.

### 사용자가 명시적인 형변환을 할 필요가 없음

#### Raw type 사용 예시

```java
public static Set union(Set s1, Set s2) {
    Set result = new HashSet(s1);
    result.addAll(s2);
    return result;
}
```

Set이라는 Raw Type을 사용하기에 경고가 발생하고 런타임에 `ClassCastException`이 발생할 위험이 있음

#### 제네릭 메서드 사용 예시

```java
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
```

컴파일 경고도 안뜨고 사용자는 반환된 Set<E>을 형변환 없이 안전하게 사용할 수 있음.

## 제네릭 메서드 사용 추가 예시

### 제네릭 싱글턴 팩토리

불변 객체가 여러 타입으로 활용될 수 있도록 만들어야 할 때 사용함.

### 재귀적 타입 한정

특정 타입 매개변수가 자기 자신을 포함하는 표현식으로 한정될 때 사용됨.<br>
주로 타입의 자연적인 순서를 정의하는 Comparable 인터페이스와 함께 쓰임.

```java
public static <E extends Comparable<E>> E max(Collection<E> c) {
    if (c.isEmpty())
        throw new IllegalArgumentException("컬렉션이 비어있습니다.");

    E result = null;
    for (E e : c)
        if (result == null || e.compareTo(result) > 0)
            result = e;

    return result;
}
```

`<E extends Comparable<E>>`는 "모든 타입 E는 자신과 비교할 수 있다"는 제약을 뜻함 <br>
-> max 메서드가 모든 원소를 안전하게 비교할 수 있도록 보장함

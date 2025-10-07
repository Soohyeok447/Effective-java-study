### 제네릭 메서드란?

메서드도 제네릭 클래스를 만든 것처럼 제네릭으로 만들 수 있다. 즉, 메서드가 어떤 타입이 올지 모를 때, 그 타입에 맞춰서 메서드를 유연하게 동작하게 할 수 있는 것.

예를 들어, 두 개의 집합(Set)을 받아서 합쳐주는 메서드를 만들고 싶다고 해보자. 집합에 사과도 넣을 수 있고, 바나나도 넣을 수 있으니까, 이 메서드는 어떤 과일 집합이 와도 잘 동작해야한다?

→ 제네릭 메서드를 사용하는 이유!

### 제네릭 메서드의 잘못된 예시

```java
public static Set union(Set s1, Set s2) {
    Set result = new HashSet(s1);
    result.addAll(s2);
    return result;
}
```

이 메서드는 제네릭을 사용하지 않고 그냥 작성했다. 이럴 경우, 컴파일할 때 경고가 뜰 수 있다. 왜냐하면, 집합에 어떤 타입의 값이 들어가는지 컴파일러가 제대로 확인하지 못하기 때문이디.

### 제네릭으로 고친 예시

```java
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
```

이렇게 하면, E라는 타입을 사용해서 집합이 어떤 타입의 값을 가질지 명확하게 정의했다. 이제 이 메서드는 어떤 타입이든 안전하게 받을 수 있어. 

**예시**

```java
Set<String> fruits = Set.of("사과", "바나나");
Set<String> moreFruits = Set.of("딸기", "포도");
Set<String> allFruits = union(fruits, moreFruits);
```

이 코드에서는 문자열(String) 타입의 집합을 합치고 있다. 제네릭 메서드 덕분에, 타입을 일일이 신경 쓰지 않고 사용할 수 있다!

### 제네릭 싱글턴 패턴

싱글턴이라는 것은, 하나의 객체만 사용한다는 뜻이다. 어떤 계산을 하는 함수가 있다면, 이 함수를 한 번만 만들어서 계속 사용하면 되는 것!

제네릭 싱글턴 패턴은 제네릭을 사용해서 모든 타입에 대해 하나의 함수만 사용하는 것이다. 예를 들어, 항등함수가 있다고 하자.(입력을 받아서 변경 없이 그대로 반환하는 함수) 이것도 제네릭 싱글턴 패턴을 사용하면 모든 타입에 대해 하나의 함수만 사용하면 된다!

```java
rivate static UnaryOperator<Object> IDENTITY_FN = t -> t;

@SuppressWarnings("unchecked")
public static <T> UnaryOperator<T> identityFunction() {
    return (UnaryOperator<T>) IDENTITY_FN;
}
```

이 함수는 어떤 타입을 받더라도 그대로 반환한다. 문자열을 넣으면 문자열을, 숫자를 넣으면 숫자를 그대로 반환!

### 재귀적 타입 한정

조금 복잡할 수 있지만, 타입끼리 비교할 때 유용하다. 숫자끼리 비교할 때는 숫자들이 서로 비교 가능해야한다. 그럴 때 재귀적 타입 한정을 사용하자

```java
public static <E extends Comparable<E>> E max(Collection<E> c) {
    E result = null;
    for (E e : c) {
        if (result == null || e.compareTo(result) > 0) {
            result = e;
        }
    }
    return result;
}
```

이 함수는 비교 가능한 타입들로 이루어진 컬렉션에서 가장 큰 값을 찾아주는 함수이다. 재귀적 타입 한정을 사용해서 비교할 수 있는 타입만 받아들인다
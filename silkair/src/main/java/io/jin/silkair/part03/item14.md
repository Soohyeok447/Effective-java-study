### `Comparable`과 `compareTo`란?

- `Comparable` 인터페이스는 객체들 사이의 순서를 정의할 수 있게 해주는 인터페이스
- `compareTo` 메서드는 두 객체의 순서를 비교해서, 작으면 음수, 같으면 0, 크면 양수를 반환
- `Comparable`을 구현한다는 것은 해당 클래스의 인스턴스들이 '자연적인 순서'가 있음을 의미 (숫자, 알파벳, 날짜 같은 객체들)

### 예시:

- `String` 클래스는 알파벳 순서로 정렬되기 때문에 이미 `Comparable`을 구현하고 있어.
- 이를 통해 `String` 배열을 정렬하거나, 중복 제거한 후 알파벳 순으로 정렬해서 출력할 수 있어.

```java
String[] words = {"apple", "banana", "cherry"};
Arrays.sort(words); // 알파벳 순서로 정렬
```

### `compareTo` 메서드의 일반 규약

- `compareTo` 메서드의 규약은 객체들 간의 순서를 비교할 때 일관성이 있어야 한다는 것을 의미
- 세 가지 주요 규칙
    1. **대칭성**: `x.compareTo(y)`의 결과와 `y.compareTo(x)`의 결과는 부호가 반대여야 한다.
    2. **추이성**: `x.compareTo(y)`가 양수이고 `y.compareTo(z)`도 양수면, `x.compareTo(z)`도 양수여야 한다. (즉, 순서가 일관적)
    3. **일관된 동치성**: `x.compareTo(y)`가 0이면, 두 객체를 다른 객체와 비교해도 결과가 같아야 한다. (즉, 크기가 같으면 항상 일관된 결과를 반환)
- 또한, `compareTo`가 `equals`와 일관되도록 구현하는 것이 좋다. → 두 객체가 `compareTo`로 0을 반환하면 `equals`로도 같다고 해야 한다.

### `compareTo` 구현 예시

- `Comparable` 인터페이스를 구현하려면 `compareTo` 메서드를 작성

### 예시: `PhoneNumber` 클래스

```java
public class PhoneNumber implements Comparable<PhoneNumber> {
    private short areaCode, prefix, lineNum;

    @Override
    public int compareTo(PhoneNumber pn) {
        int result = Short.compare(areaCode, pn.areaCode); // 가장 중요한 필드 비교
        if (result == 0) {
            result = Short.compare(prefix, pn.prefix); // 두 번째로 중요한 필드 비교
            if (result == 0) {
                result = Short.compare(lineNum, pn.lineNum); // 세 번째로 중요한 필드 비교
            }
        }
        return result;
    }
}
```

- `PhoneNumber` 클래스는 지역 코드, 앞자리 번호, 가입자 번호를 기준으로 비교
- 가장 중요한 필드부터 비교하고, 같다면 다음 중요한 필드를 비교

### `Comparator`와 `비교자 생성 메서드` 사용

- 자바 8부터는 `Comparator` 인터페이스와 다양한 비교자 생성 메서드를 사용할 수 있다.

### 예시: `PhoneNumber` 클래스에 비교자 생성 메서드 사용

```java
private static final Comparator<PhoneNumber> COMPARATOR =
        Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
                .thenComparingInt(pn -> pn.prefix)
                .thenComparingInt(pn -> pn.lineNum);

@Override
public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
}

```

- `Comparator.comparingInt` 메서드를 사용해 필드별로 순서를 정의하고, `thenComparingInt`로 다음 중요한 필드를 연속해서 비교 : (코드가 간결하고 이해하기 쉽다)

### 주의 사항

- `compareTo` 메서드를 구현할 때, 두 객체의 값을 단순히 빼서 비교하면 안된다.
    - `x - y`처럼 빼는 방식은 정수 오버플로가 발생할 수 있다.
    - 대신 `Integer.compare(x, y)` 같은 정적 메서드를 사용

### 잘못된 예시

```java
public int compare(Object o1, Object o2) {
    return o1.hashCode() - o2.hashCode(); // 오버플로 가능성
}
```

### 올바른 예시:

```java
public int compare(Object o1, Object o2) {
    return Integer.compare(o1.hashCode(), o2.hashCode()); // 정적 메서드를 사용
}
```
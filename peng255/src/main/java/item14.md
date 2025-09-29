## 14. Comparable을 구현할지 고려하라

### 요점 정리

- 자바의 `Comparable` 인터페이스는 객체 간의 자연 순서(natural ordering)를 정의하기 위해 존재한다. `equals`가 동등성만 비교한다면, `compareTo`는 순서 비교까지 가능하게 한다.
- 자연 순서(natural ordering)가 명확한 값 클래스라면 반드시 Comparable을 구현해보자.
- compareTo 구현 시 `<`,`>` 연산자 대신 `Integer.compare`, `Comparator.comparingInt` 같은 정적 비교 메서드를 사용하자.
- equals와 compareTo의 결과를 일치시키는 것이 좋다.
- 잘못된 compareTo 구현은 **`TreeSet`**, **`TreeMap`** 같은 정렬 기반 컬렉션에서 예기치 못한 버그를 일으킬 수 있다.

### Comparable을 잘못된 방식으로 구현하는 예

*비교를 뺄셈으로 구현하면 안 된다!*

```java
static Comparator<Object> hashCodeOrder = (o1, o2) -> o1.hashCode() - o2.hashCode();
```

문제점:

- **오버플로우** 발생 가능
- **부동소수점**에서는 IEEE 754 특성 때문에 **잘못된 비교 결과** 유발

올바른 방법:

```java
// 안전한 구현
static Comparator<Object> hashCodeOrder =
        Comparator.comparingInt(Object::hashCode);
```

---

**1. Comparable 구현이 필요한 경우인가?**

- 정렬이 필요한 값 클래스인가? (**`String`**,**`Integer`**,`LocalDate`같은 경우)
- 특정 기준으로 "자연스러운 순서"가 명확한가?
    - 예: 이름 → 알파벳 순서
    - 숫자 → 크기 순서
    - 날짜/시간 → 시간순

👉 명확하다면 **`Comparable<T>`** 구현해보자

**2. compareTo 계약 지키기**

- **`sgn(x.compareTo(y)) == -sgn(y.compareTo(x))`** (대칭성)
- **`(x > y && y > z) ⇒ x > z`** (추이성)
- **`x == y ⇒ x와 y를 비교한 결과가 항상 같은 부호`**
- 가급적 **`compareTo == 0 ⇔ equals == true`** 지키기

**3. 구현 패턴**

- **기본 원칙**:**`<`**,**`>`**대신**`Integer.compare`**,**`Comparator.comparing`**사용
- 🚫 차이(b - a) 연산 이용 금지 (오버플로우 위험 있음!)

**4. 단일 필드일 경우**

```java
@Override
public int compareTo(Person p) {
    return name.compareTo(p.name);
}
```

**5. 다중 필드일 경우**

**(전통 방식 버전! 불가피할 때만 쓰기)**

```java
@Override
public int compareTo(PhoneNumber pn) {
    int result = Integer.compare(areaCode, pn.areaCode);
    if (result == 0) {
        result = Integer.compare(prefix, pn.prefix);
        if (result == 0)
            result = Integer.compare(lineNum, pn.lineNum);
    }
    return result;
}
```

**(실무에서 권장, Java 8+)**

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

Comparator.comparingInt를 이용한다
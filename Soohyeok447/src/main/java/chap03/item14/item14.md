# item14. Comparable을 구현할지 고려하라

### compareTo란

-   성격은 Object의 equals와같음
-   단순 동치성 비교에 순서까지 비교 가능
-   제네릭함

### Comparable을 구현했다는 것은

그 클래스의 인스턴스들에는 순서가 있음을 뜻함. Comparable을 구현한 객체는 `Arrays.sort(a);` 처럼 쉽게 정렬 가능

### compareTo 규약을 지켜서 비교를 활용하는 클래스 예시

-   Collections, Arrays, TreeSet, TreeMap 등

### 구현이 필요한 경우

날짜, 이름, 알파벳, 숫자 등 순서가 명확한 값 객체라면 구현하는 것이 좋음 (반드시 Comparable 인터페이스 구현할 것을 권장함)

### compareTo() 규약

이 객체와 주어진 객체의 순서를 비교함. 대상 객체가 주어진 객체보다 작으면 음의 정수를, 같으면 0을, 크면 양의 정수를 반환함. 비교할 수 없는 타입의 객체가 주어지면 ClassCastException을 던짐

-   x.compareTo(x) == 0
-   sign(x.compareTo(y)) == -sign(y.compareTo(x)) |
-   x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z) > 0
-   x.equals(y) == (x.compareTo(y) == 0) 이어야 함 (만약 지켜지지 않는 경우엔 equals 메서드와 일관되지 않는다고 명시해야 함)

---

### 주의

-   compareTo()에서 필드의 값을 비교할 때 < 와 > 연산자는 지양
-   박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드(자바7)나 Comparator 인터페이스가 제공하는 비교자 생성 메서드(자바8)를 이용

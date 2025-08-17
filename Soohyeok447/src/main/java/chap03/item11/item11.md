# item11. equals를 재정의하려거든 hashCode도 재정의하라

- `equals()`를 재정의하면 반드시 `hashCode()`도 함께 재정의해야 함
- 그렇지 않으면 **HashMap, HashSet, HashTable** 등에서 **정상적으로 작동하지 않음**
  - `equals()`는 같다고 판단되는데, `hashCode()`가 다르면 **같은 객체로 인식되지 않음**

### Object의 규약
- equals가 true를 반환하는 두 객체는 반드시 같은 hashCode를 반환해야 함
- equals비교에 사용되는 정보가 안바뀌었으면 hashCode 메서드는 일관된 값을 반환해야 함
- 그런데 반대는 아님 (hashCode가 같다고 equals가 true일 필요는 없음)
- 다른 인스턴스에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아짐 

### 간단한 hashCode 구현
```java
@Override
public int hashCode() {
    return 42;
}
```
- 간단하고 구현
- 그런데 성능상 많은 객체에 쓰기에는 비용이 있음 
  - (모든 객체에 똑같은 해시코드를 반환해서 해시테이블이 O(n)으로 느려짐)

### 좋은 hashCode 구현
```java
@Override
public int hashCode() {
    int result = Integer.hashCode(x); // x는 첫번째 핵심필드 (equals 비교에 사용되는 필드)
    
    // y가 null일 경우 0을 사용
    result = 31 * result + Integer.hashCode(y); // y는 나머지 핵심필드, 숫자 31은 홀수고 널리 사용되는 소수라 전통적으로 이용해옴
    
    return result;
}
```

### 추가 팁
- 성능을 높인답시고 해시코드 계산에 핵심 필드를 생략하면 안됨 (속도는 빨라져도 해시 품질이 나빠지고 해시테이블 성능이 감소할 수 있음)
- hashCode 값 생성규칙을 API 사용자에게 공표하지 말 것
- record 클래스는 equals, hashCode, toString을 자동 생성함 
- 롬복의 @EqualsAndHashCode도 자동생성에 쓸 수 있음



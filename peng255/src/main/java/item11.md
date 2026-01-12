## 11. equals를 재정의하려거든 hashCode도 재정의하라
### **왜 hashCode를 재정의해야 하는가?**

- equals만 재정의하고 hashCode를 재정의하지 않으면, HashMap, HashSet 등 컬렉션에서 객체가 제대로 동작하지 않는다.
- hashCode 규약은 다음과 같다:
    - 같은 객체에 대해 hashCode를 여러 번 호출해도 값이 변하지 않아야 한다(equals에 사용되는 정보가 변하지 않는 한).
    - equals로 비교해 같다고 판단되는 두 객체는 반드시 같은 hashCode 값을 가져야 한다.
    - equals로 다르다고 판단되는 두 객체는 hashCode가 다를 수도 같을 수도 있지만, 다르면 성능이 좋아진다.

### **잘못된 예시**

```java
@Override public int hashCode() { return 42; }
```

- 모든 객체가 같은 hashCode를 반환하므로, HashMap/HashSet이 내부적으로 리스트처럼 동작해서 성능이 크게 저하된다.

### **좋은 hashCode 구현법**

1. 의미 있는 필드(=equals 비교에 쓰는 필드)들의 값을 이용해 hashCode를 만든다.
2. 각 필드의 hashCode 값을 31로 곱해가며 누적한다.
3. 최종적으로 result를 반환한다.

### **예시**

```java
@Override
public int hashCode() {
    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
}
```

- 의미 있는 필드가 여러 개라면, 순서대로 31을 곱해가며 합산한다.

### **간단한 방법 (성능이 중요하지 않을 때)**

```java
@Override
public int hashCode() {
    return Objects.hash(lineNum, prefix, areaCode);
}
```

- 이 방식은 코드가 짧지만, 내부적으로 배열 생성 등 오버헤드가 있어 성능이 아주 중요할 때는 권장하지 않는다.

### **캐싱 기법**

- 불변 객체에서 hashCode 계산 비용이 크면, hashCode를 필드로 저장해두고 처음 한 번만 계산하는 방법도 있다.

```java
private int hashCode; // 기본값 0

@Override
public int hashCode() {
    int result = hashCode;
    if (result == 0) {
        result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        hashCode = result;
    }
    return result;
}
```

### **주의점**

- equals에 사용하지 않는 필드는 hashCode 계산에 포함시키지 않는다.
- 성능을 위해 중요한 필드를 빼면 해시 품질이 떨어져서 HashMap/HashSet의 성능이 나빠질 수 있다.
- hashCode 구현 결과값의 구체적인 스펙을 문서로 명시하지 않는다(유연성 확보)
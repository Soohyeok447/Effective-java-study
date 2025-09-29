## 10. equals는 일반 규약을 지켜 재정의하라
### **언제 equals를 재정의해야 하는가?**

- 인스턴스마다 고유한 객체(예: Thread 등)는 equals를 재정의하지 않는다.
- 논리적 동등성(값이 같으면 같다고 판단)이 필요한 값 객체(예: Integer, String 등)는 equals를 재정의해야 한다.
- 상위 클래스가 이미 equals를 적절히 재정의했다면 굳이 다시 재정의할 필요 없다.
- private 또는 package-private 클래스이고 equals가 호출될 일이 없다면 재정의하지 않는다.

- equals를 재정의할 땐 5가지 규약(반사성, 대칭성, 추이성, 일관성, null-불일치)을 반드시 지켜야 한다.
- 잘못 구현하면 컬렉션 등에서 예측 불가능한 버그가 발생한다.
- 값 객체라면 equals와 hashCode를 함께 재정의한다.

### **equals 일반 규약**

equals 메서드를 재정의할 때 반드시 다음 5가지 규약을 지켜야 한다

| **규약** | **설명** |
| --- | --- |
| 반사성 | x.equals(x)는 항상 true여야 한다. |
| 대칭성 | x.equals(y)가 true면 y.equals(x)도 true여야 한다. |
| 추이성 | x.equals(y)와 y.equals(z)가 true면 x.equals(z)도 true여야 한다. |
| 일관성 | x.equals(y)는 객체 상태가 변하지 않는 한 여러 번 호출해도 항상 같은 결과를 반환해야 한다. |
| null-불일치 | x.equals(null)은 항상 false여야 한다. |

### **잘못된 예시**

**대칭성 위반 예시**

```java
public final class CaseInsensitiveString {
    private final String s;
    public CaseInsensitiveString(String s) { this.s = s; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        if (o instanceof String) // String과의 비교도 true로 처리
            return s.equalsIgnoreCase((String) o);
        return false;
    }
}
```

위 코드에서`cis.equals("abc")`는 true지만,`"abc".equals(cis)`는 false가 되어 대칭성을 위반한다.

**추이성 위반 예시**

```java
public class Point {
    private final int x, y;
    // equals: x, y만 비교
}

public class ColorPoint extends Point {
    private final Color color;
    // equals: x, y, color까지 비교
}
```

Point와 ColorPoint를 섞어서 비교하면 추이성이 깨질 수 있다.

### **올바른 equals 구현 예시**

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true; // 반사성
    if (!(o instanceof Book)) return false; // 타입 체크 및 null-불일치
    Book other = (Book) o;
    return this.name.equals(other.name)
        && this.published == other.published
        && this.content.equals(other.content); // 중요한 필드 비교
}
```

- 반드시 Object 타입을 파라미터로 받아야 한다.
- instanceof로 타입 체크를 하고, 필드별 값 비교를 한다.
- null 체크는 instanceof가 대신 처리한다.

### **equals 재정의 시 주의점**

- equals를 재정의하면 반드시 hashCode도 재정의한다.
- 필드 비교는 논리적으로 중요한 필드만 대상으로 한다.
- 성능상, 먼저 다를 확률이 높은 필드부터 비교하는 것이 좋다.
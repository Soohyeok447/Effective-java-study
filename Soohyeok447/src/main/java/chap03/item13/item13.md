# item13. clone 재정의는 주의해서 진행하라

-   `clone()`은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야 함
-   기본 구현은 얕은 복사여서 가능하면 **복사 생성자**나 **복사 팩터리 메서드**를 사용하는 것이 더 나음

### clone의 기본동작

-   `super.clone()`은 단순 필드 복사 (얕은 복사)만 수행함

```java
@Override
public Object clone() throws CloneNotSupportedException {
    return super.clone();
}
```

### deep copy를 하려면 참조 필드를 각각 clone 해줘야 함

```java
@Override
public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        result.elements = elements.clone(); // 참조 필드 복사
        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

### clone() 대안: 복사 생성자 & 복사 팩터리

-   복사생성자 (변환 생성자): 자신과 같은 클래스의 인스턴스를 인수로 받는 생성자

```java
public Yum(Yum yum) {...};
```

-   복사팩터리 (변환 팩터리): 복사 생성자를 모방한 정적 팩터리

```java
public static Yum newInstance(Yum yum) {...};
```

복사 생성자와 복사 팩터리는 Cloneable/clone 방식보다 나은 면이 많음

### 복사 생성자 & 복사 팩터리 장점

-   생성자를 쓰지 않는 위험한 객체 생성 메커니즘을 사용하지 않음
-   엉성하게 문서화된 규약에 기대지 않음
-   정상적인 final 필드 용법과 충돌하지도 않음
-   불필요한 검사 예외를 던지지 않음
-   형변환도 필요없음
-   해당 클래스가 구현한 인터페이스 타입의 인스턴스를 인수로 받을 수 있음
-   원본 타입에 얽매이지 않고 복제본의 타입을 직접 선택가능
    -   HashSet s -> TreeSet 타입으로 복제 가능

### clone이 위험한 이유 요약

-   복잡한 상속 구조에서 안전하게 동작하지 않을 가능성 매우매우 높음
-   deep copy 구현 시 필드를 일일이 처리해야 함

### 배열은 예외

-   배열의 clone은 런타임 타입과 컴파일타임 모두가 원본 배열과 똑같은 타입의 배열을 반환함
    -   배열을 복제할 때는 배열의 clone 사용 권장됨

---

### 그래도 구현할 때의 주의사항

-   public인 clone 메서드에서는 throws 절을 없애야 함. 예외를 던지지 않아야 사용하기 편하기 때문
-   상속용 클래스는 Cloneable을 구현해서는 안됨 -> 하위 클래스에서 `CloneNotSupportedException()`을 던지도록 구현
-   Cloneable을 구현하는 모든 클래스는 clone을 재정의해야 함
    -   public으로 클래스 자신 타입으로 반환하면 됨

---

### 요약

-   새 인터페이스 만들 때 절대 Cloneable을 확장하지 말 것
-   새 클래스도 이를 구현하면 안됨
-   final 클래스면 구현해도 위험이 크지는 않지만, 성능 최적화 관점에서 별다른 문제 없을 때만 드물게 허용
-   복제는 생성자와 팩터리를 이용할 것
-   배열은 clone 메서드 방식이 가장 깔끔

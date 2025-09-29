## 13. clone 재정의는 주의해서 진행하라
### **Cloneable 인터페이스란**

- Cloneable은 "이 객체는 복제(clone)할 수 있다"는 의도를 나타내는 마커 인터페이스다.
- 메서드는 없고, 단지 Object의 protected clone() 메서드가 예외를 던지지 않게 해준다
- Cloneable을 구현하지 않으면 clone() 호출 시 CloneNotSupportedException이 발생한다.

### **clone 메서드의 동작**

- Cloneable을 구현한 객체에서 Object의 clone()을 호출하면 **필드 단위로 복사한 새 객체**가 만들어진다(기본적으로 shallow copy)
- 하지만, Cloneable 인터페이스 자체에는 clone 메서드가 없으므로 **직접 public clone()을 오버라이드**해야 한다

### **clone 메서드의 일반 규약**

- x.clone() != x (복제 객체는 원본과 다르다)
- x.clone().getClass() == x.getClass() (보통 복제 객체의 타입은 원본과 같다)
- x.clone().equals(x) == true (보통 값이 같지만, 필수는 아니다)
- 복제본은 원본과 독립적이어야 한다. 즉, 내부 상태가 서로 영향을 주지 않아야 한다

### **shallow copy vs deep copy**

| **Shallow Copy** | **Deep Copy** |
| --- | --- |
| 필드 값만 복사, 참조 타입은 같은 객체를 가리킴 | 모든 필드와 참조 객체까지 재귀적으로 복사 |
| 내부에 가변 객체가 있으면 원본/복제본이 서로 영향을 준다 | 완전히 독립적인 객체가 된다 |
| 빠르고 메모리 효율적 | 느리지만 안전하게 분리됨 |
| 불변 객체, 간단한 구조에 적합 | 복잡하거나 가변 객체가 있을 때 필요 |
- clone()의 기본 동작은 shallow copy다.
- 내부에 가변 객체(예: 배열, 컬렉션 등)가 있으면, clone() 오버라이드 시 deep copy를 직접 구현해야 한다

### **잘못된 예시: shallow copy만 하는 경우**

```java
@Override
public Stack clone() {
    try {
        return (Stack) super.clone(); // elements 배열이 공유됨!
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

- 이 경우 복제본과 원본이 같은 배열을 공유해서, 한쪽을 변경하면 다른 쪽에도 영향이 간다.

### **올바른 예시: deep copy 구현**

```java
@Override
public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        result.elements = elements.clone(); // 배열 복제
        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

- elements 배열을 새로 복사해서 원본과 복제본이 완전히 분리된다

### **복잡한 구조(예: 연결리스트)의 deep copy**

```java
private static class Entry {
    final Object key;
    Object value;
    Entry next;

    Entry deepCopy() {
        return new Entry(key, value, next == null ? null : next.deepCopy());
    }
}

@Override
public HashTable clone() {
    try {
        HashTable result = (HashTable) super.clone();
        result.buckets = new Entry[buckets.length];
        for (int i = 0; i < buckets.length; i++)
            if (buckets[i] != null)
                result.buckets[i] = buckets[i].deepCopy();
        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
``` 

- 각 버킷의 연결리스트까지 재귀적으로 복제한다.

### **Cloneable/clone의 문제점**

- clone은 생성자를 호출하지 않으므로, 객체 불변성/초기화 보장이 어렵다.
- final 필드와 호환이 안 좋다.
- 복잡한 객체에서는 deep copy를 직접 구현해야 해서 실수하기 쉽다.
- 인터페이스임에도 메서드가 없어, 일관된 사용이 어렵다.
- checked exception(throws CloneNotSupportedException) 처리도 번거롭다

### **대안: 복사 생성자/복사 팩토리**

```java
// 복사 생성자
public Stack(Stack original) { ... }

// 복사 팩토리
public static Stack newInstance(Stack original) { ... }
```

- 명확하고, final 필드, 불변성, 상속 등과의 호환성이 좋다.
- 복사 과정이 명시적이라 실수할 여지가 적다
## 6. 불필요한 객체 생성을 피하라

- 객체 생성 자체가 항상 비싼 것은 아니지만.. 불필요하게 객체를 반복 생성하지 않고, 재사용할 수 있는 객체는 재사용하는 것이 성능과 메모리 측면에서 효율적이다.

  특히 불변 객체(immutable object)는 언제든 안전하게 재사용할 수 있다!

- 불변 객체, 비싼 객체, 어댑터 등은 재사용을 적극적으로 고려한다.
- 단, **방어적 복사**가 필요한 경우(외부에서 객체 상태가 변할 수 있는 경우)에는 객체를 재사용하지 않고 새로 생성해야 한다.
    - 방어적 복사가 필요한 상황에서 재사용하면 심각한 버그가 발생할 수 있다

### **1. String 객체 생성**

```java
// 비효율적인 예
String s = new String("java"); // 매번 새로운 String 객체 생성// 효율적인 예
String s = "java"; // String pool에서 기존 객체 재사용
```

- `new String("java")`는 매번 새로운 객체를 생성한다.
- `"java"`리터럴은 JVM의 String pool에 저장되어 같은 값의 리터럴이 있으면 기존 객체를 재사용한다

### **2. 불변 객체의 재사용**

```java
// 비효율적인 예
Boolean trueValue = new Boolean(true); // 불필요한 객체 생성// 효율적인 예
Boolean trueValue = Boolean.valueOf(true); // 캐싱된 객체 재사용
```

- `Boolean.valueOf(boolean)`은 내부적으로 미리 생성된 Boolean.TRUE, Boolean.FALSE를 반환한다.
- 불변 객체는 상태가 변하지 않으므로 여러 번 생성할 필요 없이 재사용이 가능하다

### **3. 비싼 객체의 캐싱**

정규표현식 패턴 객체는 생성 비용이 크기 때문에 캐싱해서 재사용한다.

```java
// 비효율적인 예
static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}
// 호출 때마다 Pattern 객체 새로 생성
// 효율적인 예
private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
}
// Pattern 객체를 static final로 미리 만들어두고 재사용
```

- Pattern 객체는 생성 비용이 크기 때문에 반복적으로 사용할 경우 **캐싱해서 재사용**하는 것이 성능상 유리하다

### **4. 오토박싱 주의**

```java
// 비효율적인 예
Long sum = 0L;
for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    sum += i; *// 매번 새로운 Long 객체 생성 (오토박싱)*
}

// 효율적인 예
long sum = 0L;
for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    sum += i; // 기본형 long 사용, 불필요한 객체 생성 없음
}
```

- 오토박싱은 기본형과 래퍼 객체를 자동 변환해주지만, 반복문에서 래퍼 객체를 사용하면 불필요한 객체가 대량으로 생성된다.
- 기본형을 사용하면 이런 낭비를 막을 수 있다

### **5. 어댑터(뷰) 객체의 재사용**

```java
Map<String, Integer> map = new HashMap<>();
Set<String> set1 = map.keySet();
Set<String> set2 = map.keySet();
set1.remove("key");
// set2도 영향을 받음 (같은 객체를 참조)
```

- Map의`keySet()`등은 매번 새로운 Set 객체를 반환하는 것이 아니라, 같은 Map에 대해 동일한 어댑터 객체를 반환한다.
- 어댑터 객체는 내부적으로 원본 객체만 참조하고 별도의 상태를 가지지 않으므로 재사용이 가능하다
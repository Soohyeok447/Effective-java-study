자바에서는 불필요한 객체 생성이 성능에 영향을 줄 수 있다. 특히, 값이 불변인 객체나 재사용 가능한 객체를 불필요하게 반복해서 생성하는 경우 메모리와 성능을 낭비할 수 있다. 따라서 객체를 재사용하거나 불변 객체를 활용해야한다.

### **불필요한 객체 생성**

```java
// 잘못된 예시: 매번 새로운 String 객체를 생성
String str = new String("Hello");
```

이 코드는 `"Hello"` 가 이미 String 상수 풀에 존재하는데도 불구하고, `new String()`을 사용하여 불필요한 String 객체를 생성한다. String은 불변 객체이므로, 굳이 새로운 객체를 만들지 않아도 된다.

<aside>

**해결**

```java
// String 상수 풀에서 재사용
String str = "Hello";
```

</aside>

### **객체 재사용**

```java
// 잘못된 예시: 매번 새로운 Pattern 객체를 생성
Pattern pattern = Pattern.compile("[a-zA-Z]+");
Matcher matcher = pattern.matcher(input);
```

이 코드는 반복적으로 실행될 때마다 새로운 `Pattern` 객체를 생성하므로 비효율적이다. `Pattern` 객체는 불변이므로 재사용하자!

<aside>

**해결**

```java
// Pattern 객체 재사용
private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]+");

Matcher matcher = PATTERN.matcher(input);
```

</aside>

`PATTERN`을 클래스 상수로 선언하여 재사용함으로써, 매번 객체를 생성하는 대신 이미 존재하는 객체를 사용

### 정리

> 불필요한 객체 생성을 피하고, 불변 객체는 재사용하는 것이 성능과 메모리 효율에 좋다.
String, Boolean, Integer 같은 클래스는 가능한 한 객체를 재사용하자.
>
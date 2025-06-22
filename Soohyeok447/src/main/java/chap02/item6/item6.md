# item 6. 불필요한 객체 생성을 피하라

- 객체를 **무조건 새로 생성하는 것보다**, **재사용 가능한 객체는 재사용하는 것이 낫다**
- 특히 **불변 객체** 는 캐싱하거나 상수로 재사용하면 성능과 메모리 측면에서 유리함
- 반복적으로 객체를 생성하면 GC 부하 증가 + 성능 저하 가능성 있음

### 불필요한 객체 생성하는 간단한 예시

```java
String s = new String("bikini"); // 'new String()' is redundant
Boolean b = new Boolean(true);  // 'Boolean(boolean)' is deprecated since version 9 and marked for removal 
```
#### redundant(중복)인 이유?
- "bikini" 는 String 리터럴 풀(string pool) 에 저장되는 불변 상수임
- new String("bikini")를 쓰면 기존 풀에 있는 "bikini"를 참조하지 않고, 다시 새로운 String 객체를 heap에 생성함
- 즉, 동일한 값의 String을 굳이 하나 더 만드는 불필요한 행동

```java
String s = "bikini"; // 리터럴을 직접 사용 (string pool에서 재사용)
```

#### 문자열 상수 풀?
- 자바에서 문자열 리터럴은 String Pool이라는 특수한 메모리 영역에 저장됨 (Heap 영역)
  - "bikini"와 같은 리터럴은 JVM 시작 시 클래스 로딩 단계에서 상수 풀에 등록됨
- 그 이후 "bikini"라는 리터럴이 등장하면 이미 풀에 있는 동일 객체를 재사용함
```java
String a = "bikini";
String b = "bikini";
System.out.println(a == b); // true
```

#### Heap에 저장이 된다는 건?
- 문자열 리터럴은 여전히 JVM이 클래스 로딩 시 자동으로 intern() 처리해서 재사용함
  - String.intern()은 컴파일 타임에 자동으로 해당 문자열을 String Pool에 등록해서 하나의 객체로 재사용함
- 하지만 이제 GC가 필요하면 이 문자열 풀에 있는 객체도 정리할 수 있음
  - 그래서 Heap 공간이 부족하면 오래 쓰이지 않은 리터럴도 GC될 수 있음

#### Boolean 생성자는 JDK 9부터 deprecated됨
Boolean은 불변이고, 캐시된 상수 인스턴스를 쓰면 되기 때문

```java
// Boolean.TRUE 반환
Boolean b = Boolean.valueOf(true); // Unnecessary boxing (내부에서 Boolean.TRUE 리턴)

Boolean b2 = Boolean.TRUE;
```

### 생성자 대신 정적 팩터리 메서드 를 제공하는 불변 클래스에서는 정적 팩터리 메서드를 사용해라
(item1)
- 생성자는 호출할 때마다 새로운 객체를 만들지만 팩터리 메서드는 그렇지 않음
- new Boolean()이 JDK 9부터 Deprecated된 것도 같은 이유

### 생성 비용이 비싼 객체는 캐싱하여 재사용하라
- 객체 생성 비용이 크거나 내부에 무거운 리소스를 가지는 경우 (Pattern, Connection, Formatter 등)
- 자주 사용되는 경우 캐싱이 확실히 유리
- valueOf(), static final, 팩터리 메서드 등으로 캐싱
```java
Pattern p = Pattern.compile("[a-z]+"); // 컴파일 비용이 큼

// 매번 컴파일
boolean result = "[a-z]+".matches("hello");

// static final로 캐싱
private static final Pattern PATTERN = Pattern.compile("[a-z]+");
boolean result = PATTERN.matcher("hello").matches();
```

### 오토박싱도 불필요한 객체를 만들어낸다
```java
Long sum = 0L;

for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    // sum += i는 내부적으로 sum = Long.valueOf(sum.longValue() + i); 로 변환됨;
    sum += i; // 오토박싱이 돼서 Long 객체 수억 개가 생성됨 (성능 저하, GC 부하)
}
```

```java
long sum = 0L; // primitive 타입으로 연산

for (long i = 0; i <= Integer.MAX_VALUE; i++) {
    sum += i; 
}
```
- 가능하면 기본형을 사용하고, 객체형은 nullable 하거나 컬렉션에서만 사용할 것

### "객체 생성은 무조건 비싸다"는 오해
- 예전 JVM에서는 GC가 비효율적이었고, 작은 객체라도 생성 자체가 성능 병목이었음
- 하지만 요즘 JVM은 매우 최적화되어 있음
- 작은 객체 생성은 eden 영역에서 빠르게 처리되고 회수도 쉽기 때문에 부담이 크지 않음

=> 재사용할 수 있는건 재사용하고 반복적이고 쓸데없는 객체 생성을 피하라는 뜻

### 커넥션 풀처럼 아주 무거운 객체가 아니라면 직접 객체 풀을 만들지 마라
- 객체 풀은 일반적으로 초기화 비용이 매우 높은 객체(Connection, Thread 등) 에만 유효
- String, Point, UserDto 같은 가벼운 객체에 대해 객체 풀을 만들면
  - 메모리 낭비
  - 가독성 저하
  - GC가 메모리 회수를 못함

  => 오히려 성능 저하

#### JVM은 이미 효율적임
- 현대 JVM은 Eden -> Survivor -> Old 영역을 효율적으로 관리함
- 자주 생성되고 짧게 사라지는 객체는 GC가 엄청 빠르게 회수하므로 객체 풀보다 나음

#### Eden Survivor Old 영역
- Eden 영역
  - 새로운 객체가 처음 생성되는 곳 (new 연산자)
  - **대부분의 객체는 여기서 생성되고 여기서 사라짐**
  - 가비지 컬렉터의 주요 대상이 되는 공간
  - GC가 발생하면 살아있는 객체만 Survivor로 옮기고, 나머지는 제거됨
- Survivor 영역 (S0, S1)
  - Eden에서 살아남은 객체가 이동되는 **임시 보존 공간**
  - 두 개의 영역(S0, S1) 이 번갈아 가며 사용됨
    - 한 쪽은 복사 대상
    - 한 쪽은 복사받는 대상
- Old 영역
  - Survivor 영역을 여러 번 거친 "장수 객체" 가 올라가는 공간
  - **대부분의 객체는 Old까지 가지 못하고 GC에서 사라짐**
  - Full GC가 발생할 때 정리 대상 
  - Old 영역은 Full GC 대상이므로 GC 속도가 매우 느림 **여기서 병목이 자주 발생함**

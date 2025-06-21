## 1. 생성자 대신 정적 팩토리 메서드를 고려하라

### 정적 팩토리 메서드의 예시

```java
public static Boolean valueOf(boolean b) {
	return b ? Boolean.TRUE : Boolean.FALSE;
}

------- 사용 예시 ---------
Boolean.valueOf(true)
```

### 정적 팩토리 메서드가 생성자보다 좋은 부분

1. **이름을 가질 수 있다**

   생성자는 `클래스(param, param)` 과 같은 구조로 쓰이는데, 매개변수와 생성자 자체로는 반환될 객체의 특징을 제대로 설명하지 못한다

   반면, 정적 팩토리 메서드를 쓴다면 어떤 객체가 반환될지를 이름 붙여 명확하게 보여줄 수 있다.

   ex) `BigInteger.probablePrime` → 값이 소수인 BigInteger를 반환하는 정적 팩토리 메서드

   그리고, 한 클래스에 시그니처가 같은 생성자가 여러개 필요할 때 정적팩토리 메서드로 각각의 차이가 잘 드러나는 이름을 지어줄 수도 있다.

2. **호출될 때마다 인스턴스를 새로 생성하지 않아도 된다**
    - 내부적으로 기존 객체를 재사용(캐싱)하거나, 싱글턴 패턴처럼 같은 객체를 반환할 수 있다.
    
    ```java
   // 예시코드 
   public class Settings {
        private Settings() {} // 생성자 막기
        private static final Settings INSTANCE = new Settings();
    
        public static Settings getInstance() {
            return INSTANCE;
        }
    }
    ```

3. **반환 타입의 하위 타입 객체를 반환할 수 있다**

   반환할 객체의 클래스를 자유롭게 선택할 수 있는 유연성이 생긴다

   반환 타입은 interface나 abstract class로 하고, 실제 구현체는 숨길 수 있다.

    ```java
    public interface Fruit {
        String getName();
    }
    
    class Apple implements Fruit {
        public String getName() { return "Apple"; }
    }
    
    class Banana implements Fruit {
        public String getName() { return "Banana"; }
    }
    
    public class FruitFactory {
        public static Fruit of(String name) {
            if ("apple".equalsIgnoreCase(name)) return new Apple();
            else if ("banana".equalsIgnoreCase(name)) return new Banana();
            else throw new IllegalArgumentException();
        }
    }
    
    // 사용 예시
    Fruit f = FruitFactory.of("apple"); // 실제로는 Apple 객체
    ```

4. **입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다**

   예: **`EnumSet.of()`**는 원소 개수에 따라 RegularEnumSet, JumboEnumSet 등 서로 다른 구현체를 반환한다

    ```java
    public abstract class Shape {}
    
    class Circle extends Shape {}
    class Rectangle extends Shape {}
    
    public class ShapeFactory {
        public static Shape createShape(String type) {
            if ("circle".equals(type)) return new Circle();
            else if ("rectangle".equals(type)) return new Rectangle();
            else throw new IllegalArgumentException();
        }
    }
    
    // 사용 예시
    Shape s1 = ShapeFactory.createShape("circle");    // Circle 객체 반환
    Shape s2 = ShapeFactory.createShape("rectangle"); // Rectangle 객체 반환
    ```

5. **정적 팩토리 메서드를 작성할 때는 반환할 객체의 클래스가 존재하지 않아도 된다** (나중에 바꿀 수 있다)

    ```java
    // 예를 들어, 다음과 같이 구현체를 쉽게 바꿀 수 있습니다.
    public interface MessageService {
        void send(String msg);
    }
    
    class EmailService implements MessageService {
        public void send(String msg) { System.out.println("Email: " + msg); }
    }
    
    class SmsService implements MessageService {
        public void send(String msg) { System.out.println("SMS: " + msg); }
    }
    
    public class MessageServiceFactory {
        private static boolean useEmail = true;
    
        public static MessageService getInstance() {
            if (useEmail) return new EmailService();
            else return new SmsService();
        }
    }
    
    // 사용 예시
    MessageService ms = MessageServiceFactory.getInstance();
    ms.send("Hello!");
    
    내부 구현만 바꿔주면, 클라이언트 코드는 그대로 사용할 수 있다
    ```


### 단점

1. 생성자를 private으로 막고 정적 팩토리 메서드만 제공한다면 하위 클래스를 만들 수 없다.
2. 생성자는 항상 명확하게 보이지만, 정적 팩토리 메서드는 메서드 이름이 자유로워서 문서를 잘 봐야 찾을 수 있다. 그래서 자주 쓰는 네이밍 컨벤션을 따르는 것이 좋다.

자주 쓰는 네이밍 컨벤션

| **메서드명** | **설명** | **예시** |
| --- | --- | --- |
| from | 매개변수 하나 받아 변환 | **`Date.from(instant)`** |
| of | 여러 매개변수 받아 집계 | **`Set.of("A", "B")`** |
| valueOf | from, of의 더 자세한 버전 | **`BigInteger.valueOf(123)`** |
| getInstance | 인스턴스를 반환, 같은 인스턴스임을 보장하지 않음 | **`StackWalker.getInstance(options)`** |
| newInstance | 항상 새로운 객체 반환 | **`Array.newInstance(classObj, len)`** |
| getType | 반환 타입이 다른 경우 | **`Files.getFileStore(path)`** |
| newType | 항상 새로운 객체, 반환 타입이 다른 경우 | **`Files.newBufferedReader(path)`** |
| type | getType, newType의 간결 버전 | **`Collections.list(legacyList)`** |
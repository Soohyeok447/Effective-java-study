**중첩 클래스 (Nested Class)**

중첩 클래스는 다른 클래스 안에 정의된 클래스를 의미한다. 보통 중첩 클래스는 자신을 감싸는 바깥 클래스에서만 사용되며, 바깥 클래스와 깊은 관계가 있는 경우에 사용한다. 바깥 클래스와 독립적으로 사용되거나, 다른 쓰임새가 있다면 톱레벨 클래스로 만들어야 한다.

### **중첩 클래스의 종류**

### 1. 정적 멤버 클래스 (Static Member Class)

- 바깥 클래스의 인스턴스와 독립적으로 존재할 수 있다.
- 주로 바깥 클래스와 함께 사용되는 public 도우미 클래스로 많이 사용된다.

예시:

```java
public class Calculator {
    public static class Operation {
        // 정적 멤버 클래스
    }
}
```

- private 정적 멤버 클래스는 보통 바깥 클래스의 일부 기능을 담당할 때 사용

### 2. 비정적 멤버 클래스 (Non-static Member Class)

- 비정적 멤버 클래스는 바깥 클래스의 인스턴스와 강하게 연결돼 있다. 바깥 클래스의 인스턴스 없이 생성할 수 없디.
- 바깥 클래스의 필드나 메서드에 직접 접근할 수 있고, 이 관계는 인스턴스가 만들어질 때 확립된다.

예시:

```java
public class OuterClass {
    private String name;

    public OuterClass(String name) {
        this.name = name;
    }

    public class InnerClass {
        public String getOuterName() {
            // 바깥 클래스의 필드에 접근 가능
            return name;
        }
    }
}
```

### 정적 vs 비정적 멤버 클래스

- 정적 멤버 클래스는 바깥 클래스와 독립적으로 존재 가능
- 비정적 멤버 클래스는 바깥 인스턴스와 연결되며, 그 인스턴스에 접근할 수 있다.

---

### 익명 클래스 (Anonymous Class)

- 이름이 없고, 선언과 동시에 인스턴스가 생성되는 클래스
- 오직 비정적 문맥에서만 바깥 인스턴스에 접근할 수 있다.
- 다른 클래스 상속이나 인터페이스 구현을 동시에 할 수 없다.

예시:

```java
public class AnonymousExample {
    public void print() {
        new Operator() {
            public void operate() {
                System.out.println("Operation executed");
            }
        }.operate();
    }
}

interface Operator {
    void operate();
}
```

### **익명 클래스의 특징**

- 한 번만 사용할 클래스에 적합하다.
- 코드가 길면 가독성이 떨어질 수 있다.

### **지역 클래스 (Local Class)**

- 지역 변수처럼 선언해서 사용할 수 있다.
- 익명 클래스와 비슷하지만 이름을 가질 수 있고 반복 사용이 가능하다.
- 비정적 문맥에서만 바깥 인스턴스를 참조할 수 있다.

예시:

```java
public class LocalExample {
    public void print() {
        class LocalClass {
            public void display() {
                System.out.println("Local class");
            }
        }
        LocalClass localClass = new LocalClass();
        localClass.display();
    }
}
```

### 중첩 클래스 선택 기준

- 메서드 밖에서도 사용하거나 길이가 긴 클래스라면 멤버 클래스로 만든다.
- 바깥 인스턴스를 참조할 필요가 없다면 static으로 선언한다.
- 한 메서드 안에서만 사용하고, 한 번만 인스턴스를 만들 거라면 익명 클래스나 지역 클래스로 구현한다.
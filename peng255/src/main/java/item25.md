## 25. 톱레벨 클래스는 한 파일에 하나만 담으라

### 요점 정리

- 자바 컴파일러는 한 소스 파일에 여러 톱레벨 클래스를 정의하는 걸 허용하지만…

  이렇게 하면 여러 정의가 중복될 수 있고, 컴파일 순서에 따라 프로그램 동작이 달라지는 치명적 문제가 발생한다.

  → 따라서 톱레벨 클래스(또는 인터페이스)는 반드시 한 파일에 하나씩 정의해야 한다.


### 문제 상황 코드 예시

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }
}
```

```java
// Utensil.java (한 파일에 Utensil과 Dessert를 같이 정의한 경우 - 나쁜 예)
class Utensil {
    static final String NAME = "pan";
}

class Dessert {
    static final String NAME = "cake";
}
```

```java
// Dessert.java (다른 파일에 동일한 두 클래스를 중복 정의한 경우)
class Utensil {
    static final String NAME = "pot";
}

class Dessert {
    static final String NAME = "pie";
}
```

이러면…

- 컴파일 순서에 따라 프로그램 결과가 달라지고, 컴파일 에러가 날 수도 있다.
- 예)`javac Main.java Dessert.java` → 컴파일 에러(중복 정의)
- 예)`javac Dessert.java Main.java`→ `"potpie"` 출력 → 의도와 전혀 다름
- 즉, 컴파일러가 어느 파일에서 클래스를 찾느냐에 따라 동작이 달라지는 매우 위험한 상황

### 해결법:

- 각 톱레벨 클래스는 **각자 별도의 소스 파일에 분리**한다.
- 예를 들어,`Utensil.java`는 Utensil만, `Dessert.java`는 Dessert만 포함한다.

- 여러 톱레벨 클래스를 한 파일에 넣고 싶다면 static 멤버 클래스로 바꾸자… (item 24 참고)

    ```java
    // Test.java
    public class Test {
        public static void main(String[] args) {
            System.out.println(Utensil.NAME + Dessert.NAME);
        }
    
        private static class Utensil {
            static final String NAME = "pan";
        }
    
        private static class Dessert {
            static final String NAME = "cake";
        }
    }
    ```
  


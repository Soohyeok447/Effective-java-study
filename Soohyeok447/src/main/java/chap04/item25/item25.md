# item25. 톱레벨 클래스는 한 파일에 하나만 담으라

### 핵심

하나의 소스 파일(.java)에는 최상위(톱레벨) 클래스를 오직 하나만 포함하라

### 문법적으로는 문제가 없다..

자바 컴파일러는 하나의 .java 파일에 여러 톱레벨 클래스를 넣는 것을 허용하지만 컴파일 순서에 따라 동작이 달라지는 심각한 문제를 일으킬 수 있음

### 문제가 생기는 예시

#### Utensil.java

```java
class Utensil {
    static final String NAME = "pan";
}

class Dessert {
    static final String NAME = "cake";
}
```

#### Dessert.java

```java
class Dessert {
    static final String NAME = "pie"; // 'cake'가 아님
}
```

-> 컴파일 순서에 따라 프로그램의 동작이 바뀌는 찾기 힘든 버그발생..

### 해결책

톱레벨 클래스는 무조건 자신과 이름이 같은 별도의 소스 파일에 하나씩만 정의

#### Utensil.java

```java
class Utensil {
    static final String NAME = "pan";
}
```

#### Dessert.java

```java
class Dessert {
    static final String NAME = "cake";
}
```

-> 컴파일 순서에 상관없이 언제나 예측 가능하게 동작하게 됨

### 정적 멤버 클래스를 사용하는 방법

만약 여러 개의 작은 클래스가 서로 밀접하게 연관되어 있어서 하나의 파일에 묶어두고 싶으면 정적 멤버 클래스를 쓸 수 있음

```java
public class Test{
    public static void main(String[] args){
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

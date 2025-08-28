# item23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라

### 태그 달린 클래스?

하나의 클래스가 여러 종류의 '타입'을 가질 수 있고, 현재 어떤 타입인지를 나타내는 태그(tag) 필드를 통해 동작을 분기하는 클래스

### 태그 달린 클래스는 단점이 한가득이다

-   열거 타입 선언, 태그 필드, switch 문 등 쓸데 없는 코드가 많음
-   여러 구현이 한클래스에 혼합돼 있어서 가독성도 나쁨
-   다른 의미를 위한 코드도 언제나 함께 하니 메모리도 많이 씀
-   필드들을 final로 선언하려면 해당 의미에 쓰이지 않는 필드들까지 생성자에서 초기화해야 함
-   또 다른 타입같은걸 추가하려면 코드를 수정해야 함 (switch문 같은 거로) -> 확장성이 최악 + OCP 위반
-   인스턴스의 타입만으로는 현재 나타내는 의미를 알 길이 전혀 없음
-   실수로 case문 빼먹으면 런타임 에러가 발생할 수도 있음

> 즉 태그 달린 클래스는 장황하고 오류를 내기 쉽고 비효율적임

### 클래스의 계층구조를 활용하는 서브타이핑을 사용하자

다형성을 활용하는 자연스러운 방법임

1. 계층구조의 루트가 될 추상 클래스를 정의
2. 태그 값에 따라 동작이 달라지는 메서드들을 루트 클래스의 추상 메서드로 선언
3. 태그 값에 상관없이 동작이 일정한 메서드들을 루트 클래스에 일반 메서드로 추가
4. 모든 하위 클래스에서 공통으로 사용하는 데이터 필드들도 전부 루트 클래스로 올림

```java
// 루트가 될 추상 클래스
abstract class Figure {
    abstract double area();
}

// 원
class Circle extends Figure {
    final double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

// 사각형
class Rectangle extends Figure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
```

### 정리

태그 달린 클래스를 써야 하는 상황은 거의 없고 계층구조로 대체하는 방법을 생각할 것

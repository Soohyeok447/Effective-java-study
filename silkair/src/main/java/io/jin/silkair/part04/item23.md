### **태그 달린 클래스의 문제점**

태그 달린 클래스는 하나의 클래스가 여러 다른 의미를 가지는 구조로, 태그 필드를 사용해 특정 동작을 결정하는 방식을 사용한다. →복잡하고 비효율적

1. **불필요한 코드 증가**: 열거 타입, 태그 필드, `switch`문 등이 추가되어 쓸데없는 코드가 많아지고 가독성이 떨어진다.
2. **가독성 저하**: 여러 의미가 한 클래스에 혼합되어 있어 클래스 자체가 복잡해진다.
3. **메모리 낭비**: 사용하지 않는 필드들이 클래스에 포함되어 불필요한 메모리를 사용한다.
4. **유연성 부족**: 새로운 의미나 동작을 추가하려면 코드 수정이 필수.
5. **오류 발생 가능성 증가**: 태그 값에 따라 동작이 달라지기 때문에 잘못된 태그 값으로 오류를 유발할 가능성이 높다.

### **태그 달린 클래스 예시**

```java
lass Figure {
    enum Shape { RECTANGLE, CIRCLE }

    // 태그 필드
    final Shape shape;

    // 사각형일 때 사용
    double length;
    double width;

    // 원일 때 사용
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    // 면적 계산
    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```

→ 태그(`shape`)에 따라 동작을 다르게 구현하는 방식인데, 불필요한 복잡성과 효율성 저하가 발생

---

### **클래스 계층구조 활용하기**

태그 달린 클래스의 문제를 해결하려면, 클래스 계층구조를 사용하는 것이 좋다. 추상 클래스와 구체 클래스를 사용하여 의미를 명확히 분리하고, 각 클래스에 고유한 필드와 동작을 추가하자.

1. 추상 클래스 정의: 공통된 동작을 정의할 루트 클래스를 생성
2. 구체 클래스 정의: 각 의미별로 하위 클래스를 만들어, 태그가 필요 없이 해당 의미에 맞는 동작을 구현
3. 추상 메서드 활용: 태그에 따라 달라지는 동작은 추상 메서드로 선언하고, 하위 클래스에서 이를 구현

### **클래스 계층구조 예시**

```java
// 추상 클래스: 루트 클래스
abstract class Figure {
    abstract double area();
}
```

### **원 클래스**

```java
class Circle extends Figure {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}
```

### **사각형 클래스**

```java
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

### **장점**

1. **불필요한 코드 제거**: 태그 필드, `switch`문, 의미 없는 필드 등을 제거하여 코드가 훨씬 간결해짐.
2. **가독성 향상**: 의미별로 클래스를 분리하여 코드가 명확해지고 가독성이 좋아짐.
3. **메모리 효율성**: 필요 없는 필드가 없어서 메모리 사용이 줄어듬.
4. **유연성 증가**: 새로운 의미나 동작을 추가할 때 **새로운 클래스만 만들면 되므로 유연성**이 높아짐.
5. **컴파일 타임 타입 검사**: 각 클래스가 고유한 타입이므로 컴파일 시점에 타입 검사가 가능해, 오류 가능성이 줄어듦.
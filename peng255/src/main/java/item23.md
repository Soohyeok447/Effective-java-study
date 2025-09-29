## 23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라

### 요점 정리

- “태그 필드(tag field)”로 여러 타입/상태를 한 클래스에 몰아넣지 말자!!

  → 서브클래스 계층 구조로 분리하는 게 더 깔끔하고 안전하다.

- 클래스 인스턴스에 “종류를 구분하는 tag 필드”가 있으면 계층 구조로 바꿔라

  계층 구조는 가독성, 안전성, 유지보수, 확장성 면에서 더 좋다

- 타입에 따라 동작(메서드)이나 값이 다르면, 추상 타입 + 서브클래스 구조를 적극 활용한다

### 태그 달린 클래스가 뭔데??

```java
// 학생(Student) 객체가 "학생" 또는 "선생님" 역할이 둘 다 가능한 구조 (태그 필드로 구분)
class Person {
    enum Type { STUDENT, TEACHER }
    final Type type;
    int studentId;   // 학생일 때만 사용
    String subject;  // 선생님일 때만 사용

    // 학생 생성자
    Person(int studentId) {
        type = Type.STUDENT;
        this.studentId = studentId;
    }
    // 선생님 생성자
    Person(String subject) {
        type = Type.TEACHER;
        this.subject = subject;
    }

    void printRole() {
        switch (type) {
            case STUDENT:
                System.out.println("나는 학생! 학번: " + studentId);
                break;
            case TEACHER:
                System.out.println("나는 선생님! 과목: " + subject);
                break;
        }
    }
}
```

→ 위 예시를 보면 Person이라는 한 클래스가 학생이기도 하고, 선생님이기도 한 두가지 역할을 갖고 있다

type이라는 **태그 필드**로 현태 인스턴스가 **학생인지, 선생님인지 구분**한다.

여기서 문제점!

학생이든 선생님이든 각자 필요 없는 필드를 가지고 있고, 분기마다 switch(type)이나 if문으로 구분해야 한다.
<br/>그리고 종류가 추가되면 모든 곳에서 switch/case문을 일일이 수정해야한다..
<br/>타입의 안정성을 컴파일러가 전혀 보장하지 않는다는 문제도 있다.

이걸 계층 구조로 변경하자!

### 태그 달린 클래스 → 계층구조로 바꿔보면?

```java
// 1) 추상 클래스: 공통 타입 및 추상 동작 정의
abstract class Person {
    abstract void printRole();
}

// 2) 학생 클래스: 학생 역할 구현
class Student extends Person {
    private final int studentId;

    Student(int studentId) {
        this.studentId = studentId;
    }

    @Override
    void printRole() {
        System.out.println("나는 학생! 학번: " + studentId);
    }
}

// 3) 선생님 클래스: 선생님 역할 구현
class Teacher extends Person {
    private final String subject;

    Teacher(String subject) {
        this.subject = subject;
    }

    @Override
    void printRole() {
        System.out.println("나는 선생님! 과목: " + subject);
    }
}
```

```java
사용 예시
public class Main {
    public static void main(String[] args) {
        Person p1 = new Student(12345);
        Person p2 = new Teacher("수학");

        p1.printRole(); // 나는 학생! 학번: 12345
        p2.printRole(); // 나는 선생님! 과목: 수학
    }
}
```

- 태그 값(`type`) 대신, 역할별로 서브클래스를 따로 만들어 역할별 필드와 동작을 구분한다
- switch문, 태그 필드 불필요 → 가독성, 유지보수성, 안전성 대폭 향상
- 각 인스턴스는 정확한 데이터만 갖고 있어 메모리 낭비가 없다
- 타입이 곧 역할/행위 의미 → 클라이언트 코드를 더욱 명확하고 엄격하게 만든다
# item12. toString을 항상 재정의하라

- `toString()`은 객체를 문자열로 표현하는 메서드로, **디버깅, 로깅, 출력**에 필수임
- 기본 구현은 쓸모없음 (`클래스이름@16진수해시코드` 를 반환함)
- **클래스의 핵심 정보가 담긴 사람이 읽기 쉬운 형태**로 작성할 것
- **규약에서도 모든 하위 클래스에서 재정의하라고 함**

### 좋은 toString 예시
```java
public class User {
    private final int age, height;

    @Override
    public String toString() {
        return String.format("User(age=%d, height=%d)", age, height); // User(age=28, height=178)
    }
}
```

### 하면 안되는 예시
```java
User user = new User(28, 178);

System.out.println(user); // 원하는 내부 정보가 안나옴
```
- 클래스 이름 + 해시코드만 보여줘서 의미가 없음


### 추가 팁
- 정적 유틸리티 클래스는 toString을 제공할 이유가 없음
- 대부분의 열거 타입도 자바가 완벽한 toString을 이미 제공함
- 사람이 읽기 쉬운 정보를 담되, 너무 많은 정보를 넣지 말 것
- 컬렉션/배열 등 내부 구조가 있는 클래스는 원소도 함께 출력되게 구현할 것
- **대부분의 IDE에서 자동생성하면 웬만하면 기본은 함**


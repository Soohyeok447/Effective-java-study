## 27. 비검사(unchecked) 경고를 제거하라

### 요점 정리

- Generic 코드를 작성하다 보면 unchecked 경고가 발생하는 일 일어남!

  → 언제 이런게 일어나냐?

    1. 타입 파라미터를 명확히 쓰지 않았을 때 (`List list = new ArrayList();` 등)
    2. 실제 타입이 뭔지 알 수 없는데 강제 형변환(casting) 시킬 때
    3. 불가피하게 제네릭 배열을 만들어야 할 때 (예: `List<Integer>[] arr = new ArrayList;`)

- 그럼 어떻게 경고를 없앰?
    - 대부분… 제네릭 타입을 명확히 지정하면 없어짐
    - 논리적으로 완전히 타입 안전이 보장되는 상황이고 꼭 제네릭을 유지해야 하면 `@SuppressWarnings("unchecked")` 어노테이션을 쓰자.

      → 경고를 최소 범위(지역 변수·짧은 메서드)로만 억제하기

      클래스 전체나 긴 메서드에 붙이지 말고!! **꼭 필요한 좁은 범위**에만 붙이고 반드시 주석도 남기자



### 코드 예시

- 경고 없이 안전한 코드 예시

```java
List<String> list = new ArrayList<>();
list.add("hello");

// 안전: 컴파일러가 타입체크, 강제 변환 불필요
String str = list.get(0);
```

- 이런 코드는 unchecked 경고가 발생한다

```java
Object obj = "hello";
List<String> list = new ArrayList<>();
list.add((String)obj); // 안전하긴 하지만, 컴파일러는 타입 확신X
```

```java

// 아래처럼 복잡한 상황일 때 주로 경고 발생
List<?> unknownList = new ArrayList<String>(); 

@SuppressWarnings("unchecked") // result가 실제로 T[]임을 개발자가 100% 확신하는 경우만 허용
<T> T[] toArray(List<?> list, T[] a) {

    // 실제로는 타입이 항상 맞음 (T[]로 복사), 하지만 컴파일러가 알 방법이 없음
    return (T[]) list.toArray(a);
}
```

- 이렇게는 절대 하지 말자!!! 전체 클래스에 Suppress 어노테이션 xx

```java
// 무턱대고 전체 클래스/@SuppressWarnings("unchecked") 쓰기
@SuppressWarnings("unchecked")
public class Danger { ... }
// -> 이렇게 하면 진짜 오류가 섞여도 경고를 못 보고 놓침!
```
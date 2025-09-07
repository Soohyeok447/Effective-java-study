### 비검사 경고란?

- 제네릭 타입을 제대로 사용하지 않았을 때 발생하는 컴파일러 경고
    
    → 제네릭을 사용할 때 타입 매개변수를 명시하지 않거나 타입을 안전하게 변환할 수 없는 경우에 발생한다.
    

### 비검사 경고의 예시와 해결 방법

```java
Set<Lark> exaltation = new HashSet(); // 경고 발생
```

- 이 코드에서는 `new HashSet()`에 타입 매개변수 `<Lark>`가 빠져 있다. → 타입 안전성을 보장할 수 없기 때문에 경고가 발생
- 컴파일러는 다음과 같은 경고가 발생 : 원래 필요한 타입(Set<Lark>)과 현재 사용한 타입(HashSet)이 일치하지 않는다는 의미
    
    ```vbnet
    warning: [unchecked] unchecked conversion
    required: Set<Lark>
    found:    HashSet
    ```
    

### 해결 방법: 타입 매개변수 추가

- 해결하려면 타입 매개변수를 명시
    
    ```java
    Set<Lark> exaltation = new HashSet<Lark>();
    
    ```
    
- 또는, 자바 7부터 도입된 다이아몬드 연산자(`<>`)를 사용
    
    ```java
    Set<Lark> exaltation = new HashSet<>();
    ```
    
    - 다이아몬드 연산자를 사용하면 컴파일러가 자동으로 올바른 타입(Lark)을 추론

### 모든 비검사 경고를 제거하는 것이 중요한 이유

- 모든 비검사 경고를 제거하면 코드의 타입 안전성을 보장할 수 있다.
    - 경고가 사라진다면, 런타임에 `ClassCastException` 같은 예외가 발생할 가능성이 줄어든다.

### 다 해결되지 않는 경고도 있을 수 있다

- 일부 경고는 제거하기 어려운 경우가 있다. (복잡한 제네릭 코드나 제네릭 배열을 다룰 때)
- 이런 경우라도 최대한 모든 경고를 해결하려고 노력해야 하고, 필요한 경우 `@SuppressWarnings("unchecked")` 같은 애노테이션을 사용해 경고를 무시할 수도 있다.

### @SuppressWarnings("unchecked")가 뭐야?

컴퓨터한테 "이 경고는 괜찮아, 무시해도 돼" 라고 하는 것. → 안전한 상황이라고 확신할 수 있을 때, 컴퓨터가 보여주는 경고를 숨기는 방법

### 왜 경고를 숨기는 게 필요할까?

어떤 코드에서는 컴퓨터가 무조건 경고를 보여줄 수밖에 없는 경우가 있다. 하지만 그래도 코드가 실제로 안전하다는 걸 알 수 있을 때가 있다.

이런 경우에 경고를 계속 보여주면, 진짜 중요한 경고가 나왔을 때 놓칠 수 있기 때문에(다른 경고에 파묻혀 버릴 수 있기 때문) 타입이 안전하다고 확실하게 알고 있는 경우에는 `@SuppressWarnings("unchecked")`를 사용해서 경고를 숨기자

### 어디에 써야 할까?

`@SuppressWarnings("unchecked")`는 가능한 작은 범위에만 적용하는 게 좋다.

- 변수 선언에 추가
- 짧은 메서드나 생성자에 사용

절대 클래스 전체에 사용해서는 안된다. 만약 클래스 전체에 써버리면 중요한 경고도 같이 숨겨질 수 있어서 위험!!

### 예시

컴퓨터가 경고를 보여주는 코드가 있다고 하자:

```java
public <T> T[] toArray(T[] a) {
    if (a.length < size)
        return (T[]) Arrays.copyOf(elements, size, a.getClass());
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
```

이 코드는 컴퓨터가 "안전하지 않은 형변환*이라고 경고 → 경고를 무시하고 숨길 수 있는 방법을 적용해보면

```java
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        // 여기서 우리가 왜 안전한지 설명하는 주석을 달아둔다
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
        return result;
    }
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
```

여기서 경고를 숨기기 위해 변수 `result`에만 `@SuppressWarnings`를 붙였다. 그리고 왜 이 경고를 숨기는 게 안전한지 주석을 달아 놨다. 이렇게 하면 다른 사람들이 코드가 왜 안전한지 이해하기 쉽고, 나중에 수정할 때 실수를 줄일 수 있다!
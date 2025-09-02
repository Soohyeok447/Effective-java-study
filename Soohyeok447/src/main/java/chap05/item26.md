# item26. 로 타입은 사용하지 발라

### 로 타입(Raw Type)이란 뭔가

로 타입이란 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않은 경우를 뜻함

예컨대 List<E>의 로 타입은 그냥 List

로 타입을 사용하면 제네릭의 가장 큰 장점인 컴파일 시점의 타입 체크가 완전히 무력화됨

### 로 타입 사용하는 예시(이렇게 하면 안됨)

```java
private final Collection stamps = ...;
```

이렇게 제네릭을 사용하지 않으면 stamp 대신 coin같은 다른 Collection 타입을 사용해도 컴파일 타임에 잡히지 않고 실행됨

```java
stamps.add(new Coin(...));
```

제네릭을 활용하지 않으면 컴파일 타임에 오류를 잡을 수 없고 문제가 런타임까지 미뤄져 버림

### 제네릭을 활용한 예시

```java
private final Collection<Stamp> stamps = ...;
```

이렇게 선언하면 컴파일러가 stamps에는 Stamp의 인스턴스만 넣어야 함을 인지하게 됨

### 로 타입은 절대로 쓰지 말것

로타입을 쓰면 제네릭이 안겨주는 안정성과 표현력을 모두 잃게 되니까 절대 써서는 안됨

로 타입을 지원하는 이유는 제네릭이 없던 시절 만든 코드들의 호환성 때문임

### 위험한 코드 예시

```java
public static void main(String[] args) {
    List<String> strings = new ArrayList<>();

    unsfaeAdd(strings, Integer.valueOf(42));

    String s = strings.get(0); // ClassCastException 발생
}

private static void unsafeAdd(List list, Object o){
    list.add(o); // List<String>와 Integer가 들어오게 됨 (문제)
}
```

위 코드에서 unsafeAdd 메서드는 로 타입인 List를 매개변수로 받는데

이거 때문에 컴파일러는 strings 리스트에 Integer가 들어가는 것을 못 막음

진짜 문제는 런타임에 리스트에서 원소를 꺼내 String으로 형변환하려는 순간에

ClassCastException이 발생하며 프로그램이 터지게 됨

### 매개변수화 타입과 와일드카드

#### `List<Object>` - 모든 타입의 객체를 안전하게 저장

`List`같은 로 타입은 사용해서는 안되지만

`List<Object>` 처럼 임의 객체를 명시적으로 혀용하는 매개변수화 타입은 괜찮음

(모든 타입을 허용한다는 의사를 명확히 컴파일러에게 전달한 것)

하지만 `List<Integer>`, `List<String>` 같은 더 구체적인 타입의 리스트에는 `List<Object>`를 할당할 수는 없음 (불공변)

#### `List<?>` - 어떤 타입인지 모르지만 타입 안전성을 지키고 싶을 때

`List<?>`는 비한정적 와일드카드 타입이라고 불림

제네릭을 쓰고싶지만 실제 타입 매개변수가 무엇인지 신경 쓰고 싶지 않을 때 사용

읽을 때는 항상 Object 타입으로 받고 **쓰기는 null을 제외하고 어떤 원소도 넣을 수 없음**

'이 리스트에 뭐가 들어있는지는 모르겠는데 내용물을 안 바꾸고 읽기만 할게'라는 뜻
-> 원본 리스트의 데이터 무결성을 보장하고 싶을 때 사용함

#### List<?> 사용 예시

모든 종류의 List를 받고 출력만 하고 싶을 때 다양한 제네릭의 타입을 받을 수 있어야함

그런데 List<Object>로 매개변수 타입을 지정하면 List<String>이나 List<Integer>같은 타입이 불공변때문에 할당할 수 없음

로타입을 사용하면 list.add()같은게 가능해짐 (수정가능성이 생김)

이때 write가 불가능한 와일드카드를 사용하게 됨
-> 어떤 타입이든 담고 있는 List를 매개변수로 받겠지만 타입을 모르니까 오염방지를 위해 어떤 원소도 안넣을게

### instanceof 연산은 예외

instanceof 연산에서는 로 타입을 사용해야함

제네릭의 타입정보는 런타임에 소거되기 때문(List<String>같은 타입 검사를 할 수 없음)

### 정리

로 타입을 사용하면 런타임에 예외가 일어날 수 있으니 사용하면 안됨

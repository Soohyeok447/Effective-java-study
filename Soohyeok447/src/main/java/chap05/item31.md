# item31. 한정적 와일드카드를 사용해 API 유연성을 높이라

## 선요약

메서드 시그니처에서 제네릭 타입을 매개변수로 사용할 때 조금 더 유연하게 만들고 싶다면 한정적 와일드카드(? extends T 또는 ? super T)를 써라

## 와일드 카드가 뭔가?

와일드카드는 **"어떤 타입이든 될 수 있다"** 를 의미하는 물음표(?) 기호임. 제네릭에서 알 수 없거나 신경 쓰지 않는 타입을 표현하기 위해 사용됨. List<?>는 "알 수 없는 타입의 원소를 담은 리스트"라는 뜻.

## 불공변이란 뭔가?

**불공변** 은 제네릭의 중요한 특징.<br>
Integer가 Number의 하위 타입이라 할지라도 List<Integer>와 List<Number> 사이에는 아무런 상속 관계가 없다는 규칙.

```java
// 일반적인 상속 관계는 성립함
Number number = new Integer(10);

// 제네릭에서는 상속 관계가 성립 안 됨
// List<Number> numbers = new ArrayList<Integer>();
```

이 규칙은 List<Number>에 Double을 추가하는 등의 잠재적인 런타임 오류를 컴파일 시점에 막아주는 방식으로 타입 안전성을 높임.

## 왜 와일드카드가 필요한가?

와일드카드는 불공변 때문에 발생하는 비유연성을 해결하기 위해 필요함.

```java
// 와일드카드 없는 api
public void pushAll(Iterable<E> src) { ... }

Stack<Number> numberStack = new Stack<>();
Iterable<Integer> integers = ...;

// Iterable<Integer>는 Iterable<Number>가 아니기 때문에 컴파일 오류 발생
numberStack.pushAll(integers);
```

Integer는 Number가 맞지만 Iterable<Integer>는 Iterable<Number>가 아니기때문에 위 코드는 컴파일에러가 발생함.

```java
// 와일드카드를 사용한 api
public void pushAll(Iterable<? extends E> src) { ... }

// 정상 동작
numberStack.pushAll(integers);
```

`? extends E`를 통해 `E(Number)` 또는 E의 하위 타입(Integer)을 담은 Iterable을 모두 받을 수 있게 됨 (api가 유연해짐)

## 비한정적과 한정적이란?

와일드카드에는 두 종류가 있음.

#### 비한정적 와일드카드: `?`

어떤 타입이든 상관없다는 뜻.

`List<?>`는 `List<Object>`, `List<String>`, `List<Integer>` 등을 모두 받을 수 있음.

타입 매개변수에 관심이 없고 Object의 기능만으로 충분할 때 사용.

#### 한정적 와일드카드: `? extends T`, `? super T`

어떤 타입이든 괜찮지만 특정 경계는 지켜야 한다는 뜻

`? extends T`(상한): T와 그 **하위 타입** 만 가능.

-> `List<? extends Number>`는 `List<Number>`, `List<Integer>` 등을 받을 수 있음.

`? super T` (하한): T와 그 **상위 타입** 만 가능.

-> `List<? super Integer>`는 `List<Integer>`, `List<Number>`, `List<Object>` 등을 받을 수 있음.

## 생산자와 소비자란?

PECS 원칙을 이해하기 위한 핵심 개념.

매개변수를 메서드 내부의 관점에서 바라보는 것.

### 생산자 (Producer)

메서드가 사용할 데이터를 생산(제공)하는 매개변수.

메서드 내부에서 해당 컬렉션의 원소를 꺼내서(get) 사용.

예시: `pushAll(Iterable<? extends E> src)`에서 src는 Stack에 들어갈 원소를 제공하는 생산자.

### 소비자 (Consumer)

메서드가 생성한 데이터를 소비(사용)하는 매개변수.

메서드 내부에서 해당 컬렉션에 원소를 집어넣음(add).

예시: `popAll(Collection<? super E> dst)`에서 dst는 Stack에서 꺼낸 원소를 전달받는 소비자.

## PECS 원칙

Producer-Extends, Consumer-Super (생산자는 extends, 소비자는 super)

이 원칙은 한정적 와일드카드를 언제 사용해야 하는지 알려주는 간단한 규칙.

매개변수가 데이터를 생산한다면 **? extends T**를 사용.

매개변수가 데이터를 소비한다면 **? super T**를 사용.

이 원칙을 따르면 API의 유연성을 최대한으로 높일 수 있음.

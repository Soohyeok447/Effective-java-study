# item32. 제네릭과 가변인수를 함께 쓸 때는 신중하라

## 선요약

제네릭과 가변인수(varargs)를 함께 사용하면 타입 안전성이 깨질 위험이 있음.

이 위험을 인지하고 직접 타입 안전성을 보장할 수 있을 때만 `@SafeVarargs` 애너테이션을 사용해서 메서드가 안전함을 명시해야함.

## 가변인수란?

가변인수(varargs)는 메서드를 호출할 때 같은 타입의 인수를 0개 이상 원하는 만큼 전달할 수 있게 하는 기능.

메서드 시그니처에서는 `타입...`으로 표기.

중요한 점은 컴파일러가 이 가변인수를 배열로 처리한다는 것.

```java
// String... args는 내부적으로 String[] args로 처리됨.
public void printMessages(String... messages) {
    for (String msg : messages) {
        System.out.println(msg);
    }
}

// 호출
// 내부적으로 new String[]{"안녕", "하세요"} 배열이 전달됨
printMessages("안녕", "하세요");
```

## 실체화 불가 타입이란?

**실체화 불가 타입** 이란 런타임에는 타입 정보가 지워지는 타입.

제네릭이 바로 대표적인 실체화 불가 타입임.

**실체화 가능 타입** : String[] 배열은 런타임에도 자신이 String 배열이라는 것을 정확히 알고 있음. <br>
그래서 String[]에 Integer를 넣으려고 하면 `ArrayStoreException`을 발생시킴.

**실체화 불가 타입** : List<String>은 컴파일 시점에는 String만 담을 수 있도록 강제하지만 런타임에는 그냥 List고 String을 담고 있다는 정보는 사라짐.

## 매개변수화 타입 변수란?

제네릭 `<>` 안에 실제 타입으로 명시적으로 선언된 변수.

`List<String> names;`

`Map<Integer, String> userMap;`

`Stack<Number> numberStack;`

## 매개변수화 타입의 변수가 자신이 가리키도록 선언됐다는 뜻은?

그 변수를 선언할 때 정해놓은 타입의 객체만 가리키겠다고 컴파일러와 약속했다는 의미임.

```java
// 이 names 변수는 앞으로 List<String> 타입의 객체만 가리킬거에요 라고 '선언'
List<String> names;


names = new ArrayList<String>();
names.add("홍길동"); // 선언을 잘 지킴

// 선언을 잘 지키지 못했음
List<Integer> numbers = new ArrayList<>();
numbers.add(123);

// names = numbers; -> 컴파일러: 왜??????
```

## 힙 오염이란?

**힙 오염**은 매개변수화 타입의 변수가 자신이 가리키도록 선언된 타입이 아닌 다른 타입의 객체를 참조하게 되는 상황.

힙 오염이 발생해도 즉시 오류가 나지 않음.. 나중에 이상한 곳에서 `ClassCastException`이 발생하고 버그를 찾기 쉽지 않음.

## 가변인수와 제네릭은 궁합이 좋지 않다?

가변인수는 내부적으로 배열을 생성함.

`List<String>...`과 같은 제네릭 가변인수를 사용하면 `List<String>[]`과 같은 제네릭 배열이 필요하게됨.

하지만 제네릭은 실체화 불가 타입이기 때문에 자바에서는 제네릭 배열 생성을 금지하는데

이 모순을 해결하기 위해서 컴파일러는 제네릭 가변인수를 받을 때 실제로는 Object[] 배열을 생성하고 형변환을 수행함.

이 과정에서 **형변환을 하기 때문에** 타입 안전성이 깨질 수 있고 이것이 힙 오염의 원인이 됨.

## 매개변수화 타입의 변수가 타입이 다른 객체를 참조한다는 뜻은?

힙 오염을 코드로 설명한 건데 예컨대 List<String> 타입의 변수가 실제로는 List<Integer> 객체를 가리키는 상황임.

```java
// names는 List<String> 타입의 변수
List<String> names;

List<Integer> numbers = new ArrayList<>();
numbers.add(123);

Object obj = numbers;

// names가 실제로는 List<Integer>를 참조함
names = (List<String>) obj; // 힙 오염

...
...
...
...
...

// names.get(0)은 Integer인데 String으로 형변환하려 함
String name = names.get(0); // ClassCastException 발생
```

## @SafeVarargs 애너테이션?

@SafeVarargs는 메서드 개발자가 컴파일러에게

"이 메서드는 제네릭 가변인수를 사용하지만 내가 직접 보니까 힙 오염을 일으키지 않고 타입 안전한 메서드다"

라고 보증하는 애너테이션임.

이 애너테이션을 붙이면 컴파일러는 더 이상 경고를 안 띄우고 이 메서드를 호출하는 코드도 경고를 띄우지 않음.

#### @SafeVarargs를 붙일 수 있는 조건

-   메서드 내부에서 가변인수로 받은 배열에 아무것도 저장하지 않음. (읽기만 함)
-   그 배열을 신뢰할 수 없는 외부 코드에 노출하지 않음.

이 두 가지를 만족하는 안전한 메서드임이 확실할 때만 `@SafeVarargs`를 사용해야 함.

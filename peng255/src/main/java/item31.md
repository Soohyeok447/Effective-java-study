## 31. 한정적 와일드카드를 사용해 API 유연성을 높이라

### 요점 정리

- 제네릭 타입은 기본적으로 불공변(invariant)이어서 List<Number>와 List<Integer>는 서로 호환되지 않는다
    - **불공변**이란, 타입 간 상속 관계가 리스트에는 적용되지 않는다는 것.
- 그런데 실전에서 Number의 모든 하위 타입을 받고 싶다면?

  아니면 아무 타입의 Collection을 받아서 스택을 그 Collection에 pop하고 싶다면?

  → **와일드 카드 `?` 를 써보자**

    - 생산자(아무 타입을 리스트에 담음)에는 `? extends`를 사용
    - 소비자(아무 타입의 리스트를 받음)에는 `? super` 를 사용
- 단순히 타입을 한번만 쓰는 메서드 : 와일드 카드 이용
- 타입을 여러 번 쓰고, 타입끼리 맞아야 하는 메서드 : 제네릭 타입 파라미터로

※ 와일드카드는 기능을 쉽게 확장해주지만.. 리턴타입에 쓰면 복잡해지니 조심해서 쓰자

### 생산자 ? extends 예시

List<Integer> → Stack<Number>에 넣기

List<Double> → Stack<Number>에 넣기

```java
Stack<Number> stack = new Stack<>();
List<Integer> ints = List.of(1,2,3);
List<Double> doubles = List.of(2.5, 3.5);

// 받아서 스택에 올리는 함수
public void pushAll(Iterable<? extends Number> src) {
  for(Number n : src) push(n);
}

stack.pushAll(ints);    // 정수도 넣을 수 있음
stack.pushAll(doubles); // 실수도 넣을 수 있음
```

`? extends Number` → Integer, Double, Long 등 모든 Number 하위 타입을 받을 수 있다

### 소비자 ? super 예시

Stack<Integer> → List<Object>에 넣어주기

Stack<Integer> → List<Number>에 넣어주기

```java
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.push(2);

List<Object> objs = new ArrayList<>();
List<Number> nums = new ArrayList<>();

// 스택에서 꺼내서 Collection에 추가
public void popAll(Collection<? super Integer> dst) {
  while(!isEmpty()) dst.add(pop());
}

stack.popAll(objs); // Object도 받아줌
stack.popAll(nums); // Number도 받아줌
```

`? super Integer` → Number, Object 등 Integer의 상위타입을 받을 수 있다
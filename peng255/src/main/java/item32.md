### 32. 제네릭과 가변인수를 함께 쓸 때는 신중하라

제네릭 메서드와 가변 인수(...)를 같이 쓰면 타입 안전성 문제가 생길 수 있다.
가변 인수를 쓰면 내부적으로는 배열이 만들어지는데, 이 배열이 제네릭 타입일 때 힙 오염(heap pollution) 위험이 생긴다.
잘못 만든 코드는 경고 없이도 런타임 에러(ClassCastException)를 낼 수 있다.
그래서 자바 라이브러리의 중요한 제네릭 가변인수 메서드는 내부적으로 정말 조심해서 안전하게 만들었고, 이걸 보장할 땐 @SafeVarargs 어노테이션을 붙인다.

기본 원칙
제네릭 가변인수 배열에 값을 새로 저장(덮어쓰기)하지 않는다.
이 배열의 참조를 외부(신뢰할 수 없는 코드)에 넘기지 않는다.
완전히 안전하지 않으면, 가변인수 대신 List 파라미터를 쓴다.

실생활/쉬운 코드 예시
1. 위험한 코드
```java
// 제네릭 가변인수 메서드 - 아주 위험!
static void dangerous(List<String>... stringLists) {
    List<Integer> intList = List.of(42);
    Object[] arr = stringLists;
    arr[0] = intList;  // 엉뚱한 타입으로 덮어씀 (힙 오염)
    String s = stringLists[0].get(0); // 런타임 실패! ClassCastException
}
```
이런 오류는 컴파일러가 못 찾아주고, 실행 중에만 터진다.

2. 안전한 코드 - @SafeVarargs 활용
```java
@SafeVarargs
static <T> List<T> flatten(List<T>... lists) {
    List<T> result = new ArrayList<>();
    for (List<T> l : lists) result.addAll(l);
    return result;
}
```

// 사용 예시
`List<String> all = flatten(List.of("a", "b"), List.of("c", "d"));`
// 안전하게 여러 리스트 합치기
배열에 값을 새로 덮어쓰거나 밖에 노출하지 않으므로 안전

@SafeVarargs는 "내가 보장하니 경고 띄우지 마"라는 의미

3. 제네릭 + 가변인수 대신 List 파라미터를 쓸 수도 있음
```java
static <T> List<T> flatten(List<List<T>> lists) {
    List<T> result = new ArrayList<>();
    for (List<T> l : lists) result.addAll(l);
    return result;
}
```

// 사용 예시
`List<String> all = flatten(List.of(List.of("a", "b"), List.of("c")));`
// 조금 더 코드가 길고 복잡해도 안전성은 확실하게 보장

제네릭 가변인수(예: <T> T... args)는 매우 신중하게 써야 하며,
정말 안전할 때만 @SafeVarargs를 붙여서 경고를 없애고,
안전하지 않으면 List 파라미터로 변경하는 게 좋다.

안전 기준은 "배열 수정/외부 노출 없음"

실전에서 자주 쓰는 라이브러리 예시(Arrays.asList, List.of, Collections.addAll)는 모두 이 원칙으로 만들어져 있음

- 제네릭과 가변인수를 함께 쓸 땐 무심코 만들지 말고, 안전성 원칙을 반드시 지켜야 한다!

- 실전에서 flatten, merge 등 여러 값 합칠 때 주로 등장하는 패턴이니, 무의식적으로 ...쓸 때 한 번 더 의심하고 쓰면 된다.

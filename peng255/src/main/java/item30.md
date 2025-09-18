## 30. 이왕이면 제네릭 메서드로 만들라
### 요점 정리

- 클래스뿐만 아니라 메서드도 제네릭으로 선언하면
    - 코드 재사용성, 타입 안전성, 가독성, 유지보수성 ↑
    - 직접 쓸 때 타입 캐스팅이 필요 없다
    - 컴파일러가 타입체크도 해준다

### 제네릭 메서드가 뭔데?

- 일반적인 메서드 : 타입별로 따로 만들거나… Object로 받아서 캐스팅하는 형식
- 제네릭 메서드 : 타입을 parameter로 받는다! 그 후 컴파일러가 알아서 타입을 결정해주는 형식

### 일반메서드 vs 제네릭 메서드

제네릭을 쓰지 않고 Object로 받고 캐스팅한다면?

```java
// 제네릭 없는 버전 (Object 상자)
class Box {
    private Object item;
    
    public void set(Object obj) { item = obj; }
    public Object get() { return item; }
}

Box b = new Box(); // Box 생성

b.set("커피");    // String넣기
String drink = (String) b.get(); // 매번 형변환 필요, 실수하면 에러!
```

Box에 Object로 String도 들어갈 수 있고 다른 타입 E도 들어갈 수 있는데..

Box1의 item에는 String이 들어가고

Box2의 item에는 다른 타입 E가 들어갔을 때

(String)Box2.item → 이렇게 불가능한 캐스팅을 했을 때 에러가 발생할 수 있다.

근데 각 Box 객체에 어떤 타입이 들어있는지 모르니 에러가 발생하기 쉽다.

그러니 제네릭 메서드로 구현하자

```java
class Box<T> {
    private T item;
    
    public void set(T obj) { item = obj; }
    public T get() { return item; }
}

Box<String> b = new Box<>(); // 이 상자엔 String만!
b.set("커피");    // 넣기
String drink = b.get(); // 바로 꺼내기 (캐스팅 필요 없음! 타입 안전)

Box<Integer> b2 = new Box<>();
b2.set(123); // 정수만 반환된다

int num = b2.get(); // 캐스팅 없이 안전하게 사용
```

이 예시에서는 Object가 아니라 제네릭 T로 값을 받고, get()에서 값을 돌려줄 때는 캐스팅 필요없이 그 타입 그대로 돌려준다.

잘못된 타입을 넣으려고 하면 컴파일 에러로 미리 확인할 수 있다는 장점도 있다.
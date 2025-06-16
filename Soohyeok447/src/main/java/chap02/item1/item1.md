# item1. 생성자 대신 정적 팩토리 메서드를 고려해라
정적 팩토리 메서드로 생성자(new) 대신 클래스의 인스턴스를 얻을 수 있음

### 정적 팩토리 메서드가 생성자보다 좋은 점 5가지
- 이름을 가질 수 있음
  - 메서드명으로 어떤 인스턴스를 생성시키는지 의도를 잘 표현할 수 있음
- 호출될 때마다 인스턴스를 새로 생성하지 않아도 됨
  - 내부적으로 객체를 캐싱하거나 하나의 인스턴스만 반환할 수 있음
- 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있음
  - 반환 타입이 인터페이스거나 추상 클래스면, 구현체를 다양하게 제공가능
- 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있음
- 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 됨
  - 반환할 인터페이스만 먼저 설계해두고 구현은 미래에 주입하거나 로딩해도 됨
  - 구현 안된 클래스 대신 Mocking하면 되기 때문에 테스트 유연성도 높음
  - **런타임에 구현체는 있어야함!**

### 단점 2가지
- 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없음
  - 생성자를 보통 private으로 만들기 때문에 상속을 할 수 없기 때문
- 정적 팩토리 메서드는 프로그래머가 찾기 어려움
  - 정적 메서드 이름을 직접 정할 수 있다는 점 때문에 직관적이지가 않다는 뜻..
    - 정적 팩토리 메서드에 흔히 사용하는 이름 규칙을 사용하면 어느정도 해소됨

### 정적 팩토리 메서드에 흔히 사용하는 명명 방식들
- from : 매개변수를 **하나** 받아서 해당 타입의 인스턴스를 반환하는 **형변환 메서드**
  - ```java 
    Date d = Date.from(instance);
    ```
    
- of : **여러** 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 **집계 메서드**
  - ```java 
    Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
    ```
- valueOf : of와 from 보다 좀 더 구체적 의미 포함
  - ```java 
    BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
    ```
- getInstance : (매개변수가 있으면) 매개변수로 명시한 인스턴스 반환 (같은 인스턴스임을 보장하지는 않음)
  - ```java 
    StackWalker luke = StackWalker.getInstance(options);
    ```
- create : getInstance와 같지만, 매번 **새로운 인스턴스를 생성**해 반환함을 보장함
  - ```java 
    Object newArray = Array.create(classObject, arrayLen);
    ```
- getOOO (type) : getInstance와 같으나, 다른 클래스에 팩터리 메서드를 정의할 때 씀
  - ```java 
    FileStore fs = Files.getFileStore(path); // Files 클래스 안에서 FileStore를 얻고 있음
    ```
- newOOO (type) : create와 같으나, 다른 클래스에 팩터리 메서드를 정의할 때 씀
  - ```java 
    BufferedReader br = Files.newBufferedReader(path); // Files 클래스 안에서 새로운 BufferedReader를 생성하고 있음
    ```
- OOO (type) : getOOO과 newOOO의 간결버전
  - ```java 
    List<Complaint> litany = Collections.list(legacyListany);
    ```

---
### 정적 팩토리 메서드와 public 생성자는 각각의 쓰임새가 있지만 정적 팩토리를 쓰는게 유리한 경우가 많음

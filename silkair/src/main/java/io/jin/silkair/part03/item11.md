`hashCode` 메서드를 올바르게 재정의하는 이유와 방법에 대해 알아보자.
`equals`와 `hashCode`는 항상 같이 사용해야 하는 메서드인데, 잘못 작성하면 컬렉션 클래스(`HashMap`, `HashSet` 등)에서 문제가 생길 수 있다.

### 1. **`hashCode`란?**

- `hashCode`는 객체를 숫자(정수)로 나타내는 메서드이다. 이 숫자를 사용해서 객체가 저장될 위치를 정하고, `HashMap`이나 `HashSet` 같은 해시 기반 자료구조에서 객체를 빠르게 찾을 수 있다.
- `equals`와 `hashCode`의 규칙: 만약 두 객체가 `equals`로 비교했을 때 같다면, 두 객체의 `hashCode`도 반드시 같아야 한다.

### 2. **왜 `equals`를 재정의하면 `hashCode`도 재정의해야 할까?**

- `PhoneNumber`라는 클래스를 정의했다. 이 클래스의 인스턴스를 `HashMap`에 넣을 때, `hashCode`를 재정의하지 않으면 문제가 생길 수 있다.
- **자세한 설명!**

  ### 1. **해시 테이블의 기본 동작**

    - 해시 테이블은 배열을 사용해 데이터를 저장하고 관리
    - 객체를 저장할 때, 해시 함수를 사용해서 해시코드라는 숫자를 얻는다. 이 해시코드는 저장할 위치(인덱스)를 결정하는 데 사용
    - 그리고 데이터를 검색할 때도 똑같이 해시코드를 계산해서 그 위치로 가서 데이터를 찾는다.

  ### 2. **해시코드 재정의가 왜 중요한가?**

    - 객체를 저장할 때와 검색할 때 같은 해시코드를 사용해야 같은 위치를 찾을 수 있다.
    - 만약 `hashCode`를 재정의하지 않으면, 자바의 기본 `hashCode` 메서드는 객체의 메모리 주소를 기반으로 해시코드를 만든다. 따라서 객체마다 기본적으로 다른 해시코드를 가진다.

  ### 3. **재정의하지 않으면 무슨 일이 생기나?**

  예를 들어, 우리가 전화번호 객체(PhoneNumber)를 `HashMap`에 저장하고, 나중에 그 전화번호로 데이터를 꺼내보자

    ```java
    Map<PhoneNumber, String> map = new HashMap<>();
    map.put(new PhoneNumber(123, 456, 7890), "Alice");
    ```

  위 코드는 전화번호 객체를 키로 해서 `"Alice"`라는 값을 저장했다.

  이제 같은 번호로 `"Alice"`를 꺼내보자

    ```java
    String name = map.get(new PhoneNumber(123, 456, 7890));
    ```

  문제는 새로운 전화번호 객체(`new PhoneNumber(123, 456, 7890)`)를 만들었기 때문에, 이 객체는 기본적으로 다른 해시코드를 가질 가능성이 높다. (저장할 때 사용한 객체와 다르다는 의미)

    - 저장할 때: 전화번호 객체를 저장하면서 해시코드 A가 계산되고, 해시 테이블의 위치 A에 `"Alice"`를 저장
    - 검색할 때: 새로운 전화번호 객체로 검색을 시도하면, 이 객체는 다른 해시코드 B를 가질 수 있다. 그러면 해시 테이블의 위치 B를 찾아간다.

  위치 A와 위치 B는 서로 다르기 때문에, `"Alice"`를 찾으려고 해도 해시 테이블에 존재하지 않는 위치를 찾아가게 되고, 결국 `null`을 반환

  ### 4. 그렇다면?

  우선, `PhoneNumber` 클래스에서 `equals`는 이미 재정의되어 있어서 같은 값을 가진 전화번호 객체들이 논리적으로 같다고 판단한다. 하지만 `hashCode`를 재정의하지 않으면, 같은 값을 가진 두 전화번호 객체도 서로 다른 해시코드를 가질 수 있어서 문제가 발생한다

  ### 4.1. 올바르게 수정한 `PhoneNumber` 클래스

  `equals`와 `hashCode`를 모두 재정의한 `PhoneNumber` 클래스

    ```java
    import java.util.Objects;
    
    public class PhoneNumber {
        private final int areaCode;
        private final int prefix;
        private final int lineNum;
    
        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = areaCode;
            this.prefix = prefix;
            this.lineNum = lineNum;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;  // 자기 자신과 같으면 true 반환
            }
            if (!(o instanceof PhoneNumber)) {
                return false;  // 타입이 다르면 false 반환
            }
            PhoneNumber other = (PhoneNumber) o;
            return areaCode == other.areaCode && prefix == other.prefix && lineNum == other.lineNum;
        }
    
        @Override
        public int hashCode() {
            // 모든 필드를 이용해서 해시코드를 계산
            return Objects.hash(areaCode, prefix, lineNum);
        }
    }
    ```

  ### 4.2. 해시코드 재정의 설명

    - `hashCode()` 메서드에서, `Objects.hash(areaCode, prefix, lineNum)`을 사용해 해시코드를 계산한다. 이 메서드는 모든 필드를 기반으로 고유한 해시코드를 만들어준다.
    - 이제 같은 값을 가진 `PhoneNumber` 객체들은 항상 같은 해시코드를 가진다.

  ### 4.3. 수정 후 코드 사용 예시

  이제 `PhoneNumber` 클래스를 사용해서 데이터를 넣고 꺼낼 때, 올바르게 동작한다. 아래는 수정된 코드로 데이터를 저장하고 검색하는 예시

    ```java
    import java.util.HashMap;
    import java.util.Map;
    
    public class Main {
        public static void main(String[] args) {
            Map<PhoneNumber, String> map = new HashMap<>();
    
            // 전화번호 객체를 키로 사용해서 값을 저장
            PhoneNumber phoneNumber1 = new PhoneNumber(123, 456, 7890);
            map.put(phoneNumber1, "Alice");
    
            // 같은 값을 가진 전화번호 객체로 값을 꺼냄
            PhoneNumber phoneNumber2 = new PhoneNumber(123, 456, 7890);
            String name = map.get(phoneNumber2);
    
            // 결과 출력
            System.out.println(name);  // "Alice" 출력
        }
    }
    ```

  ### 4.4. 설명

    - 저장할 때: `map.put(phoneNumber1, "Alice")`에서 `phoneNumber1`의 해시코드가 계산되어 해시 테이블의 특정 위치에 "Alice"가 저장
    - 검색할 때: `phoneNumber2`는 같은 값을 가지고 있고, `hashCode()`를 재정의했기 때문에 `phoneNumber1`과 같은 해시코드를 갖는다. 따라서 같은 위치에서 `"Alice"`를 찾을 수 있다.
    - 결과: `"Alice"`가 출력돼서, `null`이 나오지 않는다.
- 예시

    ```java
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(new PhoneNumber(707, 867, 5309), "제니");
    m.get(new PhoneNumber(707, 867, 5309));
    ```

    - 코드에서 `"제니"`를 저장할 때 사용된 `PhoneNumber` 객체와, 그 값을 가져올 때 사용된 `PhoneNumber` 객체는 논리적으로는 같지만, 물리적으로는 서로 다른 객체이다.
    - `equals`를 재정의했더라도 `hashCode`를 재정의하지 않으면, 두 객체가 다른 해시코드를 가질 수 있어서 제대로 값을 찾을 수 없다.
    - 결국, `"제니"`를 가져오려고 해도 `null`을 반환

### 3. **잘못된 `hashCode` 재정의 예시**

```java
@Override
public int hashCode() {
    return 42;
}
```

이 코드는 모든 객체에 대해 같은 해시코드(`42`)를 반환한다. 이렇게 하면 `HashMap`이나 `HashSet`에서 모든 객체가 하나의 버킷(저장 공간)에 저장

### 4. **좋은 `hashCode`를 작성하는 방법**

`hashCode`를 잘 작성하려면, 객체의 필드(변수)들을 사용해서 고유한 해시코드를 만들어야 한다.

좋은 `hashCode`를 작성하는 방법

1. **첫 번째 필드로 초기화**:
    - `int` 타입의 `result` 변수를 선언하고, 첫 번째 핵심 필드의 해시코드로 초기화

    ```java
    int result = c; // 첫 번째 필드의 해시코드
    ```

2. **나머지 필드들을 차례로 해시코드에 반영**
    - 각 필드에 대해 해시코드 `c`를 계산
        - 기본 타입 필드: 기본 타입 필드는 그 타입의 박싱 클래스의 `hashCode` 메서드를 사용한다. 예를 들어, `int` 필드는 `Integer.hashCode(int)`로 해시코드를 계산
        - 참조 타입 필드: 만약 필드가 객체라면, 그 객체의 `hashCode` 메서드를 호출한다. 만약 필드가 `null`이라면, 해시코드는 `0`으로 한다.
        - 배열 필드: 배열의 모든 원소를 하나씩 해시코드에 반영하거나, 모든 원소가 중요하다면 `Arrays.hashCode()`를 사용한다.
    - 각 필드의 해시코드를 `result`에 반영하는 코드 예시

    ```java
    result = 31 * result + c;
    ```

   여기서 31은 임의의 소수로, 해시코드를 고르게 분포시키기 위해 사용했다. 곱셈을 사용하면 해시코드가 더 고르게 분포되도록 도와준다.

3. **최종 결과 반환**:
    - 모든 필드에 대해 계산을 마치면 `result`를 반환

### 예시 코드

```java
@Override
public int hashCode() {
    int result = Integer.hashCode(areaCode);  // 첫 번째 필드의 해시코드로 초기화
    result = 31 * result + Integer.hashCode(prefix);  // 두 번째 필드 반영
    result = 31 * result + Integer.hashCode(lineNum);  // 세 번째 필드 반영
    return result;  // 최종 해시코드 반환
}
```

### **`hashCode`가 잘 구현되었는지 자문하기**

- `equals`로 같다고 판단되는 객체는 반드시 같은 `hashCode`를 반환해야 한다. 그래서 `hashCode`를 재정의할 때는 논리적으로 같은 객체들이 같은 해시코드를 가지는지 확인하는 게 중요하다.
- 이 확인을 위해 단위 테스트를 작성해서 여러 인스턴스가 같은 해시코드를 가지는지 확인하면 좋다. 만약 서로 다른 해시코드를 가진다면, `hashCode` 재정의에 문제가 있는 것!!

### 파생 필드는 해시코드 계산에서 제외 가능

- 파생 필드란? : 다른 필드로부터 계산해낼 수 있는 필드
  Ex) 만약 어떤 필드가 다른 필드들의 값에서 쉽게 계산될 수 있다면, 그 필드는 해시코드 계산에 포함하지 않아도 된다.
- 그러나 `equals` 비교에 사용되지 않는 필드는 반드시 해시코드에서 제외해야 한다. 그렇지 않으면, `equals`와 `hashCode`의 규칙이 어긋나서 문제가 발생

### `PhoneNumber` 클래스의 `hashCode`

```java
@Override
public int hashCode() {
    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
}
```

- 첫 번째 필드로 초기화 :  `result`를 첫 번째 필드인 `areaCode`의 해시코드로 초기화
- 다음 필드들을 순서대로 곱하고 더해줌 : 나머지 필드인 `prefix`와 `lineNum`도 차례로 `31`을 곱하고 더해줌으로써 해시코드를 계산
- 결과 반환: 이렇게 계산된 `result`가 최종 해시코드

### 다른 방법: `Objects.hash()` 사용

```java
@Override
public int hashCode() {
    return Objects.hash(lineNum, prefix, areaCode);
}
```

- `Objects.hash()` 메서드를 사용하면 한 줄로 해시코드를 계산할 수 있다.
- 편리하지만, 속도가 더 느릴 수 있다. (입력 값을 저장하기 위한 배열이 만들어지고, 박싱과 언박싱이 필요하기 때문)

### 캐싱을 이용한 성능 최적화

- `hashCode`를 계산하는 비용이 크다면, 해시코드를 한 번 계산한 후 저장해두는 방식(캐싱)을 사용할 수 있다.
- 불변 객체라면, 객체를 처음 만들 때 해시코드를 계산해서 저장하는 방법도 가능

### 지연 초기화된 `hashCode`

```java
private int hashCode; // 기본값 0으로 초기화됨

@Override
public int hashCode() {
    int result = hashCode;
    if (result == 0) {
        result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        hashCode = result;  // 계산된 값을 저장해둠
    }
    return result;
}
```

- `hashCode` 필드를 처음에는 `0`으로 초기화해 두고, `hashCode()`가 처음 호출될 때 해시코드를 계산하고 이를 저장
- 이후 `hashCode()`를 다시 호출하면, 저장된 값을 반환
### equals 메서드란?

자바에서 두 객체가 "같은지"를 비교하는 데 사용되는 메서드

→ 두 객체가 “물리적으로 같은지"를 확인. 즉, 메모리 상에서 동일한 객체인지를 확인한다. (기본적으로 **`==` 연산자와 같은 방식**으로 동작)

하지만, "논리적으로 같은지"를 비교하고 싶을 때가 있다. 예를 들어, 두 개의 문자열이 같은 내용을 가지고 있는지를 비교할 때, 그 문자열들이 물리적으로 같은 객체인지가 아니라, 그 값이 같은지를 확인하고 싶은 상황에서`equals` 메서드를 재정의(오버라이드) 해야 한다.

- **그럼 equals 메서드와 == 연산자의 차이점?**
    1. **`== 연산자`**
        - 객체의 주소값(참조값)을 비교. 즉, 두 객체가 메모리상에서 같은 위치에 있는지를 확인한다. (두 객체가 물리적으로 같은 객체인지 확인하고 싶을 때 사용)
        - 예시:  `false`를 출력

          `a`와 `b`는 서로 다른 메모리 위치에 있는 서로 다른 객체이기 때문.

            ```java
            String a = new String("hello");
            String b = new String("hello");
            
            System.out.println(a == b); // false (서로 다른 객체)
            ```

    2. **`equals` 메서드**:
        - 기본적으로는 객체의 주소값(참조값)을 비교. 즉, 두 객체가 메모리상에서 같은 위치에 있는지를 확인(==과 같다.)
        - 재정의 하면 객체의 실제 값을 비교해. 즉, 두 객체가 같은 값을 가지고 있는지를 확인.
        - 기본적으로 `Object` 클래스에서 제공되는 `equals`는 `==`과 같은 방식으로 작동해. 하지만 **재정의(Override)**하면 객체가 실제로 동일한 값을 가지는지 비교할 수 있다!!
        - 예시 : `true`를 출력
          String 클래스가 equals 메서드를 재정의 했기 때문에 `a`와 `b`는 서로 다른 객체이지만, 값이 "hello"로 같기 때문에

            ```java
            String a = new String("hello");
            String b = new String("hello");
            
            System.out.println(a.equals(b)); // true
            ```


    ### 정리
    
    기본적으로 같은 기능을 하지만 euqals 만 재정의 가능하다!!


### **재정의하지 않아도 되는 경우**

- **각 인스턴스가 본질적으로 고유하다**

  만약 그 객체가 값 자체보다는 그 객체 자체가 중요한 경우, `equals`를 재정의할 필요가 없다. 예를 들어, Thread 같은 클래스는 각 객체가 고유해서 그냥 그대로 두는 게 좋다.

- **인스턴스의 논리적 동치성을 검사할 일이 없다**
  어떤 클래스들은 `equals`로 논리적으로 같은지 확인할 필요가 없다. 예를 들어 정규 표현식을 다루는 `Pattern` 클래스는 논리적 비교가 필요 없기 때문에 `equals`를 재정의하지 않았다.
- **상위 클래스에서 재정의한 `equals` 가 하위 클래스에도 딱 들어맞는다**만약 부모 클래스가 `equals`를 잘 구현해둬서 그대로 써도 될 때는 재정의하지 않아도 된다. Set이나 List 같은 클래스들은 이미 부모 클래스에서 `equals`가 잘 정의되어 있다.
- **클래스가 private이거나 package-private이고 `equals` 메서드를 호출할 일이 없다**
  만약 그 클래스가 외부에서 호출될 일이 없는, 숨겨진(private) 클래스라면 굳이 `equals`를 재정의할 필요 없다.

### **재정의해야 할 경우**

- **값 클래스일 때**
  `Integer`나 `String` 같은 값 클래스를 만들 때는 `equals`를 재정의하는 게 좋다. 값이 같으면 객체도 같다고 여길 수 있어야 하기 때문!!
- **값 클래스이지만 재정의하지 않아도 되는 특별한 경우**

  **Enum** 같은 클래스는 `equals`를 재정의할 필요가 없어. `Enum`은 특정 값이 딱 하나만 존재하기 때문에, 논리적으로나 물리적으로나 같은지 비교할 필요가 없어.


### 동치 관계란? (equals메서드는 동치관계를 구현한다.)

`equals` 메서드는 두 객체가 같다고 판단하는 기준을 만들어준다. 여기서 "동치 관계"는 두 객체가 같다고 판단되는 관계이다. 이때 반드시 5가지 규칙을 지켜야 한다.

### 5가지 규칙

- **반사성 (Reflexivity)**
    - 자기 자신과는 무조건 같아야 한다
    - `x.equals(x)`는 항상 `true`(자기 자신과 같지 않다면 이상하니까)
- **대칭성 (Symmetry)**
    - 두 객체가 서로 같다고 하면, 반대도 같아야 한다
    - `x.equals(y)`가 `true`라면, `y.equals(x)`도 `true` . 그렇지 않으면 객체 사이에 불일치가 발생
    - **대칭성 문제**

      대소문자 구별을 하지 않는 문자열을 비교할 때, `x.equals(y)`는 `true`인데 `y.equals(x)`는 `false`가 될 수 있다.

      `CaseInsensitiveString` 클래스는 대소문자를 구분하지 않고 문자열을 비교하는 클래스이다. 여기서 대칭성 문제 발생!

        ```java
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        ```

        - `cis.equals(s)`는 `true`를 반환해. (`CaseInsensitiveString`이 일반 문자열 `String`도 비교하려고 했기 때문)
        - 하지만 `s.equals(cis)`는 `false` (`String` 클래스는 `CaseInsensitiveString`을 몰라서 다르다고 판단)

      해결하려면 ?

        ```java
        @Override public boolean equals(Object o) {
            return o instanceof CaseInsensitiveString &&
                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
        }
        ```

      이렇게 하면 `CaseInsensitiveString`끼리만 비교하고, 일반 `String`과의 비교는 하지 않아서 대칭성 문제를 해결

- **추이성 (Transitivity)**
    - 첫 번째와 두 번째가 같고, 두 번째와 세 번째가 같다면 첫 번째와 세 번째도 같다 (여러 객체가 같다면 그 관계가 유지)
    - 즉, `x.equals(y)`가 `true`이고, `y.equals(z)`가 `true`라면, `x.equals(z)`도 `true`
    - **추이성 문제**

      추이성은 "A가 B와 같고, B가 C와 같다면, A도 C와 같아야 한다"는 규칙이다.
      당연해 보이지만, 상속을 하면서 새로운 속성을 추가하게 되면 쉽게 어길 수 있다.

      ### EX) `Point`와 `ColorPoint` 클래스

        - `Point`는 2차원 좌표(x, y)를 나타내는 클래스고, `ColorPoint`는 `Point`를 확장해서 색상(color) 속성을 추가한 클래스
        - 문제는 `equals` 메서드를 어떻게 재정의하느냐에 따라 추이성이 깨질 수 있다는 것이다.

      ### 문제 발생

        1. `Point` 클래스에서 `equals`는 `x`와 `y`만 비교
        2. `ColorPoint`는 `x`, `y` 외에도 `color`까지 비교

      만약 두 클래스를 서로 비교할 때 색상을 무시하면

        <aside>

      Point p = new Point(1, 2);

      ColorPoint cp1 = new ColorPoint(1, 2, Color.RED);

      ColorPoint cp2 = new ColorPoint(1, 2, Color.BLUE);

        </aside>

      이 경우, `p.equals(cp1)`와 `p.equals(cp2)`는 `true`일 수 있지만, `cp1.equals(cp2)`는 `false` → 색상까지 비교하면서 추이성이 깨짐

      ### 추이성 문제를 해결하려다가 리스코프 치환 원칙을 어기는 경우

      `Point`와 `ColorPoint`를 비교할 때 색상까지 고려하면, 추이성 문제 발생
      이를 해결하기 위해 `equals` 메서드에서 `getClass()`를 사용해서 클래스가 정확히 같은지 비교하게 만들면, 추이성 문제는 해결

      리스코프 치환 원칙을 위배

      ### 잘못된 해결 예시 (리스코프 치환 원칙 위배)

        ```java
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != getClass()) return false;
            Point p = (Point) o;
            return p.x == x && p.y == y;
        }
        ```

      이 방식은 `equals` 메서드가 같은 클래스일 때만 `true`를 반환

      즉, `Point`와 정확히 같은 클래스를 가진 객체와만 같다고 판단

      이렇게 되면, `ColorPoint`가 상속된 `Point` 클래스처럼 사용되지 못하는 상황이 생긴다. 예를 들어, `ColorPoint`가 `Point`처럼 쓰여야 하는 상황에서 제대로 동작하지 않게 된다

        ```java
        //예시
        Set<Point> unitCircle = Set.of(
            new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)
        );
        
        Point p = new Point(1, 0);
        ColorPoint cp = new ColorPoint(1, 0, Color.RED);
        
        System.out.println(unitCircle.contains(p)); // true
        System.out.println(unitCircle.contains(cp)); // false, 리스코프 치환 원칙 위배!
        ```

      여기서 `ColorPoint`는 `Point`의 하위 클래스니까, 원래는 `Point`처럼 `unitCircle`에서 `contains()` 메서드를 호출했을 때 true가 나와야 한다.

      하지만, `equals` 메서드가 `getClass()`를 기준으로 비교하기 때문에 `cp`는 `Point`가 아니라고 판단해서 `false`가 출력 → 리스코프 치환 원칙 위배

        - 리스코프 치환 원칙

          상위 클래스가 사용되는 모든 곳에서 하위 클래스도 문제없이 사용할 수 있어야 한다는 원칙. 즉, `Point`를 사용하는 코드에서 `ColorPoint`도 아무 문제 없이 사용할 수 있어야 한다는 뜻이다.

          **왜 LSP를 지켜야 할까?**

          `Point`를 사용하는 코드가 `ColorPoint`와도 정상적으로 동작해야만 객체 지향의 상속이 제대로 된 것

          하지만 위처럼 `getClass()`로 비교하면, 하위 클래스는 상위 클래스와 동등하게 사용되지 못하고, 상속의 이점이 사라진다. 결국, 상속을 통한 다형성의 원칙을 지키지 못하는 셈


        ### **해결**
        
        상속 대신 컴포지션을 사용!!
        
         즉, `ColorPoint`가 `Point`를 상속하는 대신, `Point` 객체를 필드로 포함시킨다.
        
        ### 예시
        
        ```java
        public class ColorPoint {
            private final Point point;
            private final Color color;
        
            public ColorPoint(int x, int y, Color color) {
                point = new Point(x, y);
                this.color = color;
            }
        
            public Point asPoint() {
                return point;
            }
        
            @Override
            public boolean equals(Object o) {
                if (!(o instanceof ColorPoint)) return false;
                ColorPoint cp = (ColorPoint) o;
                return cp.point.equals(point) && cp.color.equals(color);
            }
        }
        ```
        
        → 상속 문제 없이 `Point`와 `ColorPoint`를 함께 사용할
        
        - ** **잘못된 설계의 예 - `Timestamp`**
            
            자바 표준 라이브러리에도 비슷한 문제가 있다.
            
            `Timestamp`는 `Date`를 상속하면서 추가된 `nanoseconds` 필드 때문에 `equals` 메서드의 대칭성 문제가 발생한다.
            이로 인해 두 객체를 섞어 사용할 때 예상치 못한 오류가 발생

- **일관성 (Consistency)**
    - 같은 두 객체를 계속 비교했을 때 결과가 유지
    - `x.equals(y)`가 계속 `true`였는데, 갑자기 `false`로 바뀌면 이상하니까
    - **일관성 문제**

      일관성이란, 두 객체가 한 번 같다고 판단되면 앞으로도 계속 같아야 한다는 원칙

      즉, 두 객체가 서로 같다고 한 후, 어느 하나가 바뀌지 않는 한 계속 같다고 나와야 한다.

      ### 불변 객체 vs. 가변 객체

        - **불변 객체 :** 한 번 값이 설정되면 그 값을 바꿀 수 없는 객체
          `equals` 메서드가 한 번 두 객체를 같다고 판단하면, 그 객체들은 영원히 같다고 나와야 한다.
        - **가변 객체** : 시간이 지나면서 그 값을 바꿀 수 있다. 그래서 두 객체가 어느 시점에는 같았다가 나중에는 달라질 수도 있다. → `equals` 메서드를 작성할 때 더 신경 쓰자

      ### **신뢰할 수 없는 자원 사용 금지**

      `equals` 메서드에서 **신뢰할 수 없는 자원**을 사용하면 안된다.
      신뢰할 수 없는 자원이란, 결과가 항상 일정하지 않은 데이터나 외부 네트워크 자원(변하는 것들). 예를 들어 네트워크를 통해 얻은 IP 주소를 비교하는 것은 위험하다. 왜냐하면 네트워크 상태에 따라 결과가 달라질 수 있기 때문이다. → 일관성이 없고, 오류를 발생

- **null이 아닌 객체와의 비교**
    - 어떤 객체도 `null`과 같을 수 없다
    - `x.equals(null)`은 항상 `false`
    - **null이 아닌 개체 비교 문제**

      모든 객체는 `null`과 절대로 같으면 안된다. 즉, `x.equals(null)`은 항상 `false`

      ### **`null` 검사**

      `null`을 직접 확인할 필요는 없다. `instanceof` 연산자를 사용하자.

      → `instanceof`는 `null` 값을 넣어도 자동으로 `false`를 반환


### **종합정리 : `equals` 메서드 구현 단계**

1. **자기 자신의 참조인지 확인**
    - **`==` 연산자**로 입력이 자기 자신인지 먼저 확인. 같으면 바로`true`를 반환
2. **`instanceof`로 타입 확인**
    - 입력된 객체가 **올바른 타입**인지 확인. 그렇지 않으면 `false`를 반환. (`instanceof`는 자동으로 `null`도 처리)
3. **형변환**
    - 타입이 맞으면 안전하게 해당 타입으로 형변환 (2번을 하면 바로 통과)
4. **핵심 필드 비교**
    - 객체의 핵심 필드들을 하나씩 비교. 모든 필드가 같다면 **`true`**, 하나라도 다르면 `false`

### 기본 타입 vs. 참조 타입

- 기본 타입 필드는 `==`로 비교
- 참조 타입 필드는 그 객체의 `equals` 메서드로 비교
- float, double 필드는 Float.compare(float,float) , Double.compare(double,double) 로 비교
- 배열 필드가 있다면 `Arrays.equals()` 메서드를 사용

### **복잡한 필드 처리**

필드가 복잡해서 비교할 수 없을 때는 그 필드를 표준화된 형태로 변환해서 비교하는 방법을 사용

특히 불변 객체의 경우, 미리 계산해둔 값을 사용

### **`equals` 구현 예시 : `PhoneNumber` 클래스**

`PhoneNumber` 클래스 예시
이 클래스는 지역번호, 앞자리 번호, 가입자 번호 세 가지 정보를 가진다. 이 필드들을 기반으로 `equals` 메서드를 작성해보면

```java
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;
//final 키워드를 사용했으므로, 한 번 설정된 값은 변경할 수 없는 불변 필드
    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
    }
//rangeCheck 메서드를 사용해서 값을 검증한 뒤 할당. 입력된 값이 범위를 벗어나지 않았는지 확인
    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }
//지역 번호는 0에서 999까지 허용되므로, 이 범위를 벗어나면 IllegalArgumentException 예외를 던져서 잘못된 입력임을 알림
//범위를 벗어나지 않으면, int 타입 값을 short로 변환해서 반환(메모리를 절약)
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;  //  객체 o가 자기 자신인지 확인, 자기 자신과 같으면 true
        }
        if (!(o instanceof PhoneNumber)) {
            return false;  // PhoneNumber 타입인지 확인. PhoneNumber가 아니라면, false
        }
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
         // 각 필드를 하나씩 비교, PhoneNumber 타입이라면, 형변환을 한 다음, 해당 객체의 필드들과 현재 객체의 필드들을 비교해.
//지역 번호(areaCode), 앞자리 번호(prefix), 가입자 번호(lineNum)를 하나씩 비교해서 모두 같으면 true, 하나라도 다르면 false
    }
}
```

## 주의 사항

### 1. **`equals`를 재정의할 땐 `hashCode`도 재정의**

- **`equals`와 `hashCode`는 함께 사용되는 메서드이다**. (아이템 11)
  특히, `HashMap`이나 `HashSet` 같은 자료구조에서 이 두 메서드를 사용하는데, `equals`만 재정의하고 `hashCode`는 그대로 두면 자료구조가 제대로 동작하지 않을 수 있다.

  **→ 왜 ?** : `equals`는 두 객체가 같은지를 확인하고, `hashCode`는 객체가 저장될 위치를 결정한다. 즉, 두 객체가 같다면 같은 **`hashCode`** 값을 가져야 한다. 그렇지 않으면 `HashMap` 같은 자료구조에서 엉뚱한 위치에 객체가 저장되거나 검색될 수 있다.


### 2. **너무 복잡하게 해결하지 말자**

- `equals`를 구현할 때 너무 깊이 파고들 필요는 없다. 단순하게 필드(변수)들만 비교하면 충분
- 예를 들어, 파일을 나타내는 `File` 클래스에서, 그 파일이 심볼릭 링크(파일이 다른 파일을 가리키는 방법)인지까지 비교하려고 하면 너무 복잡해진다.

### 3. **`Object` 외의 타입을 매개변수로 받는 `equals` 메서드를 만들지 말자**

- `equals` 메서드는 항상 `Object` 타입을 매개변수로 받아야 한다.

```java
// 잘못된 예시 : 입력 타입은 반드시 object로!
public boolean equals(MyClass o) {
    // 코드 내용...
}
```

- 이 코드는 **재정의**가 아니라 다중정의(overloading)가 된다. 즉, `Object`의 `equals` 메서드를 재정의한 것이 아니라, 완전히 새로운 `equals` 메서드를 만든 것

  이렇게 하면, 정상적인 `equals` 비교가 작동하지 않는다.


### 4. **`@Override` 애너테이션 사용**

- `@Override` 애너테이션은 메서드를 재정의할 때 사용하는데, 이걸 쓰면 실수를 방지할 수 있다. 만약 재정의할 메서드를 잘못 작성했더라도, 컴파일러가 이를 확인하고 오류를 알려준다.

```java
//예시
@Override
public boolean equals(MyClass o) {
    // 컴파일되지 않음! 오류 메시지가 뜸
}
```

이 코드는 `@Override` 애너테이션을 사용했지만, 매개변수가 `Object`가 아니라 `MyClass`이므로 컴파일 오류가 발생. `equals`는 `Object` 타입을 매개변수로 받아야 하기 때문에 컴파일되지 않는다. → 애너테이션을 사용하면 오류 메시지 확인 가능

- 애너테이션이란?

  애너테이션(annotation)은 자바에서 특정한 의미나 기능을 코드에 부여하는 주석 같은 것. 하지만 단순한 주석과는 다르게 컴파일러나 런타임에 영향을 미치는 특별한 역할을 한다. 주로 코드에 추가적인 정보를 제공하거나 특정 동작을 하게 할 수 있다.

  ### 예시: `@Override` 애너테이션

    - `@Override` 가 애너테이션 중 하나다. 이 애너테이션을 메서드 위에 붙이면, 그 메서드가 부모 클래스에서 상속받은 메서드를 '재정의'(overriding)하고 있다는 것을 명확하게 알려준다.
    - `@Override`를 사용하면 컴파일러가 재정의한 메서드가 부모 클래스의 메서드를 올바르게 재정의하고 있는지 확인해준다. 만약 잘못 재정의한 경우, 컴파일러가 오류를 알려주기 때문에 실수를 방지할 수 있다.

  ### `@Override` 예시:

    ```java
    class Animal {
        public void sound() {
            System.out.println("Animal makes a sound");
        }
    }
    
    class Dog extends Animal {
        @Override  // 부모 클래스의 메서드를 재정의하는 것을 알림
        public void sound() {
            System.out.println("Dog barks");
        }
    }
    ```

  여기서 `Dog` 클래스는 부모 클래스인 `Animal`의 `sound()` 메서드를 재정의하고 있다. `@Override` 애너테이션을 사용하면, 부모 클래스에 `sound()` 메서드가 없거나, 메서드 이름을 잘못 썼다면 컴파일 오류가 발생
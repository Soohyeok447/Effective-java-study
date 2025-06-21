## 3. private 생성자나 열거 타입으로 싱글톤임을 보증하라

- 싱글턴 : 인스턴스를 오직 하나만 생성할 수 있는 클래스
    - 애플리케이션 전체에서 동일한 객체를 공유하고 싶을 때 사용

1. **public static final 필드 방식의 싱글톤**

   가장 단순한 구현 방식. 인스턴스를 클래스가 로딩될 때 미리 생성해두고 public static final 필드로 외부에 제공한다

    ```java
    public class Singleton {
        public static final Singleton INSTANCE = new Singleton();
    
        private Singleton() {} // 외부에서 new Singleton() 쓰는 것 막음
    
        public void doSomething() {...}
    }
    ```

    - 장점: 구현이 매우 간단하고, 클래스 로딩 시점에 인스턴스가 만들어지므로 스레드 안전
    - 단점: 인스턴스가 항상 미리 생성되므로, 실제로 사용하지 않아도 메모리를 차지할 수 있음
      <br/><br/>
2. **정적 팩토리 방식의 싱글톤**

    ```java
    public class Singleton {
        private static final Singleton INSTANCE = new Singleton();
    
        private Singleton() {}
    
        public static Singleton getInstance() {
            return INSTANCE;
        }
    }
    ```

    - 장점: 추후 싱글턴에서 일반 객체로 변경할 때 API를 바꾸지 않고도 구현을 바꿀 수 있음
    - 단점: public static final 필드 방식과 마찬가지로 클래스 로딩 시 인스턴스가 생성됨    
      <br/>
3. **열거 방식(enum)의 싱글톤**

   가장 권장하는 방식! 자바의 enum 타입은 기본적으로 인스턴스가 하나만 생성되도록 보장하므로, 싱글턴을 구현하기에 가장 안전하다

    ```java
    public enum Singleton {
        INSTANCE;
    
        public void doSomething() {...}
    }
    ```

- 장점:
    - 코드가 간결함
    - 직렬화/역직렬화, 리플렉션 공격에도 안전함
    - 자바 언어 차원에서 단 하나의 인스턴스만 생성됨을 보장함
- 단점: enum은 다른 클래스를 **상속할 수 없으므로**, 반드시 상속이 필요한 경우에는 사용할 수 없습니다.
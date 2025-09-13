## 20. 추상 클래스보다는 인터페이스를 우선하라

### 요점 정리

- 다형성을 구현할 때의 유연성, 안정성 : 인터페이스 > 추상 클래스
   <br/> → 왜?? 인터페이스는 다중구현 (다중 상속), 유연한 구조 설계, 기존 클래스 확장성 등 장점이 있기 때문!!
- 새로운 타입/역할/기능은 반드시 인터페이스로 구현하는 걸 우선한다
- 표준 인터페이스로 설계했다면 구현 보조를 위해 추상 클래스를 함께 제공하자

### 왜?? 인터페이스를 우선해야 되냐

1. **다중 상속이 가능하다**

   클래스는 딱 하나의 클래스만 상속할 수 있다.

   반면 인터페이스는 여러 개를 동시에 구현 (implement)할 수 있다. 그러니 기존 타입 위에 Comparable, Serializable 등 새로운 개념을 덧붙일 때 매우 쉽다
   <br/><br/>
2. **기존 클래스에 타입을 추가할 수 있다**

   이미 존재하는 클래스를 수정하는 일 없이 새로운 인터페이스로 확장이 가능하다! 여러 개의 인터페이스를 구현할 수 있기 때문.

   예를 들어, JDK 8 이후에 수많은 기존 라이브러리 클래스들이 `Comparable`, `Iterable` 등 implements 형태로 보강됐다!
   <br/><br/>
3. **여러 특성을 조합할 때 인터페이스로 깔끔하게 표현 가능하다**

    ```java
    public interface Singer { void sing(); }
    public interface Songwriter { void compose(); }
    public interface SingerSongwriter extends Singer, Songwriter { void perform(); }
    ```

   클래스로만 설계하면 조합 수마다 별도의 클래스가 필요하다..(조합 폭발)


### 구현 예시

인터페이스로 “타입”을 정의하고.. 필요하다면 기본 구현 (목업)은 “추상 클래스”로 보조하는 느낌으로 뼈대를 잡아도 된다.

```java
public interface SimpleMap<K, V> {
    int size();
    V get(K k);
    // ...
}

public abstract class AbstractSimpleMap<K, V> implements SimpleMap<K, V> {
    // 기본 구현을 이곳에 모음
}
```

### 추상 클래스의 한계점은?

- 다중 상속이 불가능하다
- 이미 클래스 계층 구조가 짜여 있다면 기존 코드에 추상 클래스를 추가하거나 적용이 불가하다
- 클라이언트가 기존 타입과 혼용/확장이 굉장히 어렵다
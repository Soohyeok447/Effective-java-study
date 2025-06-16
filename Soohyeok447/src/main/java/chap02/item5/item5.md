# item5. 자원을 직접 명시하지 말고 의존 객체 주입(DI)을 사용하라

이것도 설계 핵심

-   객체 내부에서 필요한 **자원** 을 직접 생성 **`new`** 하지 말고, **외부에서 주입받자**
-   클래스가 사용하는 의존성을 스스로 만들지 말고 **생성자, 메서드, 설정자 등을 통해 주입**

### 직접 생성하는 상황 ❌

```java
public class SpellChecker {
    private final Dictionary dictionary = new KoreanDictionary(); // 직접 생성
    // private final Dictionary dictionary = new JapaneseDictionary(); // 바꾸고 싶으면 이런짓 해야함

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
```

-   SpellChecker는 KoreanDictionary에 강하게 결합됨 (강결합)
-   테스트 할 때, 다른 Dictionary를 사용할 수 없어서 테스트하기 어려움
-   재사용도 어려움 (다른 언어에 쓸 수 없음)

### 의존 객체 주입 (Dependency Injection)를 하자 👍

```java
// 인터페이스
public interface Dictionary {
    boolean contains(String word);
}
```

```java
// 콘크리트 클래스 2개
public class KoreanDictionary implements Dictionary {
    @Override
    public boolean contains(String word) {
        // 안녕하세요
    }
}

public class JapaneseDictionary implements Dictionary {
    @Override
    public boolean contains(String word) {
        // 콘니찌와
    }
}
```

```java
// Dictionary를 주입받을 사용 객체
public class SpellChecker {
    private final Dictionary dictionary;

    // 생성자 주입 방식 -> SpeelChecker가 생성될 때 dictionary가 정해짐
    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
```

```java
// main
public class Main {
    public static void main(String[] args) {
        // 한국어 사전
        SpellChecker koreanChecker = new SpellChecker(new KoreanDictionary()); // KoreanDictionary를 주입
        System.out.println(koreanChecker.isValid("안녕하세요"));

        // 일본어 사전
        SpellChecker japaneseChecker = new SpellChecker(new JapaneseDictionary()); // JapaneseDictionary를 주입
        System.out.println(japaneseChecker.isValid("콘니찌와"));
    }
}
```
-   Dictionary는 인터페이스고 실제 구현체는 외부에서 주입하는 방식
-   다양한 Dictionary 구현체를 자유롭게 바꿔 끼울 수 있음
    -   유연성이 증가함
    -   테스트도 쉬워짐 (stub이나 mock을 주입할 수 있기 때문)
    -   결합도도 감소함 (KoreanDictionary나 JapaenseDictionary의 구현을 알 필요가 없어졌음)
-   여러 DI 방식이 있지만 생성자 주입이 가장 좋음
-   스프링 같은 프레임워크를 쓰면 쉽게 DI를 해주기때문에 개발자가 신경쓸게 적어져서 좋음

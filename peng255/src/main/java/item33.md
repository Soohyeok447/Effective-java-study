### 33. 타입 안전 이종 컨테이너를 고려하라
타입 안전 이종 컨테이너란?

여러 가지 서로 다른 타입의 값(예: 이름은 String, 나이는 Integer, 학생 객체 등등)을 하나의 컨테이너에 안전하게 저장하고 꺼낼 수 있는 방식

보통의 컬렉션은 한 컨테이너에 한 타입만 넣을 수 있는데, 이 패턴은 여러 타입을 안전하게 다룰 수 있다.

1. 일반적인 컬렉션의 한계
```java
Set<String> names = ...;      // String만!
Set<Integer> ages = ...;      // Integer만!

// 이름과 나이 둘 다 담고 싶은데? 분리해서 관리해야 함
하나의 컨테이너에서 다양한 타입 값을 다루고 싶을 때 불편
```
2. 타입 안전 이종 컨테이너 패턴(특정 키에 따라 타입이 연결됨)
```java
public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(type, instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
```
사용 예시 
```java
Favorites f = new Favorites();
f.putFavorite(String.class, "커피");
f.putFavorite(Integer.class, 300);
f.putFavorite(Class.class, Favorites.class);

String favDrink = f.getFavorite(String.class);      // 자동으로 String 반환
int favNum = f.getFavorite(Integer.class);          // 자동으로 int 반환
Class<?> favClass = f.getFavorite(Class.class);     // 클래스 반환

System.out.println(favDrink); // "커피"
System.out.println(favNum);   // 300
System.out.println(favClass); // "Favorites"
```
여러 타입을 한 컨테이너에 안전하게 담고, 꺼낼 땐 타입까지 맞게 반환받음

실수로 타입이 안 맞으면 컴파일러 경고+런타임 오류가 나니 안정적

3. 일상 예시 비유<br/>
여러 타입의 "즐겨찾기"를 하나의 큰 박스에 넣는데,

"이 이름의 favorite을 String으로 꺼내줘"

"이 값의 favorite을 Integer로 꺼내줘"

"이 객체의 favorite을 Class 객체로 꺼내줘"

"키"는 보통 Class 객체(예: String.class, Integer.class)가 되고

값을 넣고 뺄 때 항상 타입까지 함께 전달 → 타입이 맞지 않으면 오류

4. 유익한 점<br/>
기존 컬렉션처럼 여러 개의 Set, Map을 각각 만들 필요 없음

원하는 만큼 다양한 타입을 한 곳에 안전하게 다룰 수 있다

실전에서는 설정값, 즐겨찾기, DB row 등 여러 컬럼 값을 다룰 때,
혹은 애노테이션 모듈에서 타입별 처리할 때 매우 효과적

- 전체 요약
컬렉션 타입(Set, Map 등)은 보통 한 가지 타입만 안전하게 다룸

타입 안전 이종 컨테이너는 "키"의 타입(Class<T>)에 따라 여러 다른 타입의 값을 안전하게 저장/조회할 수 있게 해준다

구현은 Class<T>를 키, Object를 값으로 두고, get할 때 타입을 안전하게 캐스팅함

일상적으로는 여러 종류 값을 한 곳에 안전하게 관리하고 싶을 때 가장 잘 쓰는 패턴

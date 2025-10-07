### **타입 안전 이종 컨테이너 패턴**이란?

일반적으로 Set이나 Map 같은 자료구조는 하나의 타입만 저장할 수 있다. `Set<String>`은 문자열만 저장할 수 있고, `Map<String, Integer>`는 문자열을 키로, 정수를 값으로 저장한다. 그런데 가끔은 다양한 타입의 데이터를 한꺼번에 안전하게 저장하고 꺼내는 것이 필요할 때가 있다.

타입 안전 이종 컨테이너 패턴은 이런 문제를 해결하기 위한 방법이다. 이 패턴에서는 컨테이너 자체가 아닌, 키를 제네릭으로 만들어서, 그 키와 연결된 값을 안전하게 저장하고 꺼낼 수 있게 해준다.

### Favorites 클래스 예시

이해를 돕기 위해, 즐겨찾기 기능을 제공하는 `Favorites` 클래스를 예로 들어보자. 이 클래스는 다양한 타입의 데이터를 안전하게 저장하고, 저장한 타입에 맞게 꺼낼 수 있다.

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

### Favorites 클래스의 원리

1. 키로 `Class<T>`를 사용: `Favorites` 클래스는 데이터를 저장할 때, 그 데이터의 타입을 나타내는 `Class` 객체를 키로 사용한다. 이 덕분에 어떤 타입의 데이터인지 기억할 수 있다.
2. 값은 `Object`로 저장: 실제 값은 `Object`로 저장되지만, `Class` 객체를 통해 안전하게 타입을 변환해서 값을 꺼낼 수 있다.
3. 타입 안전성: `Favorites`는 제네릭 덕분에 타입이 안전하다. 예를 들어, `String`을 저장하면, 꺼낼 때도 자동으로 `String` 타입으로 변환된다. 잘못된 타입을 꺼내는 일은 없다는 뜻!!

```java
Favorites f = new Favorites();
f.putFavorite(String.class, "Java");
f.putFavorite(Integer.class, 123);
f.putFavorite(Class.class, Favorites.class);

String favoriteString = f.getFavorite(String.class);
int favoriteInteger = f.getFavorite(Integer.class);
Class<?> favoriteClass = f.getFavorite(Class.class);

System.out.printf("%s %d %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
```

이 코드를 실행하면

```
Java 123 Favorites
```

위처럼 출력된다! (타입 안전하게 각 타입에 맞는 데이터를 꺼낼 수 있다.)

### 동작 원리

`Favorites` 클래스가 작동하는 핵심은 `Class<T>`의 `cast()` 메서드이다. 이 메서드는 동적으로 타입을 변환해준다. `String.class.cast()`는 값을 문자열로 변환해주고, `Integer.class.cast()`는 값을 정수로 변환해준다. 덕분에 타입 안전성을 유지하면서 데이터를 처리할 수 있다!

### 제약 사항

1. 로 타입을 사용할 때 주의: 만약 `Class<T>` 대신 로 타입(타입 매개변수가 없는 타입)을 사용하면 타입 안전성이 깨질 수 있다.
2. 실체화 불가 타입에는 사용 불가: `Favorites`는 제네릭 타입이 저장될 때 제한이 있다. `List<String>` 같은 건 저장할 수 없다. 왜냐하면 `List<String>.class`처럼 제네릭 타입에 해당하는 `Class` 객체를 만들 수 없기 때문.

### 안전성을 높이는 방법

만약 `Favorites`의 안전성을 더 강화하고 싶다면, `putFavorite` 메서드에서 동적 형변환을 사용해서 키의 타입과 값의 타입이 맞는지 확인할 수 있다

```java
public <T> void putFavorite(Class<T> type, T instance) {
    favorites.put(type, type.cast(instance));  // 값의 타입을 확인
}
```

### 한정적 타입 토큰

특정한 타입만 받도록 제한하고 싶을 때가 있다. 예를 들어, `Favorites` 클래스에서 애너테이션 타입만 받아야 할 때, 한정적 타입 토큰을 사용할 수 있다.

```java
public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    return element.getAnnotation(annotationType);
}
```

이렇게 하면 애너테이션 타입에 대해서만 값을 처리할 수 있다.
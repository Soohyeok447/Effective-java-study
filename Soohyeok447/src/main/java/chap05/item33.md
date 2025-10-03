# item33. 타입 안전 이종 컨테이너를 고려하라

## 선요약

일반적인 제네릭 컨테이너는 한 종류의 타입만 안전하게 저장할 수 있음. 하지만 타입 자체를 키로 사용하는 패턴을 적용하면 여러 종류의 타입을 하나의 컨테이너에 담으면서도 타입 안전성을 보장하는 컨테이너를 만들 수 있음.

## 동종 컨테이너란?

동종(Homogeneous) 컨테이너란 모든 원소가 **같은 타입인** 컨테이너.

대부분의 제네릭 컬렉션이 여기에 해당.

컨테이너를 선언할 때 타입(E, K, V)이 하나로 고정됨.

#### 예시: List<String>, Set<Integer>, Map<String, User>

## 이종 컨테이너란?

이종(Heterogeneous) 컨테이너란 서로 다른 타입의 원소들을 담을 수 있는 컨테이너.

Map<String, Object> 같은 것들.

#### 예시:

```java
Map<String, Object> map = new HashMap<>();
map.put("이름", "김수혁");
map.put("나이", 28);
```

여러 타입을 담을 수는 있지만 값을 Object 타입으로 받기 때문에 타입 정보가 사라짐.

값을 꺼낼 때 원래 타입이 무엇인지 추측해서 직접 형변환해야 하기때문에 타입 안전성이 깨짐.

## 타입안전 이종 컨테이너란?

타입 안전 이종 컨테이너는 여러 다른 타입을 담을 수 있으면서(이종) 값을 꺼낼 때 형변환 없이도 타입 안전성을 완벽하게 보장하는(타입 안전) 컨테이너.

Map의 키를 제네릭으로 타입 매개변수화함

#### 구현 방법: Map의 키로 `String` 대신 `Class<T>` 타입을 사용.

#### 예시 :

```java
public class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    // String.class 키에는 String 타입만 저장
    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(type, instance);
    }

    // Class 객체의 cast 메서드로 형변환해서 반환
    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }

}
```

## 장점은?

#### 타입 안전성

값을 꺼낼 때 명시적 형변환이 전혀 필요 없기때문에 런타임에 ClassCastException이 발생할 위험이 원천적으로 차단됨.

#### 코드 가독성 증가

`(String) map.get("key")`같은 위험한 형변환 코드 없음.

`favorites.getFavorite(String.class)`처럼 간결하게 값을 얻을 수 있음.

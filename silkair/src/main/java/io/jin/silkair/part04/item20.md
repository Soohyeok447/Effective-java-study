자바에서는 다중 구현 메커니즘으로 인터페이스와 추상 클래스 두 가지를 제공한다. Java 8부터 인터페이스도 디폴트 메서드를 가질 수 있게 되어, 두 메커니즘 모두 인스턴스 메서드를 구현할 수 있지만, 인터페이스는 더 유연하고 강력한 기능을 제공한다.

### 인터페이스와 추상 클래스의 차이

1. 인터페이스는 다중 상속 가능: 어떤 클래스가 여러 인터페이스를 구현할 수 있기 때문에, 새로운 인터페이스를 쉽게 추가할 수 있다. 하지만 추상 클래스는 상속이 한 번만 가능하기 때문에, 기존 클래스에 새로운 추상 클래스를 추가하기 어렵다.
2. mixin에 적합: 인터페이스는 mixin 타입으로 잘 사용된다. 믹스인은 선택적 행위를 제공하는 인터페이스로, 여러 클래스에 쉽게 혼합할 수 있다. 예를 들어 `Comparable` 인터페이스는 클래스의 주된 타입 외에도, 순서 비교를 제공하는 기능을 추가하는 mixin이다.

```java
public interface Singer {
    AudioClip sing(Song s);
}

public interface SongWriter {
    Song compose(int chartPosition);
}

public interface SingerSongWriter extends Singer, SongWriter {
    AudioClip strum();
    void actSensitive();
}
```

`Singer`와 `SongWriter` 인터페이스를 동시에 구현하고, 추가 기능을 포함한 `SingerSongWriter` 인터페이스를 만들 수 있다. 이를 클래스 계층 구조로 표현하려면 엄청나게 복잡해지겠지만, 인터페이스를 사용하면 간결하고 유연한 설계가 가능

### 디폴트 메서드

인터페이스에서 디폴트 메서드를 제공하면, 구현 방법이 명확한 메서드를 쉽게 추가할 수 있다. 디폴트 메서드는 상속받는 클래스가 재정의할 필요 없이 사용할 수 있는 기본 구현을 제공해, 인터페이스를 구현하는 클래스들의 부담을 줄여준다

```java
default boolean removeIf(Predicate<? super E> filter) {
    Objects.requireNonNull(filter);
    boolean removed = false;
    final Iterator<E> each = iterator();
    while (each.hasNext()) {
        if (filter.test(each.next())) {
            each.remove();
            removed = true;
        }
    }
    return removed;
}
```

→ 디폴트 메서드는 명백한 구현을 제공할 수 있는 경우 사용된다. 그러나 `equals`, `hashCode`, `toString` 같은 메서드는 디폴트 메서드로 제공하면 안된다. 또한, 디폴트 메서드를 추가할 때는 `@implSpec` 태그를 사용해 내부 동작을 설명해줘야 한다.

### 인터페이스와 추상 골격 클래스

인터페이스와 추상 골격 클래스를 함께 제공하면, 인터페이스의 유연성과 추상 클래스의 편리함을 모두 취할 수 있다. 인터페이스는 타입을 정의하고 골격 구현 클래스는 기본적인 메서드들을 구현하여, 하위 클래스가 쉽게 상속받아 사용할 수 있게 돕는 방식

- 인터페이스는 타입만 제공하고, 추상 골격 클래스가 실제로 필요한 메서드들을 구현하는 방식
- 이를 통해 하위 클래스는 골격 클래스를 확장하기만 하면 인터페이스 구현에 필요한 대부분의 작업이 완료된다. →템플릿 메서드 패턴!!

```java
static List<Integer> intArrayAsList(int[] a) {
    return new AbstractList<Integer>() {
        @Override public Integer get(int i) {
            return a[i];
        }

        @Override public Integer set(int i, Integer val) {
            int oldVal = a[i];
            a[i] = val;
            return oldVal;
        }

        @Override public int size() {
            return a.length;
        }
    };
}
```

→ int 배열을 `Integer` 리스트로 변환하는 어댑터를 만들었다. 추상 골격 클래스를 이용해 필요한 메서드를 간단하게 구현했다.

### 골격 구현 클래스의 작성 순서

1. 기반 메서드를 선정해, 다른 메서드들이 이 메서드를 기반으로 구현되도록 한다.
2. 디폴트 메서드로 구현할 수 있는 메서드를 제공해서 구현 부담을 덜어준다.
3. 남은 메서드가 있다면 추상 골격 클래스를 만들어서 직접 구현
4. 골격 구현은 상속을 위한 설계이므로 설계와 문서화에 신경 쓰자

### 단순 구현 클래스

골격 구현 클래스와 달리 단순 구현 클래스는 상속 없이 인터페이스를 구현한 것이다.
Ex)  `AbstractMap.SimpleEntry`는 간단한 Map.Entry 구현체이다.

```java
public static class SimpleEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
```

단순 구현 클래스는 복잡한 상속 없이, 간단한 인터페이스 구현을 제공하고 싶을 때 유용
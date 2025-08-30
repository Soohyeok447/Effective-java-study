## 21. 인터페이스는 구현하는 쪽을 생각해 설계하라
### 요점 정리

- 한 번 인터페이스가 공개되면 수정이 매우 어렵다!
- 인터페이스를 설계할 때는 장기정 호환성, 다양한 구현을 반드시 고려해서 신중하게 “최소, 필수적인 메서드만” 설계해야 한다
- default method로 보완해도 기존 그현체가 의도와 다르게 동작할 수 있다

### 인터페이스 설계시 생길 수 있는 위험

- 기존에는 인터페이스에 메서드를 하나 추가하면 모든 구현체가 컴파일 에러
- Java 8부터는`default`메서드로 새 기능을 추가할 수 있지만,
    - 예전 구현체들이 이 동작을 **원하지 않더라도 무조건 주입**됨
    - 예제:`Collection.removeIf`는 기본적으로 iterator 기반 구현을 넣었지만,

      동기화 컬렉션(synchronized collection)에선 lock 없이 removeIf가 작동 → **동시성 버그 발생**

        ```java
        // Java 8 Collection 인터페이스에 추가된 default 메서드
        default boolean removeIf(Predicate<T> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Iterator<T> it = iterator(); it.hasNext(); ) {
                if (filter.test(it.next())) {
                    it.remove();
                    removed = true;
                }
            }
            return removed;
        }
        ```


### 좋은 인터페이스 설계를 위해 실무에서 지킬 것

- 미리 여러 가지(최소 3개 이상)의 구현체와 다양한 클라이언트 코드로 테스트
- 필수적이고, 다양한 구현체에서 문제 없이 쓸 수 있는 "최소 기능"만 명확하게 정의
- 되도록이면 불변식, 동기화, 실패 처리 등이 구현마다 달라질 수 있다는 점을 고려
- 변동 가능성이 있는 기능은 처음부터 빼거나, interface 추가로 확장
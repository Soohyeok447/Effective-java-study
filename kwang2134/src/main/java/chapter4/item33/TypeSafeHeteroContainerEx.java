package chapter4.item33;

import java.util.*;

public class TypeSafeHeteroContainerEx {
    /**
     * 보통 컬렉션 (List<E>, Map<K,V>, Set<E>)은 컨테이너 자체에 타입 매개변수가 1~2개로 고정
     * 특정 컨테이너에 다양한 타입(이종 heterogeneous)을 타입 안전하게 저장하고 싶을 때는 키를 타입 매개변수화(제네릭)
     * Class<T> 같은 타입 토큰을 키로 활용하면 구현 가능
     */

    // 타입 토큰(Class<T>)을 키로, Object를 값으로 저장 → 인출 시 타입 안전 보장
    private Map<Class<?>, Object> values = new HashMap<>();

    public <T> void put(Class<T> type, T value) {
        values.put(Objects.requireNonNull(type), value);
    }
    public <T> T get(Class<T> type) {
        Object value = values.get(type);
        if (value != null)
            return type.cast(value); // 타입 안전하게 변환
        return null;
    }

    public static void main(String[] args) {
        TypeSafeHeteroContainerEx container = new TypeSafeHeteroContainerEx();
        container.put(String.class, "Hello World");          // String 저장
        container.put(Integer.class, 2025);                   // Integer 저장
        container.put(List.class, Arrays.asList("A", "B"));   // List<String> 저장 (실제 타입 정보는 소거)

        String strVal = container.get(String.class);          // 타입 안전하게 꺼낼 수 있음
        Integer intVal = container.get(Integer.class);        // 타입 안전하게 꺼낼 수 있음
        List listVal = container.get(List.class);             // List<String>이지만, List만 알 수 있음

        System.out.println(strVal); // Hello World
        System.out.println(intVal); // 2025
        System.out.println(listVal); // [A, B]

        // 타입 토큰 커스텀 키 타입으로 확장 (예: DataKey<T>)
        DataKey<Boolean> boolKey = new DataKey<>("isTest", Boolean.class);
        container.put(boolKey.type(), true);
        Boolean boolVal = container.get(boolKey.type());
        System.out.println(boolVal); // true
    }

    // 커스텀 타입 토큰 키 예시 (별도 클래스로 제네릭 키 확장 가능)
    public static class DataKey<T> {
        private final String name;
        private final Class<T> type;

        public DataKey(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }
        public Class<T> type() { return type; }
        public String name() { return name; }
    }
}

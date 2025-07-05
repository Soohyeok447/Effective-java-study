package chapter3.item18;

import java.util.*;

public class CompositionEx {

    /**
     * 상속을 사용하는 방식
     * 상속은 캡슐화를 깨뜨림
     * 상위 클래스의 내부 구현이 달라진다면 그 여파로 인해 하위 클래스가 오작동할 수 있음
     * 만약에 상위 클래스의 addAll() 내부에서 add()를 호출하게 변경된다면 addCount가 중복으로 증가하게 되어 오동작할 수 있음
     */
    class BadInstrumentedHashSet<E> extends HashSet<E> {
        private int addCount = 0;

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    /**
     * 컴포지션을 사용
     * 전달 클래스를 작성하여 모든 Set의 메서드를 내부 Set 인스턴스에 위임함
     * @param <E>
     */
    class ForwardingSet<E> implements Set<E> {
        private final Set<E> s;

        public ForwardingSet(Set<E> s) {
            this.s = Objects.requireNonNull(s);
        }

        // 모든 메서드는 내부 Set 인스턴스에 작업을 위임(전달)
        public int size() { return s.size(); }
        public boolean isEmpty() { return s.isEmpty(); }
        public boolean contains(Object o) { return s.contains(o); }
        public Iterator<E> iterator() { return s.iterator(); }
        public Object[] toArray() { return s.toArray(); }
        public <T> T[] toArray(T[] a) { return s.toArray(a); }
        public boolean add(E e) { return s.add(e); }
        public boolean remove(Object o) { return s.remove(o); }
        public boolean containsAll(Collection<?> c) { return s.containsAll(c); }
        public boolean addAll(Collection<? extends E> c) { return s.addAll(c); }
        public boolean retainAll(Collection<?> c) { return s.retainAll(c); }
        public boolean removeAll(Collection<?> c) { return s.removeAll(c); }
        public void clear() { s.clear(); }
    }

    /**
     * 래퍼 클래스에서 작성
     * 전달 클래스를 상속받아 원하는 기능만 추가
     * 상속 대신 컴포지션을 사용하여 내부 Set의 구현 변경에 대한 영향을 받지 않음
     * 즉 내부 Set이 HashSet에서 TreeSet으로 바뀌거나 HashSet의 내부 구현이 변경되어도 InstrumentSet의 동작은 그대로 유지됨
     */
    class InstrumentedSet<E> extends ForwardingSet<E> {
        private int addCount = 0;

        public InstrumentedSet(Set<E> s) {
            super(s);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

}

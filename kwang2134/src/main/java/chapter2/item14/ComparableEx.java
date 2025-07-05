package chapter2.item14;

import java.util.Comparator;

public class ComparableEx {
    /**
     * 순서 비교를 위해서는 Comparable 인터페이스를 구현해야 한다
     *
     * 구현하지 않는 경우 정렬 및 순서 관련 컬렉션 사용 불가
     */
    class Student_without_comparable {
        private String name;
        private int grade;

        public Student_without_comparable(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        // 컴파일 에러 - Comparable 미구현으로 정렬 불가
        // Collections.sort(students);

        // 순서를 보장하는 TreeSet 사용 불가
        // Set<Student> sortedSet = new TreeSet<>(students);
    }

    /**
     * compareTo의 일반 규약
     * 이 객체가 주어진 객체보다 작으면 음수 정수를, 같으면 0을, 크면 양의 정수를 반환
     */
    class Student_with_comparable implements Comparable<Student_with_comparable> {
        private String name;
        private int grade;

        public Student_with_comparable(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        // 성적을 기준으로 정렬
        @Override
        public int compareTo(Student_with_comparable o) {
            // 성적이 높은 순으로 내림차순 정렬
            return Integer.compare(o.grade, this.grade);
        }
    }

    /**
     * 다중 필드 비교를 통한 compareTo 구현
     */
    class Student_with_many_field implements Comparable<Student_with_many_field> {
        private int grade;      // 학년 (1순위)
        private String name;    // 이름 (2순위)
        private int age;        // 나이 (3순위)

        public Student_with_many_field(int grade, String name, int age) {
            this.grade = grade;
            this.name = name;
            this.age = age;
        }

        // 다중 필드 비교로 필드의 값이 동일하다면 다음 순위의 필드를 비교
        @Override
        public int compareTo(Student_with_many_field o) {
            // 1순위: 학년 비교
            int result = Integer.compare(this.grade, o.grade);
            if (result != 0) return result;

            // 2순위: 이름 비교
            result = this.name.compareTo(o.name);
            if (result != 0) return result;

            // 3순위: 나이 비교
            return Integer.compare(this.age, o.age);
        }

        // Comparator를 통해 메서드 체이닝으로 구현하는 방법
        private static final Comparator<Student_with_many_field> COMPARATOR =
                Comparator.comparingInt((Student_with_many_field s) -> s.grade)     // 1순위: 학년
                        .thenComparing(s -> s.name)             // 2순위: 이름
                        .thenComparingInt(s -> s.age);          // 3순위: 나이
    }

    /**
     * 잘못된 구현 예시
     */
    // 차이를 이용한 비교
    static class BadHashCodeComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            // 정수 오버플로우 위험
            return o1.hashCode() - o2.hashCode();
        }
    }

    // 관계 연산자 사용
    static class BadDoubleComparator implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            // 부동소수점 오류 위험
            return o1 < o2 ? -1 : (o1 == o2 ? 0 : 1);
        }
    }
}

package chapter2.item11;

import java.util.List;
import java.util.Objects;

public class HashCodeEx {

    class Student {
        private String name;
        private int studentId;

        public Student(String name, int studentId) {
            this.name = name;
            this.studentId = studentId;
        }

        // equals만 재정의하고 hashCode는 재정의하지 않음
        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if (!(o instanceof Student student)) return false;
            return studentId == student.studentId && Objects.equals(name, student.name);
        }
    }

    /**
     * equals 재정의 시 hashCode를 재정의하지 않는 경우
     * Hash 기반 컬렉션 사용 시 문제 발생
     * hashCode가 다르다면 다른 버킷에 저장되어 equals 비교조차 수행하지 않음
     */
    private List<Object>[] buckets;

    // HashSet.add() 내부 동작 (가정)
    public boolean add(Object o) {
        // 1단계: hashCode()로 버킷 위치 결정
        int hash = o.hashCode();
        int bucket = hash % buckets.length;

        // 2단계: 해당 버킷에서 equals()로 중복 검사
        for (Object existing : buckets[bucket]) {
            if (existing.equals(o)) {
                return false; // 중복이므로 추가하지 않음
            }
        }

        // 3단계: 새로운 요소 추가
        buckets[bucket].add(o);
        return true;
    }

    /**
     * 올바른 hashCode 구현
     */

    // 1. Objects.hash() 사용
    class UnivStudent {
        private String name;
        private int studentId;
        private String major;

        public UnivStudent(String name, int studentId, String major) {
            this.name = name;
            this.studentId = studentId;
            this.major = major;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UnivStudent that)) return false;
            return studentId == that.studentId && Objects.equals(name, that.name) && Objects.equals(major, that.major);
        }

        /**
         * Objects.hash() 사용
         * 간단하게 작성이 가능하나 입력 인수를 담기 위한 배열이 만들어지고
         * 입력 중 기본 타입이 있다면 박싱과 언박싱도 거쳐야 하기 때문에
         * 비교적 느린 성능
         */
        /*
        @Override
        public int hashCode() {
            return Objects.hash(name, studentId, major);
        }*/

        /**
         * 수동 구현
         * 성능을 위한다고 핵심 필드를 빼먹으면 안됨
         */
        @Override
        public int hashCode() {
            // 홀수이면서 소수인 31을 사용한 방법
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + studentId;
            result = 31 * result + (major != null ? major.hashCode() : 0);
            return result;
        }
    }
}

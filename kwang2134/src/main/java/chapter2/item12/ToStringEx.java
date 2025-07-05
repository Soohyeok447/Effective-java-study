package chapter2.item12;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToStringEx {

    /**
     * toString()을 제정의하지 않는 경우
     * 객체를 출력할 때 단순히 클래스 이름과 16진수로 표현한 해시코드가 출력됨
     * 객체의 상태를 알 수 없기 때문에 도움이 되지 않음
     */
    class User {
        private final Long id;
        private final String name;
        private final String email;
        private final LocalDateTime createdAt;
        private final boolean isActive;

        public User(Long id, String name, String email, LocalDateTime createdAt, boolean isActive) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.createdAt = createdAt;
            this.isActive = isActive;
        }

        /**
         * 모든 중요한 정보를 포함하여 toString 재정의
         * 객체를 출력할 때 객체 내부의 모든 정보가 출력되어
         * 객체의 상태를 한 번에 파악할 수 있음
         */
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", createdAt=" + createdAt +
                    ", isActive=" + isActive +
                    '}';
        }
    }

    /**
     * 객체의 상태를 문자열로 표현하기 힘든 경우
     * 요약 정보를 제공
     */
    class LargeDataSet {
        private final List<Integer> data;
        private final String name;

        public LargeDataSet(List<Integer> data, String name) {
            this.data = new ArrayList<>(data);
            this.name = name;
        }

        // 객체의 요약된 정보를 제공하는 toString
        @Override
        public String toString() {
            return String.format("LargeDataSet{name='%s', size=%d, range=[%d~%d]}",
                    name,
                    data.size(),
                    data.isEmpty() ? 0 : Collections.min(data),
                    data.isEmpty() ? 0 : Collections.max(data));
        }
    }

    /**
     * 주의사항
     *
     * 순환 참조 방지
     */
    class Department {
        private String name;
        private List<Employee> employees = new ArrayList<>();

        public Department(String name) {
            this.name = name;
        }

        public void addEmployee(Employee employee) {
            employees.add(employee);
            employee.setDepartment(this);
        }

        @Override
        public String toString() {
            /*순환 참조 위험 -> employees.toString() 이 다시 department.toString() 호출
            return "Department{name='" + name + "', employees=" + employees + "}";*/

            // 순환 참조 방지로 직원 수만 표시
            return String.format("Department{name='%s', employeeCount=%d}", name, employees.size());
        }
    }

    class Employee {
        private String name;
        private Department department;

        public Employee(String name) {
            this.name = name;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        // 부서 이름만 표시하여 순환 참조 방지
        @Override
        public String toString() {
            return String.format("Employee{name='%s', department=%s}", name, department != null ? department.name : "없음");
        }
    }
}

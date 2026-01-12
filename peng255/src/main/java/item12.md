## 12. toString을 항상 재정의하라
- Object의 기본 toString은 클래스명@해시코드(16진수) 형태로, 사람이 보기엔 거의 쓸모가 없다.
- toString의 일반 규약은 “**간결하면서도 사람이 읽기 쉬운, 유용한 정보**”를 반환해야 한다.
- toString을 재정의하면 디버깅, 로그, 출력, 에러 메시지 등에서 객체 정보를 쉽게 확인할 수 있다.
- 컬렉션이나 Map에 객체가 들어갈 때도 toString이 잘 구현되어 있으면 전체 구조를 한눈에 파악하기 쉽다.
- toString 구현 시, 객체의 중요한 정보를 모두 포함하는 것이 좋다.

  단, 객체가 너무 크거나 복잡하면 요약 정보를 반환한다.


### **예시: PhoneNumber 클래스**

```java
@Override
public String toString() {
    return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
}
```

- 010-123-4567처럼 사람이 직관적으로 이해할 수 있는 형태로 반환한다.

### **포맷 명시의 장단점**

- **포맷을 명시하면:**
    - 표준화된 문자열 표현을 만들 수 있다.
    - CSV, 로그, 파일 저장 등에 활용하기 좋다.
    - 반대로, 포맷을 바꾸면 기존 사용자 코드가 깨질 수 있으니 신중해야 한다.
- **포맷을 명시하지 않으면:**
    - 향후 포맷 변경이 자유롭다.
    - 대신, 외부에서 toString 결과를 파싱해서 쓰면 안 된다.

### **포맷 명시 예시 (JavaDoc)**

```java
// "XXX-YYY-ZZZZ" 형태의 전화번호 문자열을 반환한다.
// 각 부분이 자릿수보다 작으면 앞에 0을 붙인다.
@Override
public String toString() {
    return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
}
```

### **포맷 미명시 예시 (JavaDoc)**

```java
// 이 포션의 간단한 설명을 반환한다.
// 구체적 포맷은 바뀔 수 있다.
// 예: "[Potion #9: type=love, smell=turpentine, look=india ink]"
@Override
public String toString() { ... }
```

### **추가 팁**

- toString이 반환하는 정보는 반드시 getter 등으로도 접근할 수 있게 만든다.

  그렇지 않으면, 외부에서 toString 결과를 파싱해서 쓰는 불안정한 코드가 생길 수 있다.

- static 유틸리티 클래스나 대부분의 enum 타입에는 toString을 따로 구현하지 않는다.
- IDE 자동 생성 toString도 쓸 수 있지만, 의미 있는 값 객체라면 직접 포맷을 정의하는 것이 더 좋다.
- toString을 재정의하지 않으면, 로그나 에러 메시지에서 객체 정보를 알 수 없어 디버깅이 불편해진다.
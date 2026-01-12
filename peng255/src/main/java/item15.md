## 15. 클래스와 멤버의 접근 권한을 최소화하라

잘 설계된 컴포넌트는 자신의 내부 구현을 철저히 감춘다. 이를 **정보 은닉(Encapsulation)**이라고 하며, 소프트웨어 설계의 핵심 원칙이다!

접근 권한을 최소화하면 컴포넌트 간 결합도가 낮아져 개발, 테스트, 유지보수, 최적화, 재사용에서 큰 장점이 생긴다.

### 접근 제어자 선택 기준

상황에 맞게 접근 제어자를 정해야 한다.

최대한 닫고, 꼭 필요할 때만 점진적으로 여는 것을 기본으로 한다.

**1. 클래스/인터페이스 (Top-level)**

| `public` | 외부 API로 공개해야 할 클래스 | 최소한으로만 |
| --- | --- | --- |
| `default`(package-private) | 같은 패키지 안에서만 사용 | ✅ 기본 선택 |

**2. 멤버(필드, 메서드, 중첩 클래스)**

| **접근 제어자** | **사용 범위** | **실무 권장도** | **비고** |
| --- | --- | --- | --- |
| `private` | 해당 클래스 내부 | 최우선 선택 | 기본값으로 시작 |
| `default`(package-private) | 같은 패키지 내부 | 필요한 경우만 | 테스트 용도 or 패키지 내부 협력 |
| `protected` | 같은 패키지 + 하위 클래스 | 신중히 사용 | 사실상 public API, 영구 유지 부담 |
| `public` | 어디서든 | 최소한만 사용 | 진짜 공개 API만 |

**3. 필드(field)**

- **인스턴스 필드(public) → 절대 금지**
    - 불변식 깨짐, 스레드 안정성 붕괴할 수 있기 때문!
- **상수(public static final)** → 허용
    - 단, 반드시 **기본 타입** 또는 **불변 객체**만 사용
    - 배열 직접 공개 금지 → **`Collections.unmodifiableList()`** 또는 **`clone()`**으로 대체하자

1. **테스트 목적 접근 제어**
- private → package-private 확대는 OK
- public까지 확대는 절대 금지
- 같은 패키지에 테스트 클래스를 두면 package-private 멤버 접근 가능

> 정리
> 1. 클래스는 기본적으로 package-private
> 2. 멤버는 기본적으로 private
> 3. public은 진짜 공개 API만, protected는 특별한 경우만
> 4. 필드 공개 금지 (상수 제외, 배열은 특히 주의)
> 5. 테스트용 접근 확장은 package-private까지만


### 주의해야할 원칙들

- **public static final 배열 금지**
    - 배열은 항상 가변적이기 때문에 보안 구멍이 생길 수 있다

    ```java
    public static final Thing[] VALUES = { ... }; // ❌ 위험
    ```

    - 해결책:
        1. 불변 리스트로 감싸기 (Collections.unmodifiableList)

        ```java
        private static final Thing[] PRIVATE_VALUES = { ... };
        public static final List<Thing> VALUES =
            Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
        ```

        2. 복제본 반환하기
        ```java
        private static final Thing[] PRIVATE_VALUES = { ... };
        public static Thing[] values() {
            return PRIVATE_VALUES.clone();
        }
        ```
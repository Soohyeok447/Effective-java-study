## 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

### public 클래스에 public 필드를 두면 생기는 문제들

**1. 캡슐화(정보은닉) 깨짐**

- (권장) **`private + getter/setter`** 설계 시: 내부 표현 변경 시 외부 API 그대로 유지 가능하다
- (권장x) **`public field`**: 내부 구현 상세가 API 계약으로 굳어져, 표현 방식을 바꿀 수 없게 된다

---

**2. 불변식(invariant) 강제 불가**

- 예: 좌표(Point)에서 **`x,y ≥ 0`** 조건을 강제하고 싶음 → 불가능
- setter 메서드가 없으므로 검증할 통로가 없음
- 필드 직접 접근이 가능해 **잘못된 상태의 객체**가 생길 수 있음

---

**3. 필드 접근 시 부수작용(로깅, 검증, 캐싱 등) 불가**

- 예: **`setBalance()`** 호출 시 로그 남기기
- **필드 직접 접근**하면 이런 **후처리 불가능 →** 나중에 요구사항 생기면 API 전체를 바꿔야 한다

---

**4. API 변경 불가 → 유지보수가 너무 어려워진다**

- `public double x;`를 `private int x;`로 바꾸는 순간 외부 모든 클라이언트가 컴파일 에러

---

**5. 멀티스레드 환경에서 안전성 깨짐**

- 필드가 `public`이라 동기화 처리 불가
- 예: **`x`** 값을 읽거나 쓸 때 동시성 문제가 발생해도 제어 불가

---

**6. 배열 같은 참조 타입은 특히 치명적**

- **`public static final Type[] arr;`** → 모듈 사용자가 **배열 안 값 변경 가능**
- 보안 취약점 및 심각한 **버그를 유발**

```java
// 캡슐화를 잘 적용한 예시
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // getter
    public double getX() { return x; }
    public double getY() { return y; }

    // setter (클래스가 불변이라면 생략 가능)
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```
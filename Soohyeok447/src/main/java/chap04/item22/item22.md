# item22. 인터페이스는 타입을 정의하는 용도로만 사용하라

-   인터페이스는 클래스에게 어떤 동작을 할 수 있다고 알려주는 타입 역할을 함
-   인터페이스는 꼭 타입역할로써만 사용되어야 함

### 인터페이스 잘못 사용한 예 (상수 인터페이스)

```java
// 내부 구현을 클래스의 API로 노출하는 행위
public interface PhysicalConstants {
    // 아보가드로 수 (1/몰)
    static final double AVOGADROS_NUMBER = 6.022_140_857e23;

    // 볼츠만 상수 (J/K)
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;

    // 전자 질량 (kg)
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}

```

-   사용자에게 혼란을 야기하고 심하게는 클라이언트 코드가 내부 구현에 해당하는 이 상수들에 종속되게 함
-   final이 아닌 클래스가 상수 인터페이스를 구현한다면 모든 하위 클래스의 이름공간이 인터페이스가 정의한 상수들로 오염됨

    -   예시)

    ```java
        // 상수 인터페이스
        public interface AppConfig {
            String APP_VERSION = "2.1.0";
            int MAX_LOGIN_ATTEMPTS = 5;
            String DEFAULT_THEME_COLOR = "blue";
        }

        // 상수 인터페이스를 구현한 final이 아닌 부모 클래스
        public class BaseActivity extends Activity implements AppConfig {

            void checkAppVersion() {
                // 인터페이스를 구현했기 때문에 'AppConfig.' 없이 바로 접근 가능 (괜찮아보일지도 모름)
                System.out.println("현재 앱 버전: " + APP_VERSION);
            }
        }

        // BaseActivity를 상속받는 특별한 기능의 자식 클래스
        public class UserProfileActivity extends BaseActivity {

        public void displayUserProfile() {
            // ... 사용자 프로필을 보여주는 로직 ...
        }

        public void someMethod() {
            // UserProfileActivity는 AppConfig를 직접 구현한 적이 없는데도,
            // 부모를 통해 상속받아 바로 접근이 가능하다.

            System.out.println("테마 색상: " + DEFAULT_THEME_COLOR);

            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                // ...
            }
        }
    }
    ```

    > 이름 공간이 오염된다라는 뜻은 그 클래스와는 아무 상관없는 변수(상수)들이 마치 그 클래스에 원래부터 있던 멤버 변수처럼 취급되어 버린다는 뜻

    -   AppConfig의 상수들은 UserProfileActivity의 핵심 기능과는 무관함
    -   UserProfileActivity 개발자가 자신만의 DEFAULT_THEME_COLOR 상수를 만들고 싶을 때 충돌이 발생함 (개발자의 자유를 빼앗김)
    -   UserProfileActivity는 AppConfig를 구현한 적이 없지만, 상속 때문에 결과적으로 AppConfig 타입으로도 취급될 수 있음 (클래스의 정체성이 모호해짐)

### 그렇다면 상수는 어디에 정의해야 할까?

#### 유틸리티 클래스

상수는 유틸리티 클래스에 보관하고 정적 임포트(static import)를 사용하면 이 문제가 해결됨

```java

// 상수용 유틸리티 클래스
public final class AppConfig {
    private AppConfig() {} // 인스턴스화 방지

    public static final String APP_VERSION = "2.1.0";
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final String DEFAULT_THEME_COLOR = "blue";
}

// 사용하는 쪽
import static com.example.AppConfig.*; // 정적 임포트

public class BaseActivity extends Activity {
    void checkAppVersion() {
        System.out.println("현재 앱 버전: " + APP_VERSION); // 깔끔하게 사용
    }
}

public class UserProfileActivity extends BaseActivity {
    public void someMethod() {
        // 이 클래스의 이름 공간은 깨끗함
        // AppConfig의 상수가 필요하면 명시적으로 import해서 사용하면 됨
        System.out.println("테마 색상: " + DEFAULT_THEME_COLOR);
    }
}
```

> 이름 공간이 깨끗하다는건 클래스의 설계도(API)가 깨끗하다는 뜻

### 정리

인터페이스는 타입을 정의하는 용도로만 사용하고 상수 공개용 수단으로 사용하면 안됨

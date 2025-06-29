## 5. 자원을 직접 명시하지 말고 의존객체 주입을 사용해라

- 클래스가 내부에서 **직접 자원을 생성**하거나 **싱글턴, static** 으로 자원을 고정하면 유연성과 테스트성이 떨어진다.
- **의존성 주입(**Dependency Injection, DI)을 사용하면 **필요한 자원을 외부에서 주입**받아 더 유연하고 **테스트하기 쉬운 코드**를 만들 수 있다.
- DI는 **생성자 / 정적 팩토리 / 빌더** 등을 통해 자원을 주입하는 방식으로 구현한다.

### 의존성 주입x 직접 생성하는 경우 (강한 결합)

```java
public class EmailService {
    public void sendEmail(String message, String receiver) {
        System.out.println("Email sent to " + receiver + " with Message=" + message);
    }
}

public class MyApplication {
    private EmailService emailService = new EmailService(); // 직접 생성

    public void processMessages(String msg, String rec) {
        // 메시지 검증, 가공 등
        emailService.sendEmail(msg, rec);
    }
}
```

이렇게 하면 `MyApplication`이 항상 `EmailService`만 사용하게 되고, 테스트나 확장에 불리

### 의존성 주입 이용

```java
public class EmailService {
    public void sendEmail(String message, String receiver) {
        System.out.println("Email sent to " + receiver + " with Message=" + message);
    }
}

public class MyApplication {
    private EmailService emailService;

    // 생성자를 통한 의존성 주입
    public MyApplication(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processMessages(String msg, String rec) {
        emailService.sendEmail(msg, rec);
    }
}

```

이제`MyApplication` 을 만들 때 어떤 `EmailService`든 주입할 수 있다. 테스트용 가짜 서비스(mock)도 쉽게 넣을 수 있다

```java
활용예시

EmailService emailService = new EmailService();
MyApplication app = new MyApplication(emailService);
app.processMessages("안녕!", "test@example.com");
```

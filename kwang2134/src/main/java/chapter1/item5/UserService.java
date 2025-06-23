package chapter1.item5;


public class UserService {
    /**
     * 구현체(자원)을 직접 명시하는 방법
     * memory 저장소만 사용한다고 했을 때 문제는 없으나 저장소가 변경될 일이 있거나 여러 저장소를 사용한다면 코드 수정 필요
     */
    private final UserMemoryRepository userMemoryRepository = new UserMemoryRepository();

    /**
     * 의존 객체를 주입하는 방법
     * 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방법
     * 인터페이스로 명시하여 인터페이스를 구현한 객체를 상황에 따라 넘겨 유연성을 높임
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

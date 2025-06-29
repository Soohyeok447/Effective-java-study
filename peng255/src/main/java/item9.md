## 9. try-finally 보다는 try-with-resources를 활용하라

### try-finally 문제점

- 여러 자원을 닫을 때 중첩된 try-finally 문이 복잡하고 가독성이 떨어진다.
- try 블록과 finally 블록 모두 예외를 던질 수 있는데, finally 블록에서 발생한 예외가 try 블록의 예외를 덮어버려 디버깅이 어렵다.

### try-with-resources 장점

- try-with-resources 문은 AutoCloseable 인터페이스를 구현한 자원을 자동으로 닫아준다!!
- 코드가 훨씬 간결하고 읽기 쉽다.
- 여러 자원을 한 번에 선언하고 닫을 수 있다.
- 예외가 여러 개 발생해도 첫 번째 예외를 보존하고 나머지 예외는 suppressed 예외로 기록한다 ⇒ 디버깅에 도움됨
- catch 문과 함께 사용해 예외 처리를 깔끔하게 할 수 있다.

try-with-resources는 파일, 네트워크, 데이터베이스 등 AutoCloseable을 구현한 모든 자원에 간단하게 적용할 수 있다.

### try-finally 사용 예시

```java
FileReader fr = null;
try {
    fr = new FileReader("test.txt");
    int ch = fr.read();
    System.out.println((char) ch);
} finally {
    if (fr != null) fr.close();
}
```

- 파일을 열고 읽은 뒤, finally에서 직접 close()를 호출한다.

---

### try-with-resources 사용 예시

```java
try (FileReader fr = new FileReader("test.txt")) {
    int ch = fr.read();
    System.out.println((char) ch);
}
```

- try 괄호 안에서 FileReader를 선언하면, 블록이 끝날 때 자동으로 close()가 호출된다.

---

### **여러 자원 사용 예시**

```java
try (Scanner scanner = new Scanner(new File("input.txt"));
     PrintWriter pw = new PrintWriter(new File("output.txt"))) {
    String line = scanner.nextLine();
    pw.println(line);
}
```

- Scanner와 PrintWriter 두 자원을 동시에 선언하고, 블록이 끝나면 자동으로 모두 close()된다
# item9. try-finally보다 try-with-resources를 써라

- sql Connection이나 InputStream같은 자원은 반드시 쓰고나서 해제해야함
- `try-finally`로 처리했지만 `try-with-resources`가 더 안전함

### 전통 방식 (try-finally)
```java
static String firstLineOfFile(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        br.close(); // 예외가 나도 무조건 닫아야 하니까 finally에 둠
    }
}
```
- finally에서 예외가 발생할 수 있다는 문제가 있음 (실수 유발 가능)
- 앞의 예외가 덮어질 수 있음

### 개선 방식 (try-with-resources)
```java
static String firstLineOfFile(String path) throws IOException {
    try(BufferedReader br = new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```
- br이 AutoCloseable을 구현하면 자동으로 close()를 호출함
- 코드가 간결하고 명확함
- 예외가 중첩돼도 모두 추적 가능함

#### AutoCloseable을 구현한 자원을 사용할 땐 무조건 try-with-resources를 쓸 것
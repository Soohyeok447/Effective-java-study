자원을 다룰 때는 반드시 사용한 자원을 해제해야 한다. 자바에서는 오래전부터 **`try-finally`** 구문을 사용해 자원을 해제해왔지만, 자바 7부터 **`try-with-resources`** 구문이 도입!
`try-with-resources`는 자원 해제를 자동화하여 코드의 가독성과 안전성을 높여준다.

### **`try-finally` 구문을 사용한 자원 해제**

```java
public class FileProcessor {
    public void processFile(String filePath) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
```

→ `BufferedReader` 자원을 사용하고, `finally` 블록에서 자원을 해제하고 있다. 하지만 `finally` 블록이 길어지고, 자원 해제 코드가 중복될 가능성이 있다.

<aside>

**해결: `try-with-resources`**

```java
public class FileProcessor {
    public void processFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
```

</aside>

`try-with-resources` 구문을 사용하면, 자원 해제를 자동으로 처리. `BufferedReader`는 `AutoCloseable` 인터페이스를 구현하고 있기 때문에, `try` 블록이 끝나면 자원이 자동으로 반환

### **여러 자원을 사용하는 경우**

```java
public class FileCopier {
    public void copy(String src, String dest) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
```

`try-with-resources`는 여러 자원을 관리할 때도 매우 유용하다. 코드에서는 `InputStream`과 `OutputStream` 자원을 한꺼번에 관리하며, 각 자원이 자동으로 해제된다.

### 정리

> `try-with-resources`는 자원 해제 코드를 자동화하여, 예외가 발생하더라도 안전하게 자원을 반환
자원이 `AutoCloseable` 인터페이스를 구현하고 있으면 `try-with-resources`를 사용하자
>
package chapter1.item9;

public class Try {

    /**
     * try-finally 사용 시 try 블록과 finally 블록에서 같이 예외가 발생한다면
     * try 블록에서 발생한 예외는 무시되고
     * 마지막 자원 해제 과정에서 발생한 finally 블록의 예외가 try 블록의 예외를 덮어씀
     * 중요한 예외가 덮어써져 사라진다면 디버깅이 어려움
     */
    static class ProblemResource {
        public void doWork() throws Exception {
            throw new Exception("try 블록에서 발생한 예외");
        }

        public void close() throws Exception {
            throw new Exception("finally 블록에서 발생한 예외");
        }
    }

    public static void tryFinally() throws Exception {
        ProblemResource resource = new ProblemResource();
        try {
            resource.doWork();
        } finally {
            resource.close();
        }
    }

    /**
     * AutoCloseable 구현으로 try-with-resources 사용
     * 동일하게 try 블록과 자원 해제 과정에서 예외 발생 시
     * 자원 해제 과정에서 발생하는 예외는 억제되고 try 블록에서 발생한 예외가 메서드에 던져짐
     * try 블록에서 발생한 예외가 손실되지 않고 보존
     */
    static class Resource implements AutoCloseable {
        public void doWork() throws Exception {
            throw new Exception("try 블록에서 발생한 예외");
        }

        @Override
        public void close() throws Exception {
            throw new Exception("close에서 발생한 예외");
        }
    }

    public static void tryWithResource() throws Exception {
        try (Resource resource = new Resource()) {
            resource.doWork();
        }
    }
}

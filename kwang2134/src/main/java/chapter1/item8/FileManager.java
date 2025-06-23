package chapter1.item8;

import java.lang.ref.Cleaner;

public class FileManager implements AutoCloseable{
    /**
     * AutoCloseable + Cleaner를 안전망으로 사용
     */
    private static final Cleaner cleaner = Cleaner.create();

    /**
     * 정리 작업을 담당하는 State 클래스
     */
    private static class FileState implements Runnable {
        private String fileName;
        private boolean isOpen;

        FileState(String fileName) {
            this.fileName = fileName;
            this.isOpen = true;
            System.out.println("파일 열기: " + fileName);
        }

        /**
         * cleaner 는 나중에 실행할 정리 작업을 저장해야 함
         * Runnable 의 run()이 정리 작업을 의미
         */
        @Override
        public void run() {
            if (isOpen) {
                System.out.println("cleaner로 파일 닫기: " + fileName);
                isOpen = false;
            }
        }


        void closeNormally() {
            if (isOpen) {
                System.out.println("파일 닫기: " + fileName);
                isOpen = false;
            }
        }
    }

    private final FileState state;
    private final Cleaner.Cleanable cleanable;

    /**
     * cleaner 에 등록 - this 객체가 GC될 때 state.run() 실행
     */
    public FileManager(String fileName) {
        this.state = new FileState(fileName);
        this.cleanable = cleaner.register(this, state);
    }

    /**
     * 명시적 자원 해제
     * try-with-resources 블럭 내에서 AutoCloseable 을 구현한 객체가 사용되면 자동으로 close() 호출
     */
    @Override
    public void close() throws Exception {
        state.closeNormally();
        cleanable.clean();
    }
}

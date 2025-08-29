package chapter3.item20;

public class InterfaceEx {

    /**
     * 1. 추상 클래스 방식
     * - 여러 클래스에 공통 동작을 정의하려면 상속 계층에 강제 편입
     */
    abstract static class AbstractSinger {
        abstract void sing();

        public void rest(){
            System.out.println("휴식");
        }
    }

    static class PopSinger extends AbstractSinger {

        @Override
        void sing() {
            System.out.println("팝송 부르기");
        }
    }

    // 이미 다른 클래스를 상속 받고 있다면 AbstractSinger를 상속할 수 없음
    static class Dancer {

        public void dance() {
            System.out.println("춤추기");
        }
    }

    // 동시 상속 불가능
    //static class SingingDancer extends AbstractSinger, Dancer {}

    /**
     * 2. 인터페이스 방식
     * - 어떤 클래스든 자유롭게 구현 추가 가능
     * - 다중 구현이 가능 -> 계층 구조에 덜 구애받음
     */
    interface Singer {

        void sing();

        default void rest() {
            System.out.println("휴식");
        }
    }

    interface SongWriter {
        void compose();
    }

    // Singer, SongWriter 동시 구현 가능
    static class SingerSongWriter implements Singer, SongWriter {
        @Override
        public void sing() {
            System.out.println("자작곡 부르기");
        }

        @Override
        public void compose() {
            System.out.println("작곡하기");
        }
    }

    /**
     * 3. Skeletal Implementation (골격 구현 클래스)
     * - 인터페이스 + 추상 클래스를 혼합하여 유연성과 코드 재사용 모두 얻기
     * - 인터페이스는 타입 정의, 골격 구현은 기본 동작 제공
     */
    interface Instrument {

        void play();

        default void tune() {
            System.out.println("악기 튜닝");
        }
    }

    // 골격 구현 (Skeletal Implementation)
    abstract static class AbstractInstrument implements Instrument {
        @Override
        public void play() {
            System.out.println("기본적인 소리 연주");
        }
    }

    // 하위 클래스는 재사용하면서 필요한 부분만 확장
    static class Guitar extends AbstractInstrument {
        @Override
        public void play() {
            System.out.println("기타 연주");
        }
    }
}


package io.jin.silkair.item01;

public class PhoneNumber {
    private final String areaCode;
    private final String number;

    // 생성자 방식
    //만약 private 하면 외부에서 new 불가
    public PhoneNumber(String areaCode, String number) {
        this.areaCode = areaCode;
        this.number = number;
    }

    // 정적 팩터리 메서드 방식
    public static PhoneNumber of(String areaCode, String number) {
        return new PhoneNumber(areaCode, number);
    }

    @Override
    public String toString() {
        return areaCode + "-" + number;
    }
}

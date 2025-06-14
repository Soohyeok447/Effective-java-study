package io.jin.silkair;

import io.jin.silkair.item01.PhoneNumber;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilkairApplication {

    public static void main(String[] args) {
        // 생성자 방식
        PhoneNumber phone1 = new PhoneNumber("02", "1234-5678");

        // 정적 팩터리 메서드 방식
        PhoneNumber phone2 = PhoneNumber.of("02", "1234-5678");

        System.out.println(phone1); // 02-1234-5678
        System.out.println(phone2); // 02-1234-5678
    }
}

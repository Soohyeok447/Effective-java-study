package io.jin.silkair;

import io.jin.silkair.part02.item02.NutritionFactsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilkairApplication {

//    public static void main(String[] args) {
//        // 생성자 방식
//        PhoneNumber phone1 = new PhoneNumber("02", "1234-5678");
//
//        // 정적 팩터리 메서드 방식
//        PhoneNumber phone2 = PhoneNumber.of("02", "1234-5678");
//
//        System.out.println(phone1); // 02-1234-5678
//        System.out.println(phone2); // 02-1234-5678
//    }

    public static void main(String[] args) {
        NutritionFactsBuilder cocaCola = new NutritionFactsBuilder.Builder(250, 1)
                .calories(140)
                .sodium(35)
                .carbohydrate(27)
                .build();

        System.out.println(cocaCola);
    }
}

package item2;

public class NutritionFactsTelescoping {

    // 매개 변수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.

    private final int servingSize;      // 필수 입력값
    private final int servings;         // 필수 입력값
    private final int calories;         // 선택 입력값
    private final int fat;              // 선택 입력값
    private final int sodium;           // 선택 입력값
    private final int carbohydrate;     // 선택 입력값

    public NutritionFactsTelescoping(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFactsTelescoping(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFactsTelescoping(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories,  fat, 0);
    }

    public NutritionFactsTelescoping(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories,  fat,  sodium, 0);
    }

    public NutritionFactsTelescoping(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
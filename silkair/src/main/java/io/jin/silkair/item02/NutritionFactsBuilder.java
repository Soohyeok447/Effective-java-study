package io.jin.silkair.item02;

public class NutritionFactsBuilder {
    private final int servingSize;    // (ml)         필수
    private final int servings;       // (per pack)   필수
    private final int calories;       // (optional)
    private final int fat;            // (optional)
    private final int sodium;         // (optional)
    private final int carbohydrate;   // (optional)

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화
        private int calories      = 0;
        private int fat           = 0;
        private int sodium        = 0;
        private int carbohydrate  = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val)      { this.calories = val; return this; }
        public Builder fat(int val)           { this.fat = val; return this; }
        public Builder sodium(int val)        { this.sodium = val; return this; }
        public Builder carbohydrate(int val)  { this.carbohydrate = val; return this; }

        public NutritionFactsBuilder build() {
            return new NutritionFactsBuilder(this);
        }
    }

    private NutritionFactsBuilder(Builder builder) {
        this.servingSize   = builder.servingSize;
        this.servings      = builder.servings;
        this.calories      = builder.calories;
        this.fat           = builder.fat;
        this.sodium        = builder.sodium;
        this.carbohydrate  = builder.carbohydrate;
    }

    @Override
    public String toString() {
        return String.format("ServingSize: %d, Servings: %d, Calories: %d, Fat: %d, Sodium: %d, Carbohydrate: %d",
                servingSize, servings, calories, fat, sodium, carbohydrate);
    }
}
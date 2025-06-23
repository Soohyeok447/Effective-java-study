# item8. finalizer와 cleaner 사용을 피하라

- `finalize()`와 `Cleaner`는 **예측 불가능하고, 느리고 위험**하기 때문에 **사용을 피해야 함**
- **언제 실행될지 예측할 수 없고**, **성능/보안 문제**도 발생할 수 있음

### finalizer
- 객체가 GC에 의해 수거되기 전에 자동 호출되는 메서드

### Cleaner (Java 9)
- `finalizer`보다 안전하지만 여전히 예측 불가능함
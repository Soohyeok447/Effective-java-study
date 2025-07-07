package chapter3.item15;

public class EncapsulationEx {

    /**
     * 과도하게 넓은 접근 권한을 부여 - 모두 public
     * 누구나 직접 balance, owner, accountNumber를 변경할 수 있음
     * 내부 구현 변경으로 외부에 영향을 미칠 수 있음
     */
    class Account {
        public String owner;
        public int balance;
        public String accountNumber;

        public Account(String owner, String accountNumber) {
            this.owner = owner;
            this.accountNumber = accountNumber;
            this.balance = 0;
        }

        public void deposit(long amount) {
            balance += amount;
        }

        public void withdraw(long amount) {
            balance -= amount;
        }
    }

    /**
     * 접근 권한을 최소화
     * 외부에서 필요한 기능만 public으로 공개
     * getter와 같은 값 제공도 필요한 경우만 제공
     * private로 접근 권한을 막아 외부에서 데이터 변경을 막음
     */
    class BankAccount {
        private String owner;
        private int balance;
        private String accountNumber;

        // 생성자도 필요한 경우만 패키지 접근 허용
        public BankAccount(String owner, String accountNumber) {
            this.owner = owner;
            this.accountNumber = accountNumber;
            this.balance = 0;
        }

        public void deposit(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
            }
            balance += amount;
        }

        public void withdraw(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
            }
            if (amount > balance) {
                throw new IllegalArgumentException("잔액이 부족합니다.");
            }
            balance -= amount;
        }

        public long getBalance() {
            return balance;
        }
    }
}

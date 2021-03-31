package bank;

public
class BankAccount {
    private double balance;

    public
    BankAccount () {
        this.balance = 0;
    }

    public void withDraw(double amount){
        this.balance -= amount;
    }

    public
    double getBalance () {
        return balance;
    }
    public void deposit(double amount){
        if (amount<=0){
            throw new IllegalStateException ("cannot deposit negative amount");
        }
        this.balance += amount;
    }
}

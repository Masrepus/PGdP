package pgdp;

/**
 * Created by samuel on 16.12.16.
 */
public class BankAccount {

    private int bankaccount;
    private String firstname;
    private String surname;
    private Money balance;

    public BankAccount(int accountnumber, String fname, String sname) {
        bankaccount = accountnumber;
        firstname = fname;
        surname = sname;
        balance = new Money(0);
    }

    public int getAccountnumber() {
        return bankaccount;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public Money getBalance() {
        return balance;
    }

    public void addMoney(Money m) {
        balance = balance.addMoney(m);
    }

    @Override
    public String toString() {
        return bankaccount + ": " + firstname + " " + surname + ", balance: " + balance;
    }
}

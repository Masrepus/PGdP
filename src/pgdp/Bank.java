package pgdp;

/**
 * Created by samuel on 16.12.16.
 */
public class Bank {

    public BankAccountList accounts;

    public Bank() {
        accounts = new BankAccountList();
    }

    public int newAccount(String firstname, String lastname) {
        BankAccount account = new BankAccount(getLastAccountNr() + 1, firstname, lastname);
        accounts.add(account);
        return account.getAccountNr();
    }

    private int getLastAccountNr() {
        //recursively search for the last accountnumber that is used and return the next one
        return accounts.getLastAccountNr(-1);
    }

    public void removeAccount(int accountnumber) {
        accounts.remove(accountnumber, accounts);
    }

    public Money getBalance(int accountnumber) {
        //don't accept wrong account numbers
        if (accountnumber < 0) return null;
        return accounts.getBalance(accountnumber);
    }

    public boolean depositOrWithdraw(int accountnumber, Money m) {
        return accounts.depositOrWithdraw(accountnumber, m);
    }

    public boolean transfer(int from, int to, Money m) {
        BankAccount fromAcc = accounts.getAccount(from);
        BankAccount toAcc = accounts.getAccount(to);

        //check if these accounts exist
        if (fromAcc == null || toAcc == null) return false;

        //execute the transfer
        fromAcc.addMoney(new Money(-m.getCent()));
        toAcc.addMoney(m);
        return true;
    }

    class BankAccountList {
        public BankAccount info;
        public BankAccountList next;

        private int getLastAccountNr(int currAccountNr) {
            if (info == null) return currAccountNr;
            else {
                if (next == null) return info.getAccountNr();
                else return next.getLastAccountNr(info.getAccountNr() + 1);
            }
        }

        public BankAccount getInfo() {
            return info;
        }

        public void add(BankAccount account) {
            if (info == null) info = account;
            else {
                if (next == null) next = new BankAccountList();
                next.add(account);
            }
        }

        public void remove(int accountnumber, BankAccountList previous) {
            if (info != null) {
                if (info.getAccountNr() == accountnumber) {
                    //remove it
                    info = null;
                    previous.next = next;
                } else {
                    if (next != null) next.remove(accountnumber, this);
                }
            }
        }

        public BankAccount getAccount(int accountnumber) {
            if (info.getAccountNr() == accountnumber) return info;
            else {
                if (next == null) return null;
                else return next.getAccount(accountnumber);
            }
        }

        public Money getBalance(int accountnumber) {
            if (info.getAccountNr() == accountnumber) return info.getBalance();
            else {
                if (next != null) return next.getBalance(accountnumber);
                else return null;
            }
        }

        public boolean depositOrWithdraw(int accountnumber, Money m) {
            if (info != null) {
                if (info.getAccountNr() == accountnumber) {
                    //execute the operation
                    info.addMoney(m);
                    return true;
                } else {
                    if (next == null) return false;
                    else return next.depositOrWithdraw(accountnumber, m);
                }
            } else return false;
        }

        @Override
        public String toString() {
            return ((info == null) ? "" : (info.toString()) + "\n") + ((next == null) ? "---" : next.toString());
        }
    }

    @Override
    public String toString() {
        return accounts.toString();
    }

    public static void main(String[] args) {
    }
}

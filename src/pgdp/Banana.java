package pgdp;

/**
 * Created by Samuel on 19.12.2016.
 */
public class Banana extends Fruit {

    @Override
    public boolean isBanana() {
        return true;
    }

    @Override
    public int shelfLife() {
        return 7;
    }
}

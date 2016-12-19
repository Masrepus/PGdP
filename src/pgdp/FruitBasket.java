package pgdp;

import java.util.LinkedList;

/**
 * Created by Samuel on 19.12.2016.
 */
public class FruitBasket {

    private LinkedList<Fruit> fruits = new LinkedList<Fruit>();

    public void addFruit(Fruit f) {
        fruits.add(f);
    }

    public LinkedList<Apple> getApples() {

        LinkedList<Apple> apples = new LinkedList<Apple>();

        //iterate through fruits and get apples
        for (Fruit f : fruits) {
            if (f.isApple()) apples.add((Apple)f);
        }

        return apples;
    }

    public LinkedList<Fruit> getEqualOrLongerShelfLife(int n) {
        LinkedList<Fruit> nFruits = new LinkedList<Fruit>();

        for (Fruit f : fruits) {
            if (f.shelfLife() >= n) nFruits.add(f);
        }

        return nFruits;
    }
}

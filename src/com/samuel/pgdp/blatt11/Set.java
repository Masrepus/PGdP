package com.samuel.pgdp.blatt11;

import java.util.Iterator;

/**
 * Created by Samuel on 16.01.2017.
 */
public class Set<T> implements Iterable<T> {

    private final List<T> list;

    public Set() {
        list = new List<>();
    }

    public Set(List<T> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        Set<String> set = new Set<>();
        System.out.println(set);
        set = set.add("a");
        System.out.println(set);
        set = set.add("b");
        System.out.println(set);
        set = set.add("c");
        System.out.println(set);
        set = set.add("d");
        System.out.println(set);
        System.out.println("Iterator:");

        for (String s : set) {
            System.out.println(s);
        }
    }

    public Set<T> add(T e) {
        if (e == null) throw new NullPointerException();

        //check if we already have this item
        if (contains(e)) return this;

        //this set must not be changed!
        return new Set<>(list.add(e));
    }

    public Set<T> remove(Object e) {
        if (e == null) throw new NullPointerException();

        //first check if we actually have this item, maybe the object is not of type T, catch this
        try {
            int id = list.find((T) e);

            if (id == -1) return this;

            //this object must not be changed!
            return new Set<>(list.remove(id));
        } catch (ClassCastException exception) {
            return this;
        }
    }

    public boolean contains(Object e) {
        try {
            return list.find((T) e) != -1;
        } catch (ClassCastException exception) {
            //wrong type
            return false;
        }
    }

    public int size() {
        return list.length();
    }

    public List<T> getList() {
        return list;
    }

    public T get(int id) {
        return list.get(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Set)) return false;
        else {
            //check sizes
            if (size() != ((Set) obj).size()) return false;

            //cycle through our list and check if the other one has all our entries
            for (int i = 0; i < size(); i++) {
                if (!((Set) obj).contains(get(i))) return false;
            }

            //seems like our set is equal to obj
            return true;
        }
    }

    @Override
    public String toString() {
        List<T> tmp = list.flip(list);
        String out = "{";
        for (int i = 0; i < size(); i++) {
            out += tmp.get(i);
            if (i != size() - 1) out += ",";
        }

        return out + "}";
    }

    /**
     * @return a new {@link SetIterator} that goes through {@link #list} (backwards, because {@link List} stores items starting from the end to the start) and returns the corresponding items
     */
    @Override
    public Iterator<T> iterator() {
        return new SetIterator(list.length() - 1);
    }

    private class SetIterator implements Iterator<T> {

        private int end;

        public SetIterator(int end) {
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return end >= 0;
        }

        @Override
        public T next() {
            end--;
            return list.get(end + 1);
        }
    }
}
package com.samuel.pgdp.blatt11;

/**
 * Created by Samuel on 16.01.2017.
 */
public class Set<T> {

    private final List<T> list;

    public static void main(String[] args) {
        Set<String> set = new Set<>();
        System.out.println(set);
        set = set.add("a");
        System.out.println(set);
        set = set.add("b");
        System.out.println(set);
    }

    public Set() {
        list = new List<>();
    }

    public Set(List<T> list) {
        this.list = list;
    }

    public Set<T> add(T e) {
        if (e == null) throw new NullPointerException();

        //check if we already have this item
        if (contains(e)) return this;

        //this set must not be changed!
        return new Set<T>(list.add(e));
    }

    public Set<T> remove(T e) {
        //first check if we actually have this item
        int id = list.find(e);

        if (id == -1) return this;

        //this object must not be changed!
        return new Set<>(list.remove(id));
    }

    public boolean contains(T e) {
        return list.find(e) != -1;
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
            if (size() != ((Set)obj).size()) return false;

            //cycle through our list and check if the other one has all our entries
            for (int i = 0; i < size(); i++) {
                if (!list.get(i).equals(((Set)obj).get(i))) return false;
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
            if (i != size()-1) out += ",";
        }

        return out + "}";
    }
}

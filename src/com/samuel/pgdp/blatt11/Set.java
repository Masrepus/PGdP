package com.samuel.pgdp.blatt11;

import com.samuel.pgdp.MutableList;

/**
 * Created by Samuel on 16.01.2017.
 */
public class Set<T> {

    private final MutableList<T> list;

    public Set() {
        list = new MutableList<>();
    }

    public Set(MutableList<T> list) {
        this.list = list;
    }

    public Set<T> add(T e) {
        //this set must not be changed!
        MutableList<T> temp = list.duplicate();
        temp.add(e);

        return new Set<>(temp);
    }

    public Set<T> remove(T e) {
        //this object must not be changed!
        MutableList<T> temp = list.duplicate();
        temp.remove(list.find(e));

        return new Set<>(temp);
    }

    public boolean contains(T e) {
        return list.find(e) != -1;
    }

    public int size() {
        return list.length();
    }

    public MutableList<T> getList() {
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Set && list.equals(((Set)obj).getList());
    }
}

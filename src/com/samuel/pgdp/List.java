package com.samuel.pgdp;

/**
 * Generic, immutable version of {@link com.samuel.pgdp.blatt7.HeadList}
 *
 * @param <T> the type of this list's elements
 */
public class List<T> {

    private final Entry head;
    private final List<T> rest;

    /**
     * constructor empty HeadList
     */
    public List() {
        head = null;
        rest = null;
    }

    public List(Entry head, List<T> rest) {
        this.head = head;
        this.rest = rest;
    }

    /**
     * Appends a new element with value info to the end of this list
     *
     * @param data value of the new element
     */
    public List<T> add(T data) {
        //prepend this item to the current list
        return new List<>(new Entry(data), this);
    }

    /**
     * Get the list's length
     *
     * @return the list's length
     */
    public int length() {
        return getLength(0);
    }

    private int getLength(int i) {
        //iterate through all appended lists until we find the end
        if (head == null || rest == null) return i;
        else {
            return rest.getLength(i+1);
        }
    }

    public List<T> remove(int id) {
        //check if negative
        if (id < 0) return null;
        return flip(remove(new List<>(), id, 0));
    }

    /**
     * Removes  the element at position index from this list.
     *
     * @param targetId position of the element that is removed
     */
    public List<T> remove(List<T> leftRest, int targetId, int currId) {
        //now iterate through our list and see if we can find the index
        if (targetId == currId) {
            //this is the element, don't add it
            if (rest != null) return rest.remove(leftRest, targetId, currId+1);
            else return leftRest;

        } else {
            if (rest != null) return rest.remove(leftRest.add((head == null ? null : head.elem)), targetId, currId+1);
            else return leftRest;
        }
    }

    /**
     * Get a list element
     *
     * @param index the position of the element
     * @return the element being searched for
     * null if it could not be found
     */
    public T get(int index) {
        //check if negative
        if (index < 0) return null;
        else return get(index, 0);
    }

    public T get(int targetId, int currId) {
        if (targetId == currId) return head.elem;
        else return rest.get(targetId, currId+1);
    }

    /**
     * Find the position of a specific element in the list
     *
     * @param element the element to be found
     * @return the position of the element in the list, if found.
     * -1 if it hasn't been found
     */
    public int find(T element) {
        if (element == null) return -1;

        //iterate through the list and see if we find this element
        return find(element, 0);
    }

    public int find(T element, int currId) {
        if (head == null) return -1;
        if (element.equals(head.elem)) return currId;
        else {
            if (rest != null) return rest.find(element, currId+1);
            else return -1;
        }
    }

    /**
     * Creates an exact, independent copy of this list
     * @return a copy of this list
     */
    public List<T> duplicate() {
        List<T> tmp = new List<>();

        for (int i = 0; i < length(); i++) {
            tmp = tmp.add(get(i));
        }

        return tmp;
    }

    public List<T> flip(List<T> toFlip) {
        List<T> tmp = new List<>();

        for (int i = 0; i < toFlip.length(); i++) {
            tmp = tmp.add(toFlip.get(i));
        }

        return tmp;
    }

    @Override
    public String toString() {
        if (head != null) {
            String out = head.elem.toString();
            if (rest != null) return out + rest;
            else return out;
        }

        return "";
    }

    /**
     * Custom class that holds list data
     */
    class Entry {

        final T elem;

        public Entry(T elem) {
            this.elem = elem;
        }

        @Override
        public String toString() {
            return elem.toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        //frst check the type
        if (!(obj instanceof List)) return false;

        //types match, check the entries
        for (int i = 0; i < length(); i++) {
            if (!(((List)obj).get(i).equals(get(i)))) return false;
        }

        //elements match
        return true;
    }
}
 

package com.samuel.pgdp.game;

/**
 * Generic version of {@link com.samuel.pgdp.blatt7.HeadList}
 *
 * @param <T>
 */
public class List<T> {

    Entry head;

    /**
     * constructor empty HeadList
     */
    public List() {
        head = null;
    }

    /**
     * Appends a new element with value info to the end of this list
     *
     * @param data value of the new element
     */
    public void add(T data) {
        //iterate through all our entries until we find the end
        Entry curr = head;

        //if head is null do it separately
        if (head == null) {
            head = new Entry(null, null, data);
            //now set the entry as real head
            head.first = head;

        } else {
            while (curr.next != null) {
                curr = curr.next;
            }

            //we found the end, add our new item
            curr.next = new Entry(head, null, data);
        }
    }

    /**
     * Get the list's length
     *
     * @return the list's length
     */
    public int length() {
        int i = 0;
        Entry curr = head;

        while (curr != null) {
            i++;
            curr = curr.next;
        }

        return i;
    }

    /**
     * Removes  the element at position index from this list.
     *
     * @param index position of the element that is removed
     */
    public void remove(int index) {
        //check if negative
        if (index < 0) return;

        //now iterate through our list and see if we can find the index
        Entry curr = head, previous = null;
        int id = 0;
        while (curr != null) {
            if (id == index) {
                //now remove it: set previous' next to the next element
                //if this is head, update head
                if (id == 0) {
                    setHead(curr.next);
                    head = curr.next;
                }
                if (previous != null) previous.next = curr.next;
            }
            id++;
            previous = curr;
            curr = curr.next;
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

        //now iterate through our list and see if we can find the index
        Entry curr = head;
        int id = 0;
        while (curr != null) {
            if (id == index) {
                return curr.elem;
            }
            id++;
            curr = curr.next;
        }

        //we didn't reach index
        return null;
    }

    public int find(T element) {
        if (element == null) return -1;

        //iterate through the list and see if we find this element
        Entry curr = head;
        int id = 0;
        while (curr != null) {
            if (curr.elem.equals(element)) return id;
            id++;
            curr = curr.next;
        }

        //didn't find the element
        return -1;
    }

    public List<T> duplicate() {
        List<T> tmp = new List<>();

        for (int i = 0; i < length(); i++) {
            tmp.add(get(i));
        }

        return tmp;
    }

    /**
     * sets the head of each list element to newHead
     *
     * @param newHead reference to the new head
     */
    private void setHead(Entry newHead) {
        //iterate through all entries and update head
        Entry curr = head;
        while (curr != null) {
            curr.first = newHead;
            curr = curr.next;
        }
    }

    @Override
    public String toString() {
        String out = "[";
        if (head != null) {
            out += head.elem;
            Entry tmp = head.next;
            while (tmp != null) {
                out = out + "," + tmp.elem;
                tmp = tmp.next;
            }
        }
        out += "]";
        return out;
    }

    class Entry {

        Entry first;
        Entry next;
        T elem;

        public Entry(Entry first, Entry next, T elem) {
            this.first = first;
            this.next = next;
            this.elem = elem;
        }

    }
}
 

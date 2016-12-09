package com.samuel.pgdp.blatt7;

public class HeadList {

    Entry head;

    /**
     * constructor empty HeadList
     */
    public HeadList() {
        head = null;
    }

    public static void main(String[] args) {
        HeadList l = new HeadList();
        System.out.println("empty list: " + l);
        // Test implementation
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        l.add(5);
        l.add(6);
        System.out.println(l);
        l.reverse();
        System.out.println(l);
    }

    /**
     * Appends a new element with value info to the end of this list
     *
     * @param info value of the new element
     */
    public void add(int info) {
        //iterate through all our entries until we find the end
        Entry curr = head;

        //if head is null do it separately
        if (head == null) {
            head = new Entry(null, null, info);
            //now set the entry as real head
            head.first = head;

        } else {
            while (curr.next != null) {
                curr = curr.next;
            }

            //we found the end, add our new item
            curr.next = new Entry(head, null, info);
        }
    }

    /**
     * Removes and returns the element at position index from this list.
     *
     * @param index position of the element that is removed
     * @return value of the removed element
     */
    public int remove(int index) {
        //check if negative
        if (index < 0) return Integer.MIN_VALUE;

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
                return curr.elem;
            }
            id++;
            previous = curr;
            curr = curr.next;
        }

        //we didn't reach index
        return Integer.MIN_VALUE;
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

    /**
     * reverse the list
     * example: [1,2,3,4,5] --> [5,4,3,2,1], [] --> [], [1] --> [1]
     */
    public void reverse() {
        //check if empty
        if (head == null) return;

        Entry curr = head;
        Entry previous = null;

        while (curr != null) {
            //backup current next
            Entry currNext = curr.next;
            curr.next = previous;
            previous = curr;
            curr = currNext;
        }

        //now update head
        head = previous;
        setHead(previous);
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
        int elem;

        public Entry(Entry first, Entry next, int elem) {
            this.first = first;
            this.next = next;
            this.elem = elem;
        }

    }

}
 

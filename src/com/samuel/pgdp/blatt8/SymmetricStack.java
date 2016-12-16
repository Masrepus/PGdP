package com.samuel.pgdp.blatt8;

public class SymmetricStack {

    private int[] data;
    private int first;
    private int last;

    public SymmetricStack() {
        //init data
        data = new int[2];
        first = last = -1;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public int getNumberOfElements() {
        //calculate the number of elements
        if (last > first) {
            //no overflow has happened yet
            return last - first + 1;
        } else if (first > last) {
            //overflow happened, number of elements has been divided in two segments that have to be counted separately
            return (data.length - 1 - first + 1) + last + 1;
        } else if (first == last && first != -1) return 1;
        else return 0;
    }

    public void increase() {
        if (isFull()) {
            int[] newData = new int[2 * data.length];
            int i = data.length / 2;
            int curr = first;

            //iterate through data and copy all entries
            while (curr < data.length) {
                newData[i] = data[curr];
                i++;
                curr++;
            }

            //check if we have to handle overflow
            if (first > last) {
                curr = 0;

                //now handle the last segment of data
                while (curr <= last) {
                    newData[i] = data[curr];
                    i++;
                    curr++;
                }
            }

            //apply changes
            first = data.length / 2;
            data = newData;
            last = i - 1;
        }
    }

    public void decrease() {
        //check if decrease is necessary
        if (getNumberOfElements() <= data.length / 4) {
            int[] newData = new int[data.length / 2];
            int i = data.length / 8;
            int curr = first;

            //check if there has been an overflow
            if (first > last) {
                //copy the first segment
                while (curr < data.length) {
                    newData[i] = data[curr];
                    i++;
                    curr++;
                }

                //copy the last segment
                curr = 0;

                while (curr <= last) {
                    newData[i] = data[curr];
                    i++;
                    curr++;
                }
            } else {
                //copy normally
                while (curr <= last) {
                    newData[i] = data[curr];
                    i++;
                    curr++;
                }
            }

            //apply changes
            first = data.length / 8;
            data = newData;
            last = i - 1;
        }
    }

    public boolean isEmpty() {
        return (first == -1 && last == -1);
    }

    public boolean isFull() {
        //check if there is no space left
        if (first == 0 && last == data.length - 1) return true;
        else if (last == first - 1) return true;
        else return false;
    }

    public void prepend(int x) {
        //make sure we have enough space
        if (isFull()) increase();

        //check if the stack is empty
        if (isEmpty()) {
            first = data.length / 2;
            last = first;
            data[first] = x;
            return;
        }

        //check if underflow is necessary
        if (first == 0) {
            first = data.length - 1;
            data[first] = x;
        } else {
            //insert normally
            first--;
            data[first] = x;
        }
    }

    public void append(int x) {
        //make sure we have enough space
        if (isFull()) increase();

        //check if the stack is empty
        if (isEmpty()) {
            first = data.length / 2;
            last = first;
            data[last] = x;
            return;
        }

        //check if we have to do an overflow
        if (last == data.length - 1) {
            last = 0;
            data[0] = x;
        } else {
            //just insert normally
            last++;
            data[last] = x;
        }
    }

    public void removeFirst() {
        //check if the stack is empty
        if (isEmpty()) return;

        //check if stack will be empty after this operation
        boolean empty = getNumberOfElements() == 1;

        //check if there has been an underflow
        if (first > last) {
            data[first] = 0;

            //check if this ends the underflow
            if (first == data.length - 1) first = 0;
            else first++;
        } else {
            //remove normally
            data[first] = 0;
            first++;
        }

        //check if empty and initialize accordingly
        if (empty) {
            first = last = -1;
            return;
        }

        //perform decrease if necessary
        if (getNumberOfElements() <= data.length / 4) decrease();
    }

    public void removeLast() {
        //check if the stack is empty
        if (isEmpty()) return;

        //check if stack will be empty after this operation
        boolean empty = getNumberOfElements() == 1;

        //check if there has been an overflow
        if (first > last) {
            data[last] = 0;

            //check if this ends the underflow
            if (last == 0) last = data.length - 1;
            else last--;
        } else {
            //remove normally
            data[last] = 0;
            last--;
        }

        //check if empty and initialize accordingly
        if (empty) {
            first = last = -1;
            return;
        }

        //perform decrease if necessary
        if (getNumberOfElements() <= data.length / 4) decrease();
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < data.length; i++) {
            if (first <= last && (i < first || i > last))
                out += "* ";
            if (first <= last && i > first && i < last)
                out += " " + data[i];
            if (first > last && i > last && i < first)
                out += "* ";
            if (first > last && (i > first || i < last))
                out += " " + data[i];
            if (i == first)
                out = out + "(" + data[i];
            if (i == last)
                out += (first == last ? "" : " " + data[i]) + ")";
        }
        return out;
    }

    public static void main(String[] args) {
    }
}

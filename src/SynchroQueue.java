public class SynchroQueue <E> {
    protected MyListElem<E> first = null;
    protected MyListElem<E> last = null;
    private int len = 0;
    public int transId=1;

    public synchronized boolean isEmpty () {
        return (first == null);
    }

    public synchronized void insert (E elem) {
        if (isEmpty()) {
            first = last = new MyListElem<E>(elem);
            notifyAll();
        }
        else {
            last.next = new MyListElem<E>(elem);
            last = last.next;
        }
        len++;
    }

    public synchronized E extract () throws InterruptedException {

        while (isEmpty()) {
            wait();
        }

        E result = first.value;

        first = first.next;
        if (first == null) last = null;
        len--;
        return result;
    }

    public synchronized int getLen() {
        return len;
    }

    public synchronized int getId() {
        return transId++;
    }

}
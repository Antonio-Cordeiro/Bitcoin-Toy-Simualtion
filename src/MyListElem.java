public class MyListElem <E> {

    public E value;
    public MyListElem<E> next = null;

    MyListElem (E value) {
        this.value = value;
    }
}

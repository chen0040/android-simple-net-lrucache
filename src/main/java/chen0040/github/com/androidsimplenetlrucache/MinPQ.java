package chen0040.github.com.androidsimplenetlrucache;

import java.util.Comparator;

/**
 * Created by chen0 on 18/7/2017.
 */

public class MinPQ<T> {
    private T[] a;
    private int N = 0;
    private final Comparator<T> comparator;

    public MinPQ(Comparator<T> comparator){
        a = (T[])new Object[10];
        this.comparator = comparator;
    }

    public void add(T item) {
        if(this.N+1 == a.length) {
            resize(a.length * 2);
        }
        this.a[++this.N] = item;
        swim(this.N);
    }

    public T delMin() {
        if(isEmpty()) {
            return null;
        }

        T item = a[1];
        exchange(a, 1, this.N--);
        sink(1);
        a[this.N+1] = null;
        if(this.N == a.length / 4){
            resize(a.length / 2);
        }
        return item;
    }

    private void sink(int k) {
        while(2 * k <= N) {
            int child = 2 * k;
            if(child < N && less(a[child+1], a[child])) {
                child++;
            }
            if(less(a[child], a[k])) {
                exchange(a, child, k);
                k = child;
            } else {
                break;
            }
        }
    }


    public int size() {
        return this.N;
    }

    public boolean isEmpty(){
        return this.N == 0;
    }

    private void swim(int k) {
        while(k > 1) {
            int parent = k / 2;
            if(less(a[k], a[parent])) {
                exchange(a, k, parent);
                k = parent;
            } else {
                break;
            }
        }
    }

    private boolean less(T a1, T a2) {
        return comparator.compare(a1, a2) < 0;
    }

    private void exchange(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private void resize(int newSize) {
        T[] temp = (T[])new Object[newSize];
        int size = Math.min(newSize, a.length);
        for(int i=0; i < size; ++i){
            temp[i] = a[i];
        }
        a = temp;
    }
}

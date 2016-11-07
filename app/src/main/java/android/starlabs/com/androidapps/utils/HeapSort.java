package android.starlabs.com.androidapps.utils;

/**
 * Created by AveNGeR on 05-07-2016.
 */
public class HeapSort {
    private int[] array_clone;
    private static int n;
    private static int left;
    private static int right;
    private static int max;


    public HeapSort(int[] array) {
        array_clone = new int[array.length];
        for (int i = 0; i < array.length; i++)
            array_clone[i] = array[i];
    }



    public String doheapsort() {
        long strt = System.currentTimeMillis();
        buildheap(array_clone);
        for(int i=n; i>=0; i--) {
            exchange(0, i);
            n=n-1;
            maxheap(array_clone, 0);
        }
        long end = System.currentTimeMillis();
        return ""+(end-strt);
    }

    public void buildheap(int[] array_clone) {
        n = array_clone.length-1;
        for(int i=n/2; i>=0; i--){
            maxheap(array_clone,i);
        }
    }

    public void maxheap(int[] array_clone, int i) {
        left = 2*i;
        right = 2*i+1;

        if(left <= n && array_clone[left] > array_clone[i]){
            max=left;
        } else {
            max=i;
        }

        if(right <= n && array_clone[right] > array_clone[max]) {
            max=right;
        }
        if(max!=i) {
            exchange(i, max);
            maxheap(array_clone, max);
        }
    }

    public void exchange(int i, int j) {
        int temp = array_clone[i];
        array_clone[i] = array_clone[j];
        array_clone[j] = temp;
    }
}

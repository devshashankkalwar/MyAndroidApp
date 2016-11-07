package android.starlabs.com.androidapps.utils;

public class Quicksort{
	
	private int[] array_clone;
	
	public Quicksort(int[] array) {
		array_clone=new int[array.length];
		for(int i=0;i<array.length;i++)
			array_clone[i]=array[i];
	}
	
	public String sort(){
		int low=0,high=(array_clone.length-1);
		long strt = System.currentTimeMillis();
		qSort(low,high);
		long end = System.currentTimeMillis();
		return ""+(end-strt);
	}
	
	public void qSort(int low,int high){
	int mid=(low+high)/2;
	int pivot= array_clone[mid];
	int i=low,j=high;
	while(i<=j){
	while(array_clone[i]<pivot) 
		i++;
	while(array_clone[j]>pivot)
		j--;
	if(i<=j) {
	int temp;
	temp=array_clone[i];
	array_clone[i]=array_clone[j];
	array_clone[j]=temp;
	i++; j--;
		}
	}
	if (low<j) qSort(low,j);
	if (high>i) qSort(i,high);
	}
}
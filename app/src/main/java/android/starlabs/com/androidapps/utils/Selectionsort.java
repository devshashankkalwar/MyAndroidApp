package android.starlabs.com.androidapps.utils;

public class Selectionsort{
	private int[] array_clone;
	
	public Selectionsort(int[] array) {
		array_clone=new int[array.length];
		for(int i=0;i<array.length;i++)
			array_clone[i]=array[i];
		
	}
	public String selSort(){
	long strt = System.currentTimeMillis();
	for(int i =0;i<array_clone.length;i++){
	int min=i;
	for(int j=i+1;j<array_clone.length;j++){
	if(array_clone[j]<array_clone[min])
	min=j;}
	swapnumber(min,i);
	}
	long end = System.currentTimeMillis();
		return ""+(end-strt);
	}
	public void swapnumber(int i, int min){
	int temp;
	temp=array_clone[i];
	array_clone[i]=array_clone[min];
	array_clone[min]=temp;
	}
}
package android.starlabs.com.androidapps.utils;

public class Bubblesort{
	private int[] array_clone;
	
	public Bubblesort(int[] array) {
		array_clone=new int[array.length];
		for(int i=0;i<array.length;i++)
			array_clone[i]=array[i];
	}
	
	public String bubSort(){
	long strt = System.currentTimeMillis();
	for(int i=0;i<(array_clone.length-1);i++){
		for (int j=0; j<(array_clone.length-1-i);j++){
		if (array_clone[j]>array_clone[j+1])
		swapnumber(j);}
	}
	long end = System.currentTimeMillis();
		return ""+(end-strt);
	}
	public void swapnumber(int i){
	int temp;
	temp=array_clone[i];
	array_clone[i]=array_clone[i+1];
	array_clone[i+1]=temp;
	}
	
}
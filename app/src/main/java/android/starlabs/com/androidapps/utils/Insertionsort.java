package android.starlabs.com.androidapps.utils;

public class Insertionsort{
	private int[] array_clone;
	
	public Insertionsort(int[] array) {
		array_clone=new int[array.length];
		for(int i=0;i<array.length;i++)
			array_clone[i]=array[i];
	}

	public String insertSort(){
	long strt = System.currentTimeMillis();
	int l=array_clone.length;
	for (int i=1;i<l;i++){
	int min= array_clone[i];
	int j=i-1;
	while(j>-1&&array_clone[j]>min)
		array_clone[j+1]=array_clone[j--];
	array_clone[j+1]=min;
	}
	long end = System.currentTimeMillis();
	return ""+(end-strt);
	}
}
	
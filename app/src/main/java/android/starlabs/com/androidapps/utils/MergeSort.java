package android.starlabs.com.androidapps.utils;

public class MergeSort{
	int[] array_clone;
	int[] temparray;
	
	public MergeSort(int[] array) {
		array_clone=new int[array.length];
		for(int i=0;i<array.length;i++)
			array_clone[i]=array[i];
	}
	public String sort(){
		temparray = new int[array_clone.length];
		long strt = System.currentTimeMillis();
		mergSort(0,array_clone.length-1);
		long end = System.currentTimeMillis();
		return ""+(end-strt);
	}
	public void mergSort(int low, int high){
		int mid= low+((high-low)/2);
		if (low<high){
		mergSort(low,mid); //for recursively sorting left to mid index
		mergSort(mid+1,high); //for recursively sorting mid index to right 
		merge(low,mid,high);
		}
	}
	public void merge(int low, int mid, int high){
		for(int i =low; i<=high;i++)
			temparray[i]=array_clone[i];
		int i=low, j=mid+1, k=low;
		while(i<=mid&&j<=high){
			if (temparray[i]<=temparray[j]){
				array_clone[k]=temparray[i];
				i++;
			}
			else{
				array_clone[k]=temparray[j];
				j++;
			}
			k++;
		}
		while(i<=mid){
			array_clone[k]=temparray[i];
			k++; 
			i++;
		}
		while(j<=high){
			array_clone[k]=temparray[j];
			j++;
			k++;
		}
		
		
	}
}
			
	
	
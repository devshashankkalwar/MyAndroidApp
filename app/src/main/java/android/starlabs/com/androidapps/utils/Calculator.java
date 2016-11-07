package android.starlabs.com.androidapps.utils;

import java.util.Random;

public class Calculator {
			
		public static int [] createBestCase(int size){
			int array[] = new int[size];
			for(int count=0;count<size;count++)
			{
			 array[count]=count+1;
			}
			return array;
		}
		public static int [] createWorstCase(int size){
			int array[] = new int[size];
			int index=0;
			for(int count=array.length;count>0;count--)
			{
			 array[index++]=count;

			}
			return array;
		}
		public static int[] generateRandom(int size){
			int array[] = new int[size];
			Random t= new Random();
			for(int i=0;i<array.length;i++){
			array [i] = t.nextInt(10000);
			}
			return array;
			}

}

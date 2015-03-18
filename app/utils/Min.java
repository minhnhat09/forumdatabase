package utils;

import java.util.ArrayList;
import java.util.Collections;

public class Min {
	public static void main(String[] args) {
	    ArrayList<Integer> arrayList = new ArrayList<Integer>();

	    arrayList.add(new Integer("9"));
	    arrayList.add(new Integer("2"));
	    arrayList.add(new Integer("2"));
	    arrayList.add(new Integer("3"));
	    arrayList.add(new Integer("4"));
	    arrayList.add(new Integer("5"));

	    //Object obj = Collections.min(arrayList);
	    Min min = new Min();
	    int minValue = min.findMinValue(arrayList, 12);
	    System.out.println(minValue);
	  }
	
	
	private int findMinValue(final ArrayList<Integer> list, int addValue){
		int minValue = Collections.min(list);
		System.out.println(list);
		boolean test = list.remove((Object)minValue);
		list.add(minValue + addValue);
		System.out.println(list);
		return minValue;
	}
}

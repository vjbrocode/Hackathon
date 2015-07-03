package com.test;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class VectorAdd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<String> v = new Vector<String>();
		
		v.add("ID");
		v.add("STARTTIME");
		v.add("METERSTART");
		v.add("ENDTIME");
		v.add("ENDTIME");
		v.add("METEREND");
		
		Vector<Vector<String>> outer = new Vector<Vector<String>>();
		
		outer.add(v);
		v = new Vector<String>();
		v.add("1234567");
		v.add("6/30/2015 5:00 AM");
		v.add("50012");
		v.add("6/30/2015 5:15 AM");
		v.add("50023");
		outer.add(v);
		System.out.println(outer);
		
		Collections.sort(outer,new Comparator<Vector<String>>() {
			public int compare(Vector<String> v1, Vector<String> v2){
				return v1.get(0).compareTo(v2.get(0));
			}
		});
		
		System.out.println(outer);
	}

}
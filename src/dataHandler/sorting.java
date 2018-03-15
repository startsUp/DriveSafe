/**
 * @author Vaibhav Chadha and Usman Irfan
 * @version Eclipse Oxygen
 */
package dataHandler;

import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;

public class sorting {
	private ArrayList<Violation> data;

	public sorting(ArrayList<Violation> data) {
		// this arraylist is what you need to sort
		// change all the comparables
		this.data = data;
		// System.out.println(data.get(0).getLatlong());
	}

	public static void sort(ArrayList<Violation> a) {
		sort(a, 0, a.size() - 1);
	}

	/**
	 * Comparing 2 objects using Comparable
	 * 
	 * @param v
	 *            -first one to compare
	 * @param w
	 *            -second one to compare
	 * @return 0 if they are equal, 1 if v > w, -1 if v < w. if the return value is
	 *         less than 0, it will return false
	 */
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	/*
	 * private static boolean less(ArrayList<Violation> v, ArrayList<Violation> w) {
	 * if(v < w) return true; else return false; }
	 */

	/**
	 * Exchanging 2 integers using ArrayList
	 * 
	 * @param a
	 *            -the input arrayList containing Strings that need to be exchanged
	 * @param i
	 *            -index of first integer to be changed
	 * @param j
	 *            -index of second integer to be changed
	 * 
	 *            will end up interchanging the objects at index i and j
	 */
	private static void exchange(ArrayList<Violation> a, int i, int j) {
		Violation t = a.get(i);
		a.set(i, a.get(j));
		a.set(j, t);
	}

	/**
	 * Quicksort using ArrayList
	 * 
	 * @param a
	 *            - the input arraylist containing Strings that need to be sorted.
	 * @param lo
	 *            -left most side of array
	 * @param hi
	 *            -right most side of array
	 * 
	 */
	private static void sort(ArrayList<Violation> a, int lo, int hi) {
		if (hi <= lo)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	/**
	 * Divides the array using the randomly chosen partition element.
	 * 
	 * @param a
	 *            - the input arraylist containing Strings that need to be sorted.
	 * @param lo
	 *            -left most side of arrayList
	 * @param hi
	 *            - right most side of arrayList
	 * @return
	 */
	private static int partition(ArrayList<Violation> a, int lo, int hi) {
		int i = lo, j = hi + 1;
		String v = a.get(lo).getLatlong()[0];
		while (true) {
			while (less(a.get(++i), v))
				if (i == hi)
					break;
			while (less(a.get(--j), v))
				if (j == lo)
					break;
			if (i >= j)
				break;
			exchange(a, i, j);
		}
		exchange(a, lo, j);
		return j;
	}

	public static void main(String[] args) {
		// System.out.println(data.get(0).getLatlong());
	}
}

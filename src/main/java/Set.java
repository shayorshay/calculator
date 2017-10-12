import java.math.BigInteger;
import java.util.HashMap;

public class Set<E extends Comparable<E>> implements SetInterface<E> {

	ListInterface<E> setContent;
//	PrintStream out;
	HashMap<IdentifierInterface, SetInterface<BigInteger>> hmap;

	Set() {
		setContent = new List<E>();
//		out = new PrintStream(System.out);
	}

	public void add(E e) {
		if (!setContent.find(e)){
			setContent.insert(e);
		}
	}

	public void remove(E e) {
		setContent.find(e);
		setContent.remove();
	}


	public int size() {
		return setContent.size();
	}


	public boolean contains(E e) {
		return setContent.find(e);
	}


	public E get() throws APException{
		E data = setContent.retrieve();
		//		setContent.remove();
		return data;
	}


	public SetInterface<E> copy() throws APException {
		SetInterface<E> copyOfSet = new Set<E>();
		setContent.goToFirst();

		copyOfSet.add(setContent.retrieve());
		while (setContent.goToNext()){
			copyOfSet.add(setContent.retrieve());
		}

		return copyOfSet;


	}


	public SetInterface<E> union(SetInterface<E> set) throws APException {
		SetInterface<E> unionSet = new Set<E>();
		SetInterface<E> set1 = this.copy();
		SetInterface<E> set2 = set.copy();

		while (set1.size() != 0){
			E newItem = set1.get();
			unionSet.add(newItem);
			set1.remove(newItem);
		}
		while (set2.size() != 0){
			E newItem = set2.get();
			unionSet.add(newItem);
			set2.remove(newItem);
		}

		return unionSet;
	}


	public SetInterface<E> intersection(SetInterface<E> set) throws APException {
		SetInterface<E> intersectionSet = new Set<E>();

		SetInterface<E> set1 = this.copy();
		//		SetInterface<E> set2 = set.copy();

//		out.printf("Number of items in set 1: %d \n", set1.size());
//		out.printf("Content of Set 1\n");
//		printfSet(set1);
		//		out.printf("Number of items in set 2: %d \n", set2.size());
		//		printfSet(set2);


		while (set1.size() != 0){
			E newItem = set1.get();
			String newi = newItem.toString();
//			out.println("Comparing number from set 1 " + newi);
			SetInterface<E> set2 = set.copy();
			while(set2.size() != 0){
				E otherItem = set2.get();
				String otheri = otherItem.toString();
//				out.println("comparing to number of set 2 " + otheri);

				if (otherItem.compareTo(newItem) == 0){
					//					out.println("ik voeg nummers toe");
					intersectionSet.add(newItem);
					String i = newItem.toString();
//					out.println("number added to the intersectionset: " + i + "\n");
				}
				set2.remove(otherItem);
			}

			set1.remove(newItem);
		}
//		out.printf("Content of intersection set:  \n");
//		printfSet(intersectionSet);
		return intersectionSet;
	}

/*	void printfSet(SetInterface<E> s) throws APException{
		SetInterface<E> printingSet = s.copy();

		while (printingSet.size()>0){
			BigInteger i = (BigInteger) printingSet.get();
			printingSet.remove((E) i);
			i.toString();
			out.printf("%s ", i);
		}
		out.printf("\n");

	}*/

	public SetInterface<E> complement(SetInterface<E> set) throws APException {
		SetInterface<E> complementSet = new Set<E>();
		SetInterface<E> set1 = this.copy();

//		out.printf("Number of items in set 1: %d \n", set1.size());
//		out.printf("Content of Set 1\n");
//		printfSet(set1);
//		out.printf("Number of items in set 2: %d \n", set.size());
//		out.printf("Content of Set 2\n");
//		printfSet(set);

		while(set1.size() != 0) {
			E newItem = set1.get();
			SetInterface<E> set2 = set.copy();
			boolean contains = false;

			while(set2.size() != 0) {
//				out.println("hierkomik");
				E otherItem = set2.get();
				if (otherItem.compareTo(newItem) == 0) {
					//					complementSet.add(newItem);
					//					complementSet.add(otherItem);
					contains = true;
				}
				set2.remove(otherItem);
			}
			if (contains == false) {
				complementSet.add(newItem);
			}
			set1.remove(newItem);
		}

		return complementSet;
	}


	public SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException {

		SetInterface<E> unionSet = this.union(set);
//		out.println("unionset");
//		printfSet(unionSet);
		SetInterface<E> intersectionSet = this.intersection(set);
//		out.println("intersectionSet");
//		printfSet(intersectionSet);
		SetInterface<E> symDifferenceSet = unionSet.complement(intersectionSet);
//		out.println("symDifferenceSet");
//		printfSet(symDifferenceSet);		

		
//		SetInterface<E> set1 = this.copy();
//		SetInterface<E> set2 = set.copy();		
		
//		while(unionSet.size() != 0) {
//			E newItem = unionSet.get();
//			SetInterface<E> intersectionSet = set1.intersection(set2);

/*			while (intersectionSet.size() != 0) {
				E otherItem = intersectionSet.get();
				if(otherItem.compareTo(newItem) != 0) {
					symDifferenceSet.add(newItem);
					symDifferenceSet.add(otherItem);
				}
				intersectionSet.remove(otherItem);
			}
			unionSet.remove(newItem);
		}*/

		return symDifferenceSet;
	}

}

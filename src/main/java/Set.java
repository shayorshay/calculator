import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;

public class Set<E extends Comparable<E>> implements SetInterface<E> {

	ListInterface<E> setContent;
	PrintStream out;
	HashMap<IdentifierInterface, SetInterface<BigInteger>> hmap;

	Set() {
		setContent = new List<E>();
		out = new PrintStream(System.out);
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
		return data;
	}


	public SetInterface<E> copy() throws APException {
		SetInterface<E> copyOfSet = new Set<E>();
		setContent.goToFirst();

		if (setContent.goToFirst()==false) {
			return copyOfSet;
		}
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

		while (set1.size() != 0) {
			E newItem = set1.get();
			unionSet.add(newItem);
			set1.remove(newItem);
		}
		while (set2.size() != 0) {
			E newItem = set2.get();
			unionSet.add(newItem);
			set2.remove(newItem);
		}
		return unionSet;
	}


	public SetInterface<E> intersection(SetInterface<E> rhs) throws APException {
		SetInterface<E> intersectionSet = new Set<E>();
		SetInterface<E> set1 = this.copy();

		while (set1.size() != 0){
			E var1 = set1.get();
			SetInterface<E> set2 = rhs.copy();
			while(set2.size() != 0){
				E var2 = set2.get();
				if (var2.compareTo(var1) == 0){
					intersectionSet.add(var1);
				}
				set2.remove(var2);
			}
			set1.remove(var1);
		}
		return intersectionSet;
	}


	public SetInterface<E> complement(SetInterface<E> rhs) throws APException {
		SetInterface<E> complementSet = new Set<E>();
		SetInterface<E> set1 = this.copy();

		while(set1.size() != 0) {
			E var1 = set1.get();
			SetInterface<E> set2 = rhs.copy();
			boolean contains = false;

			while(set2.size() != 0) {
				E var2 = set2.get();
				if (var2.compareTo(var1) == 0) {
					contains = true;
				}
				set2.remove(var2);
			}
			if (contains == false) {
				complementSet.add(var1);
			}
			set1.remove(var1);
		}
		return complementSet;
	}


	public SetInterface<E> symmetricDifference(SetInterface<E> rhs) throws APException {
		SetInterface<E> unionSet = this.union(rhs);
		SetInterface<E> intersectionSet = this.intersection(rhs);
		SetInterface<E> symDifferenceSet = unionSet.complement(intersectionSet);
		
		return symDifferenceSet;
	}

}


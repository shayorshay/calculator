import java.math.BigInteger;
import java.util.HashMap;

public class Set<E extends Comparable<E>> implements SetInterface<E> {
	
	ListInterface<E> setContent;
	HashMap<IdentifierInterface, SetInterface<BigInteger>> hmap;

	Set() {
		setContent = new List<E>();
	}

	public void add(E e) {
		if (!setContent.find(e)){
			setContent.insert(e);
		}
	}

	public void remove(E e) {	//check if set is empty first and throw an exception if it is
		setContent.find(e);
		setContent.remove();
	}


	public int size() {
		return setContent.size();
	}


	public boolean contains(E e) {
		return setContent.find(e);
	}


	public E get() throws APException{		//check if set is empty first
		E data = setContent.retrieve();
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


	public SetInterface<E> union(SetInterface<E> rhs) throws APException {	//check if the operand sets and resulting sets are empty.
		SetInterface<E> unionSet = new Set<E>();
		SetInterface<E> set1 = this.copy();
		SetInterface<E> set2 = rhs.copy();
		
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


	public SetInterface<E> intersection(SetInterface<E> rhs) throws APException { //check if the operand sets and resulting sets are empty.
		SetInterface<E> intersectionSet = new Set<E>();
		SetInterface<E> set1 = this.copy();

		while (set1.size() != 0){
			E num1 = set1.get();
			SetInterface<E> set2 = rhs.copy();
			while(set2.size() != 0){
				E num2 = set2.get();
				if (num2.compareTo(num1) == 0){
					intersectionSet.add(num1);
				}
				set2.remove(num2);
			}
			set1.remove(num1);
		}
		return intersectionSet;
	}


	public SetInterface<E> complement(SetInterface<E> rhs) throws APException { //check if the operand sets and resulting sets are empty.
		SetInterface<E> complementSet = new Set<E>();
		SetInterface<E> set1 = this.copy();

		while(set1.size() != 0) {
			E num1 = set1.get();
			SetInterface<E> set2 = rhs.copy();
			boolean set2Contains = false;

			while(set2.size() != 0) {
				E num2 = set2.get();
				if (num2.compareTo(num1) == 0) {
					set2Contains = true;
				}
				set2.remove(num2);
			}
			if (!set2Contains) {
				complementSet.add(num1);
			}
			set1.remove(num1);
		}
		return complementSet;
	}


	public SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException { //check if the operand sets and resulting sets are empty.
		SetInterface<E> unionSet = this.union(set);
		SetInterface<E> intersectionSet = this.intersection(set);
		SetInterface<E> symDifferenceSet = unionSet.complement(intersectionSet);

		return symDifferenceSet;
	}

}

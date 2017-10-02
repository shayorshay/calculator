
public class Set<E extends Comparable<E>> implements SetInterface<E> {

	ListInterface<E> setContent;

	Set() {
		setContent = new List<E>();
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


	public SetInterface<E> copy() throws APException {	//why don't we use the copy constructor of the list class?
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
		SetInterface<E> set2 = set.copy();
		
		while (set1.size() != 0){
			E newItem = set1.get();
			while(set2.size() != 0){
				E otherItem = set2.get();
				if (otherItem.compareTo(newItem) == 0){
					intersectionSet.add(newItem);
				}
				set2.remove(otherItem);
			}
			set1.remove(newItem);
		}
		return intersectionSet;
	}


	public SetInterface<E> complement(SetInterface<E> set) throws APException {
		SetInterface<E> complementSet = new Set<E>();
		SetInterface<E> set1 = this.copy();
		SetInterface<E> set2 = set.copy();
		
		while(set1.size() != 0) {
			E newItem = set1.get();
			while(set2.size() != 0) {
				E otherItem = set2.get();
				if (otherItem.compareTo(newItem) != 0) {
					complementSet.add(newItem);
					complementSet.add(otherItem);
				}
				set2.remove(otherItem);
			}
			set1.remove(newItem);
		}
		
		return complementSet;
	}


	public SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException {
		SetInterface<E> set1 = this.copy();
		SetInterface<E> set2 = set.copy();
		SetInterface<E> unionSet = set1.union(set2);
		SetInterface<E> intersectionSet = set1.intersection(set2);
		SetInterface<E> symDifferenceSet = new Set<E>();
		
		while(unionSet.size() != 0) {
			E newItem = unionSet.get();
			while (intersectionSet.size() != 0) {
				E otherItem = intersectionSet.get();
				if(otherItem.compareTo(newItem) != 0) {
					symDifferenceSet.add(newItem);
					symDifferenceSet.add(otherItem);
				}
				intersectionSet.remove(otherItem);
			}
			unionSet.remove(newItem);
		}
		
		return symDifferenceSet;
	}

}


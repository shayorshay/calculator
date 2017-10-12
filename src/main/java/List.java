public class List<E extends Comparable> implements ListInterface<E>{

	private class Node {

		E data;
		Node prior,
		next;

		public Node(E d) {
			this(d, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}

	}

	Node current;
	int size;


	List () {
		current = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}


	public ListInterface<E> init() {
		current = null;
		size = 0;
		return this;

	}


	public int size() {	
		return size;
	}


	public ListInterface<E> insert(E d) {
		goToFirst();

		if (current == null){
			Node newNode = new Node(d);
			current = newNode;
			size += 1;
			return this;
		}

		if (d.compareTo(current.data) < 0) {
			insertFirst(d);
			size += 1;
			return this;
		} 

		goToNext();
		
		while (current.next != null) {
			
			if (current.data.compareTo(d) > 0) {
				insertBefore(d);
				size += 1;
				return this;
			}
			current = current.next;	
			if (current.next == null && d.compareTo(current.data) < 0) {
				insertBefore(d);
				size += 1;
				return this;
			}
		}

		insertLast(d);
		size += 1;
		return this;
	}


	private void insertLast(E d) {
		//System.out.println("new node being inserted at last position");
		Node newNode = new Node(d, current, null);
		current.next = newNode;
		current = newNode;
	}

	private void insertBefore(E d) {
		//System.out.println("new node being inserted before current");
		Node newNode = new Node(d, current.prior, current);
		current.prior.next = newNode;
		current.prior = newNode;
		current = newNode;

	}

	private void insertFirst(E d) {
		//System.out.println("new node being inserted at first position");
		Node newNode = new Node(d, null, current);
		current.prior = newNode;
		current = newNode;	

	}

	public E retrieve() {
		return current.data;
	}


	public ListInterface<E> remove() {

		if (size < 2) {
			current = null;
			size = 0;
			return this;
		}

		if(current.next != null) {
			current.prior.next = current.next;
			current.next.prior = current.prior;
			current = current.next;
			size -= 1;
			return this;
		} else {
			current = current.prior;
			//System.out.println(current.prior.data);
			//System.out.printf("new current after the last element is: ");
			//System.out.println(current.data);
			current.next = null;
			size -= 1;
			return this;
		}

	}


	public boolean find(E d) {	
		goToFirst();

		while(current != null) {
			//System.out.println(current.data);
			if (current.data.compareTo(d) == 0) {
				return true;
			}

			if(current.data.compareTo(d) > 0) {
				if (current.prior != null) {
					current = current.prior;
					return false;
				}
				return false;
			} 
			if (current.next == null) {
				return false;
			} 
			current = current.next;
		}
		return false;
	}


	public boolean goToFirst() {
		if (current == null){
			return false;
		} else {
			while (current.prior != null){
				current = current.prior;
			}
		}
		//System.out.printf("gotofirst returns: ");
		//System.out.println(current.data);
		//System.out.println(current==null);
		return true;
	}


	public boolean goToLast() {
		if (current == null){
			return false;
		}
		while (current.next != null){
			current = current.next;
		}
		return true;
	}


	public boolean goToNext() {
		if (current == null || current.next == null){
			return false;
		}

		current = current.next;
		return true;
	}


	public boolean goToPrevious() {
		if (current == null || current.prior == null){
			return false;
		}

		current = current.prior;
		return true;
	}


	public ListInterface<E> copy() {
		List <E> result = new List <E>();
		goToFirst();
		while (current.next != null){
			result.insert(current.data);
			goToNext();
		}
		result.insert(current.data);
		return result;
	}
}
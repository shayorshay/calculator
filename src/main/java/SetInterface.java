/**
 * @elements objects of the type E
 * @structure none
 * @domain all sets of objects of the type E
 **/
public interface SetInterface <E extends Comparable<E>>  {
	/**
	 * @pre E e must not already be in the set
	 * @post E e is added to the set
	 */
	void add(E e);

	/**
	 * @pre 
	 * @post E e is removed from the set
	 */
	void remove(E e);

	/**
	 * @pre -
	 * @post The number of elements in the set is returned
	 */
	int size();
	
	/**
	 * @pre Set must not be empty
	 * @post Returns true if the set contains e
	 */
	boolean contains(E e);

	/**
	 * @pre Set must not be empty
	 * @post An element of type E is returned
	 */
	E get() throws APException;
	
	/**
	 * @pre -
	 * @post A copy of the set is made
	 */
	SetInterface<E> copy() throws APException;

	/**
	 * 
	 * @pre -
	 * @post The set returned is a union of two sets
	 */
	SetInterface<E> union(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @pre -
	 * @post The set returned is an intersection of two sets
	 */
	SetInterface<E> intersection(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @pre -
	 * @post The set returned is a complement of two sets
	 */
	SetInterface<E> complement(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @pre -
	 * @post The set returned is a complement of two sets
	 */
	SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException;
}
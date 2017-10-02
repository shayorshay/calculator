/**
 * @elements objects of the type E
 * @structure none
 * @domain all sets of objects of the type E
 **/
public interface SetInterface <E extends Comparable<E>> {
	/**
	 * @pre 
	 * @post The set contains E e
	 */
	void add(E e);

	/**
	 * @pre 
	 * @post The set does not contain E e 
	 */
	void remove(E e);

	/**
	 * @pre -
	 * @post The number of elements in the set is returned
	 */
	int size();
	
	/**
	 * @pre 
	 * @post Returns true if the set contains e and otherwise, false
	 */
	boolean contains(E e);

	/**
	 * @pre 
	 * @post An element of type E is returned or an exception is thrown
	 */
	E get() throws APException;
	
	/**
	 * @throws APException 
	 * @pre -
	 * @post A copy of the set is made
	 */
	SetInterface<E> copy() throws APException;

	/**
	 * 
	 * @throws APException 
	 * @pre -
	 * @post The set returned is a union of copies of the two sets
	 */
	SetInterface<E> union(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @throws APException 
	 * @pre -
	 * @post The set returned is an intersection of copies of the two sets 
	 */
	SetInterface<E> intersection(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @throws APException 
	 * @pre -
	 * @post The set returned is a complement of copies of the two sets
	 */
	SetInterface<E> complement(SetInterface<E> set) throws APException;
	
	/**
	 * 
	 * @throws APException 
	 * @pre -
	 * @post The set returned is a symmetric difference of copies of the two sets
	 */
	SetInterface<E> symmetricDifference(SetInterface<E> set) throws APException;
}

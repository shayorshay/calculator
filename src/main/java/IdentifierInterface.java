/**
 * @elements characters of type char
 * @structure linear
 * @domain all rows of alphanumeric characters and it has to start with a letter.
 */
public interface IdentifierInterface extends Comparable<IdentifierInterface> {

	/**
	 * @pre -
	 * @post The value associated with this identifier has been returned a String.
	 */
	String toString();
	
	/**
	 * @pre -
	 * @post - Returns the hash code of this identifier.
	 */
	int hashCode();
	
	/**
	 * @pre -
	 * @post - FALSE: The two identifiers are not the same.
	 *     	   TRUE: The two identifiers are the same.
	 */
	boolean equals(Object Obj);
	
	/**
	 * @pre -
	 * @post - Appends chars to a string to form an identifier.
	 */
	void add(char c) throws APException;
	
}
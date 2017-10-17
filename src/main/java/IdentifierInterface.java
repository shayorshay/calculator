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
	 * @post - Returns true if the two objects are of the same type
	 */
	boolean equals(Object Obj);
	
	/**
	 * @pre -
	 * @post - Returns the hash code of this identifier
	 */
	int hashCode();
	
	/**
	 * @pre -
	 * @post - Initializes the identifier
	 */
	void init (char c) throws APException;
	
	/**
	 * @pre -
	 * @post - Appends characters to the name of the identifier
	 */
	void add(char c) throws APException;
	
}

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
	 * @post -
	 */
	boolean equals(Object Obj);
	
}

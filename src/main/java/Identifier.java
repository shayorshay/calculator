public class Identifier implements IdentifierInterface{

	String data;
	int hashcode;

	Identifier (char c) throws APException{
		init(c);
	}

	public String toString() {
		return data;
	}

	public int hashCode() {
		return data.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof IdentifierInterface) { 
			boolean result = data.equals(((Identifier) o).toString());
			return result;
		} else {
			return false;
		}
	}

	public void init(char c) throws APException {
		Character ch = c;
		if ( ch.isDigit(ch)||ch.isLetter(ch)){
			data = ""+c;
		}
		else {
			throw new APException("Format of identifier is incorrect");
		}
	}

	public void add(char c) throws APException {
		Character ch = c;
		if ( ch.isDigit(ch)||ch.isLetter(ch)){
			data = data+c;
		}
		else {
			throw new APException("Format of identifier is incorrect");
		}
	}


	public int compareTo(IdentifierInterface o) {
		return 0;
	}

}
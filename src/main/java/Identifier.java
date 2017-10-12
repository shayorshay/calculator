
public class Identifier implements IdentifierInterface{

	String data;
	int hashcode;

	Identifier (char c) throws APException{
		init(c);
	}
	
/*	Identifier() {
		data = null;
	}

	Identifier(String data) {
		this.data = data;
		hashcode = data.hashCode();
	}*/
	
	public String toString() {
		return data;
	}
	
	public int hashCode() {
		return hashcode;
	}
	
	public boolean equals(Identifier i) {
		return this.data == i.data;
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	



}

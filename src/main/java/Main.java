import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.PrintStream;

public class Main {

	PrintStream out;
	HashMap<Identifier, Set<BigInteger>> hmap;

	Main() {
		out = new PrintStream(System.out);
		hmap = new HashMap<Identifier, Set<BigInteger>>();
	}

	private boolean nextCharIs(Scanner input, char c) throws APException {
		input.useDelimiter( "(\\b|\\B)" );
		return input.hasNext(Pattern.quote(c+""));
	}


	private void eoln(Scanner input) throws APException {
		if(input.hasNext()) {
			throw new APException("End of line expected");
		}
	}


	private boolean nextCharIsLetter(Scanner input) throws APException {
		input.useDelimiter( "(\\b|\\B)" );
		return input.hasNext("[a-zA-Z]");
	}


	private boolean nextCharIsNumber(Scanner input) {
		input.useDelimiter( "(\\b|\\B)" );
		return input.hasNext("[0-9]");
	}


	private void character(Scanner input, char c) throws APException {
		if (! nextCharIs(input, c)) {
			out.println(c);
			String result = "Incorrect char found, ";
			result = result + c;
			result = result + " was expected";
			throw new APException(result);
		}
		nextChar(input); // read out the character
	}


	private char additiveOperator(Scanner input) throws APException {
		if (!nextCharIs(input,'+') && !nextCharIs(input,'-') && !nextCharIs(input,'|')) {
			throw new APException("Additive Operator Expected");
		}
		char operator = nextChar(input); // read out the additive operator
		out.printf("operator: %c", operator);
		return operator;
	}

	private char multiplicativeOperator(Scanner input) throws APException {
		if (!nextCharIs(input,'*')) {
			throw new APException("Multiplicative Operator Expected");
		}
		char operator = nextChar(input); // read out the additive operator
		return operator;
	}

	private char nextChar(Scanner input) {
		return input.next().charAt(0);
	}

	void statement (Scanner input) throws APException {
		char questionMark = '?';
		char forwardSlash = '/';

		if (nextCharIs(input, forwardSlash)) {
			comment(input);
		} else if (nextCharIs(input, questionMark)) {
			printStatement(input);
		}
		else if (nextCharIsLetter(input)) {
			assignment(input);
		} else {
			throw new APException ("Command not recognised.");
		}
	}

	private void assignment(Scanner in) throws APException {
		IdentifierInterface id = getIdentifier(in);
		character (in,'=');			// reads out the = sign
		expression(in);
		eoln(in);
	}

	private SetInterface<BigInteger> expression(Scanner input) throws APException {
		SetInterface<BigInteger> result = term(input);

		while(input.hasNext()) {
			char operator = additiveOperator(input); // checks for an additive operator, throws exception if there isn't, else it reads out the additive operator. 
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			newSet = term (input);

			if (operator == '+'){
				out.printf("take Union ");
				result = result.union(newSet);
			}
			else if (operator == '-'){
				out.printf("take Complement ");
				result = result.complement(newSet);
			}
			else if (operator == '-'){
				out.printf("take Sym-Diff ");
				result = result.symmetricDifference(newSet);
			}			
		}
		return result;
	}


	private SetInterface<BigInteger> term (Scanner input) throws APException{
		out.println("term");
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = factor (input);
		while (input.hasNext()){
			multiplicativeOperator(input);
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			newSet = factor(input);
			out.printf("take Intersection ");
			result = result.intersection(newSet);
		}
		return result;
	}


	SetInterface<BigInteger> factor (Scanner input) throws APException{
		out.printf("factor\n");

		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIs(input, '(')){
			character (input,'('); // checks for ( else throws exception and takes it out
			result = complexFactor(input);
			character (input,')'); // checks for ) else throws exception and takes it out

		}

		// !!!! With the methods, like complexFactor, we use a while loop that takes input.hasnext. But the last character is a (, and we take it out after it returns, so it will always return an error, because ( is not considered a correct input

		else if(nextCharIs(input, '{')){
			character (input,'{'); // checks for ( else throws exception and takes it out
			result = set(input);
			character (input,'}'); // checks for ) else throws exception and takes it out

		}

		else if (nextCharIsLetter(input)) {
			IdentifierInterface id = getIdentifier(input);
			out.println("id = " + id.toString());
			result = hmap.get(id);
		}
		return result;
	}

	private SetInterface<BigInteger> set(Scanner input) throws APException{
		out.printf("read in Set\n");

		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = rowNaturalNumbers(input);
		return result;
	}

	SetInterface<BigInteger> rowNaturalNumbers(Scanner input) throws APException{
		out.println("row of natural numbers");
		
		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIs(input, '}')) {	//if set is empty
			return result = null;
		}
		
		BigInteger num = naturalNumber(input);
		out.println(num);
		result.add(num);
		
		while(input.hasNext()) {
			if(nextCharIs(input, ',')) {
				character(input, ',');
				BigInteger newNum = naturalNumber(input);
				out.println(newNum);
				result.add(newNum);	//I think there is a problem with  the add method in the Set class
				//because it gets stuck when I run this line.
				
			} else if (nextCharIs(input, '}')) {
				out.println("end of RowNumbers");
				return result;
			} else {
				throw new APException ("wrong input in rowofnaturalnumbers");
			}
		}
		out.println("end of RowNumbers");
		return result;

	}

	private BigInteger naturalNumber(Scanner input) throws APException{
		StringBuffer sb = new StringBuffer();
		
		if (!nextCharIsNumber(input)) {
			throw new APException ("wrong input: natural number expected");
		}
		while (nextCharIsNumber(input)) {
			sb.append(input.next());
		}
		return new BigInteger(sb.toString());
	}

	SetInterface<BigInteger> complexFactor(Scanner input)throws APException{
		out.printf("Complex Factor\n");
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = expression(input);
		return result;
	}

	private IdentifierInterface getIdentifier(Scanner in) throws APException {
		in.useDelimiter("\\W");		//delimiter is non-alphanumeric character
		String data = in.next();
		StringBuffer result = new StringBuffer();
		Scanner idScanner = new Scanner(data);
		while (idScanner.hasNext()){ // maybe not while scanner hasNext but while there is no == sign?
			if ( nextCharIsLetter(idScanner)||nextCharIsNumber(idScanner)){
				result.append(nextChar(idScanner));
			}
			else {
				throw new APException("Format of identifier is incorrect");
			}
		}
		Identifier id = new Identifier(result.toString());
		out.printf("identifier: %s\n", id.toString());
		return id;
	}

	private void comment(Scanner in) throws APException {
		in.nextLine();
		eoln(in);
	}

	private void printStatement(Scanner in) {
		out.printf("printStatement");
	}

	private void start() {
		Scanner in = new Scanner (System.in);
		while (in.hasNextLine()){
			String statement = in.nextLine();
			String newstring = statement.replaceAll(" ","");
			out.println(newstring);
			Scanner statementScanner = new Scanner(newstring);
			statementScanner.useDelimiter( "(\\b|\\B)" );

			try {
				statement(statementScanner);
			} catch (APException e) {
				System.out.printf("Error: %s\n", e.getMessage());
			}
		}
	}

	public static void main(String[] argv) throws APException {
		new Main().start();
	}
}
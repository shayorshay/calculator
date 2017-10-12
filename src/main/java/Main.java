import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.PrintStream;

public class Main {
	
	static final char QUESTION_MARK = '?';
	static final char FORWARD_SLASH = '/';
	static final char EQUAL_SIGN = '=';
	static final char UNION = '+';
	static final char INTERSECTION = '*';
	static final char COMPLEMENT = '-';
	static final char SYMMETRIC_DIFFERENCE = '|';
	static final char COMMA = ',';
	static final char LEFT_PARENTHESIS = '{';
	static final char RIGHT_PARENTHESIS = '}';
	static final char OPENING_BRACKET = '(';
	static final char CLOSING_BRACKET = ')';
	static final char WHITE_SPACE = ' ';

	
	PrintStream out;
	HashMap<IdentifierInterface, SetInterface<BigInteger>> hmap;

	Main() {
		out = new PrintStream(System.out);
		hmap = new HashMap<IdentifierInterface, SetInterface<BigInteger>>();
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
	
	private boolean nextCharIsWhiteSpace(Scanner input) {
		char whitespace = ' ';
		return input.hasNext(Pattern.quote(whitespace+""));

	}
	
	private void readWhitespace(Scanner input) throws APException{
		input.useDelimiter( "(\\b|\\B)" );

		while (nextCharIsWhiteSpace(input)){
			nextChar(input);
		}
	}

	private void character(Scanner input, char c) throws APException {
		if (! nextCharIs(input, c)) {
			String result = "Incorrect char found, : .";
			char found = nextChar(input);
			out.println(found);
			result = result + found + ". "+ c;
			result = result + " was expected";
			throw new APException(result);
		}
		nextChar(input); 
	}

	private char additiveOperator(Scanner input) throws APException {
		if (!nextCharIs(input, UNION) && !nextCharIs(input, COMPLEMENT) && !nextCharIs(input, SYMMETRIC_DIFFERENCE)) {
			throw new APException("Additive Operator Expected");
		}
		char operator = nextChar(input); 
//		out.printf("operator: %c", operator);
		return operator;
	}

	private char multiplicativeOperator(Scanner input) throws APException {
		if (!nextCharIs(input, INTERSECTION)) {
			throw new APException("Multiplicative Operator Expected");
		}
		char operator = nextChar(input); 
		return operator;
	}

	private char nextChar(Scanner input) {
		return input.next().charAt(0);
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
	
	SetInterface<BigInteger> rowNaturalNumbers(Scanner input) throws APException{
//		out.println("row of natural numbers");

		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIs(input, RIGHT_PARENTHESIS)) {	//if set is empty
			return result = null;
		}
		readWhitespace(input);

		BigInteger num = naturalNumber(input);
		result.add(num);
//		out.print(" number added: ");
//		out.print(num);
//		out.print("\n");
//		readWhitespace(input);


		while(input.hasNext()) {
			readWhitespace(input);
			if(nextCharIs(input, COMMA)) {
				character(input, COMMA);
				readWhitespace(input);
				num = naturalNumber(input);
				result.add(num);
//				out.print(" number added: ");
//				out.print(num);
//				out.print("\n");
			} else if (nextCharIs(input, RIGHT_PARENTHESIS)) {
//				out.printf("Number of items in this set %d \n", result.size());

				return result;
			} else {
				throw new APException ("wrong input in row of natural numbers");
			}
//			readWhitespace(input);
		}
//		out.printf("Number of items in this set %d \n", result.size());

		return result;

	}

	private SetInterface<BigInteger> set(Scanner input) throws APException{
//		out.println("set");

		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = rowNaturalNumbers(input);
		return result;
	}
	
	SetInterface<BigInteger> complexFactor(Scanner input)throws APException{
	//	out.println("Complex Factor");
		readWhitespace(input);
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = expression(input);
		return result;
	}
	
	SetInterface<BigInteger> factor (Scanner input) throws APException{
//		out.println("factor");
		readWhitespace(input);

		SetInterface<BigInteger> result = new Set<BigInteger>();
		if (nextCharIs(input, OPENING_BRACKET)){
			character (input, OPENING_BRACKET); 
			readWhitespace(input);

			result = complexFactor(input);
			readWhitespace(input);
			character (input, CLOSING_BRACKET); 

		} else if(nextCharIs(input, LEFT_PARENTHESIS)){
			character (input,LEFT_PARENTHESIS); 
			readWhitespace(input);
			result = set(input);
			readWhitespace(input);

			character (input, RIGHT_PARENTHESIS); 

		} else if (nextCharIsLetter(input)) {
			IdentifierInterface id = getIdentifier(input);
			String idString = id.toString();
			out.println("read id: " + idString);
//			out.println("id = " + id.toString());
			result = hmap.get(id); //here something is not working!!--------------------------------------------------------------------------------
//			out.println("hmap get methods gives back content: ");
//			out.println ( result != null);
//			System.out.println(result);
		}
		readWhitespace(input);
		return result;
	}
	
	private SetInterface<BigInteger> term (Scanner input) throws APException{
//		out.println("term");
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = factor (input);
		readWhitespace(input);

//		System.out.println(result == null);
		while (input.hasNext()){                  // <-- Expects multiplicative operator. 
			if (nextCharIs(input, UNION) || nextCharIs(input, COMPLEMENT) || nextCharIs(input, SYMMETRIC_DIFFERENCE)|| nextCharIs(input, CLOSING_BRACKET)) {
			return result;
			}
			readWhitespace(input);
			multiplicativeOperator(input);
			readWhitespace(input);
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			newSet = factor(input);
			out.println("take Intersection");
			result = result.intersection(newSet);
		}
		return result;
	}
	
	private SetInterface<BigInteger> expression(Scanner input) throws APException {
//		out.println("expression");
		SetInterface<BigInteger> result = term(input);
		while(input.hasNext()) {
			readWhitespace(input);
			if (nextCharIs(input, CLOSING_BRACKET)) {
			return result;
			}
			char operator = additiveOperator(input); 
			readWhitespace(input);
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			newSet = term (input);

			if (operator == UNION){
				out.println("take Union ");
				result = result.union(newSet);
			}
			else if (operator == COMPLEMENT){
				out.println("take Complement ");
				result = result.complement(newSet);
			}
			else if (operator == SYMMETRIC_DIFFERENCE){
				out.println("take Sym-Diff ");
				result = result.symmetricDifference(newSet);
			}			
		}
		return result;
	}

	private IdentifierInterface getIdentifier(Scanner in) throws APException {
//		out.println("identifier");
		
		IdentifierInterface id = new Identifier(nextChar(in));
		
		while (in.hasNext()){ 
			if ( !nextCharIsNumber(in) && !nextCharIsLetter(in)){
				return id; 
			}
			id.add(nextChar(in));
		}
//		out.printf("result for identifier is %s \n", result);

//		Identifier id = new Identifier(result.toString());
//		out.printf("identifier: %s\n", id.toString());

		return id;
	}

	private void assignment(Scanner in) throws APException {
//		out.println("assignment");
		IdentifierInterface id = getIdentifier(in);
		readWhitespace(in);	
		character (in, EQUAL_SIGN);			
		SetInterface<BigInteger> result = expression(in);
		eoln(in);
		hmap.put(id, result);
//		out.println("id has a value");
//		out.println(id!=null);
//		out.println("set has a value");

//		out.println(result!=null);
//		out.println("hmap get method works");
//		out.println(hmap.get(id)!=null);
		
		//do we need to use hashmap here to link the set to the id. Also, should it return something??
	}

	private void printStatement(Scanner in) throws APException {
//		SetInterface<BigInteger> set = expression(in);
		readWhitespace(in);
		character(in, QUESTION_MARK);
		readWhitespace(in);
		SetInterface<BigInteger> expression = expression(in);
		readWhitespace(in);

		eoln(in);
		printSet(expression);
//		eoln(in);
	}

	void printSet(SetInterface<BigInteger> s) throws APException{
//		out.printf("size of this set; %d\n", s.size());
		SetInterface<BigInteger> printingSet = s.copy();
//		out.printf("size of the coppied set; %d\n", printingSet.size());

		while (printingSet.size()>0){
			BigInteger i = printingSet.get();
			printingSet.remove(i);
			i.toString();
			out.printf("%s ", i);
		}
		out.printf("\n");

	}
	
	private void comment(Scanner in) throws APException {
		in.nextLine();
		eoln(in);
	}
	
	private void statement (Scanner input) throws APException {
		if (nextCharIs(input, FORWARD_SLASH)) {
			comment(input);
		} else if (nextCharIs(input, QUESTION_MARK)) {
			printStatement(input);
		}
		else if (nextCharIsLetter(input)) {
			assignment(input);
		} else {
			throw new APException ("Statement not recognised.");
		}
	}

	private void start() {
		Scanner in = new Scanner (System.in);
		while (in.hasNextLine()){
			String statement = in.nextLine();
			//String newstring = statement.replaceAll(" ","");
			//out.println(statement);
			Scanner statementScanner = new Scanner(statement);
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
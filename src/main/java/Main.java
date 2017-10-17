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
			throw new APException("no end of line");
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
	
	private boolean nextCharIsZero(Scanner input) {
		input.useDelimiter( "(\\b|\\B)" );
		return input.hasNext("[0]");
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
		if (!input.hasNext()) {
			throw new APException("char: More input was expected");
		}
		if (! nextCharIs(input, c)) {
			throw new APException("Incorrect char found");
		}
		nextChar(input); 
	}

	private char additiveOperator(Scanner input) throws APException {
		if (!input.hasNext()) {
			throw new APException("add op: More input was expected");
		}
		if (!nextCharIs(input, UNION) && !nextCharIs(input, COMPLEMENT) && !nextCharIs(input, SYMMETRIC_DIFFERENCE)) {
			throw new APException("Additive operator was expected");
		}
		char operator = nextChar(input); 
		return operator;
	}

	private char multiplicativeOperator(Scanner input) throws APException {
		if (!input.hasNext()) {
			throw new APException("mult op: More input was expected");
		}
		if (!nextCharIs(input, INTERSECTION)) {
			throw new APException("Multiplicative operator was expected");
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
			throw new APException ("Wrong input, natural number was expected");
		}
		
		if (nextCharIsZero(input)) {
			sb.append(input.next());
			return new BigInteger(sb.toString());
		}
		
		while (nextCharIsNumber(input)) {
			sb.append(input.next());
		}
		return new BigInteger(sb.toString());
	}

	SetInterface<BigInteger> rowNaturalNumbers(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		
		if (nextCharIs(input, RIGHT_PARENTHESIS)) {	
			return result;
		}
		readWhitespace(input);
		BigInteger num = naturalNumber(input);
		result.add(num);
		
		while(input.hasNext()) {
			readWhitespace(input);
			if(nextCharIs(input, COMMA)) {
				character(input, COMMA);
				readWhitespace(input);
				num = naturalNumber(input);
				result.add(num);
			} else if (nextCharIs(input, RIGHT_PARENTHESIS)) {
				return result;
			} else {
				throw new APException ("Wrong input, natural number was expected");
			}
		}
		return result;
	}

	private SetInterface<BigInteger> set(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = rowNaturalNumbers(input);
		return result;
	}

	SetInterface<BigInteger> complexFactor(Scanner input)throws APException {
		readWhitespace(input);
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = expression(input);
		return result;
	}

	SetInterface<BigInteger> factor (Scanner input) throws APException {
		readWhitespace(input);
		SetInterface<BigInteger> result = new Set<BigInteger>();
		
		if (nextCharIs(input, OPENING_BRACKET)){
			character (input, OPENING_BRACKET); 
			readWhitespace(input);
			result = complexFactor(input);
			readWhitespace(input);
			character (input, CLOSING_BRACKET); 
			readWhitespace(input);
			return result;
			
		} else if(nextCharIs(input, LEFT_PARENTHESIS)){
			character (input,LEFT_PARENTHESIS); 
			readWhitespace(input);
			result = set(input);
			readWhitespace(input);
			character (input, RIGHT_PARENTHESIS); 
			readWhitespace(input);
			return result;
			
		} else if (nextCharIsLetter(input)) {
			IdentifierInterface id = getIdentifier(input);
			if (!hmap.containsKey(id)) {
				String errorMessage = "The identifier \"" + id.toString() + "\" is undefined";
				throw new APException (errorMessage);
			}
			result = hmap.get(id); 
			readWhitespace(input);
			return result;
		}
		else {
			throw new APException ("Factor was expected");
		}
	}

	private SetInterface<BigInteger> term (Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = factor (input);
		readWhitespace(input);

		while (input.hasNext()){               
			if (nextCharIs(input, UNION) || nextCharIs(input, COMPLEMENT) || nextCharIs(input, SYMMETRIC_DIFFERENCE)|| nextCharIs(input, CLOSING_BRACKET)) {
				return result;
			}
			readWhitespace(input);
			multiplicativeOperator(input);
			readWhitespace(input);
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			newSet = factor(input);
			result = result.intersection(newSet);
		}
		return result;
	}

	private SetInterface<BigInteger> expression(Scanner input) throws APException {
		SetInterface<BigInteger> result = term(input);
		while(input.hasNext()) {
			readWhitespace(input);
			if (nextCharIs(input, CLOSING_BRACKET)) {
				return result;
			}
			char operator = additiveOperator(input); 
			readWhitespace(input);
			SetInterface<BigInteger> newSet = new Set<BigInteger>();
			if (!input.hasNext()) {
				throw new APException("Term was expected");
			}
			newSet = term (input);

			if (operator == UNION) {
				result = result.union(newSet);
			}
			else if (operator == COMPLEMENT) {
				result = result.complement(newSet);
			}
			else if (operator == SYMMETRIC_DIFFERENCE) {
				result = result.symmetricDifference(newSet);
			}			
		}
		return result;
	}

	private IdentifierInterface getIdentifier(Scanner in) throws APException {
		IdentifierInterface id = new Identifier(nextChar(in));

		while (in.hasNext()) { 
			if ( !nextCharIsNumber(in) && !nextCharIsLetter(in)){
				return id; 
			}
			id.add(nextChar(in));
		}
		return id;
	}

	private void assignment(Scanner in) throws APException {
		IdentifierInterface id = getIdentifier(in);
		readWhitespace(in);	
		character (in, EQUAL_SIGN);	
		SetInterface<BigInteger> result = expression(in);
		eoln(in);
		hmap.put(id, result);
	}

	private void printStatement(Scanner in) throws APException {
		readWhitespace(in);
		character(in, QUESTION_MARK);
		readWhitespace(in);
		SetInterface<BigInteger> expression = expression(in);
		readWhitespace(in);
		eoln(in);
		printSet(expression);
	}

	void printSet(SetInterface<BigInteger> s) throws APException {
		SetInterface<BigInteger> printingSet = s.copy();

		while (printingSet.size() > 0) {
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
		readWhitespace(input);
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
		while (in.hasNextLine()) {
			String statement = in.nextLine();
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
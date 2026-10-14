package main.java.cs1632;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static Boolean endsSequence(Token tok) {
        if (tok == null) return true;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == ';') return true;
            if (((OpToken) tok).getOperator() == '.') return true;
        }
        return false;
    }
    
    private static ArrayList<Token> getNextSequence(Tokenizer toknizr) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        Token tok;
        do {
            tok = toknizr.nextToken();
            if (tok != null) tokens.add(tok);
        } while (!endsSequence(tok));
        if (tok == null) return null;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == '.') {
                return null;
            }
        }
        return tokens;
    }
    
    // Evaluate method
    public static String evaluate(ArrayList<Token> tokens) {
		OpStack<Token> newOpStack = new OpStack<Token>();
		for (Token tok : tokens) {
			if (tok.getTokenString().equals(";")) {
				continue;
			} else {
				newOpStack.push(tok);
			}
		}

		OpStack<Token> flippedOpStack = new OpStack<Token>();
		while (!newOpStack.isEmpty()) {
			Token val = newOpStack.pop();

			if (val.getTokenString().equals("+")) {
        		Token token1 = flippedOpStack.pop();
        		int num1 = Integer.parseInt(token1.getTokenString());
        		
        		Token token2 = flippedOpStack.pop();
        		int num2 = Integer.parseInt(token2.getTokenString());
        		
        		int sumNums = (num1 + num2);
        		Token newToken = new Token(Integer.toString(sumNums));
        		
        		flippedOpStack.push(newToken);                    		
        	} 
        	
        	// Subtraction operation
        	else if (val.getTokenString().equals("-")) {
				Token token1 = flippedOpStack.pop();
        		int num1 = Integer.parseInt(token1.getTokenString());
        		
        		Token token2 = flippedOpStack.pop();
        		int num2 = Integer.parseInt(token2.getTokenString());
        		
        		int diffNums = (num1 - num2);
        		Token newToken = new Token(Integer.toString(diffNums));
        		
        		flippedOpStack.push(newToken);  
        	} 
        	
			// Multiplication operation
        	else if (val.getTokenString().equals("*")) {
				Token token1 = flippedOpStack.pop();
        		int num1 = Integer.parseInt(token1.getTokenString());
        		
        		Token token2 = flippedOpStack.pop();
        		int num2 = Integer.parseInt(token2.getTokenString());
        		
        		int prodNums = (num1 * num2);
        		Token newToken = new Token(Integer.toString(prodNums));
        		
        		flippedOpStack.push(newToken);               		
			} 
			
			// Division operation
        	else if (val.getTokenString().equals("/")) {
				Token token1 = flippedOpStack.pop();
        		int num1 = Integer.parseInt(token1.getTokenString());
        		
        		Token token2 = flippedOpStack.pop();
        		int num2 = Integer.parseInt(token2.getTokenString());
        		
    			int divNums = (num1 / num2);
    			Token newToken = new Token(Integer.toString(divNums));
        		
        		flippedOpStack.push(newToken);  
			} 

			else {
				flippedOpStack.push(val);
			}
		}

		// Return the final result
		if (flippedOpStack.size() != 1) {
			throw new IllegalArgumentException(
				"Malformed expression: " + flippedOpStack.size()
				+ " values left on stack, expected exactly 1");
		}
		return flippedOpStack.pop().getTokenString();
	}
    
    public static void main(String[] args) {
		System.out.println(" -- PREFIX CALCULATOR --\n");
		try {
			Tokenizer myScanner = new Tokenizer(System.in);
			ArrayList<Token> tokenSequence;
			ArrayList<Token> postfixSequence = null;
			do {
				System.out.print("Input an equation: ");
				tokenSequence = getNextSequence(myScanner);
				
				if (tokenSequence != null) {
					// Print initial input
					System.out.print("Your input: ");
					for (Token tok : tokenSequence) {
						System.out.print(tok.getTokenString() + " ");
					}
					System.out.println();
					
					// Evaluate and display result
					System.out.println("Result: " + evaluate(tokenSequence));
				}
			} while (tokenSequence != null);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
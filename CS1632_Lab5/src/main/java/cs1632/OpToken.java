package main.java.cs1632;

public class OpToken extends Token {
    protected int operator;
    protected int precedence;
    protected OpToken() { super(""); }
    public int getOperator() { return operator; }
    public int getPrecedence() { return precedence; }
    public OpToken(int op) {
        super("" + ((char) op));
        switch (op) {
            case '+': case '-':
                precedence = 11;
                break;
            case '*': case '/':
                precedence = 12;
                break;
            case '.':
                precedence = 16;
                break;
        }
        operator = op;
    }
}

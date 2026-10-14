package main.java.cs1632;

public class NumToken extends Token {
    protected NumToken() { super(""); }
    protected int value;
    public NumToken(String contents) {
        super(contents);
        value = Integer.valueOf(contents);
    }
}

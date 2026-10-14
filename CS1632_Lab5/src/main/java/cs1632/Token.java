package cs1632;

public class Token {
    private String tokenString;
    private Token() { tokenString = ""; }
    public Token(String ts) { tokenString = ts; }
    public String getTokenString() { return tokenString; }
}

package cs1632;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tokenizer {
    private BufferedReader buffer;
    private Boolean putbackP;
    private int putbackCh;
    private int nextChar() throws IOException {
        if (putbackP) {
            putbackP = false;
            return putbackCh;
        }
        else {
            int ch = buffer.read();
            return ch;
        }
    }
    private void putback(int ch) {
        putbackP = true;
        putbackCh = ch;
    }
    private Tokenizer() { putbackP = false; putbackCh = -1; }
    public Tokenizer(InputStream inStream) throws IOException {
        buffer = new BufferedReader(new InputStreamReader(inStream));
        putbackP = false;
        putbackCh = -1;
    }
    public Token nextToken() throws IOException {
        int ch;
        ch = nextChar();
        if (ch == -1) {
            return null;
        }
        do {
            if ((ch >= '0') && (ch <= '9')) {
                String nString = new String();
                nString += (char) ch;
                do {
                    ch = nextChar();
                    if ((ch >= '0') && (ch <= '9')) {
                        nString += ch;
                    }
                    else {
                        putback(ch);
                    }
                } while ((ch >= '0') && (ch <= '9'));
                return new NumToken(nString);
            }
            else {
                switch ((char) ch) {
                    case ' ':
                    case '\t':
                    case '\n':
                        ch = nextChar();
                        break;
                    case '+':
                    case '*':
                    case '-':
                    case '/':
                    case ';':
                    case '(':
                    case ')':
                    case '.':
                        return new OpToken(ch);
                    default:
                        System.err.println("Unknown character (" + ((char) ch) + ")");
                        break;
                }
            }
        } while (ch != -1);
        return null;
    }
}

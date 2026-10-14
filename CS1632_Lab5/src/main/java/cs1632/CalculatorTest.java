package main.java.cs1632;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CalculatorTest {

	@Test
	public void opStackIsLIFO() {
		OpStack<Integer> stack = new OpStack<>();
		stack.push(1);
		stack.push(2);
		assertEquals(2, stack.pop());  // last pushed comes out first
		assertEquals(1, stack.pop());
	}

	@Test
	public void opStackSizeTracksPushAndPop() {
		OpStack<Integer> stack = new OpStack<>();
		assertTrue(stack.isEmpty());
		stack.push(5);
		assertEquals(1, stack.size());
		stack.pop();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void tokenizerParsesMultiDigitNumber() throws IOException {
		Tokenizer tk = new Tokenizer(new ByteArrayInputStream("42;".getBytes()));
		ArrayList<Token> seq = collect(tk);
		assertEquals("42", seq.get(0).getTokenString());
	}

	@Test
	public void tokenizerSkipsWhitespace() throws IOException {
		Tokenizer tk = new Tokenizer(new ByteArrayInputStream("  +\n\t 2;".getBytes()));
		ArrayList<Token> seq = collect(tk);
		assertEquals("+", seq.get(0).getTokenString());  // whitespace vanished, not tokenized
	}

	@Test
	public void evaluateAddition() throws IOException {
		ArrayList<Token> seq = collect(
			new Tokenizer(new ByteArrayInputStream("+ 2 3;".getBytes())));
		assertEquals("5", Main.evaluate(seq));
	}

	@Test
	public void evaluateNestedPrefix() throws IOException {
		// + 2 * 3 4  →  14. Your exact failing case!
		ArrayList<Token> seq = collect(
			new Tokenizer(new ByteArrayInputStream("+ 2 * 3 4;".getBytes())));
		assertEquals("14", Main.evaluate(seq));
	}

	@Test
	public void evaluateSubtractionRespectsOperandOrder() throws IOException {
		ArrayList<Token> seq = collect(
			new Tokenizer(new ByteArrayInputStream("- 10 3;".getBytes())));
		assertEquals("7", Main.evaluate(seq));  // 10-3, not 3-10
	}
	
	@Test
	public void evaluateRejectsTooManyOperands() throws IOException {
		ArrayList<Token> seq = collect(
			new Tokenizer(new ByteArrayInputStream("+ 1 2 3;".getBytes())));
		assertThrows(IllegalArgumentException.class, () -> Main.evaluate(seq));
	}

	private ArrayList<Token> collect(Tokenizer tk) throws IOException {
		ArrayList<Token> out = new ArrayList<>();
		Token t;
		do {
			t = tk.nextToken();
			if (t != null) out.add(t);
		} while (t != null && !(t instanceof OpToken
			&& ((OpToken) t).getOperator() == ';'));
		return out;
	}
}
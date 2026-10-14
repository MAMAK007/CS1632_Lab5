package main.java.cs1632;

import java.util.Iterator;
import java.util.LinkedList;

public class OpStack<T> implements Iterable<T> {
	protected LinkedList<T> stackData;
	
	public OpStack() { 
		stackData = new LinkedList<>(); 
	}
	
	public void push(T d) { 
		stackData.addFirst(d);
	}
	
	public T pop() {
		T res = stackData.getFirst();
		stackData.remove();
		return res;
	}
	
	public T peek() { 
		return stackData.getFirst();	
	}
	
	public int size() {
		return stackData.size();
	}
	
	public Boolean isEmpty() { 
		return stackData.isEmpty(); 
	}

	@Override
	public Iterator<T> iterator() {
		return stackData.iterator();
	}
}

package HelperPackage;

public class Pair<A, B> {
	protected A first;
	protected B second;
	
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
	
	public A first() {
		return first;
	}
	
	public B second() {
		return second;
	}
	
	public boolean contains(Object o) {
		return (first.equals(o) || second.equals(o));
	}
}

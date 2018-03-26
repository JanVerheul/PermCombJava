package slist;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class SListImpl<T> implements SList<T>, Iterable<T> {

	/* Private data */
	private T head;
	private SList<T> tail;

	/* Getters; no Setters to enforce immutability */
	public T getHead() {return head;}
	public SList<T> getTail() {return tail;}
	
	/* Constructors */
	public SListImpl(T head, SList<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	/* Public methods */
	public boolean isEmpty() {
		return false;
	}
	public int size() {
		if (tail.isEmpty()) return 1;
		else return sizeTailRec(tail, 1);
	}
	private int sizeTailRec(SList<T> sList, int partResult) {
		if (sList.isEmpty()) return partResult;
		else return sizeTailRec(sList.getTail(), partResult + 1);
	}
	public Iterator<T> iterator() {
		return new SListImplIterator();
	}
	public SList<T> reverse() {
		return SList.reverse(this);
	}
	public String toString() {
		if (isEmpty()) return "SList()";
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("SList(");
			sb.append(head.toString());
			SList<T> walker = tail;
			while (!walker.isEmpty()) {
				sb.append(", ");
				sb.append(walker.getHead().toString());
				walker = walker.getTail();
			}
			sb.append(")");
			return sb.toString();
		}
	}
	public SList<T> append(SList<T> sList) {
		return SList.append(this, sList);
	}
	
	public T get(int i) {
		if (i < 0) throw new IllegalArgumentException("Negative indexes not allowed in SList.get()...");
		if (i == 0) return head;
		else return tail.get(i - 1);
	}
	
	public boolean contains(T elem) {
		if (head.equals(elem)) return true;
		else return tail.contains(elem);
	}
	
	public SList<T> filter(Predicate<T> pred) {
		return SList.filter(this, pred);
	}
	
	public <R> SList<R> map(Function<T, R> f) {
		return SList.map(this, f);
	}
	
	public <U, R> SList<R> map2(BiFunction<T, U, R> f, SList<U> sList) {
		return SList.map2(this, f, sList);
	}
	
	public <R> SList<R> mapIndex(BiFunction<T, Integer, R> f) {
		return SList.mapIndex(this, f);
	}
	
	public <R> R foldLeft(BiFunction<R, T, R> f, R partResult) {
		return SList.foldLeft(this, f, partResult);
	}
	
	public <R> R foldRight(BiFunction<T, R, R> f, R initVal) {
		return SList.foldRight(this, f, initVal);
	}

	private class SListImplIterator implements Iterator<T> {
		private SList<T> walker;
		public SListImplIterator() {
			walker = SListImpl.this;
		}
		public boolean hasNext() {
			return !walker.isEmpty();
		}
		public T next() {
			T head = walker.getHead();
			walker = walker.getTail();
			return head;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SListImpl<?>) {
			SListImpl<T> sListImpl = (SListImpl<T>)o;
			return head.equals(sListImpl.getHead()) && tail.equals(sListImpl.getTail());
		}
		else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return 3911 * head.hashCode() + 7411 * tail.hashCode();
	}
	
}

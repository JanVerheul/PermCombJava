package slist;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import slist.*;

public class SListNil<T> implements SList<T> {

	public T getHead() {
		throw new UnsupportedOperationException("getHead() not allowed on empty SList...");
	}
	public SList<T> getTail() {
		throw new UnsupportedOperationException("getTail() not allowed on empty SList...");
	}
	public boolean isEmpty() {
		return true;
	}
	public int size() {
		return 0;
	}
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			public boolean hasNext() {
				return false;
			}
			public T next() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public SList<T> reverse() {
		return this;
	}
	public SList<T> append(SList<T> sList) {
		return sList;
	}
	public T get(int i) {
		throw new IllegalArgumentException("Index out of bounds in SList.get()...");
	}
	public boolean contains(T elem) {
		return false;
	}
	public SList<T> filter(Predicate<T> pred) {
		return new SListNil<T>();
	}
	public <R> SList<R> map(Function<T, R> f) {
		return new SListNil<R>();
	}
	public <U, R> SList<R> map2(BiFunction<T, U, R> f, SList<U> sList) {
		return new SListNil<R>();
	}
	public <R> SList<R> mapIndex(BiFunction<T, Integer, R> f) {
		return new SListNil<R>();
	}
	public <R> R foldLeft(BiFunction<R, T, R> f, R partResult) {
		return partResult;
	}
	public <R> R foldRight(BiFunction<T, R, R> f, R initVal) {
		return initVal;
	}
	public String toString() {
		return "SList()";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SList<?>) return (((SList<T>) o).size() == 0);
		else return false;
	}
	@Override
	public int hashCode() {
		return 358309725;
	}


}

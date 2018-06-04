package slist.iter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import slist.SList;
import tuple.Tuple2;

public class SListNil<T> implements SList<T> {

	/* Getters */
	public T head() {
		throw new UnsupportedOperationException("head() not allowed on empty SL...");
	}
	public SList<T> tail() {
		throw new UnsupportedOperationException("tail() not allowed on empty SL...");
	}

	/* Setters only for the implementation of fast iterative utility functions */
	public void fixHead(T head) {
		throw new UnsupportedOperationException("This implementations does not support replaceHead()...");
	}
	public void fixTail(SList<T> tail) {
		throw new UnsupportedOperationException("This implementations does not support replaceHead()...");
	}

	
	/* Inquirers */
	public boolean isEmpty() {
		return true;
	}
	public int size() {
		return 0;
	}
	public T get(int i) {
		throw new IllegalArgumentException("Index out of bounds in SL.get()...");
	}
	public boolean contains(T elem) {
		return false;
	}
	public boolean contains(T elem, Comparator<T> comp) {
		return false;
	}
	public boolean containsList(SList<T> sList) {
		return false;
	}
	public boolean containsList(SList<T> sList, Comparator<T> comp) {
		return false;
	}

	/* Extenders */
	public SList<T> push(T elem) {
		return SL.single(elem);
	}
	public SList<T> insertOrdered(T elem) {
		return SL.single(elem);
	}
	public SList<T> insertOrdered(T elem, Comparator<T> comp) {
		return SL.single(elem);
	}
	public SList<T> append(SList<T> sList) {
		return sList;
	}
	
	/* Reductors */
	public SList<T> drop(int n) {
		return SL.empty();
	}
	public SList<T> take(int n) {
		return SL.empty();
	}
	public SList<T> slice(int m, int n) {
		return SL.empty();
	}
	public SList<T> removeOne(T elem) {
		return SL.empty();
	}
	public SList<T> removeOne(T elem, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> removeAll(T elem) {
		return SL.empty();
	}
	public SList<T> removeAll(T elem, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> removeListOne(SList<T> sList) {
		return SL.empty();
	}
	public SList<T> removeListOne(SList<T> sList, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> removeListAll(SList<T> sList) {
		return SL.empty();
	}
	public SList<T> removeListAll(SList<T> sList, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> remDoubles() {
		return SL.empty();
	}
	public SList<T> remDoublesSorted() {
		return SL.empty();
	}
	public SList<T> remDoublesSorted(Comparator<T> comp) {
		return SL.empty();
	}
	
	/* Mutators */
	public SList<T> reverse() {
		return this;
	}
	public SList<T> sort() {
		return this;
	}
	public SList<T> sort(Comparator<T> comp) {
		return this;
	}
	public SList<T> merge(SList<T> sList) {
		return sList;
	}
	public SList<T> merge(SList<T> sList, Comparator<T> comp) {
		return sList;
	}
	
	/* Higher Order Workers */
	public void forEach(BiConsumer<T, Integer> f) {
		// nothing to be done
	}
	public SList<T> filter(Predicate<T> pred) {
		return SL.empty();
	}
	public SList<T> filterRev(Predicate<T> pred) {
		return SL.empty();
	}
	public SList<T> minBy(Function<T, Integer> f) {
		return SL.empty();
	}
	public SList<T> maxBy(Function<T, Integer> f) {
		return SL.empty();
	}
	public <R> SList<R> map(Function<T, R> f) {
		return SL.empty();
	}
	public <R> SList<R> mapRev(Function<T, R> f) {
		return SL.empty();
	}
	public <R> SList<R> map(BiFunction<T, Integer, R> f) {
		return SL.empty();
	}
	public <R> SList<R> mapRev(BiFunction<T, Integer, R> f) {
		return SL.empty();
	}
	public <U, R> SList<R> map2(BiFunction<T, U, R> f, SList<U> sList) {
		return SL.empty();
	}
	public <U, R> SList<R> map2Rev(BiFunction<T, U, R> f, SList<U> sList) {
		return SL.empty();
	}
	public <R> SList<R> flatMap(Function<T, SList<R>> f) {
		return SL.empty();
	}
	public <R> SList<R> flatMapRev(Function<T, SList<R>> f) {
		return SL.empty();
	}
	public <R> R foldLeft(BiFunction<R, T, R> f, R partResult) {
		return partResult;
	}
	public <R> R foldRight(BiFunction<T, R, R> f, R initVal) {
		return initVal;
	}
	public boolean forAll(Predicate<T> p) {
		return true;
	}
	public boolean exists(Predicate<T> p) {
		return false;
	}
	public Tuple2<T, Integer> find(Predicate<T> pred) {
		return new Tuple2<T, Integer>(null, -1);
	}
	public <R> Tuple2<R, Integer> findMapped(Function<T, R> f, Predicate<R> p) {
		return new Tuple2<R, Integer>(null, -1);
	}

	
	/* Set Representation Workers  */
	public SList<T> setRep() {
		return SL.empty();
	}
	public SList<T> setRep(Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> setInsertOrdered(T elem) {
		return SL.empty();
	}
	public SList<T> setInsertOrdered(T elem, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> setUnion(SList<T> sList) {
		return SL.empty();
	}
	public SList<T> setUnion(SList<T> sList, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> setIntersect(SList<T> sList) {
		return SL.empty();
	}
	public SList<T> setIntersect(SList<T> sList, Comparator<T> comp) {
		return SL.empty();
	}
	public SList<T> setMerge(SList<T> sList) {
		return SL.empty();
	}
	public SList<T> setMerge(SList<T> sList, Comparator<T> comp) {
		return SL.empty();
	}

	
	@Override
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
	


}

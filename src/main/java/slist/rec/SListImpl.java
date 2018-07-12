package slist.rec;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import slist.SList;
import tuple.Tuple2;

public class SListImpl<T> implements SList<T>, Iterable<T> {

	/* Private data */
	public final T head;
	public final SList<T> tail;

	/* Constructors */
	public SListImpl(T head, SList<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	
	/* Getters; no Setters to enforce immutability */
	public T head() { return head; }
	public SList<T> tail() { return tail; }
	public T get(int i) {
		if (i < 0) throw new IllegalArgumentException("Negative indexes not allowed in SL.get()...");
		if (i == 0) return head;
		else return tail.get(i - 1);
	}
	public Iterator<T> iterator() {
		return new SListImplIterator();
	}

	/* Fix methods for the implementation of fast iterative utility functions */
	public void fixHead(T head) { throw new UnsupportedOperationException("This implementations does not support replaceHead..."); }
	public void fixTail(SList<T> tail) { throw new UnsupportedOperationException("This implementations does not support replaceHead..."); }
	
	
	/* Inquirers */
	public boolean isEmpty() {
		return false;
	}
	public int size() {
		if (tail.isEmpty()) return 1;
		else return 1 + tail.size();
	}
	public boolean contains(T elem) {
		return SL.contains(this,  elem);
	}
	public boolean contains(T elem, Comparator<? super T> comp) {
		return SL.contains(this,  elem, comp);
	}
	public boolean containsList(SList<T> sList) {
		return SL.containsList(this,  sList);
	}
	public boolean containsList(SList<T> sList, Comparator<? super T> comp) {
		return SL.containsList(this, sList, comp);
	}
	
	/* Extenders */
	public SList<T> push(T elem) {
		return new SListImpl<T>(elem, this);
	}
	public SList<T> insertOrdered(T elem) {
		return SL.insertOrdered(this, elem);
	}
	public SList<T> insertOrdered(T elem, Comparator<? super T> comp) {
		return SL.insertOrdered(this, elem, comp);
	}
	public SList<T> append(SList<T> sList) {
		return SL.append(this, sList);
	}

	/* Reductors */
	public SList<T> drop(int n) {
		if (n == 0) return this;
		else return tail.drop(n - 1);
	}
	public SList<T> take(int n) {
		if (n == 0 || size() == 0) return SL.empty();
		else return new SListImpl<T>(head, tail.take(n - 1));
	}
	public SList<T> slice(int m, int n) {
		return take(n).drop(m);
	}
	public SList<T> removeOne(T elem) {
		return SL.removeOne(this, elem);
	}
	public SList<T> removeOne(T elem, Comparator<? super T> comp) {
		return SL.removeOne(this, elem, comp);
	}
	public SList<T> removeAll(T elem) {
		return SL.removeAll(this, elem);
	}
	public SList<T> removeAll(T elem, Comparator<? super T> comp) {
		return SL.removeAll(this, elem, comp);
	}
	public SList<T> removeListOne(SList<T> sList) {
		return SL.removeListOne(this, sList);
	}
	public SList<T> removeListOne(SList<T> sList, Comparator<? super T> comp) {
		return SL.removeListOne(this, sList, comp);
	}
	public SList<T> removeListAll(SList<T> sList) {
		return SL.removeListAll(this, sList);
	}
	public SList<T> removeListAll(SList<T> sList, Comparator<? super T> comp) {
		return SL.removeListAll(this, sList, comp);
	}
	public SList<T> remDoubles() {
		return SL.remDoubles(this);
	}
	public SList<T> remDoublesSorted() {
		return SL.remDoublesSorted(this);
	}
	public SList<T> remDoublesSorted(Comparator<? super T> comp) {
		return SL.remDoublesSorted(this, comp);
	}

	/* Mutators */
	public SList<T> reverse() {
		return SL.reverse(this);
	}
	public SList<T> sort() {
		return SL.sort(this);
	}
	public SList<T> merge(SList<T> sList) {
		return SL.merge(this, sList);
	}
	public SList<T> sort(Comparator<? super T> comp) {
		return SL.sort(this, comp);
	}
	public SList<T> merge(SList<T> sList, Comparator<? super T> comp) {
		return SL.merge(this, sList, comp);
	}

	/* Higher Order Workers */
	public void forEach(BiConsumer<? super T, Integer> f) {
		SL.forEach(this, f);
	}
	public SList<T> filter(Predicate<? super T> pred) {
		return SL.filter(this, pred);
	}
	public SList<T> filterRev(Predicate<? super T> pred) {
		return SL.filterRev(this, pred);
	}
	public SList<T> minBy(Function<? super T, Integer> f) {
		return SL.empty(); // TODO
	}
	public SList<T> maxBy(Function<? super T, Integer> f) {
		return SL.empty(); // TODO
	}
	public <R> SList<R> map(Function<? super T, ? extends R> f) {
		return SL.map(this, f);
	}
	public <R> SList<R> mapRev(Function<? super T, ? extends R> f) {
		return SL.mapRev(this, f);
	}
	public <R> SList<R> map(BiFunction<? super T, Integer, ? extends R> f) {
		return SL.map(this, f);
	}
	public <R> SList<R> mapRev(BiFunction<? super T, Integer, ? extends R> f) {
		return SL.mapRev(this, f);
	}
	public <U, R> SList<R> map2(BiFunction<? super T, ? super U, ? extends R> f, SList<U> sList) {
		return SL.map2(this, f, sList);
	}
	public <U, R> SList<R> map2Rev(BiFunction<? super T, ? super U, ? extends R> f, SList<U> sList) {
		return SL.map2(this, f, sList);
	}
	public <R> SList<R> flatMap(Function<? super T, ? extends SList<R>> f) {
		return SL.flatMap(this, f);
	}
	public <R> SList<R> flatMapRev(Function<? super T, ? extends SList<R>> f) {
		return SL.flatMap(this, f);
	}
	public <R> R foldLeft(BiFunction<R, ? super T, R> f, R partResult) {
		return SL.foldLeft(this, f, partResult);
	}
	public <R> R foldRight(BiFunction<? super T, R, R> f, R initVal) {
		return SL.foldRight(this, f, initVal);
	}
	public boolean forAll(Predicate<? super T> pred) {
		return SL.forAll(this, pred);
	}
	public boolean exists(Predicate<? super T> pred) {
		return SL.exists(this, pred);
	}
	public Tuple2<T, Integer> find(Predicate<? super T> pred) {
		return SL.find(this, pred);
	}
	public <R> Tuple2<R, Integer> findMapped(Function<T, R> f, Predicate<? super R> p) {
		return SL.findMapped(this, f, p);
	}


	/* Set Representation Workers */
	public SList<T> setRep() {
		return SL.setRep(this);
	}
	public SList<T> setInsertOrdered(T elem) {
		return SL.setInsertOrdered(this, elem);
	}
	public SList<T> setUnion(SList<T> sList) {
		return SL.setUnion(this, sList);
	}
	public SList<T> setIntersect(SList<T> sList) {
		return SL.setIntersect(this, sList);
	}
	public SList<T> setMerge(SList<T> sList) {
		return SL.setMerge(this, sList);
	}
	public SList<T> setRep(Comparator<T> comp) {
		return SL.setRep(this, comp);
	}
	public SList<T> setInsertOrdered(T elem, Comparator<T> comp) {
		return SL.setInsertOrdered(this, elem, comp);
	}
	public SList<T> setUnion(SList<T> sList, Comparator<T> comp) {
		return SL.setUnion(this, sList, comp);
	}
	public SList<T> setIntersect(SList<T> sList, Comparator<T> comp) {
		return SL.setIntersect(this, sList, comp);
	}
	public SList<T> setMerge(SList<T> sList, Comparator<T> comp) {
		return SL.setMerge(this, sList, comp);
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
			T head = walker.head();
			walker = walker.tail();
			return head;
		}
	}

	@Override
	public String toString() {
		if (isEmpty()) return "SList()";
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("SList(");
			sb.append(head.toString());
			SList<T> walker = tail;
			while (!walker.isEmpty()) {
				sb.append(", ");
				sb.append(walker.head().toString());
				walker = walker.tail();
			}
			sb.append(")");
			return sb.toString();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SListImpl<?>) {
			SListImpl<T> sListImpl = (SListImpl<T>)o;
			return head.equals(sListImpl.head()) && tail.equals(sListImpl.tail());
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

package slist;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import slist.rec.SListImpl;
import slist.SListNil;
import tuple.Tuple2;

public interface SList<T> extends Iterable<T> {

	/* Getters */
	T head();
	SList<T> tail();
	T get(int i);
	
	/* Fix methods for the implementation of fast iterative utility functions */
	public void fixHead(T head);
	public void fixTail(SList<T> tail);
	
	/* Inquirers */
	boolean isEmpty();
	int size();
	boolean contains(T elem);
	boolean contains(T elem, Comparator<T> comp);
	boolean containsList(SList<T> sList);
	boolean containsList(SList<T> sList, Comparator<T> comp);
	
	/* Extenders */
	SList<T> push(T elem);
	SList<T> insertOrdered(T elem);
	SList<T> insertOrdered(T elem, Comparator<T> comp);
	SList<T> append(SList<T> sList);

	/* Reductors */
	SList<T> drop(int n);
	SList<T> take(int n);
	SList<T> slice(int m, int n);
	SList<T> removeOne(T elem);
	SList<T> removeOne(T elem, Comparator<T> comp);
	SList<T> removeAll(T elem);
	SList<T> removeAll(T elem, Comparator<T> comp);
	SList<T> removeListOne(SList<T> sList);
	SList<T> removeListOne(SList<T> sList, Comparator<T> comp);
	SList<T> removeListAll(SList<T> sList);
	SList<T> removeListAll(SList<T> sList, Comparator<T> comp);
	SList<T> remDoubles();
	SList<T> remDoublesSorted();
	SList<T> remDoublesSorted(Comparator<T> comp);
	
	/* Mutators */
	SList<T> reverse();
	SList<T> sort();
	SList<T> sort(Comparator<T> comp);
	SList<T> merge(SList<T> sList);
	SList<T> merge(SList<T> sList, Comparator<T> comp);
	
	/* Higer Order Workers */
	void forEach(BiConsumer<T, Integer> f);
	SList<T> filter(Predicate<T> pred);
	SList<T> filterRev(Predicate<T> pred);
	<R> SList<R> map(Function<T, R> f);
	<R> SList<R> mapRev(Function<T, R> f);
	<R> SList<R> map(BiFunction<T, Integer, R> f);
	<R> SList<R> mapRev(BiFunction<T, Integer, R> f);
	<U, R> SList<R> map2(BiFunction<T, U, R> f, SList<U> sList);
	<U, R> SList<R> map2Rev(BiFunction<T, U, R> f, SList<U> sList);
	<R> SList<R> flatMap(Function<T, SList<R>> f);
	<R> SList<R> flatMapRev(Function<T, SList<R>> f);
	<R> R foldLeft(BiFunction<R, T, R> f, R partResult);
	<R> R foldRight(BiFunction<T, R, R> f, R initVal);
	boolean forAll(Predicate<T> p);
	boolean exists(Predicate<T> p);
	Tuple2<T, Integer> find(Predicate<T> pred);
	<R> Tuple2<R, Integer> findMapped(Function<T, R> f, Predicate<R> p);

	/* Set Representation Workers  */
	SList<T> setRep();
	SList<T> setRep(Comparator<T> comp);
	SList<T> setInsertOrdered(T elem);
	SList<T> setInsertOrdered(T elem, Comparator<T> comp);
	SList<T> setUnion(SList<T> sList);
	SList<T> setUnion(SList<T> sList, Comparator<T> comp);
	SList<T> setIntersect(SList<T> sList);
	SList<T> setIntersect(SList<T> sList, Comparator<T> comp);
	SList<T> setMerge(SList<T> sList);
	SList<T> setMerge(SList<T> sList, Comparator<T> comp);
	
	@Override
	boolean equals(Object o);
	@Override
	int hashCode();

}

package slist;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tuple.Tuple2;

public interface SList<T> extends Iterable<T> {

	/* Getters */
	T head();
	SList<T> tail();
	T get(int i);
	
	/* Inquirers */
	boolean isEmpty();
	int size();
	boolean contains(T elem);
	boolean contains(T elem, Comparator<? super T> comp);
	boolean containsList(SList<T> sList);
	boolean containsList(SList<T> sList, Comparator<? super T> comp);
	
	/* Extenders */
	SList<T> push(T elem);
	SList<T> insertOrdered(T elem);
	SList<T> insertOrdered(T elem, Comparator<? super T> comp);
	SList<T> append(SList<T> sList);

	/* Reductors */
	SList<T> drop(int n);
	SList<T> take(int n);
	SList<T> slice(int m, int n);
	SList<T> removeOne(T elem);
	SList<T> removeOne(T elem, Comparator<? super T> comp);
	SList<T> removeAll(T elem);
	SList<T> removeAll(T elem, Comparator<? super T> comp);
	SList<T> removeListOne(SList<T> sList);
	SList<T> removeListOne(SList<T> sList, Comparator<? super T> comp);
	SList<T> removeListAll(SList<T> sList);
	SList<T> removeListAll(SList<T> sList, Comparator<? super T> comp);
	SList<T> remDoubles();
	SList<T> remDoublesSorted();
	SList<T> remDoublesSorted(Comparator<? super T> comp);
	
	/* Mutators */
	SList<T> reverse();
	SList<T> sort();
	SList<T> sort(Comparator<? super T> comp);
	SList<T> merge(SList<T> sList);
	SList<T> merge(SList<T> sList, Comparator<? super T> comp);
	
	/* Higher Order Workers */
	void forEach(BiConsumer<? super T, Integer> f);
	SList<T> filter(Predicate<? super T> pred);
	SList<T> filterRev(Predicate<? super T> pred);
	SList<T> minBy(Function<? super T, Integer> f);
	SList<T> maxBy(Function<? super T, Integer> f);
	<R> SList<R> map(Function<? super T, ? extends R> f);
	<R> SList<R> mapRev(Function<? super T, ? extends R> f);
	<R> SList<R> map(BiFunction<? super T, Integer, ? extends R> f);
	<R> SList<R> mapRev(BiFunction<? super T, Integer, ? extends R> f);
	<U, R> SList<R> map2(BiFunction<? super T, ? super U, ? extends R> f, SList<U> sList);
	<U, R> SList<R> map2Rev(BiFunction<? super T, ? super U, ? extends R> f, SList<U> sList);
	<R> SList<R> flatMap(Function<? super T, ? extends SList<R>> f);
	<R> SList<R> flatMapRev(Function<? super T, ? extends SList<R>> f);
	<R> R foldLeft(BiFunction<R, ? super T, R> f, R partResult);
	<R> R foldRight(BiFunction<? super T, R, R> f, R initVal);
	boolean forAll(Predicate<? super T> p);
	boolean exists(Predicate<? super T> p);
	Tuple2<T, Integer> find(Predicate<? super T> pred);
	<R> Tuple2<R, Integer> findMapped(Function<T, R> f, Predicate<? super R> p);

	@Override
	boolean equals(Object o);
	@Override
	int hashCode();

}

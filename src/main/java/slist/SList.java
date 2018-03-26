package slist;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface SList<T> extends Iterable<T> {

	T getHead();
	SList<T> getTail();
	boolean isEmpty();
	int size();
	SList<T> reverse();
	SList<T> append(SList<T> sList);
	T get(int i);
	boolean contains(T elem);
	SList<T> filter(Predicate<T> pred);
	<R> SList<R> map(Function<T, R> f);
	<U, R> SList<R> map2(BiFunction<T, U, R> f, SList<U> sList);
	<R> SList<R> mapIndex(BiFunction<T, Integer, R> f);
	<R> R foldLeft(BiFunction<R, T, R> f, R partResult);
	<R> R foldRight(BiFunction<T, R, R> f, R initVal);
	@Override
	boolean equals(Object o);
	@Override
	int hashCode();

	/* Static factory methods */
	static <T> SList<T> empty() {
		SList<T> result = new SListNil<>();
		return result;
	}
	public static <T> SList<T> cons(T head, SList<T> tail) {
		return new SListImpl<T>(head, tail);
	}
	public static <T> SList<T> cons(T... elems) {
		SList<T> result = empty();
		for (int i = elems.length - 1; i >= 0; i--) {
			result = cons(elems[i], result);
		}
		return result;
	}
	public static <T> SList<T> cons(Collection<T> col) {
		SList<T> result = empty();
		for (T elem : col) {
			result = cons(elem, result);
		}
		return result;
	}
	
	/* Static worker methods */
	static <T> SList<T> reverse(SList<T> sList) {
		return reverseTailRec(sList, empty());
	}
	static <T> SList<T> reverseTailRec(SList<T> sList, SList<T> partResult) {
		if (sList.isEmpty()) return partResult;
		else return reverseTailRec(sList.getTail(), cons(sList.getHead(), partResult));
	}
	static <T> SList<T> append(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else return cons(sList1.getHead(), append(sList1.getTail(), sList2));
	}
	static <T extends Comparable<T>> SList<T> sort(SList<T> sList) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length);
		else return sList;
	}
	static <T extends Comparable<T>> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SList.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay);
		if (halfWay + 1 == upperEx) upperPart = SList.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx);
		return merge(lowerPart, upperPart);
	}
	static <T extends Comparable<T>> SList<T> merge(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (sList1.getHead().compareTo(sList2.getHead()) < 0) return SList.cons(sList1.getHead(), merge(sList1.getTail(), sList2));
			else return SList.cons(sList2.getHead(), merge(sList1, sList2.getTail())); 
		}
	}

	static <T extends Comparable<T>> SList<T> sort(SList<T> sList, Comparator<T> comp) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length, comp);
		else return sList;
	}
	static <T extends Comparable<T>> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx, Comparator<T> comp) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SList.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay, comp);
		if (halfWay + 1 == upperEx) upperPart = SList.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx, comp);
		return merge(lowerPart, upperPart, comp);
	}
	static <T extends Comparable<T>> SList<T> merge(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (comp.compare(sList1.getHead(), sList2.getHead()) < 0) return SList.cons(sList1.getHead(), merge(sList1.getTail(), sList2, comp));
			else return SList.cons(sList2.getHead(), merge(sList1, sList2.getTail(), comp)); 
		}
	}
	
	static <T> boolean contains(SList<T> sList, T elem) {
		if (sList.isEmpty()) return false;
		else if (sList.getHead().equals(elem)) return true;
		else return contains(sList.getTail(), elem);
	}

	// no tail recursion; length of prefix list limited...
	static <T> SList<T> prepend(SList<T> prefix, SList<T> sList) {
		if (prefix.isEmpty()) return sList;
		else return SList.cons(prefix.getHead(), prepend(prefix.getTail(), sList));
	}
	
	static <T> SList<T> remove(SList<T> sList1, SList<T> sList2) {
		SList<T> result = new SListNil<>();
		for (T t : sList1) {
			if (!SList.contains(sList2, t)) {
				result = SList.cons(t, result);
			}
		}
		return result;
	}
	
	static <T> SList<T> filter(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return sList;
		else if (pred.test(sList.getHead())) return SList.cons(sList.getHead(), filter(sList.getTail(), pred));
		else return filter(sList.getTail(), pred);
	}
	
	static <T, R> SList<R> map(SList<T> sList, Function<T, R> f) {
		if (sList.isEmpty()) return new SListNil<R>();
		else return SList.cons(f.apply(sList.getHead()), map(sList.getTail(), f));
	}

	static <T, U, R> SList<R> map2(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2) {
		if (sList1.isEmpty() || sList2.isEmpty()) return new SListNil<R>();
		else return SList.cons(f.apply(sList1.getHead(), sList2.getHead()), map2(sList1.getTail(), f, sList2.getTail()));
	}
	
	static <T, R> SList<R> mapIndex(SList<T> sList, BiFunction<T, Integer, R> f) {
		return mapIndexImpl(sList, f, 0);
	}
	
	static <T, R> SList<R> mapIndexImpl(SList<T> sList, BiFunction<T, Integer, R> f, Integer index) {
		if (sList.isEmpty()) return new SListNil<R>();
		else return SList.cons(f.apply(sList.getHead(), index), mapIndexImpl(sList.getTail(), f, index + 1));
	}
	
	static <T, R> R foldLeft(SList<T> sList, BiFunction<R, T, R> f, R partResult) {
		if (sList.isEmpty()) return partResult;
		else return foldLeft(sList.getTail(), f, f.apply(partResult, sList.getHead())); 
	}
	
	static <T, R> R foldRight(SList<T> sList, BiFunction<T, R, R> f, R initVal) {
		if (sList.isEmpty()) return initVal;
		else return f.apply(sList.getHead(), foldRight(sList.getTail(), f, initVal));
	}

}

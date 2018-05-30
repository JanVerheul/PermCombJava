package slist.rec;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import slist.SList;
import tuple.Tuple2;

public class SL {

	private SL() {
	}

	
	/* Static factory methods */
	public static <T> SList<T> empty() {
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
	public static <T> SList<T> single(T elem) {
		return cons(elem, empty());
	}
	public static <T> SList<T> pair(T elem1, T elem2) {
		return cons(elem1, cons(elem2, empty()));
	}
	public static <T> SList<T> triple(T elem1, T elem2, T elem3) {
		return cons(elem1, cons(elem2, cons(elem3, empty())));
	}
	public static SList<Integer> indexesTill(int uBound) {
		SList<Integer> result = SL.empty();
		for (int i = uBound - 1; i >= 0; i--) result = result.push(i);
		return result;
	}
	public static SList<Integer> indexesFromTill(int lBound, int uBound) {
		SList<Integer> result = SL.empty();
		for (int i = uBound - 1; i >= lBound; i--) result = result.push(i);
		return result;
	}
	
	/* Static worker methods */
	
	/* Inquirers */
	static <T> boolean contains(SList<T> sList, T elem) {
		if (sList.isEmpty()) return false;
		else if (sList.head().equals(elem)) return true;
		else return contains(sList.tail(), elem);
	}
	static <T> boolean contains(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return false;
		else if (comp.compare(sList.head(), elem) == 0) return true;
		else return contains(sList.tail(), elem);
	}
	static <T> boolean containsList(SList<T> sList1, SList<T> sList2) {
		if (sList2.isEmpty()) return true;
		else return contains(sList1, sList2.head()) && containsList(sList1, sList2.tail());
	}
	static <T> boolean containsList(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList2.isEmpty()) return true;
		else return contains(sList1, sList2.head(), comp) && containsList(sList1, sList2.tail(), comp);
	}
	
	/* Extenders */
	static <T> SList<T> insertOrdered(SList<T> sList, T elem) {
		Comparable<T> compElem = (Comparable<T>)elem;
		if (sList.isEmpty()) return SL.single(elem);
		else if (compElem.compareTo(sList.head()) <= 0) return new SListImpl<T>(elem, sList);
		else return new SListImpl<T>(sList.head(), SL.insertOrdered(sList.tail(), elem));
	}
	static <T> SList<T> insertOrdered(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (comp.compare(elem, sList.head()) <= 0) return new SListImpl<T>(elem, sList);
		else return new SListImpl<T>(sList.head(), SL.insertOrdered(sList.tail(), elem, comp));
	}
	static <T> SList<T> setInsertOrdered(SList<T> sList, T elem) {
		Comparable<T> compElem = (Comparable<T>)elem;
		if (sList.isEmpty()) return SL.single(elem);
		else if (compElem.compareTo(sList.head()) == 0) return sList;
		else if (compElem.compareTo(sList.head()) < 0) return new SListImpl<T>(elem, sList);
		else return new SListImpl<T>(sList.head(), SL.insertOrdered(sList.tail(), elem));
	}
	static <T> SList<T> setInsertOrdered(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (comp.compare(elem, sList.head()) == 0) return sList;
		else if (comp.compare(elem, sList.head()) < 0) return new SListImpl<T>(elem, sList);
		else return new SListImpl<T>(sList.head(), SL.insertOrdered(sList.tail(), elem, comp));
	}
	static <T> SList<T> append(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else return cons(sList1.head(), append(sList1.tail(), sList2));
	}

	/* Reductors */
	static <T> SList<T> removeOne(SList<T> sList, T elem) {
		if (sList.isEmpty()) return sList;
		else if (elem.equals(sList.head())) return sList.tail();
		else return new SListImpl<T>(sList.head(), SL.removeOne(sList.tail(), elem));
	}
	static <T> SList<T> removeOne(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return sList;
		else if (comp.compare(elem, sList.head()) == 0) return sList.tail();
		else return new SListImpl<T>(sList.head(), SL.removeOne(sList.tail(), elem, comp));
	}
	static <T> SList<T> removeAll(SList<T> sList, T elem) {
		if (sList.isEmpty()) return sList;
		else if (elem.equals(sList.head())) return SL.removeAll(sList.tail(), elem);
		else return new SListImpl<T>(sList.head(), SL.removeAll(sList.tail(), elem));
	}
	static <T> SList<T> removeAll(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return sList;
		else if (comp.compare(elem, sList.head()) == 0) return SL.removeAll(sList.tail(), elem);
		else return new SListImpl<T>(sList.head(), SL.removeAll(sList.tail(), elem, comp));
	}
	static <T> SList<T> removeListOne(SList<T> sList1, SList<T> sList2) {
		if (sList2.isEmpty()) return sList1;
		else return SL.removeOne(SL.removeListOne(sList1, sList2.tail()), sList2.head());
	}
	static <T> SList<T> removeListOne(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList2.isEmpty()) return sList1;
		else return SL.removeOne(SL.removeListOne(sList1, sList2.tail(), comp), sList2.head());
	}
	static <T> SList<T> removeListAll(SList<T> sList1, SList<T> sList2) {
		if (sList2.isEmpty()) return sList1;
		else return SL.removeAll(SL.removeListAll(sList1, sList2.tail()), sList2.head());
	}
	static <T> SList<T> removeListAll(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList2.isEmpty()) return sList1;
		else return SL.removeAll(SL.removeListAll(sList1, sList2.tail(), comp), sList2.head());
	}
	static <T> SList<T> remDoubles(SList<T> sList) {
		if (sList.isEmpty()) return sList;
		else return SL.cons(sList.head(), SL.removeAll(SL.remDoubles(sList.tail()), sList.head()));
	}
	static <T> SList<T> remDoublesSorted(SList<T> sList) {
		if (sList.isEmpty()) return sList;
		else return SL.remDoublesSortedImpl(sList.tail(), sList.head());
	}
	static <T> SList<T> remDoublesSortedImpl(SList<T> sList, T elem) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (elem.equals(sList.head())) return remDoublesSortedImpl(sList.tail(), elem);
		else return SL.cons(elem, remDoublesSortedImpl(sList.tail(), sList.head()));
	}
	static <T> SList<T> remDoublesSorted(SList<T> sList, Comparator<T> comp) {
		if (sList.isEmpty()) return sList;
		else return SL.remDoublesSortedImpl(sList.tail(), sList.head(), comp);
	}
	static <T> SList<T> remDoublesSortedImpl(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (comp.compare(sList.head(), elem) == 0) return remDoublesSortedImpl(sList.tail(), elem, comp);
		else return SL.cons(elem, remDoublesSortedImpl(sList.tail(), sList.head(), comp));
	}

	
	
	
	
	
	
	/* Mutators */
	static <T> SList<T> reverse(SList<T> sList) {
		return reverseTailRec(sList, empty());
	}
	static <T> SList<T> reverseTailRec(SList<T> sList, SList<T> partResult) {
		if (sList.isEmpty()) return partResult;
		else return reverseTailRec(sList.tail(), cons(sList.head(), partResult));
	}
	static <T> SList<T> sort(SList<T> sList) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length);
		else return sList;
	}
	static <T> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SL.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay);
		if (halfWay + 1 == upperEx) upperPart = SL.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx);
		return merge(lowerPart, upperPart);
	}
	static <T> SList<T> merge(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			Comparable<T> head1 = (Comparable<T>)sList1.head();
			T head2 = sList2.head();
			if (head1.compareTo(head2) < 0) return SL.cons((T)head1, merge(sList1.tail(), sList2));
			else return SL.cons(head2, merge(sList1, sList2.tail())); 
		}
	}
	static <T> SList<T> setMerge(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			Comparable<T> head1 = (Comparable<T>)sList1.head();
			T head2 = sList2.head();
			if (head1.compareTo(head2) < 0) return SL.cons((T)head1, SL.setMerge(sList1.tail(), sList2));
			else if (head1.compareTo(head2) == 0) return SL.setMerge(sList1.tail(), sList2);
			else return SL.cons(head2, SL.setMerge(sList1, sList2.tail()));
		}
	}
	static <T> SList<T> sort(SList<T> sList, Comparator<T> comp) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length, comp);
		else return sList;
	}
	static <T> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx, Comparator<T> comp) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SL.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay, comp);
		if (halfWay + 1 == upperEx) upperPart = SL.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx, comp);
		return merge(lowerPart, upperPart, comp);
	}
	static <T> SList<T> merge(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (comp.compare(sList1.head(), sList2.head()) < 0) return SL.cons(sList1.head(), merge(sList1.tail(), sList2, comp));
			else return SL.cons(sList2.head(), merge(sList1, sList2.tail(), comp)); 
		}
	}
	static <T> SList<T> setMerge(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (comp.compare(sList1.head(), sList2.head()) < 0) return SL.cons(sList1.head(), SL.setMerge(sList1.tail(), sList2, comp));
			else if (comp.compare(sList1.head(), sList2.head()) == 0) return SL.setMerge(sList1.tail(), sList2, comp);
			else return SL.cons(sList2.head(), SL.setMerge(sList1, sList2.tail(), comp));
		}
	}
	
	/* Higher Order Workers */
	static <T> void forEach(SList<T> sList, BiConsumer<T,Integer> f) {
		SL.forEachImpl(sList, f, 0);
	}
	static <T> void forEachImpl(SList<T> sList, BiConsumer<T,Integer> f, Integer i) {
		if (!sList.isEmpty()) {
			f.accept(sList.head(), i);
			forEachImpl(sList.tail(), f, i + 1);
		}
	}
	static <T> SList<T> filter(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return sList;
		else if (pred.test(sList.head())) return SL.cons(sList.head(), filter(sList.tail(), pred));
		else return filter(sList.tail(), pred);
	}
	static <T> SList<T> filterRev(SList<T> sList, Predicate<T> pred) {
		return filterRevImpl(sList, pred, SL.empty());
	}
	static <T> SList<T> filterRevImpl(SList<T> sList, Predicate<T> pred, SList<T> partRes) {
		if (sList.isEmpty()) return partRes;
		else if (pred.test(sList.head())) return filterRevImpl(sList.tail(), pred, SL.cons(sList.head(), partRes));
		else return filterRevImpl(sList.tail(), pred, partRes);
	}
	static <T, R> SList<R> map(SList<T> sList, Function<T, R> f) {
		if (sList.isEmpty()) return SL.empty();
		else return SL.cons(f.apply(sList.head()), map(sList.tail(), f));
	}
	static <T, R> SList<R> mapRev(SList<T> sList, Function<T, R> f) {
		return mapRevImpl(sList, f, SL.empty());
	}
	static <T, R> SList<R> mapRevImpl(SList<T> sList, Function<T, R> f, SList<R> partRes) {
		if (sList.isEmpty()) return partRes;
		else return mapRevImpl(sList.tail(), f, SL.cons(f.apply(sList.head()), partRes));
	}
	static <T, R> SList<R> map(SList<T> sList, BiFunction<T, Integer, R> f) {
		return mapImpl(sList, f, 0);
	}
	static <T, R> SList<R> mapImpl(SList<T> sList, BiFunction<T, Integer, R> f, Integer index) {
		if (sList.isEmpty()) return SL.empty();
		else return SL.cons(f.apply(sList.head(), index), mapImpl(sList.tail(), f, index + 1));
	}
	static <T, R> SList<R> mapRev(SList<T> sList, BiFunction<T, Integer, R> f) {
		return mapRevImpl(sList, f, 0, SL.empty());
	}
	static <T, R> SList<R> mapRevImpl(SList<T> sList, BiFunction<T, Integer, R> f, Integer index, SList<R> partRes) {
		if (sList.isEmpty()) return partRes;
		else return mapRevImpl(sList.tail(), f, index + 1, SL.cons(f.apply(sList.head(), index), partRes));
	}
	static <T, U, R> SList<R> map2(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2) {
		if (sList1.isEmpty() || sList2.isEmpty()) return SL.empty();
		else return SL.cons(f.apply(sList1.head(), sList2.head()), map2(sList1.tail(), f, sList2.tail()));
	}
	static <T, U, R> SList<R> map2Rev(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2) {
		return map2RevImpl(sList1, f, sList2, SL.empty());
	}
	static <T, U, R> SList<R> map2RevImpl(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2, SList<R> partRes) {
		if (sList1.isEmpty() || sList2.isEmpty()) return partRes;
		else return map2RevImpl(sList1.tail(), f, sList2.tail(), SL.cons(f.apply(sList1.head(), sList2.head()), partRes));
	}
	static <T, R> SList<R> flatMap(SList<T> sList, Function<T, SList<R>> f) {
		if (sList.isEmpty()) return new SListNil<R>();
		else return SL.append(f.apply(sList.head()), flatMap(sList.tail(), f));
	}
	static <T, R> SList<R> flatMapRev(SList<T> sList, Function<T, SList<R>> f) {
		return flatMapRevImpl(sList, f, SL.empty());
	}
	static <T, R> SList<R> flatMapRevImpl(SList<T> sList, Function<T, SList<R>> f, SList<R> partRes) {
		if (sList.isEmpty()) return partRes;
		else return flatMapRevImpl(sList.tail(), f, SL.append(f.apply(sList.head()), partRes));
	}
	static <T, R> R foldLeft(SList<T> sList, BiFunction<R, T, R> f, R partResult) {
		if (sList.isEmpty()) return partResult;
		else return foldLeft(sList.tail(), f, f.apply(partResult, sList.head())); 
	}
	static <T, R> R foldRight(SList<T> sList, BiFunction<T, R, R> f, R initVal) {
		if (sList.isEmpty()) return initVal;
		else return f.apply(sList.head(), foldRight(sList.tail(), f, initVal));
	}


	/* Set Representation Workers  */
	static <T> SList<T> setRep(SList<T> sList) {
		return sList.sort().remDoublesSorted();
	}
	static <T> SList<T> setRep(SList<T> sList, Comparator<T> comp) {
		return sList.sort(comp).remDoublesSorted(comp);
	}
	static <T> SList<T> setUnion(SList<T> sList1, SList<T> sList2) {
		return setMerge(sList1, sList2);
	}
	
	static <T> SList<T> setUnion(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return setMerge(sList1, sList2, comp);
	}
	static <T> SList<T> intersect(SList<T> sList1, SList<T> sList2) {
		return null;
	}
	static <T> SList<T> intersect(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return null;
	}
	static <T> SList<T> setIntersect(SList<T> sList1, SList<T> sList2) {
		return null;
	}
	static <T> SList<T> setIntersect(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return null;
	}

	static <T> Tuple2<T, Integer> find(SList<T> sList, Predicate<T> pred) {
		return SL.findImpl(sList, pred, 0);
	}
	static <T> Tuple2<T, Integer> findImpl(SList<T> sList, Predicate<T> pred, Integer i) {
		if (sList.isEmpty()) return new Tuple2<T, Integer>(null, -1);
		else if (pred.test(sList.head())) return new Tuple2<T, Integer>(sList.head(), i);
		else return findImpl(sList.tail(), pred, i + 1);
	}
	static <T, R> Tuple2<R, Integer> findMapped(SList<T> sList, Function<T, R> f, Predicate<R> pred) {
		return SL.findMappedImpl(sList, f, pred, 0);
	}
	static <T, R> Tuple2<R, Integer> findMappedImpl(SList<T> sList, Function<T, R> f, Predicate<R> pred, Integer i) {
		if (sList.isEmpty()) return new Tuple2<R, Integer>(null, -1);
		else if (pred.test(f.apply(sList.head()))) return new Tuple2<R, Integer>(f.apply(sList.head()), i);
		else return findMappedImpl(sList.tail(), f, pred, i + 1);
	}
	static <T> SList<T> flatten(SList<SList<T>> sListList) {
		if (sListList.isEmpty()) return SL.empty();
		else return sListList.head().append(SL.flatten(sListList.tail()));
	}
	static <T> boolean forAll(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return true;
		else return pred.test(sList.head()) && forAll(sList.tail(), pred);
	}
	static <T> boolean exists(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return false;
		else return pred.test(sList.head()) || exists(sList.tail(), pred);
	}
	static <T> SList<T> remove(SList<T> sList1, SList<T> sList2) {
		SList<T> result = new SListNil<>();
		for (T t : sList1) {
			if (!SL.contains(sList2, t)) {
				result = SL.cons(t, result);
			}
		}
		return result;
	}
	

}

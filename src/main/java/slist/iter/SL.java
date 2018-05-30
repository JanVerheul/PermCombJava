package slist.iter;

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
	public static <T> SList<T> part(T head) {
		return new SListImpl<T>(head);
	}
	public static <T> SList<T> part(SList<T> tail) {
		return new SListImpl<T>(tail);
	}
	public static <T> SList<T> part() {
		return new SListImpl<T>();
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
	public static <T> boolean contains(SList<T> sList, T elem) {
		while (!sList.isEmpty()) {
			if (elem.equals(sList.head())) return true;
			sList = sList.tail();
		}
		return false;
	}
	public static <T> boolean contains(SList<T> sList, T elem, Comparator<T> comp) {
		while (!sList.isEmpty()) {
			if (comp.compare(elem, sList.head()) == 0) return true;
			sList = sList.tail();
		}
		return false;
	}
	public static <T> boolean containsList(SList<T> sList1, SList<T> sList2) {
		while (!sList2.isEmpty()) {
			if (!SL.contains(sList1, sList2.head())) return false;
			sList2 = sList2.tail();
		}
		return true;
	}
	public static <T> boolean containsList(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		while (!sList2.isEmpty()) {
			if (!SL.contains(sList1, sList2.head(), comp)) return false;
			sList2 = sList2.tail();
		}
		return true;
	}
	
	/* Extenders */
	public static <T> SList<T> insertOrdered(SList<T> sList, T elem) {
		Comparable<T> compElem = (Comparable<T>)elem;
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && compElem.compareTo(sList.head()) > 0) {
			destWalker.fixTail(SL.part(sList.head()));
			sList = sList.tail();
			destWalker = destWalker.tail();
		}
		destWalker.fixTail(SL.cons(elem, sList));
		return resultHandle.tail();
	}
	public static <T> SList<T> insertOrdered(SList<T> sList, T elem, Comparator<T> comp) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && comp.compare(elem, sList.head()) > 0) {
			destWalker.fixTail(SL.part(sList.head()));
			sList = sList.tail();
			destWalker = destWalker.tail();
		}
		destWalker.fixTail(SL.cons(elem, sList));
		return resultHandle.tail();
	}
	public static <T> SList<T> setInsertOrdered(SList<T> sList, T elem) {
		Comparable<T> compElem = (Comparable<T>)elem;
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && compElem.compareTo(sList.head()) > 0) {
			destWalker.fixTail(SL.part(sList.head()));
			sList = sList.tail();
			destWalker = destWalker.tail();
		}
		if (!sList.isEmpty() && compElem.compareTo(sList.head()) == 0) destWalker.fixTail(sList); 
		else destWalker.fixTail(SL.cons(elem, sList));
		return resultHandle.tail();
	}
	public static <T> SList<T> setInsertOrdered(SList<T> sList, T elem, Comparator<T> comp) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && comp.compare(elem, sList.head()) > 0) {
			destWalker.fixTail(SL.part(sList.head()));
			sList = sList.tail();
			destWalker = destWalker.tail();
		}
		if (!sList.isEmpty() && comp.compare(elem, sList.head()) == 0) destWalker.fixTail(sList); 
		else destWalker.fixTail(SL.cons(elem, sList));
		return resultHandle.tail();
	}
	public static <T> SList<T> append(SList<T> sList1, SList<T> sList2) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList1.isEmpty()) {
			destWalker.fixTail(SL.part(sList1.head()));
			destWalker = destWalker.tail();
			sList1 = sList1.tail();
		}
		destWalker.fixTail(sList2);
		return resultHandle.tail();
	}

	/* Reductors */
	public static <T> SList<T> removeOne(SList<T> sList, T elem) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && !sList.head().equals(elem)) {
			destWalker.fixTail(SL.part(sList.head()));
			destWalker = destWalker.tail();
			sList = sList.tail();
		}
		if (!sList.isEmpty()) destWalker.fixTail(sList.tail());
		else destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T> SList<T> removeOne(SList<T> sList, T elem, Comparator<T> comp) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty() && comp.compare(elem, sList.head()) != 0) {
			destWalker.fixTail(SL.part(sList.head()));
			destWalker = destWalker.tail();
			sList = sList.tail();
		}
		if (!sList.isEmpty()) destWalker.fixTail(sList.tail());
		else destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T> SList<T> removeAll(SList<T> sList, T elem) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty()) {
			if (!sList.head().equals(elem)) {
				destWalker.fixTail(SL.part(sList.head()));
				destWalker = destWalker.tail();
			}
			sList = sList.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T> SList<T> removeAll(SList<T> sList, T elem, Comparator<T> comp) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty()) {
			if (comp.compare(sList.head(), elem) != 0) {
				destWalker.fixTail(SL.part(sList.head()));
				destWalker = destWalker.tail();
			}
			sList = sList.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T> SList<T> removeListOne(SList<T> sList1, SList<T> sList2) {
		while (!sList2.isEmpty()) {
			sList1 = removeOne(sList1, sList2.head());
			sList2 = sList2.tail();
		}
		return sList1;
	}
	public static <T> SList<T> removeListOne(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		while (!sList2.isEmpty()) {
			sList1 = removeOne(sList1, sList2.head(), comp);
			sList2 = sList2.tail();
		}
		return sList1;
	}
	public static <T> SList<T> removeListAll(SList<T> sList1, SList<T> sList2) {
		while (!sList2.isEmpty()) {
			sList1 = removeAll(sList1, sList2.head());
			sList2 = sList2.tail();
		}
		return sList1;
	}
	public static <T> SList<T> removeListAll(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		while (!sList2.isEmpty()) {
			sList1 = removeAll(sList1, sList2.head(), comp);
			sList2 = sList2.tail();
		}
		return sList1;
	}
	public static <T> SList<T> remDoubles(SList<T> sList) {
		if (sList.isEmpty()) return sList;
		else return SL.cons(sList.head(), SL.removeAll(SL.remDoubles(sList.tail()), sList.head()));
	}
	public static <T> SList<T> remDoublesSorted(SList<T> sList) {
		if (sList.isEmpty()) return sList;
		else return SL.remDoublesSortedImpl(sList.tail(), sList.head());
	}
	public static <T> SList<T> remDoublesSortedImpl(SList<T> sList, T elem) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (elem.equals(sList.head())) return remDoublesSortedImpl(sList.tail(), elem);
		else return SL.cons(elem, remDoublesSortedImpl(sList.tail(), sList.head()));
	}
	public static <T> SList<T> remDoublesSorted(SList<T> sList, Comparator<T> comp) {
		if (sList.isEmpty()) return sList;
		else return SL.remDoublesSortedImpl(sList.tail(), sList.head(), comp);
	}
	public static <T> SList<T> remDoublesSortedImpl(SList<T> sList, T elem, Comparator<T> comp) {
		if (sList.isEmpty()) return SL.single(elem);
		else if (comp.compare(sList.head(), elem) == 0) return remDoublesSortedImpl(sList.tail(), elem, comp);
		else return SL.cons(elem, remDoublesSortedImpl(sList.tail(), sList.head(), comp));
	}

	
	/* Mutators */
	public static <T> SList<T> reverse(SList<T> sList) {
		return reverseTailRec(sList, empty());
	}
	public static <T> SList<T> reverseTailRec(SList<T> sList, SList<T> partResult) {
		if (sList.isEmpty()) return partResult;
		else return reverseTailRec(sList.tail(), cons(sList.head(), partResult));
	}
	public static <T> SList<T> sort(SList<T> sList) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length);
		else return sList;
	}
	public static <T> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SL.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay);
		if (halfWay + 1 == upperEx) upperPart = SL.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx);
		return merge(lowerPart, upperPart);
	}
	public static <T> SList<T> merge(SList<T> sList1, SList<T> sList2) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			Comparable<T> head1 = (Comparable<T>)sList1.head();
			T head2 = sList2.head();
			if (head1.compareTo(head2) < 0) return SL.cons((T)head1, merge(sList1.tail(), sList2));
			else return SL.cons(head2, merge(sList1, sList2.tail())); 
		}
	}
	public static <T> SList<T> setMerge(SList<T> sList1, SList<T> sList2) {
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
	public static <T> SList<T> sort(SList<T> sList, Comparator<T> comp) {
		int length = sList.size();
		if (length > 1)	return mergeSort(sList, 0, length, comp);
		else return sList;
	}
	public static <T> SList<T> mergeSort(SList<T> sList, int lowerInc, int upperEx, Comparator<T> comp) {
		int halfWay = (lowerInc + upperEx) / 2;
		SList<T> lowerPart;
		SList<T> upperPart;
		if (lowerInc + 1 == halfWay) lowerPart = SL.cons(sList.get(lowerInc));
		else lowerPart = mergeSort(sList, lowerInc, halfWay, comp);
		if (halfWay + 1 == upperEx) upperPart = SL.cons(sList.get(halfWay));
		else upperPart = mergeSort(sList, halfWay, upperEx, comp);
		return merge(lowerPart, upperPart, comp);
	}
	public static <T> SList<T> merge(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (comp.compare(sList1.head(), sList2.head()) < 0) return SL.cons(sList1.head(), merge(sList1.tail(), sList2, comp));
			else return SL.cons(sList2.head(), merge(sList1, sList2.tail(), comp)); 
		}
	}
	public static <T> SList<T> setMerge(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		if (sList1.isEmpty()) return sList2;
		else if (sList2.isEmpty()) return sList1;
		else {
			if (comp.compare(sList1.head(), sList2.head()) < 0) return SL.cons(sList1.head(), SL.setMerge(sList1.tail(), sList2, comp));
			else if (comp.compare(sList1.head(), sList2.head()) == 0) return SL.setMerge(sList1.tail(), sList2, comp);
			else return SL.cons(sList2.head(), SL.setMerge(sList1, sList2.tail(), comp));
		}
	}
	
	/* Higher Order Workers */
	public static <T> void forEach(SList<T> sList, BiConsumer<T,Integer> f) {
		int index = 0;
		while (!sList.isEmpty()) {
			f.accept(sList.head(), index++);
		}
	}
	public static <T> SList<T> filter(SList<T> sList, Predicate<T> pred) {
		SList<T> resultHandle = SL.part();
		SList<T> destWalker = resultHandle;
		while (!sList.isEmpty()) {
			if (pred.test(sList.head())) {
				destWalker.fixTail(SL.part(sList.head()));
				destWalker = destWalker.tail();
			}
			sList = sList.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T> SList<T> filterRev(SList<T> sList, Predicate<T> pred) {
		SList<T> result = SL.empty();
		while (!sList.isEmpty()) {
			if (pred.test(sList.head())) {
				result = SL.cons(sList.head(), result);
			}
			sList = sList.tail();
		}
		return result;
	}
	public static <T, R> SList<R> map(SList<T> sList, Function<T, R> f) {
		SList<R> resultHandle = SL.part();
		SList<R> destWalker = resultHandle;
		while (!sList.isEmpty()) {
			destWalker.fixTail(SL.part(f.apply(sList.head())));
			sList = sList.tail();
			destWalker = destWalker.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T, R> SList<R> mapRev(SList<T> sList, Function<T, R> f) {
		SList<R> result = SL.empty();
		while (!sList.isEmpty()) {
			result = SL.cons(f.apply(sList.head()), result);
			sList = sList.tail();
		}
		return result;
	}
	public static <T, R> SList<R> map(SList<T> sList, BiFunction<T, Integer, R> f) {
		SList<T> sourceWalker = sList;
		SList<R> resultHandle = SL.part();
		SList<R> destWalker = resultHandle;
		int index = 0;
		while (!sourceWalker.isEmpty()) {
			destWalker.fixTail(SL.part(f.apply(sourceWalker.head(), index++)));
			sourceWalker = sourceWalker.tail();
			destWalker = destWalker.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T, R> SList<R> mapRev(SList<T> sList, BiFunction<T, Integer, R> f) {
		SList<R> result = SL.empty();
		int index = 0;
		while (!sList.isEmpty()) {
			result = SL.cons(f.apply(sList.head(), index++), result);
			sList = sList.tail();
		}
		return result;
	}
	public static <T, U, R> SList<R> map2(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2) {
		SList<T> sourceWalker1 = sList1;
		SList<U> sourceWalker2 = sList2;
		SList<R> resultHandle = SL.part();
		SList<R> destWalker = resultHandle;
		while (!sourceWalker1.isEmpty() && !sourceWalker2.isEmpty()) {
			destWalker.fixTail(SL.part(f.apply(sourceWalker1.head(), sourceWalker2.head())));
			sourceWalker1 = sourceWalker1.tail();
			sourceWalker2 = sourceWalker2.tail();
			destWalker = destWalker.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T, U, R> SList<R> map2Rev(SList<T> sList1, BiFunction<T, U, R> f, SList<U> sList2) {
		SList<R> result = SL.empty();
		while (!sList1.isEmpty() && !sList2.isEmpty()) {
			result = SL.cons(f.apply(sList1.head(), sList2.head()), result);
			sList1 = sList1.tail();
			sList2 = sList2.tail();
		}
		return result;
	}
	public static <T, R> SList<R> flatMap(SList<T> sList, Function<T, SList<R>> f) {
		SList<T> sourceWalker = sList;
		SList<R> resultHandle = SL.part();
		SList<R> destWalker = resultHandle;
		while (!sourceWalker.isEmpty()) {
			SList<R> partRes = f.apply(sourceWalker.head());
			SList<R> partResWalker = partRes;
			while (!partResWalker.isEmpty()) {
				destWalker.fixTail(SL.part(partResWalker.head()));
				partResWalker = partResWalker.tail();
				destWalker = destWalker.tail();
			}
			sourceWalker = sourceWalker.tail();
		}
		destWalker.fixTail(SL.empty());
		return resultHandle.tail();
	}
	public static <T, R> SList<R> flatMapRev(SList<T> sList, Function<T, SList<R>> f) {
		SList<R> result = SL.empty();
		while (!sList.isEmpty()) {
			SList<R> partRes = f.apply(sList.head());
			while (!partRes.isEmpty()) {
				result = SL.cons(partRes.head(), result);
				partRes = partRes.tail();
			}
			sList = sList.tail();
		}
		return result.tail();
	}
	public static <T, R> R foldLeft(SList<T> sList, BiFunction<R, T, R> f, R partRes) {
		while (!sList.isEmpty()) {
			partRes = f.apply(partRes, sList.head());
			sList = sList.tail();
		}
		return partRes;
	}
	public static <T, R> R foldRight(SList<T> sList, BiFunction<T, R, R> f, R partRes) {
		SList<T> revWalker = sList.reverse();
		while (!revWalker.isEmpty()) {
			partRes = f.apply(revWalker.head(), partRes);
			revWalker = revWalker.tail();
		}
		return partRes;
	}


	/* Set Representation Workers  */
	public static <T> SList<T> setRep(SList<T> sList) {
		return sList.sort().remDoublesSorted();
	}
	public static <T> SList<T> setRep(SList<T> sList, Comparator<T> comp) {
		return sList.sort(comp).remDoublesSorted(comp);
	}
	public static <T> SList<T> setUnion(SList<T> sList1, SList<T> sList2) {
		return setMerge(sList1, sList2);
	}
	
	public static <T> SList<T> setUnion(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return setMerge(sList1, sList2, comp);
	}
	public static <T> SList<T> intersect(SList<T> sList1, SList<T> sList2) {
		return null;
	}
	public static <T> SList<T> intersect(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return null;
	}
	public static <T> SList<T> setIntersect(SList<T> sList1, SList<T> sList2) {
		return null;
	}
	public static <T> SList<T> setIntersect(SList<T> sList1, SList<T> sList2, Comparator<T> comp) {
		return null;
	}

	public static <T> Tuple2<T, Integer> find(SList<T> sList, Predicate<T> pred) {
		return SL.findImpl(sList, pred, 0);
	}
	public static <T> Tuple2<T, Integer> findImpl(SList<T> sList, Predicate<T> pred, Integer i) {
		if (sList.isEmpty()) return new Tuple2<T, Integer>(null, -1);
		else if (pred.test(sList.head())) return new Tuple2<T, Integer>(sList.head(), i);
		else return findImpl(sList.tail(), pred, i + 1);
	}
	public static <T, R> Tuple2<R, Integer> findMapped(SList<T> sList, Function<T, R> f, Predicate<R> pred) {
		return SL.findMappedImpl(sList, f, pred, 0);
	}
	public static <T, R> Tuple2<R, Integer> findMappedImpl(SList<T> sList, Function<T, R> f, Predicate<R> pred, Integer i) {
		if (sList.isEmpty()) return new Tuple2<R, Integer>(null, -1);
		else if (pred.test(f.apply(sList.head()))) return new Tuple2<R, Integer>(f.apply(sList.head()), i);
		else return findMappedImpl(sList.tail(), f, pred, i + 1);
	}
	public static <T> SList<T> flatten(SList<SList<T>> sListList) {
		if (sListList.isEmpty()) return SL.empty();
		else return sListList.head().append(SL.flatten(sListList.tail()));
	}
	public static <T> boolean forAll(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return true;
		else return pred.test(sList.head()) && forAll(sList.tail(), pred);
	}
	public static <T> boolean exists(SList<T> sList, Predicate<T> pred) {
		if (sList.isEmpty()) return false;
		else return pred.test(sList.head()) || exists(sList.tail(), pred);
	}
	public static <T> SList<T> remove(SList<T> sList1, SList<T> sList2) {
		SList<T> result = new SListNil<>();
		for (T t : sList1) {
			if (!SL.contains(sList2, t)) {
				result = SL.cons(t, result);
			}
		}
		return result;
	}

	/* Helpers */
	public static <T> SList<T> completeResult(SList<T> revPref, SList<T> partRes) {
		SList<T> result = partRes;
		SList<T> walker = revPref;
		while (!walker.isEmpty()) {
			result = result.push(walker.head());
			walker = walker.tail();
		}
		return result;
	}

}

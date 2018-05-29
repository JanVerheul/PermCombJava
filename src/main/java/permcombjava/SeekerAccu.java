package permcombjava;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import slist.SList;
import slist.rec.SL;
import slist.SListNil;

public class SeekerAccu<T> implements Predicate<SList<T>> {
	private SList<SList<T>> target;
    private SList<SList<T>> listAccu;
    private Map<SList<T>, SList<Integer>> mapAccu;
    private int counter;
	public SeekerAccu(SList<SList<T>> target) {
		this.target = target;
		clear();
	}
	public void clear() {
		listAccu = new SListNil<SList<T>>();
		mapAccu = new HashMap<SList<T>, SList<Integer>>();
		counter = 0;
	}
	public boolean test(SList<T> listElem) {
System.out.println(listElem.toString());		
        listAccu = SL.cons(listElem, listAccu);
        SList<Integer> mapElem = (mapAccu.get(listElem) == null ? new SListNil<Integer>() : mapAccu.get(listElem));
        mapAccu.put(listElem, SL.cons(counter, mapElem));
        counter += 1;
        return target.contains(listElem);
	}
	public int getSize() {
		return counter;
	}
	public int getSizeDistinct() {
		return mapAccu.size();
	}
	public int getSize(SList<T> listElem) {
		return (mapAccu.get(listElem) == null ? 0 : mapAccu.get(listElem).size());
	}
	public int getIndexFirst(SList<T> listElem) {
		return (mapAccu.get(listElem) == null ? -1 : mapAccu.get(listElem).head());
	}
}

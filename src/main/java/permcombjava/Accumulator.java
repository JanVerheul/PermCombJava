package permcombjava;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import slist.SList;
import slist.SListNil;

public class Accumulator<T> implements Consumer<SList<T>> {
    private SList<SList<T>> listAccu;
    private Map<SList<T>, SList<Integer>> mapAccu;
    private int counter;
	public Accumulator() {
		clear();
	}
	public void clear() {
		listAccu = new SListNil<SList<T>>();
		mapAccu = new HashMap<SList<T>, SList<Integer>>();
		counter = 0;
	}
	public void accept(SList<T> listElem) {
System.out.println(listElem.toString());		
        listAccu = SList.cons(listElem, listAccu);
        SList<Integer> mapElem = (mapAccu.get(listElem) == null ? new SListNil<Integer>() : mapAccu.get(listElem));
        mapAccu.put(listElem, SList.cons(counter, mapElem));
        counter += 1;
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
		return (mapAccu.get(listElem) == null ? -1 : mapAccu.get(listElem).getHead());
	}
}

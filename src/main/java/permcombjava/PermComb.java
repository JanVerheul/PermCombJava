package permcombjava;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import slist.SList;
import slist.SListNil;

public class PermComb {

    /*
     * The method calls <f> with all permutations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> can occur.
     */
    public static <T> void repeatingPerm(SList<T> elems, int genSize, Consumer<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in repeatingPerm...");
        repeatingPermRec(elems, genSize, new SListNil<T>(), f);
    }
    private static <T> void repeatingPermRec(SList<T> elems, int depth, SList<T> partResult, Consumer<SList<T>> f) {
    	switch (depth) {
		    case 0: f.accept(new SListNil<T>()); break;
		    case 1: for (T elem : elems) f.accept(SList.cons(elem, partResult)); break;
		    default: for (T elem : elems) repeatingPermRec(elems, depth - 1, SList.cons(elem, partResult), f);
    	}
    }
   
    
    /*
     * The method calls <f> with all permutations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> cannot occur.
     */
    public static <T> void nonRepeatingPerm(SList<T> elems, int genSize, Consumer<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatingPerm...");
        if (genSize > elems.size()) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatingPerm...");
        nonRepeatingPermRec(elems.reverse(), genSize, new SListNil<T>(), f);
    }
    private static <T> SList<T> removeElem(SList<T> elems, T elem) {
    	if (elems.isEmpty()) {
    		return elems;
    	}
    	else if (elems.getHead().equals(elem)) {
    		return removeElem(elems.getTail(), elem);
    	}
    	else {
    		return SList.cons(elems.getHead(), removeElem(elems.getTail(), elem));
    	}
    }
    private static <T> void nonRepeatingPermRec(SList<T> elems, int depth, SList<T> partResult, Consumer<SList<T>> f) {
    	switch (depth) {
		    case 0: f.accept(new SListNil<T>()); break;
		    case 1: for (T elem : elems) f.accept(SList.cons(elem, partResult)); break;
		    default: for (T elem : elems) nonRepeatingPermRec(removeElem(elems, elem), depth - 1, SList.cons(elem, partResult), f);
    	}
    }

    /*
     * The method calls <f> with all combinations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> can occur.
     *
     * This is the most difficult one of all four algorithms. Internally, the algorithm for generating combinations
     * without repetitions is used and the problem of generating combinations with repetitions is reduced to the problem
     * of generating combinations without repetition.
     */
    public static <T> void repeatingComb(SList<T> elems, int genSize, Consumer<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in repeatingComb...");
        int simulationSize = genSize + elems.size() - 1;
        SList<Integer> intList = new SListNil<Integer>();
        for (int i = 0; i < simulationSize; i++) {
        	intList = SList.cons(i, intList);
        }
        nonRepeatingCombRec(intList, genSize, new SListNil<Integer>(), new SimulationMapper<T>(elems, f));
    }
    private static class SimulationMapper<T> implements Consumer<SList<Integer>> {
    	private SList<T> elems;
    	private Consumer<SList<T>> exConsumer;
    	SimulationMapper(SList<T> elems, Consumer<SList<T>> exConsumer) {
    		this.elems = elems;
    		this.exConsumer = exConsumer;
    		
    	}
    	public void accept(SList<Integer> simList) {
    		int expecter = 0;
    		int index = 0;
    		SList<T> result = new SListNil<T>();
    		for (int i : simList) {
    			index = index + (i - expecter);
    			expecter = i + 1;
    			result = SList.cons(elems.get(index), result);
    		}
    		this.exConsumer.accept(result.reverse());
    	}
    }

    /*
     * The method calls <f> with all combinations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> cannot occur.
     */
    public static <T> void nonRepeatingComb(SList<T> elems, int genSize, Consumer<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatingComb...");
        if (genSize > elems.size()) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatingComb...");
        nonRepeatingCombRec(elems.reverse(), genSize, new SListNil<T>(), f);
    }
    private static <T> void nonRepeatingCombRec(SList<T> elems, int length, SList<T> partResult, Consumer<SList<T>> f) {
        if (elems.size() == length) f.accept(elems.reverse().append(partResult));
        else {
            if (!elems.isEmpty()) {
                nonRepeatingCombRec(elems.getTail(), length, partResult, f);
                if (length > 0) nonRepeatingCombRec(elems.getTail(), length - 1, SList.cons(elems.getHead(), partResult), f);
            }
        }
    }
    
    

    /*
     * The method calls <f> with all combinations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> cannot occur.
     *
     * The method delivers the first combination for which <f> evaluates to true and then stops.
     * If none of the combinations evaluates to true, the result Option will be empty.
     */
    public static <T> Optional<SList<T>> nonRepeatingCombSeekFirst(SList<T> elems, int genSize, Predicate<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatingComb...");
        if (genSize > elems.size()) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatingComb...");
        return nonRepeatingCombSeekFirstRec(elems.reverse(), genSize, new SListNil<T>(), f);
    }
    private static <T> Optional<SList<T>> nonRepeatingCombSeekFirstRec(SList<T> elems, int length, SList<T> partResult, Predicate<SList<T>> f) {
        if (elems.size() == length) {
        	SList<T> result = elems.reverse().append(partResult);
        	if (f.test(result)) {
        		return Optional.of(result);
        	}
        	else {
        		return Optional.empty();
        	}
        }
        else {
            if (!elems.isEmpty()) {
                Optional<SList<T>> result = nonRepeatingCombSeekFirstRec(elems.getTail(), length, partResult, f);
                if (result.isPresent()) {
                	return result;
                }
                else if (length > 0) {
                	return nonRepeatingCombSeekFirstRec(elems.getTail(), length - 1, SList.cons(elems.getHead(), partResult), f);
                }
                else {
                	return Optional.empty();
                }
            }
            else {
            	return Optional.empty();
            }
        }
    }

//    def nonRepeatingCombSeekFirst[T](elems: List[T], length: Int, f: List[T] => Boolean): Option[List[T]] = {
//        def nonRepeatingCombSeekFirstRec(elems: List[T], length: Int, partResult: List[T]): Option[List[T]] = {
//            if (elems.size == length) {
//                val result = elems.reverse ::: partResult
//                if (f(result)) Some(result) else None
//            }
//            else {
//                if (!elems.isEmpty) {
//                    nonRepeatingCombSeekFirstRec(elems.tail, length, partResult) match {
//                        case None => nonRepeatingCombSeekFirstRec(elems.tail, length - 1, elems.head :: partResult)
//                        case x => x
//                    }
//                }
//                else None
//            }
//        }
//        if (length < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatingCombSeekFirst...")
//        if (length > elems.size) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatingCombSeekFirst...")
//        nonRepeatingCombSeekFirstRec(elems.reverse, length, Nil)
//    }

    /*
     * The method calls <f> with all combinations of length <length> of the elements <elems>.
     * Repetitions of elements of <elems> cannot occur.
     *
     * The method delivers a list of all the combinations for which <f> evaluates to true.
     */
    public static <T> SList<SList<T>> nonRepeatingCombSeekAll(SList<T> elems, int genSize, Predicate<SList<T>> f) {
        if (genSize < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatingComb...");
        if (genSize > elems.size()) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatingComb...");
        return nonRepeatingCombSeekAllRec(elems.reverse(), genSize, new SListNil<T>(), f);
    }
    private static <T> SList<SList<T>> nonRepeatingCombSeekAllRec(SList<T> elems, int length, SList<T> partResult, Predicate<SList<T>> f) {
        if (elems.size() == length) {
        	SList<T> result = elems.reverse().append(partResult);
        	if (f.test(result)) {
        		return SList.cons(result);
        	}
        	else {
        		return new SListNil<>();
        	}
        }
        else {
            if (!elems.isEmpty()) {
                SList<SList<T>> result = nonRepeatingCombSeekAllRec(elems.getTail(), length, partResult, f);
                if (length > 0) {
                	result = result.append(nonRepeatingCombSeekAllRec(elems.getTail(), length - 1, SList.cons(elems.getHead(), partResult), f));
                }
                return result;
            }
            else {
            	return new SListNil<>();
            }
        }
    }

//    def nonRepeatSeekAll[T](elems: List[T], length: Int, f: List[T] => Boolean): List[List[T]] = {
//        def nonRepeatRec(elems: List[T], length: Int, partResult: List[T]): List[List[T]] = {
//            if (elems.size == length) {
//                val result = elems.reverse ::: partResult
//                if (f(result)) List(result) else List()
//            }
//            else {
//                if (!elems.isEmpty) {
//                    val tailPart = nonRepeatRec(elems.tail, length, partResult)
//                    val headPart = nonRepeatRec(elems.tail, length - 1, elems.head :: partResult)
//                    tailPart ::: headPart
//                }
//                else Nil
//            }
//        }
//        if (length < 0) throw new IllegalArgumentException("Negative lengths not allowed in nonRepeatSeekAll...")
//        if (length > elems.size) throw new IllegalArgumentException("Lengths over elems.size not allowed in nonRepeatSeekAll...")
//        nonRepeatRec(elems.reverse, length, Nil)
//    }

}

package permcombjava;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import slist.SList;
import slist.iter.SListNil;
import slist.iter.SL;


public class PermCombTester {

	private SList<String> testList1 = new SListNil<String>();
	private SList<String> testList2 = SL.cons("A", "B", "C", "D", "E", "F", "G");
	private Accumulator<String> accumulator = new Accumulator<>();

	@Test
	public void testRepeatingPerm() {
        accumulator.clear();
        PermComb.repeatingPerm(testList1, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.repeatingPerm(testList1, 2, accumulator);
        assertEquals(accumulator.getSize(), 0);
        assertEquals(accumulator.getSizeDistinct(), 0);

        accumulator.clear();
        PermComb.repeatingPerm(testList1, 4, accumulator);
        assertEquals(accumulator.getSize(), 0);
        assertEquals(accumulator.getSizeDistinct(), 0);

        accumulator.clear();
        PermComb.repeatingPerm(testList2, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.repeatingPerm(testList2, 2, accumulator);
        assertEquals(accumulator.getSize(), 49);
        assertEquals(accumulator.getSizeDistinct(), 49);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 1);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

        accumulator.clear();
        PermComb.repeatingPerm(testList2, 4, accumulator);
        assertEquals(accumulator.getSize(), 2401);
        assertEquals(accumulator.getSizeDistinct(), 2401);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 1);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 1);
	}
	
	@Test
	public void testNonRepeatingPerm() {
        accumulator.clear();
        PermComb.nonRepeatingPerm(testList1, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingPerm(testList2, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingPerm(testList2, 2, accumulator);
        assertEquals(accumulator.getSize(), 42);
        assertEquals(accumulator.getSizeDistinct(), 42);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 1);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 0);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

        accumulator.clear();
        PermComb.nonRepeatingPerm(testList2, 4, accumulator);
        assertEquals(accumulator.getSize(), 840);
        assertEquals(accumulator.getSizeDistinct(), 840);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 1);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 1);
	}

	@Test
    public void testRepeatingComb() {
        accumulator.clear();
        PermComb.repeatingComb(testList1, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.repeatingComb(testList1, 2, accumulator);
        assertEquals(accumulator.getSize(), 0);
        assertEquals(accumulator.getSizeDistinct(), 0);

        accumulator.clear();
        PermComb.repeatingComb(testList1, 4, accumulator);
        assertEquals(accumulator.getSize(), 0);
        assertEquals(accumulator.getSizeDistinct(), 0);

        accumulator.clear();
        PermComb.repeatingComb(testList2, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.repeatingComb(testList2, 2, accumulator);
        assertEquals(accumulator.getSize(), 28);
        assertEquals(accumulator.getSizeDistinct(), 28);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 1);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 0);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

        accumulator.clear();
        PermComb.repeatingComb(testList2, 4, accumulator);
        assertEquals(accumulator.getSize(), 210);
        assertEquals(accumulator.getSizeDistinct(), 210);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 0);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 1);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 1);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);
    }

	
	@Test
    public void testNonRepeatingComb() {
        accumulator.clear();
        PermComb.nonRepeatingComb(testList1, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 2, accumulator);
        assertEquals(accumulator.getSize(), 21);
        assertEquals(accumulator.getSizeDistinct(), 21);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 1);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 0);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 4, accumulator);
        assertEquals(accumulator.getSize(), 35);
        assertEquals(accumulator.getSizeDistinct(), 35);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 0);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 1);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);
    }

	@Test
    public void testNonRepeatingCombSeekFirst() {
        accumulator.clear();
        PermComb.nonRepeatingComb(testList1, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 0, accumulator);
        assertEquals(accumulator.getSize(), 1);
        assertEquals(accumulator.getSizeDistinct(), 1);
        assertEquals(accumulator.getSize(new SListNil<String>()), 1);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 2, accumulator);
        assertEquals(accumulator.getSize(), 21);
        assertEquals(accumulator.getSizeDistinct(), 21);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 1);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 0);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

        accumulator.clear();
        PermComb.nonRepeatingComb(testList2, 4, accumulator);
        assertEquals(accumulator.getSize(), 35);
        assertEquals(accumulator.getSizeDistinct(), 35);
        assertEquals(accumulator.getSize(new SListNil<String>()), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B")), 0);
        assertEquals(accumulator.getSize(SL.cons("B", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "A", "A", "A")), 0);
        assertEquals(accumulator.getSize(SL.cons("A", "B", "C", "D")), 1);
        assertEquals(accumulator.getSize(SL.cons("D", "C", "B", "A")), 0);

	}
	
	
}

package slist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Date;

import org.junit.Test;

import slist.iter.SL;
import slist.iter.SListNil;
import slist.iter.SListImpl;
import tuple.Tuple2;

public class SListTester {

	private static SList<Integer> L0 = SL.empty();
	private static SList<Integer> L1 = SL.single(1);
	private static SList<Integer> L2 = SL.pair(1, 2);
	private static SList<Integer> L3 = SL.cons(1, 2, 3);
	private static SList<Integer> L4 = SL.cons(1, 2, 3, 4);
	private static SList<Integer> L5 = SL.cons(1, 2, 3, 4, 5);
	private static SList<Integer> L6 = SL.cons(1, 2, 3, 4, 5, 6);
	private static SList<Integer> L7 = SL.cons(1, 2, 3, 4, 5, 6, 7);
	private static SList<Integer> L8 = SL.cons(1, 2, 3, 4, 5, 6, 7, 8);
	private static SList<Integer> L9 = SL.cons(1, 2, 3, 4, 5, 6, 7, 8, 9);
	private static SList<TestClass> L10 = SL.cons(new TestClass(0, 0), new TestClass(0, 1), new TestClass(1, 0), new TestClass(1, 1), new TestClass(0, 2));
	
	static class TestClass {
		private int i1;
		private int i2;
		public TestClass(int i1, int i2) {
			this.i1 = i1;
			this.i2 = i2;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TestClass) {
				final TestClass testClass = (TestClass)obj;
				return (this.i1 == testClass.i1 && this.i2 == testClass.i2);
			}
			else {
				return false;
			}
		}
		@Override
		public int hashCode() {
			return 1373 * i1 + 23689 * i2;
		}
		public int compareTo(TestClass testClass) {
			return (this.i1 == testClass.i1 ? this.i2 - testClass.i2 : this.i1 - testClass.i1);
		}
	}
	
	/* Getters */
	@Test
	public void testGetHead() {
		final SList<String> LL2 = SL.pair("A", "AA");
		int dummy = 0;
		assertTrue(L2.head() == 1);
		assertTrue(L4.tail().tail().head() == 3);
		assertTrue(LL2.head().equals("A"));
		boolean exception = false;
		try {
			dummy = L0.head();
		}
		catch (UnsupportedOperationException e) {
			exception = true;
		}
		assertTrue(exception);
	}
	@Test
	public void testGetTail() {
		assertTrue(L1.tail().equals(SL.empty()));
		assertTrue(L2.tail().equals(SL.single(2)));
		assertTrue(L3.tail().equals(SL.pair(2,  3)));
		boolean exception = false;
		try {
			SList<Integer> dummy = L0.tail();
		}
		catch (UnsupportedOperationException e) {
			exception = true;
		}
		assertTrue(exception);
	}
	@Test
	public void testGet() {
		boolean exception;
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		assertEquals(LL5.get(0), (Integer)2);
		assertEquals(LL5.get(1), (Integer)4);
		assertEquals(LL5.get(4), (Integer)10);
		assertEquals(LL5.get(6), (Integer)14);

		exception = false;
		try {
			Integer dummy = LL5.get(7);
		}
		catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
		
		exception = false;
		try {
			Integer dummy = LL5.get(-1);
		}
		catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
		
		exception = false;
		try {
			Integer dummy = L0.get(0);
		}
		catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
		
		exception = false;
		try {
			Integer dummy = L0.get(-1);
		}
		catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
	}

	/* Setters */
	
	
	/* Static Factory Methods */
	@Test
	public void testIndexesTill() {
		assertEquals(SL.indexesTill(0), SL.empty());
		assertEquals(SL.indexesTill(1), SL.cons(0));
		assertEquals(SL.indexesTill(2), SL.cons(0, 1));
		assertEquals(SL.indexesTill(3), SL.cons(0, 1, 2));
		assertEquals(SL.indexesTill(-1), SL.empty());
	}
	
	@Test
	public void testIndexesFromTill() {
		assertEquals(SL.indexesFromTill(4, 3), SL.empty());
		assertEquals(SL.indexesFromTill(4, 4), SL.empty());
		assertEquals(SL.indexesFromTill(4, 5), SL.cons(4));
		assertEquals(SL.indexesFromTill(4, 6), SL.cons(4, 5));
		assertEquals(SL.indexesFromTill(4, 7), SL.cons(4, 5, 6));
		assertEquals(SL.indexesFromTill(-2, 2), SL.cons(-2, -1, 0, 1));
	}
	
	/* Inquirers */
	@Test
	public void testIsEmpty() {
		final SList<SList<Integer>> LL2 = SL.empty();
		final SList<SList<Integer>> LL3 = SL.single(SL.empty());
		final SList<SList<Integer>> LL4 = SL.single(SL.single(1));
		assertTrue(L0.isEmpty());
		assertFalse(L1.isEmpty());
		assertTrue(LL2.isEmpty());
		assertFalse(LL3.isEmpty());
		assertTrue(LL3.head().isEmpty());
		assertFalse(LL4.isEmpty());
	}
	@Test
	public void testSize() {
		final SList<String> LL1 = SL.empty(); 
		final SList<String> LL2 = SL.cons("A");
		final SList<String> LL3 = SL.cons("A", "B");
		final SList<String> LL4 = SL.cons("A", "B", "C");
		final SList<SList<String>> LL5 = SL.cons(LL3, LL3);
		assertEquals(LL1.size(), 0);
		assertEquals(LL2.size(), 1);
		assertEquals(LL3.size(), 2);
		assertEquals(LL4.size(), 3);
		assertEquals(LL5.size(), 2);
	}
	@Test
	public void testContains() {
		final SList<String> LL1 = SL.empty(); 
		final SList<String> LL2 = SL.cons("A");
		final SList<String> LL3 = SL.cons("A", "B");
		final SList<String> LL4 = SL.cons("A", "B", "C");
		final SList<SList<String>> LL5 = SL.cons(LL3, LL4);
		final SList<TestClass> LL6 = SL.cons(new TestClass(0, 0), new TestClass(1, 1), new TestClass(2, 2));
		assertFalse(LL1.contains("A"));
		assertTrue(LL2.contains("A"));
		assertFalse(LL2.contains("B"));
		assertTrue(LL3.contains("A"));
		assertTrue(LL3.contains("B"));
		assertFalse(LL3.contains("C"));
		assertFalse(LL5.contains(LL2));
		assertTrue(LL5.contains(LL3));
		assertTrue(LL5.contains(LL4));
		assertTrue(LL6.contains(new TestClass(1, 1)));
		assertFalse(LL6.contains(new TestClass(0, 2)));
	}
	@Test
	public void testContainsComparator() {
		Comparator<TestClass> comp = (tc1, tc2) -> (tc1.i1 == tc2.i1 ? tc2.i2 - tc1.i2 : tc2.i1 - tc1.i1);
		final SList<TestClass> LL5 = SL.empty();
		final SList<TestClass> LL6 = SL.cons(new TestClass(0, 0), new TestClass(1, 1), new TestClass(2, 2));
		assertFalse(LL5.contains(new TestClass(1, 1), comp));
		assertFalse(LL5.contains(new TestClass(0, 2), comp));
		assertTrue(LL6.contains(new TestClass(1, 1), comp));
		assertFalse(LL6.contains(new TestClass(0, 2), comp));
	}
	@Test
	public void testContainsList() {
		final SList<String> LL0 = SL.empty();
		final SList<String> LL1 = SL.single("");
		final SList<String> LL2 = SL.pair("A", "AA");
		final SList<String> LL3 = SL.triple("A", "AA", "AAA");
		final SList<Integer> LL4 = SL.cons(1, 2, 3, 4, 5, 6, 7);
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		final SList<Integer> LL7 = SL.cons(6, 2, 4, 1, 9, 0, 5, 3, 8 ,7);
		assertTrue(L3.containsList(L2));
		assertTrue(LL4.containsList(L3));
		assertTrue(LL5.containsList(LL5));
		assertTrue(LL5.containsList(L0));
		assertTrue(LL4.containsList(LL6));
		assertFalse(L0.containsList(L2));
		assertFalse(LL5.containsList(L3));
		assertFalse(L2.containsList(L3));
		assertFalse(LL5.containsList(LL6));
		
		assertTrue(LL3.containsList(LL3));
		assertTrue(LL3.containsList(LL2));
		assertTrue(LL3.containsList(LL0));
		assertFalse(LL3.containsList(LL1));
		assertFalse(LL1.containsList(LL3));
		assertFalse(LL2.containsList(LL3));
	}
	@Test
	public void testContainsListComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2);
		final Comparator<String> strComp = (s1, s2) -> (s1.compareTo(s2));
		final SList<String> LL0 = SL.empty();
		final SList<String> LL1 = SL.single("");
		final SList<String> LL2 = SL.pair("A", "AA");
		final SList<String> LL3 = SL.triple("A", "AA", "AAA");
		final SList<Integer> LL4 = SL.cons(1, 2, 3, 4, 5, 6, 7);
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		final SList<Integer> LL7 = SL.cons(6, 2, 4, 1, 9, 0, 5, 3, 8 ,7);
		assertTrue(L3.containsList(L2, intComp));
		assertTrue(LL4.containsList(L3, intComp));
		assertTrue(LL5.containsList(LL5, intComp));
		assertTrue(LL5.containsList(L0, intComp));
		assertTrue(LL4.containsList(LL6, intComp));
		assertFalse(L0.containsList(L2, intComp));
		assertFalse(LL5.containsList(L3, intComp));
		assertFalse(L2.containsList(L3, intComp));
		assertFalse(LL5.containsList(LL6, intComp));
		assertTrue(LL3.containsList(LL3, strComp));
		assertTrue(LL3.containsList(LL2, strComp));
		assertTrue(LL3.containsList(LL0, strComp));
		assertFalse(LL3.containsList(LL1, strComp));
		assertFalse(LL1.containsList(LL3, strComp));
		assertFalse(LL2.containsList(LL3, strComp));
	}
	
	/* Extenders */
	@Test
	public void testPush() {
		final SList<Integer> LL0 = SL.empty();
		final SList<Integer> LL1 = SL.single(1);
		final SList<Integer> LL2 = SL.pair(1, 2);
		final SList<SList<Integer>> LL3 = SL.empty();
		assertTrue(LL0.push(0).toString().equals("SList(0)"));
		assertTrue(LL1.push(0).toString().equals("SList(0, 1)"));
		assertTrue(LL2.push(0).toString().equals("SList(0, 1, 2)"));
		assertTrue(LL3.push(LL1).toString().equals("SList(SList(1))"));
	}
	@Test
	public void testInsertOrdered() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		assertTrue(LL4.insertOrdered(0).equals(SL.cons(0, 1, 3, 5, 7)));
		assertTrue(LL4.insertOrdered(1).equals(SL.cons(1, 1, 3, 5, 7)));
		assertTrue(LL4.insertOrdered(2).equals(SL.cons(1, 2, 3, 5, 7)));
		assertTrue(LL4.insertOrdered(3).equals(SL.cons(1, 3, 3, 5, 7)));
		assertTrue(LL4.insertOrdered(8).equals(SL.cons(1, 3, 5, 7, 8)));
	}
	@Test
	public void testInsertOrderedComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i2 - i1);
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7).reverse();
		assertTrue(LL4.insertOrdered(0, intComp).equals(SL.cons(0, 1, 3, 5, 7).reverse()));
		assertTrue(LL4.insertOrdered(1, intComp).equals(SL.cons(1, 1, 3, 5, 7).reverse()));
		assertTrue(LL4.insertOrdered(2, intComp).equals(SL.cons(1, 2, 3, 5, 7).reverse()));
		assertTrue(LL4.insertOrdered(3, intComp).equals(SL.cons(1, 3, 3, 5, 7).reverse()));
		assertTrue(LL4.insertOrdered(8, intComp).equals(SL.cons(1, 3, 5, 7, 8).reverse()));
		final SList<Integer> LL5 = L0.insertOrdered(3, intComp).insertOrdered(1, intComp).insertOrdered(7, intComp).insertOrdered(5, intComp);
		assertTrue(LL5.equals(LL4));
		
	}
	@Test
	public void testAppend() {
		final SList<String> LL1 = SL.cons("1", "2");
		final SList<String> LL2 = SL.cons("2", "1");
		final SList<String> LL3 = SL.cons("1", "2", "3");
		final SList<String> LL4 = SL.cons("3", "2", "1");
		final SList<String> LL5 = SL.empty();
		assertTrue(LL1.append(LL2).equals(SL.cons("1", "2", "2", "1")));
		assertTrue(LL1.append(LL5).equals(LL1));
		assertTrue(LL5.append(LL1).equals(LL1));
		assertTrue(LL4.append(LL3).toString().equals("SList(3, 2, 1, 1, 2, 3)"));
		assertTrue(LL1.append(LL2).toString().equals("SList(1, 2, 2, 1)"));
		assertTrue(LL1.append(LL5).toString().equals("SList(1, 2)"));
		assertTrue(LL5.append(LL1).toString().equals("SList(1, 2)"));
		assertTrue(LL4.append(LL3).toString().equals("SList(3, 2, 1, 1, 2, 3)"));
	}

	/* Reductors */
	@Test
	public void testDrop() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		assertTrue(LL4.drop(0).equals(SL.cons(1, 3, 5, 7)));
		assertTrue(LL4.drop(1).equals(SL.cons(3, 5, 7)));
		assertTrue(LL4.drop(2).equals(SL.cons(5, 7)));
		assertTrue(LL4.drop(3).equals(SL.cons(7)));
		assertTrue(LL4.drop(4).equals(SL.empty()));
	}
	@Test
	public void testTake() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		assertTrue(LL4.take(0).equals(SL.empty()));
		assertTrue(LL4.take(1).equals(SL.cons(1)));
		assertTrue(LL4.take(2).equals(SL.cons(1, 3)));
		assertTrue(LL4.take(3).equals(SL.cons(1, 3, 5)));
		assertTrue(LL4.take(4).equals(SL.cons(1, 3, 5, 7)));
	}
	@Test
	public void testSlice() {
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		assertTrue(LL5.slice(0, 7).equals(SL.cons(2, 4, 6, 8, 10, 12, 14)));
		assertTrue(LL5.slice(1, 6).equals(SL.cons(4, 6, 8, 10, 12)));
		assertTrue(LL5.slice(2, 5).equals(SL.cons(6, 8, 10)));
		assertTrue(LL5.slice(3, 4).equals(SL.cons(8)));
	}
	@Test
	public void testRemoveOne() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		assertTrue(LL4.removeOne(0).equals(SL.cons(1, 3, 5, 7)));
		assertTrue(LL4.removeOne(1).equals(SL.cons(3, 5, 7)));
		assertTrue(LL4.removeOne(2).equals(SL.cons(1, 3, 5, 7)));
		assertTrue(LL4.removeOne(7).equals(SL.cons(1, 3, 5)));
	}
	@Test
	public void testRemoveOneComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2) % 10;
		final SList<Integer> LL4 = SL.cons(1, 33, 55, 777);
		assertTrue(LL4.removeOne(0, intComp).equals(SL.cons(1, 33, 55, 777)));
		assertTrue(LL4.removeOne(1, intComp).equals(SL.cons(33, 55, 777)));
		assertTrue(LL4.removeOne(2, intComp).equals(SL.cons(1, 33, 55, 777)));
		assertTrue(LL4.removeOne(7, intComp).equals(SL.cons(1, 33, 55)));
	}
	@Test
	public void testRemoveAll() {
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertTrue(LL6.removeAll(0).equals(SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4)));
		assertTrue(LL6.removeAll(1).equals(SL.cons(2, 2, 3, 3, 3, 4, 4, 4 ,4)));
		assertTrue(LL6.removeAll(2).equals(SL.cons(1, 3, 3, 3, 4, 4, 4 ,4)));
		assertTrue(LL6.removeAll(3).equals(SL.cons(1, 2, 2, 4, 4, 4 ,4)));
		assertTrue(LL6.removeAll(4).equals(SL.cons(1, 2, 2, 3, 3, 3)));
		assertTrue(LL6.removeAll(5).equals(SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4)));
	}
	@Test
	public void testRemoveAllComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2);
		final SList<Integer> LL1 = SL.cons(2, 5, 8, 22, 22, 55, 88, 88, 88);
		assertEquals(LL1.removeAll(22), SL.cons(2, 5, 8, 55, 88, 88, 88));
		assertEquals(LL1.removeAll(88), SL.cons(2, 5, 8, 22, 22, 55));
		assertEquals(LL1.removeAll(44), SL.cons(2, 5, 8, 22, 22, 55, 88, 88, 88));
		assertEquals(L1.removeAll(44), L1);
		
		
	}
	@Test
	public void testRemoveListOne() {
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertEquals(LL6.removeListOne(SL.pair(2,  3)), SL.cons(1, 2, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.triple(4, 4, 4)), SL.cons(1, 2, 2, 3, 3, 3, 4));
		assertEquals(LL6.removeListOne(SL.pair(1,  10)), SL.cons(2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.pair(10,  100)), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.empty()), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
		
	}
	@Test
	public void testRemoveListOneComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertEquals(LL6.removeListOne(SL.pair(2,  3), intComp), SL.cons(1, 2, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.triple(4, 4, 4), intComp), SL.cons(1, 2, 2, 3, 3, 3, 4));
		assertEquals(LL6.removeListOne(SL.pair(1,  10), intComp), SL.cons(2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.pair(10,  100), intComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListOne(SL.empty(), intComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
	}
	@Test
	public void testRemoveListAll() {
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertEquals(LL6.removeListAll(SL.pair(2,  3)), SL.cons(1, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.triple(4, 4, 4)), SL.cons(1, 2, 2, 3, 3, 3));
		assertEquals(LL6.removeListAll(SL.pair(1,  10)), SL.cons(2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.pair(10,  100)), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.empty()), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
	}
	@Test
	public void testRemoveListAllComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertEquals(LL6.removeListAll(SL.pair(2,  3), intComp), SL.cons(1, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.triple(4, 4, 4), intComp), SL.cons(1, 2, 2, 3, 3, 3));
		assertEquals(LL6.removeListAll(SL.pair(1,  10), intComp), SL.cons(2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.pair(10,  100), intComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
		assertEquals(LL6.removeListAll(SL.empty(), intComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4));
	}
	@Test
	public void testRemDoubles() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL5 = SL.cons(3, 7, 1, 5);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		final SList<Integer> LL7 = SL.cons(4, 4, 4 ,4, 3, 3, 3, 2, 2, 1);
		final SList<Integer> LL8 = SL.cons(4, 3, 4, 1, 2, 4, 4, 2, 3, 3);

		assertEquals(LL4.remDoubles(), SL.cons(1, 3, 5, 7));
		assertEquals(LL5.remDoubles(), SL.cons(3, 7, 1, 5));
		assertEquals(LL6.remDoubles(), SL.cons(1, 2, 3, 4));
		assertEquals(LL7.remDoubles(), SL.cons(4, 3, 2, 1));
		assertEquals(LL8.remDoubles(), SL.cons(4, 3, 1, 2));
	}
	@Test
	public void testRemDoublesSorted() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);

		assertEquals(LL4.remDoublesSorted(), SL.cons(1, 3, 5, 7));
		assertEquals(LL6.remDoublesSorted(), SL.cons(1, 2, 3, 4));
	}
	@Test
	public void testRemDoublesSortedComparator() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		final SList<Integer> LL7 = SL.cons(4, 4, 4 ,4, 3, 3, 3, 2, 2, 1);
		Comparator<Integer> comp = (i1, i2) -> (i1 - i2);
		assertEquals(LL4.remDoublesSorted(comp), SL.cons(1, 3, 5, 7));
		assertEquals(LL6.remDoublesSorted(comp), SL.cons(1, 2, 3, 4));
		assertEquals(LL7.remDoublesSorted(comp), SL.cons(4, 3, 2, 1));
	}
	
	/* Mutators */
	@Test
	public void testReverse() {
		SList<String> LL1 = SL.cons("1", "2");
		SList<String> LL2 = SL.cons("2", "1");
		SList<String> LL3 = SL.cons("1", "2", "3");
		SList<String> LL4 = SL.cons("3", "2", "1");
		SList<SList<Integer>> LL5 = SL.cons(SL.cons(1, 2), SL.cons(1, 2), SL.cons(3, 1));
		SList<SList<Integer>> LL6 = SL.cons(SL.cons(3, 1), SL.cons(1, 2), SL.cons(1, 2));
		SList<String> LL7 = SL.cons("1", "2", "3", "2", "1");
		SList<String> LL8 = SL.empty();
		assertTrue(LL1.reverse().equals(LL2));
		assertTrue(LL3.reverse().equals(LL4));
		assertTrue(LL5.reverse().equals(LL6));
		assertTrue(LL5.reverse().equals(LL6));
		assertTrue(LL7.reverse().equals(LL7));
		assertTrue(LL8.reverse().equals(LL8));
	}
	@Test
	public void testSort() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
		final SList<Integer> LL7 = SL.cons(6, 2, 4, 1, 9, 0, 5, 3, 8, 7);
		final SList<Integer> LL8 = SL.cons(3, 4, 2, 3, 1, 2, 4, 4, 3, 4);
		assertEquals(LL7.sort(), SL.cons(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		assertEquals(LL6.sort(), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4, 4));
		assertEquals(LL4.sort(), SL.cons(1, 3, 5, 7));
		assertEquals(LL8.sort(), LL6);
	}
	@Test
	public void testSortComparator() {
		final Comparator<Integer> intComp = (i1, i2) -> (i1 - i2);
		final Comparator<Integer> revIntComp = (i1, i2) -> (i2 - i1);
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
		final SList<Integer> LL7 = SL.cons(6, 2, 4, 1, 9, 0, 5, 3, 8, 7);
		final SList<Integer> LL8 = SL.cons(3, 4, 2, 3, 1, 2, 4, 4, 3, 4);
		assertEquals(LL7.sort(intComp), SL.cons(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		assertEquals(LL6.sort(intComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4, 4));
		assertEquals(LL4.sort(intComp), SL.cons(1, 3, 5, 7));
		assertEquals(LL8.sort(intComp), LL6);
		assertEquals(LL7.sort(revIntComp), SL.cons(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).reverse());
		assertEquals(LL6.sort(revIntComp), SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4, 4).reverse());
		assertEquals(LL4.sort(revIntComp), SL.cons(1, 3, 5, 7).reverse());
		assertEquals(LL8.sort(revIntComp), LL6.reverse());
	}
	@Test
	public void testMerge() {
		final SList<Integer> LL1 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL2 = SL.cons(2, 4, 6, 8);
		final SList<Integer> LL3 = SL.cons(1, 3, 3, 5, 5, 7, 7, 7);
		final SList<Integer> LL4 = SL.cons(2, 3, 4, 5, 5, 6, 8, 8);
		assertEquals(LL1.merge(LL2), SL.cons(1, 2, 3, 4, 5, 6, 7, 8));
		assertEquals(LL1.merge(LL1), SL.cons(1, 1, 3, 3, 5, 5, 7, 7));
		assertEquals(LL1.merge(LL3), SL.cons(1, 1, 3, 3, 3, 5, 5, 5, 7, 7, 7, 7));
		assertEquals(LL2.merge(LL3), SL.cons(1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 7, 8));
		assertEquals(LL3.merge(LL4), SL.cons(1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7, 8, 8));
		assertEquals(LL2.merge(LL1), SL.cons(1, 2, 3, 4, 5, 6, 7, 8));
		assertEquals(LL3.merge(LL1), SL.cons(1, 1, 3, 3, 3, 5, 5, 5, 7, 7, 7, 7));
		assertEquals(LL3.merge(LL2), SL.cons(1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 7, 8));
		assertEquals(LL4.merge(LL3), SL.cons(1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7, 8, 8));

	}
	@Test
	public void testMergeComparator() {
		final Comparator<Integer> revIntComp = (i1, i2) -> (i2 - i1);
		final SList<Integer> LL1 = SL.cons(1, 3, 5, 7).reverse();
		final SList<Integer> LL2 = SL.cons(2, 4, 6, 8).reverse();
		final SList<Integer> LL3 = SL.cons(1, 3, 3, 5, 5, 7, 7, 7).reverse();
		final SList<Integer> LL4 = SL.cons(2, 3, 4, 5, 5, 6, 8, 8).reverse();
		assertEquals(LL1.merge(LL2, revIntComp), SL.cons(1, 2, 3, 4, 5, 6, 7, 8).reverse());
		assertEquals(LL1.merge(LL1, revIntComp), SL.cons(1, 1, 3, 3, 5, 5, 7, 7).reverse());
		assertEquals(LL1.merge(LL3, revIntComp), SL.cons(1, 1, 3, 3, 3, 5, 5, 5, 7, 7, 7, 7).reverse());
		assertEquals(LL2.merge(LL3, revIntComp), SL.cons(1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 7, 8).reverse());
		assertEquals(LL3.merge(LL4, revIntComp), SL.cons(1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7, 8, 8).reverse());
		assertEquals(LL2.merge(LL1, revIntComp), SL.cons(1, 2, 3, 4, 5, 6, 7, 8).reverse());
		assertEquals(LL3.merge(LL1, revIntComp), SL.cons(1, 1, 3, 3, 3, 5, 5, 5, 7, 7, 7, 7).reverse());
		assertEquals(LL3.merge(LL2, revIntComp), SL.cons(1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 7, 8).reverse());
		assertEquals(LL4.merge(LL3, revIntComp), SL.cons(1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7, 8, 8).reverse());
	}
	
	/* Higer Order Workers */
	@Test
	public void testForEach() {
		StringBuilder sb = new StringBuilder();
		L4.forEach((elem) -> sb.append(elem));
		assertEquals(sb.toString(), "1234");
		L0.forEach((elem) -> assertTrue(false));
	}
	@Test
	public void testFilter() {
		SList<String> LL1 = SL.cons("A", "AA", "BB", "CCC", "D", "EE", "XX", "YYY", "ZZZZ", "ZZ");
		assertEquals(LL1.filter((String elem) -> elem.length() == 2), SL.cons("AA", "BB", "EE", "XX", "ZZ"));
		SList<Integer> LL2 = SL.cons(1, 1, 2, 3, 5, 8, 13, 21, 34);
		assertEquals(LL2.filter((Integer elem) -> elem % 2 == 0), SL.cons(2, 8, 34));
		SList<Float> LL3 = SL.empty();
		assertEquals(LL3.filter((Float elem) -> elem == 3.1415), SL.empty());
	}
	
	@Test
	public void testMinBy() {
		SList<Integer> LL1 = SL.cons(2, 4, 5, 1, 4, 5, 3, 2, 1);
		assertEquals(LL1.minBy((elem) -> elem), SL.cons(1, 1));
		SList<Integer> LL2 = SL.cons(2, 4, 5, 1, 0, 5, 3, 2, 1);
		assertEquals(LL2.minBy((elem) -> elem), SL.cons(0));
		SList<String> LL3 = SL.cons("A", "BB", "CCC", "XXX", "FF", "Y", "OOO", "C", "PP");
		assertEquals(LL3.minBy((elem) -> elem.length()), SL.cons("A", "Y", "C"));
	}
	@Test
	public void testMaxBy() {
		SList<Integer> LL1 = SL.cons(2, 4, 5, 1, 4, 5, 3, 2, 1);
		assertEquals(LL1.maxBy((elem) -> elem), SL.cons(5, 5));
		SList<Integer> LL2 = SL.cons(2, 4, 5, 1, 6, 5, 3, 2, 1);
		assertEquals(LL2.maxBy((elem) -> elem), SL.cons(6));
		SList<String> LL3 = SL.cons("A", "BB", "CCC", "XXX", "FF", "Y", "OOO", "C", "PP");
		assertEquals(LL3.maxBy((elem) -> elem.length()), SL.cons("CCC", "XXX", "OOO"));
	}
	
	@Test
	public void testMap() {
		final String[] aas = { "", "A", "AA", "AAA", "AAAA", "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA" };
		SList<Integer> LL1 = SL.cons(1, 3, 5, 7, 9);
		assertEquals(LL1.map((Integer elem) -> elem * elem * elem), SL.cons(1, 27, 125, 343, 729));
		SList<Double> LL2 = new SListNil<Double>();
		assertEquals(LL2.map((Double elem) -> Math.sqrt(elem)), new SListNil<Double>());
		SList<Integer> LL3 = SL.cons(1, 3, 2, 4, 3, 5);
		assertEquals(LL3.map((Integer elem) -> aas[elem]), SL.cons("A", "AAA", "AA", "AAAA", "AAA", "AAAAA"));
	}
	@Test
	public void testMapIndex() {
		SList<String> LL1 = SL.cons("", "A", "AA", "AAA", "AAAA", "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA");
		assertEquals(LL1.map((String str, Integer index) -> str + index), SL.cons("0", "A1", "AA2", "AAA3", "AAAA4", "AAAAA5", "AAAAAA6", "AAAAAAA7", "AAAAAAAA8"));
		SList<String> LL2 = SL.empty();
		assertEquals(LL2.map((String str, Integer index) -> str + index), SL.empty());
	}
	@Test
	public void testMap2() {
		SList<Integer> LL1 = SL.cons(1, 1, 2, 3, 5, 8, 13, 21);
		SList<Integer> LL2 = SL.cons(1, 1, 2, 6, 24, 120, 720, 5040);
		SList<Integer> LL3 = SL.cons(4, 3, 2, 1);
		SList<Integer> LL4 = SL.empty();
		assertEquals(LL1.map2((Integer i1, Integer i2) -> (i1 + i2), LL2), SL.cons(2, 2, 4, 9, 29, 128, 733, 5061));
		assertEquals(LL1.map2((Integer i1, Integer i2) -> (i1 * i2), LL3), SL.cons(4, 3, 4, 3));
		assertEquals(LL3.map2((Integer i1, Integer i2) -> (i1 * i2), LL2), SL.cons(4, 3, 4, 6));
		assertEquals(LL1.map2((Integer i1, Integer i2) -> (i1 + i2), LL4), LL4);
		assertEquals(LL4.map2((Integer i1, Integer i2) -> (i1 + i2), LL1), LL4);
	}
	@Test
	public void testFlatMap() {
		final SList<Integer> L1 = SL.single(1);
		final SList<Integer> L2 = SL.pair(1, 2);
		final SList<Integer> L3 = SL.triple(1, 2, 3);
		final SList<Integer> L4 = SL.cons(1, 3, 5, 7);
		assertEquals(L3.flatMap((n) -> SL.indexesTill(n)), SL.cons(0, 0, 1, 0 ,1, 2));
		assertEquals(SL.cons(L2, L3, L4).flatMap((list) -> list), SL.cons(1, 2, 1, 2, 3, 1, 3, 5, 7));
		assertEquals(SL.cons(SL.empty(), SL.empty()).flatMap((list) -> list), SL.empty());
		assertEquals(SL.cons(SL.empty(), L1, SL.empty(), L3, SL.empty()).flatMap((list) -> list), SL.cons(1, 1, 2, 3));
	}
	@Test
	public void testFoldLeft() {
		SList<Integer> l1 = SL.cons(1, 2, 6, 24, 120);
		assertTrue(l1.foldLeft((Integer accu, Integer elem) -> accu - elem, 0) == -153);
		SList<Object> res = l1.foldLeft((SList<Object> oList, Integer elem) -> new SListImpl(oList, SL.cons(elem)), new SListNil<Object>());
		assertEquals(res.toString(), "SList(SList(SList(SList(SList(SList(), 1), 2), 6), 24), 120)");
	}
	
	@Test
	public void testFoldRight() {
		SList<Integer> l1 = SL.cons(1, 2, 6, 24, 120);
		assertTrue(l1.foldRight((Integer accu, Integer elem) -> accu - elem, 0) == 101);
		SList<Object> res = l1.foldRight((Integer elem, SList<Object> oList) -> new SListImpl(SL.cons(elem), oList), new SListNil<Object>());
		assertEquals(res.toString(), "SList(SList(1), SList(2), SList(6), SList(24), SList(120))");
		// this is the same as: SList(SList(1, SList(2, SList(6, SList(24, SList(120, SList()))))))
	}
	@Test
	public void testForAll() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertTrue(LL4.forAll((elem) -> elem % 2 == 1));
		assertTrue(LL5.forAll((elem) -> elem % 2 == 0));
		assertTrue(LL6.forAll((elem) -> elem >= 0));
		assertTrue(L0.forAll((elem) -> elem == 153));
	}
	@Test
	public void testExists() {
		final SList<Integer> LL4 = SL.cons(1, 3, 5, 7);
		final SList<Integer> LL5 = SL.cons(2, 4, 6, 8, 10, 12, 14);
		final SList<Integer> LL6 = SL.cons(1, 2, 2, 3, 3, 3, 4, 4, 4 ,4);
		assertTrue(LL4.exists((elem) -> elem % 2 == 1));
		assertTrue(LL5.exists((elem) -> elem == 12));
		assertFalse(LL6.exists((elem) -> elem < 0));
		assertFalse(L0.exists((elem) -> elem == 153));
	}
	@Test
	public void testFind() {
		Tuple2<TestClass, Integer> result = L10.find((elem) -> elem.equals(new TestClass(1, 1)));
		assertEquals(result._2, (Integer)3);
		result = L10.find((elem) -> (elem.i1 + elem.i2 == 0));
		assertEquals(result._2, (Integer)0);
	}
	@Test
	public void testFindMapped() {
	}


	@Test
	public void testSListBuilder() {
		SL.SListBuilder<Integer> builder = new SL.SListBuilder<>();
		builder.add(0).add(2).add(4).add(6).add(8);
		assertEquals(builder.build(), SL.cons(0, 2, 4, 6, 8));
		builder.add(13);
		assertEquals(builder.build(), SL.single(13));
		assertEquals(builder.build(), SL.empty());
	}
	
	@Test
	public void testSListRevBuilder() {
		SL.SListRevBuilder<Integer> builder = new SL.SListRevBuilder<>();
		builder.add(0).add(2).add(4).add(6).add(8);
		assertEquals(builder.build(), SL.cons(8, 6, 4, 2, 0));
		builder.add(13);
		assertEquals(builder.build(), SL.single(13));
		assertEquals(builder.build(), SL.empty());
	}
	
	
	
	@Test
	public void testEquals() {
		SList<String> l1 = SL.cons("1", "2", "3");
		SList<String> l2 = SL.cons("1", "2", "3");
		SList<String> l3 = SL.cons("1", "2", "4");
		SList<String> l4 = SL.cons("1", "2", "3", "4");
		SList<Integer> l5 = SL.cons(1, 2, 3, 4);
		SList<SList<String>> l6 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "EE"), SL.cons("AA", "MM", "YY"));
		SList<SList<String>> l7 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "EE"), SL.cons("AA", "MM", "YY"));
		SList<SList<String>> l8 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "ee"), SL.cons("AA", "MM", "YY"));
		SList<SList<String>> l9 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC"), SL.cons("AA", "MM", "YY"));
		SList<Integer> l10 = SL.cons(1, 2);
		SList<Integer> l11 = SL.cons(2, 1);
		SListNil<String> nil1 = new SListNil<>();
		SListNil<Integer> nil2 = new SListNil<>();
		
		assertTrue(l1.equals(l2));
		assertFalse(l1.equals(l3));
		assertFalse(l1.equals(l4));
		assertFalse(l1.equals(l5));
		assertFalse(l4.equals(l5));
		assertFalse(l3.equals(l1));
		assertFalse(l4.equals(l1));
		assertFalse(l5.equals(l1));
		assertFalse(l5.equals(l4));
		assertTrue(l6.equals(l7));
		assertFalse(l6.equals(l8));
		assertFalse(l6.equals(l9));
		assertFalse(l10.equals(l11));

		assertTrue(nil1.equals(nil2));
		assertFalse(l1.equals(nil1));
		assertFalse(l1.equals(nil2));
		assertFalse(nil1.equals(l1));
		assertFalse(nil2.equals(l1));
		
		assertEquals(l1.hashCode(), l2.hashCode());
		assertEquals(nil1.hashCode(), nil2.hashCode());
		
	}

	@Test
	public void testHashCode() {
		SList<String> l1 = SL.cons("1", "2", "3");
		SList<String> l2 = SL.cons("1", "2", "3");
		SListNil<String> nil1 = new SListNil<>();
		SListNil<Integer> nil2 = new SListNil<>();
		SList<SList<String>> l6 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "EE"), SL.cons("AA", "MM", "YY"));
		SList<SList<String>> l7 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "EE"), SL.cons("AA", "MM", "YY"));

		assertEquals(l1.hashCode(), l2.hashCode());
		assertEquals(nil1.hashCode(), nil2.hashCode());
		assertEquals(l6.hashCode(), l7.hashCode());
	}
	
	@Test
	public void testToString() {
		SListNil<String> l1 = new SListNil<>();
		SList<String> l2 = SL.cons("A");
		SList<String> l3 = SL.cons("A", "B");
		SList<String> l4 = SL.cons("A", "B", "C");
		SList<String> l5 = SL.cons("1", "2", "3");
		SList<Integer> l6 = SL.cons(1, 2, 3);
		SList<SList<String>> l7 = SL.cons(SL.cons("AA", "BB", "CC"), SL.cons("AA", "CC", "EE"), SL.cons("AA", "MM", "YY"));
		assertTrue(l1.toString().equals("SList()"));
		assertTrue(l2.toString().equals("SList(A)"));
		assertTrue(l3.toString().equals("SList(A, B)"));
		assertTrue(l4.toString().equals("SList(A, B, C)"));
		assertTrue(l5.toString().equals(l6.toString()));
		assertTrue(l7.toString().equals("SList(SList(AA, BB, CC), SList(AA, CC, EE), SList(AA, MM, YY))"));
	}
	
	
	
	@Test
	// will not run with very long lists because the Java compiler does no tail call optimization
	public void testStressMap() {
		
		SList<Integer> L1 = SL.empty();
		for (int i = 0; i < 1000000; i++) {
			L1 = SL.cons(i, L1);
		}
		
		SList<Integer> dummy;
		
		long start = new Date().getTime();
		
		for (int j = 0; j < 100; j++) {
			dummy = SL.map(L1, (k) -> k);
		}

		long end = new Date().getTime();
		
		System.out.println("ITER TIME IN MS " + (end - start));
		
	}
	
	
	@Test
	public void testIterator() {
		SList<String> l1 = SL.cons("AA", "BBB", "CCCC", "DDDD", "EEE", "FF");
		StringBuilder sb = new StringBuilder();
		for (String str : l1) {
			sb.append(str);
		}
		assert sb.toString().equals("AABBBCCCCDDDDEEEFF");
		sb = new StringBuilder();
		SList<String> l2 = SL.cons();
		for (String str : l2) {
			sb.append(str);
		}
		assert sb.toString().equals("");
	}
	
	
}

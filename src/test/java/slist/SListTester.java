package slist;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import slist.SList;
import slist.SListNil;

public class SListTester {

	@Test
	public void testEquals() {
		SList<String> l1 = SList.cons("1", "2", "3");
		SList<String> l2 = SList.cons("1", "2", "3");
		SList<String> l3 = SList.cons("1", "2", "4");
		SList<String> l4 = SList.cons("1", "2", "3", "4");
		SList<Integer> l5 = SList.cons(1, 2, 3, 4);
		SList<SList<String>> l6 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "EE"), SList.cons("AA", "MM", "YY"));
		SList<SList<String>> l7 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "EE"), SList.cons("AA", "MM", "YY"));
		SList<SList<String>> l8 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "ee"), SList.cons("AA", "MM", "YY"));
		SList<SList<String>> l9 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC"), SList.cons("AA", "MM", "YY"));
		SList<Integer> l10 = SList.cons(1, 2);
		SList<Integer> l11 = SList.cons(2, 1);
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
		SList<String> l1 = SList.cons("1", "2", "3");
		SList<String> l2 = SList.cons("1", "2", "3");
		SListNil<String> nil1 = new SListNil<>();
		SListNil<Integer> nil2 = new SListNil<>();
		SList<SList<String>> l6 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "EE"), SList.cons("AA", "MM", "YY"));
		SList<SList<String>> l7 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "EE"), SList.cons("AA", "MM", "YY"));

		assertEquals(l1.hashCode(), l2.hashCode());
		assertEquals(nil1.hashCode(), nil2.hashCode());
		assertEquals(l6.hashCode(), l7.hashCode());
	}
	
	@Test
	public void testToString() {
		SListNil<String> l1 = new SListNil<>();
		SList<String> l2 = SList.cons("A");
		SList<String> l3 = SList.cons("A", "B");
		SList<String> l4 = SList.cons("A", "B", "C");
		SList<String> l5 = SList.cons("1", "2", "3");
		SList<Integer> l6 = SList.cons(1, 2, 3);
		SList<SList<String>> l7 = SList.cons(SList.cons("AA", "BB", "CC"), SList.cons("AA", "CC", "EE"), SList.cons("AA", "MM", "YY"));
		assertTrue(l1.toString().equals("SList()"));
		assertTrue(l2.toString().equals("SList(A)"));
		assertTrue(l3.toString().equals("SList(A, B)"));
		assertTrue(l4.toString().equals("SList(A, B, C)"));
		assertTrue(l5.toString().equals(l6.toString()));
		assertTrue(l7.toString().equals("SList(SList(AA, BB, CC), SList(AA, CC, EE), SList(AA, MM, YY))"));
	}
	
	@Test
	public void testReverse() {
		SList<String> l1 = SList.cons("1", "2");
		SList<String> l2 = SList.cons("2", "1");
		SList<String> l3 = SList.cons("1", "2", "3");
		SList<String> l4 = SList.cons("3", "2", "1");
		SList<SList<Integer>> l5 = SList.cons(SList.cons(1, 2), SList.cons(1, 2), SList.cons(3, 1));
		SList<SList<Integer>> l6 = SList.cons(SList.cons(3, 1), SList.cons(1, 2), SList.cons(1, 2));
		SList<String> l7 = SList.cons("1", "2", "3", "2", "1");
		SList<String> l8 = SList.empty();
		assertTrue(l1.reverse().equals(l2));
		assertTrue(l3.reverse().equals(l4));
		assertTrue(l5.reverse().equals(l6));
		assertTrue(l5.reverse().equals(l6));
		assertTrue(l7.reverse().equals(l7));
		assertTrue(l8.reverse().equals(l8));
	}

	@Test
	public void testAppend() {
		SList<String> l1 = SList.cons("1", "2");
		SList<String> l2 = SList.cons("2", "1");
		SList<String> l3 = SList.cons("1", "2", "3");
		SList<String> l4 = SList.cons("3", "2", "1");
		SList<String> l5 = SList.empty();
		assertTrue(l1.append(l2).equals(SList.cons("1", "2", "2", "1")));
		assertTrue(l1.append(l5).equals(l1));
		assertTrue(l5.append(l1).equals(l1));
		assertTrue(l4.append(l3).toString().equals("SList(3, 2, 1, 1, 2, 3)"));
		assertTrue(l1.append(l2).toString().equals("SList(1, 2, 2, 1)"));
		assertTrue(l1.append(l5).toString().equals("SList(1, 2)"));
		assertTrue(l5.append(l1).toString().equals("SList(1, 2)"));
		assertTrue(l4.append(l3).toString().equals("SList(3, 2, 1, 1, 2, 3)"));
	}
	
	@Test
	public void testSize() {
		SList<String> l1 = new SListNil<>(); 
		SList<String> l2 = SList.cons("A");
		SList<String> l3 = SList.cons("A", "B");
		SList<String> l4 = SList.cons("A", "B", "C");
		SList<SList<String>> l5 = SList.cons(l3, l3);
		assertEquals(l1.size(), 0);
		assertEquals(l2.size(), 1);
		assertEquals(l3.size(), 2);
		assertEquals(l4.size(), 3);
		assertEquals(l5.size(), 2);
	}
	
	@Test
	public void testContains() {
		SList<String> l1 = new SListNil<>(); 
		SList<String> l2 = SList.cons("A");
		SList<String> l3 = SList.cons("A", "B");
		SList<String> l4 = SList.cons("A", "B", "C");
		SList<SList<String>> l5 = SList.cons(l3, l4);
		assertFalse(l1.contains("A"));
		assertTrue(l2.contains("A"));
		assertFalse(l2.contains("B"));
		assertTrue(l3.contains("A"));
		assertTrue(l3.contains("B"));
		assertFalse(l3.contains("C"));
		assertFalse(l5.contains(l2));
		assertTrue(l5.contains(l3));
		assertTrue(l5.contains(l4));
	}
	
	@Test
	public void testFilter() {
		SList<String> l1 = SList.cons("A", "AA", "BB", "CCC", "D", "EE", "XX", "YYY", "ZZZZ", "ZZ");
		assertEquals(l1.filter((String elem) -> elem.length() == 2), SList.cons("AA", "BB", "EE", "XX", "ZZ"));
		SList<Integer> l2 = SList.cons(1, 1, 2, 3, 5, 8, 13, 21, 34);
		assertEquals(l2.filter((Integer elem) -> elem % 2 == 0), SList.cons(2, 8, 34));
		SList<Float> l3 = SList.empty();
		assertEquals(l3.filter((Float elem) -> elem == 3.1415), new SListNil<Float>());
	}
	
	@Test
	public void testMap() {
		final String[] aas = { "", "A", "AA", "AAA", "AAAA", "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA" };
		SList<Integer> l1 = SList.cons(1, 3, 5, 7, 9);
		assertEquals(l1.map((Integer elem) -> elem * elem * elem), SList.cons(1, 27, 125, 343, 729));
		SList<Double> l2 = new SListNil<Double>();
		assertEquals(l2.map((Double elem) -> Math.sqrt(elem)), new SListNil<Double>());
		SList<Integer> l3 = SList.cons(1, 3, 2, 4, 3, 5);
		assertEquals(l3.map((Integer elem) -> aas[elem]), SList.cons("A", "AAA", "AA", "AAAA", "AAA", "AAAAA"));
	}
	
	@Test
	public void testMapIndex() {
		SList<String> l1 = SList.cons("", "A", "AA", "AAA", "AAAA", "AAAAA", "AAAAAA", "AAAAAAA", "AAAAAAAA");
		assertEquals(l1.mapIndex((String str, Integer index) -> str + index), SList.cons("0", "A1", "AA2", "AAA3", "AAAA4", "AAAAA5", "AAAAAA6", "AAAAAAA7", "AAAAAAAA8"));
		SList<String> l2 = SList.empty();
		assertEquals(l2.mapIndex((String str, Integer index) -> str + index), SList.empty());
	}
	
	@Test
	public void testMap2() {
		SList<Integer> l1 = SList.cons(1, 1, 2, 3, 5, 8, 13, 21);
		SList<Integer> l2 = SList.cons(1, 1, 2, 6, 24, 120, 720, 5040);
		SList<Integer> l3 = SList.cons(4, 3, 2, 1);
		SList<Integer> l4 = SList.empty();
		assertEquals(l1.map2((Integer i1, Integer i2) -> (i1 + i2), l2), SList.cons(2, 2, 4, 9, 29, 128, 733, 5061));
		assertEquals(l1.map2((Integer i1, Integer i2) -> (i1 * i2), l3), SList.cons(4, 3, 4, 3));
		assertEquals(l3.map2((Integer i1, Integer i2) -> (i1 * i2), l2), SList.cons(4, 3, 4, 6));
		assertEquals(l1.map2((Integer i1, Integer i2) -> (i1 + i2), l4), l4);
		assertEquals(l4.map2((Integer i1, Integer i2) -> (i1 + i2), l1), l4);
	}
	
	@Test
	// will not run with very long lists because the Java compiler does no tail call optimization
	public void testStressMap() {
		SList<Integer> l1 = SList.empty();
		for (int i = 0; i < 1000; i++) {
			l1 = SList.cons(i, l1);
		}
		System.out.println("Long Length: " + l1.size());
	}
	
	@Test
	public void testFoldLeft() {
		SList<Integer> l1 = SList.cons(1, 2, 6, 24, 120);
		assertTrue(l1.foldLeft((Integer accu, Integer elem) -> accu - elem, 0) == -153);
		SList<Object> res = l1.foldLeft((SList<Object> oList, Integer elem) -> new SListImpl(oList, SList.cons(elem)), new SListNil<Object>());
		assertEquals(res.toString(), "SList(SList(SList(SList(SList(SList(), 1), 2), 6), 24), 120)");
	}
	
	@Test
	public void testFoldRight() {
		SList<Integer> l1 = SList.cons(1, 2, 6, 24, 120);
		assertTrue(l1.foldRight((Integer accu, Integer elem) -> accu - elem, 0) == 101);
		SList<Object> res = l1.foldRight((Integer elem, SList<Object> oList) -> new SListImpl(SList.cons(elem), oList), new SListNil<Object>());
		assertEquals(res.toString(), "SList(SList(1), SList(2), SList(6), SList(24), SList(120))");
		// this is the same as: SList(SList(1, SList(2, SList(6, SList(24, SList(120, SList()))))))
	}
	
	@Test
	public void testIterator() {
		SList<String> l1 = SList.cons("AA", "BBB", "CCCC", "DDDD", "EEE", "FF");
		StringBuilder sb = new StringBuilder();
		for (String str : l1) {
			sb.append(str);
		}
		assert sb.toString().equals("AABBBCCCCDDDDEEEFF");
		sb = new StringBuilder();
		SList<String> l2 = SList.cons();
		for (String str : l2) {
			sb.append(str);
		}
		assert sb.toString().equals("");
	}
	
}

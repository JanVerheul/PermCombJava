package permcombjava;

import slist.SList;
import slist.SListNil;

public class ManualTester {

	private static SList<String> testList1 = new SListNil<String>();
	private static SList<String> testList2 = SList.cons("A", "B", "C", "D");
	private static Accumulator<String> accumulator = new Accumulator<>();


	public static void main(String[] args) {
        accumulator.clear();
        PermComb.repeatingComb(testList2, 3, accumulator);
        System.out.println("Aantal gegenereerd: " + accumulator.getSize());
        System.out.println("Aantal verschillende gegenereerd: " + accumulator.getSizeDistinct());
        
        SList<String> sList = SList.cons("AAA", "BBB", "CCC", "DDD", "EEE");
        
        System.out.println("XXX: " + sList);
        
        for (String str : sList) {
        	System.out.println(str);
        }
        
	}
}

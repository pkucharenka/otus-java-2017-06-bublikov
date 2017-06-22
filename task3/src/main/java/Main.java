import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vadim on 19.06.17.
 */
public class Main {
    public static void main(String[] args) {
        List<String> arrayList1 = new ArrayList<String>(); List<String> myArrayList1 = new MyArrayList<String>();
        arrayList1.add("1_1"); myArrayList1.add("1_1");
        arrayList1.add("1_2"); myArrayList1.add("1_2");
        arrayList1.add("1_3"); myArrayList1.add("1_3");
        List<String> arrayList2 = new ArrayList<String>(); List<String> myArrayList2 = new MyArrayList<String>();
        arrayList2.add("2_1"); myArrayList2.add("2_1");
        arrayList2.add("2_2"); myArrayList2.add("2_2");
        arrayList2.add("2_3"); myArrayList2.add("2_3");

        System.out.println("-------addAll-------------");
        List<String> arrayListAdd = new ArrayList<String>(); List<String> myArrayListAdd = new MyArrayList<String>();
        
        arrayListAdd.addAll( arrayList1 ); myArrayListAdd.addAll( myArrayList1 );
        arrayListAdd.addAll( arrayList2 ); myArrayListAdd.addAll( myArrayList2 );
        Collections.addAll(arrayListAdd, "3_1", "3_2", "3_3"); Collections.addAll(myArrayListAdd, "3_1", "3_2", "3_3");
        int size = Math.max( arrayListAdd.size(), myArrayListAdd.size() );
        for (int i=0; i<size; i++) {
            System.out.println("ArrayList: " + arrayListAdd.get(i) + "   MyArrayList: " + myArrayListAdd.get(i));
        }

        System.out.println("-------copy-------------");
        List<String> arrayListCopy = new ArrayList<String>( arrayList1 ); List<String> myArrayListCopy = new MyArrayList<String>( myArrayList1 );
        Collections.copy(arrayListCopy, arrayList2); 
        Collections.copy(myArrayListCopy, myArrayList2);
        size = Math.max( arrayListCopy.size(), myArrayListCopy.size() );
        for (int i=0; i<size; i++) {
            System.out.println("ArrayList: " + arrayListCopy.get(i) + "   MyArrayList: " + myArrayListCopy.get(i));
        }

        System.out.println("-------sort-------------");
        Collections.sort(arrayListAdd, new Comparator<String>() {
            public int compare(String o1, String o2) {
                int i1 = Integer.parseInt(o1.substring(0,1) + Integer.parseInt(o1.substring(2,3)) );
                int i2 = Integer.parseInt(o2.substring(0,1) + Integer.parseInt(o2.substring(2,3)) );
                if (i1==i2) {
                    return 0;
                } else {
                    return ( (i1>i2) ? -1 : 1 );
                }
            }
        });
        Collections.sort(myArrayListAdd, new Comparator<String>() {
            public int compare(String o1, String o2) {
                int i1 = Integer.parseInt(o1.substring(0,1) + Integer.parseInt(o1.substring(2,3)) );
                int i2 = Integer.parseInt(o2.substring(0,1) + Integer.parseInt(o2.substring(2,3)) );
                if (i1==i2) {
                    return 0;
                } else {
                    return ( (i1>i2) ? -1 : 1 );
                }
            }
        });
        size = Math.max( arrayListAdd.size(), myArrayListAdd.size() );
        for (int i=0; i<size; i++) {
            System.out.println("ArrayList: " + arrayListAdd.get(i) + "   MyArrayList: " + myArrayListAdd.get(i));
        }

    }
}

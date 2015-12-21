package girlsofsteel.objects;

import edu.wpi.first.wpilibj.util.SortedVector;

public class MapDoubleComparator implements SortedVector.Comparator {

    public int compare(Object object1, Object object2) {
        MapDouble o1 = (MapDouble) object1;
        MapDouble o2 = (MapDouble) object2;
        return o1.compare(o2);
    }
   
}

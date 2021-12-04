package edu.wpi.first.wpilibj.util;

import java.util.Vector;

@SuppressWarnings("PMD")
public class SortedVector<E> extends Vector<E> {
    public interface Comparator {
        int compare(Object object1, Object object2);
    }

    private final Comparator m_comparator;

    public SortedVector(Comparator comparator) {
        m_comparator = comparator;
    }

    @Override
    public synchronized void addElement(E element) {
        int highBound = size();
        int lowBound = 0;
        while (highBound - lowBound > 0) {
            int index = (highBound + lowBound) / 2;
            int result = m_comparator.compare(element, elementAt(index));
            if (result < 0) {
                lowBound = index + 1;
            } else if (result > 0) {
                highBound = index;
            } else {
                lowBound = index;
                highBound = index;
            }
        }
        insertElementAt(element, lowBound);
    }

    @SuppressWarnings("unchecked")
    public synchronized void sort() {
        Object[] array = new Object[size()];
        copyInto(array);
        removeAllElements();
        for (Object o : array) {
            addElement((E) o);
        }
    }
}

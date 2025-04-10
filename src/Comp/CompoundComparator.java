package Comp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompoundComparator<T> implements Comparator<T> {
    //tablica komparatorów ; od najważniejszego
    private final List<Object> _comparators = new ArrayList<>();

    public void addComparator(Comparator<T> comparator)
    {
        _comparators.add(comparator);
    }

    @SuppressWarnings("unchecked")
    public int compare(T left, T right) throws ClassCastException {
        int result = 0;
        for (Object obj : _comparators){
            Comparator<T> comp = (Comparator<T>) obj;
            result = comp.compare(left, right);

            if(result!=0){
                break;
            }
        }
        return result;
    }
}
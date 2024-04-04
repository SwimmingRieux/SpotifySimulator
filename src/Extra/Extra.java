package Extra;

import java.util.ArrayList;

public class Extra {
    public static <T> int findIndxOf(ArrayList<T> list, T t)
    {
        int tmpIndx = 0;
        for(T tmp : list)
        {
            if(list.equals(t)) break;
            tmpIndx++;
        }
        if(tmpIndx == list.size()) return -1;
        return tmpIndx;
    }
}

package top.onehundred.android.onekit.kits;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 列表工具包
 */
public class ListKit {

    private static ListKit listKit;
    public static ListKit getInstance(){
        if(listKit == null){
            synchronized (ListKit.class){
                if(listKit == null){
                    listKit = new ListKit();
                }
            }
        }
        return listKit;
    }

    /**
     * 删除列表中的重复项
     *
     * @param list 操作的列表
     * @return 操作完成后的列表，会返回list本身
     */
    public List removeDuplicate(List list) {
        Set set = new LinkedHashSet();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public <T> List<T> array2List(T[] array){
        List<T> list = Arrays.asList(array);
        return list;
    }

    public <T> T[] list2Array(List<T> list){
        if(list == null){
            return null;
        }
        return (T[]) list.toArray();
    }


}

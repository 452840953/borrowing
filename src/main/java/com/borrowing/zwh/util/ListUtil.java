package com.borrowing.zwh.util;

import com.borrowing.zwh.entity.Book;

import java.util.List;

public class ListUtil {
    /**
     * 使用时，务必保证每个对象中实现了equals()方法。
     * 如果是自己写的类，比如，Dog，Cat这些的，请重写Object中的equals方法！
     *
     * @param aList 左右顺序无所谓
     * @param bList 左右顺序无所谓
     * @return 尽可能避免相同的情况
     */
    public static boolean equals(List<Book> aList, List<Book> bList) {
        int n = aList.size();
        int i = 0;
        while (n-- != 0) {
            if (!aList.get(i).getName().equals(bList.get(i).getName())){
                if (!aList.get(i).getCt().equals(bList.get(i).getCt())){
                    return false;
                }
            }
            i++;
        }
        return true;
    }
}

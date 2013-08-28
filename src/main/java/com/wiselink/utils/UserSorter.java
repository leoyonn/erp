/**
 * UserSorter.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-8-28 下午11:51:37
 */
package com.wiselink.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wiselink.model.user.UserCard;
import com.wiselink.result.Checked;

/**
 * @author leo
 */
public enum UserSorter {
    ByDeptId(new Comparator<Checked<UserCard>>() {
        @Override
        public int compare(Checked<UserCard> u1, Checked<UserCard> u2) {
            int c = u1.data.corp.compareTo(u2.data.corp);
            if (c == 0) {
                c = u1.data.dept.compareTo(u2.data.dept);
                if (c == 0) {
                    c = u1.data.id.compareTo(u2.data.id);
                }
            }
            return c;
        }
    });
    
    UserSorter(Comparator<?> comparator) {
        this.comparator = comparator;
    }
    
    private Comparator<?> comparator;

    @SuppressWarnings("unchecked")
    public static List<Checked<UserCard>> sort(List<Checked<UserCard>> list, UserSorter sorter) {
        Collections.sort(list, (Comparator<Checked<UserCard>>) sorter.comparator);
        return list;
    }
}

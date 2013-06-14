/**
 * Op.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-6-11 下午3:55:52
 */
package com.wiselink.model.role;

import net.sf.json.JSONArray;

import com.wiselink.utils.Utils;

/**
 * func-role 对应的操作
 * @author leo
 */
public class Op {
    /**
     * 定义功能操作模块，每个操作都属于一个模块，一个模块中有多种操作。
     * @author leo
     */
    public static class Modules {
        
        /**
         * 用户相关的模块
         * @author leo
         */
        public static enum User {
            UserAdd(000, "添加用户"),
            UserDel(001, "删除用户"),
            UserMod(002, "修改用户");

            public final  int code;
            public final String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (User v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            User(int code, String desc) {
                this.code = code;
                this.desc = desc;
                json = Utils.buildEnumJson(code, name(), desc);
            }
            public static String allAsJson() {
                return ALL;
            }
        }
        
        /**
         * 耗材相关的模块
         * @author leo
         */
        public static enum Mat {
            MatApply(010, "耗材申领"),
            MatIn(011, "耗材入库"),
            MatOut(012, "耗材出库"),
            MatMan(013, "耗材管理"),
            BuyMan(014, "采购管理");

            public final int code;
            public final String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (Mat v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            Mat(int code, String desc) {
                this.code = code;
                this.desc = desc;
                json = Utils.buildEnumJson(code, name(), desc);
            }
            public static String allAsJson() {
                return ALL;
            }
        }

        /**
         * 指标相关的模块
         * @author leo
         */
        public static enum Aim {
            AimMan(020, "指标管理"),
            AimPlan(021, "指标申报");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (Aim v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            Aim(int code, String desc) {
                this.code = code;
                this.desc = desc;
                json = Utils.buildEnumJson(code, name(), desc);
            }
            public static String allAsJson() {
                return ALL;
            }
        }
        /**
         * 报表相关的模块
         * @author leo
         */
        public static enum Report {
            Statistics(050, "统计报表");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (Report v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            Report(int code, String desc) {
                this.code = code;
                this.desc = desc;
                json = Utils.buildEnumJson(code, name(), desc);
            }
            public static String allAsJson() {
                return ALL;
            }
        }

    /**
            Approval(0, "审批审核"),
            Apply(1, "耗材申领"),
            Statistics(2, "统计报表");
            ResOp(0, "耗材管理"),
            Statistic(1, "统计报表");
            Plan(0, "计划申报"),
            QuotMan(1, "询报价单管理"),
            BuyMan(2, "采购管理"),
            PayMan(3, "财务付款管理"),
            AimMan(4, "耗材指标管理"),
            Statistics(5, "统计报表");
            ApplyMan(0, "耗材领用管理"),
            Eval(1, "使用评价"),
            Statistics(2, "统计报表");
            QuotMan(0, "询报价管理"),
            SupMan(1, "供货管理"),
            PayMan(2, "财务付款管理"),
            Statistics(3, "统计报表"),
            SysMan(4, "系统维护");
            SysMan(1, "耗材申领"),
            Statistics(2, "统计报表");

     */
    }
}

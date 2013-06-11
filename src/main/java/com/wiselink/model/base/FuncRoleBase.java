/**
 * FuncRole.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-5-4 下午4:00:06
 */
package com.wiselink.model.base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 功能角色定义
 * @author leo
 */
@Deprecated
public enum FuncRoleBase {
    // TODO: + name
    Leader(0, "技术部领导", Actions.Leader.allAsJson()),
    ResOper(1, "耗材管理员", Actions.ResOper.allAsJson()),
    BuyOper(2, "采购管理员", Actions.BuyOper.allAsJson()),
    ResUser(3, "耗材使用人", Actions.ResUser.allAsJson()),
    SupOper(4, "供货商经办人", Actions.SupOper.allAsJson()),
    SysOper(5, "系统管理员", Actions.SysOper.allAsJson());

    public final int code;
    public final String desc;
    public final String actions; // actions in json-format
    public final String json;
    private static final String ALL;
    static {
        JSONArray allJson = new JSONArray();
        for (FuncRoleBase v: values()) {
            allJson.add(v.json);
        }
        ALL = allJson.toString();
    }
    
    public static String allAsJson() {
        return ALL;
    }
    
    FuncRoleBase(int code, String desc, String actions) {
        this.code = code;
        this.desc = desc;
        this.actions = actions;
        JSONObject j = new JSONObject();
        j.put("code", code);
        j.put("name", name());
        j.put("desc", desc);
        j.put("actions", actions);
        json = j.toString();
    }

    /**
     * defines actions of FuncRole with same name.
     * @author leo
     */
    public static class Actions {

        /**
         * 功能角色：技术部领导 可使用的action
         * @author leo
         */
        public static enum Leader {
            Approval(0, "审批审核"),
            Apply(1, "耗材申领"),
            Statistics(2, "统计报表");

            public final  int code;
            public final String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (Leader v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            Leader(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }

        /**
         * 功能角色：耗材管理员 可使用的action
         * @author leo
         */
        public static enum ResOper {
            ResOp(0, "耗材管理"),
            Statistic(1, "统计报表");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (ResOper v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            ResOper(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }
        /**
         * 功能角色：采购管理员 可使用的action
         * @author leo
         */
        public static enum BuyOper {
            Plan(0, "计划申报"),
            QuotMan(1, "询报价单管理"),
            BuyMan(2, "采购管理"),
            PayMan(3, "财务付款管理"),
            AimMan(4, "耗材指标管理"),
            Statistics(5, "统计报表");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (BuyOper v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            BuyOper(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }

        /**
         * 功能角色：耗材使用人 可使用的action
         * @author leo
         */
        public static enum ResUser {
            ApplyMan(0, "耗材领用管理"),
            Eval(1, "使用评价"),
            Statistics(2, "统计报表");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (ResUser v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            ResUser(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }
        /**
         * 功能角色：供货商经办人 可使用的action
         * @author leo
         */
        public static enum SupOper {
            QuotMan(0, "询报价管理"),
            SupMan(1, "供货管理"),
            PayMan(2, "财务付款管理"),
            Statistics(3, "统计报表"),
            SysMan(4, "系统维护");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (SupOper v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            SupOper(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }
        /**
         * 耗材管理员功能角色可有的action
         * @author leo
         */
        public static enum SysOper {
            SysMan(1, "耗材申领"),
            Statistics(2, "统计报表");

            public int code;
            public String desc;
            public final String json;
            private static final String ALL;
            static {
                JSONArray allJson = new JSONArray();
                for (SysOper v: values()) {
                    allJson.add(v.json);
                }
                ALL = allJson.toString();
            }

            SysOper(int code, String desc) {
                this.code = code;
                this.desc = desc;
                JSONObject j = new JSONObject();
                j.put("code", code);
                j.put("name", name());
                j.put("desc", desc);
                json = j.toString();
            }
            public static String allAsJson() {
                return ALL;
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(FuncRoleBase.allAsJson());
    }
}

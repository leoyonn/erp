/**
 * UtDAO.java
 * [CopyRight]
 * @author leo [leoyonn@gmail.com]
 * @date 2013-7-21 上午11:01:25
 */
package com.wiselink.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.wiselink.model.Ut;

/**
 * 
 * @author leo
 */
@DAO
public interface UtDAO {
    @SQL("INSERT INTO PICC.UT (id, name, age, tel) VALUES(:id, :name, :age, :tel)")
    boolean add(@SQLParam("id") String id, @SQLParam("name") String name, 
            @SQLParam("age") int age, @SQLParam("tel") String tel);
    
    @SQL("INSERT INTO PICC.UT (id, name, age, tel) VALUES(:ut.id, :ut.name, :ut.age, :ut.tel)")
    boolean add(@SQLParam("ut") Ut ut);

    @SQL("UPDATE PICC.UT SET #if(:name != null && :name.length() > 0){name=:name, }"
            + "#if(:age > 0){age=:age, } #else{age=100,}" +
            "#if(:tel.length() > 0){tel=:tel} WHERE id=:id")
    boolean update(@SQLParam("id") String id, @SQLParam("name") String name, 
            @SQLParam("age") int age, @SQLParam("tel") String tel);

    @SQL("SELECT * FROM PICC.UT WHERE id=:id")
    Ut get(@SQLParam("id") String id);
    
    @SQL("SELECT * FROM PICC.UT WHERE #if(:ut.id != null && :ut.id.length() > 0){id=:ut.id AND }" +
    		"#if(:ut.name != null && :ut.name.length() > 0){name=:ut.name AND }" +
    		"#if(:ut.age > 0){age=:ut.age AND }" +
    		"#if(:ut.tel != null && :ut.tel.length() > 0){tel=:ut.tel AND }" +
    		"1=1")
    List<Ut> query(@SQLParam("ut") Ut ut);

    @SQL("DELETE FROM PICC.UT")
    void clear();
}

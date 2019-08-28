package com.teayork.module_main.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * author：pengzhixian on 2019-08-23 17:33
 * e-mail：759560522@qq.com
 */
@Entity
public class HotBean {

    @Id(autoincrement = true)//设置自增长
    private long id;

    @Index(unique = true)//设置唯一性
    private String searchNo;//人员编号


    
    private String name;
    private String link;
    private int order;
    private int visible;

    @Generated(hash = 1143035992)
    public HotBean(long id, String searchNo, String name, String link, int order,
            int visible) {
        this.id = id;
        this.searchNo = searchNo;
        this.name = name;
        this.link = link;
        this.order = order;
        this.visible = visible;
    }
    @Generated(hash = 1964254435)
    public HotBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getVisible() {
        return this.visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    public String getSearchNo() {
        return this.searchNo;
    }
    public void setSearchNo(String searchNo) {
        this.searchNo = searchNo;
    }
    public void setId(Long id) {
        this.id = id;
    }
}

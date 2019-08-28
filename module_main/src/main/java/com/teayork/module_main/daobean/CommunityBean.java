package com.teayork.module_main.daobean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author：pengzhixian on 2019-08-19 16:09
 * e-mail：759560522@qq.com
 */
@Entity
public class CommunityBean {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    @Index(unique = true)//设置唯一性
    private String perNo;//人员编号

    private String name; //昵称
    private String content; //内容
    private String date; //日期



    //用到了这个Convert注解，表明它们的转换类，这样就可以转换成String保存到数据库中了
    @Convert(columnType = String.class, converter = Photos_Converter.class)
    private List<String> photos;



    @Generated(hash = 183405556)
    public CommunityBean(Long id, String perNo, String name, String content,
            String date, List<String> photos) {
        this.id = id;
        this.perNo = perNo;
        this.name = name;
        this.content = content;
        this.date = date;
        this.photos = photos;
    }



    @Generated(hash = 435866697)
    public CommunityBean() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getPerNo() {
        return this.perNo;
    }



    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }



    public String getName() {
        return this.name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getContent() {
        return this.content;
    }



    public void setContent(String content) {
        this.content = content;
    }



    public String getDate() {
        return this.date;
    }



    public void setDate(String date) {
        this.date = date;
    }



    public List<String> getPhotos() {
        return this.photos;
    }



    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }




    

//    @Convert(columnType = String.class, converter = Temp_Converter.class)
//    private TempReport maxTemp;//实体类套实体类
//    public class Temp_Converter implements PropertyConverter<TempReport, String> {
//        @Override
//        public TempReport convertToEntityProperty(String databaseValue) {
//            return new Gson().fromJson(databaseValue, TempReport.class);
//        }
//
//        @Override
//        public String convertToDatabaseValue(TempReport entityProperty) {
//            return new Gson().toJson(entityProperty);
//        }
//    }



}

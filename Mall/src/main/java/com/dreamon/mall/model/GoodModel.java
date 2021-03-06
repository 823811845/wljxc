package com.dreamon.mall.model;

import com.dreamon.mall.base.BaseEntity;
import com.dreamon.mall.base.BaseException;
import com.dreamon.mall.base.BaseModel;
import com.dreamon.mall.db.dao.GoodDao;
import com.dreamon.mall.db.dao.GoodTypeDao;
import com.dreamon.mall.db.entity.Good;
import com.dreamon.mall.db.entity.GoodType;
import com.dreamon.mall.exception.DaoException;
import com.dreamon.mall.exception.IllegalArguementException;
import com.dreamon.mall.exception.OutException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qiumengsong on 2017/8/19.
 */
@Component
public class GoodModel extends BaseModel {

    @Resource
    private GoodTypeDao goodTypeDao;

    @Resource
    private GoodDao goodDao;

    /**
     * 添加商品种类
     * @param name
     * @param typeimg
     * @throws DaoException
     * @throws IllegalArguementException
     */
    public void addRootType(String name,int typeimg) throws OutException {
        try {
            goodTypeDao.addType(name,0,typeimg);
        } catch (BaseException e){
            e.printStackTrace();
            throw new OutException(GOODTYPE_ADD_FAIL,GOODTYPE_ADD_FAIL_STR);
        }

    }

    /**
     * 添加商品类型
     * @param name
     * @param parentid
     * @param typeimg
     * @throws OutException
     */
    public void addGoodType(String name,int parentid,int typeimg) throws OutException{
        try {
            goodTypeDao.addType(name, parentid, typeimg);
        } catch (BaseException e){
            e.printStackTrace();
            throw new OutException(GOODTYPE_ADD_FAIL,GOODTYPE_ADD_FAIL_STR);
        }
    }

    /**
     * 获取全部的类型
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public HashMap<String,Object> getAllType(int pageIndex, int pageSize){
        List<BaseEntity> list = goodTypeDao.getAll(GoodType.class,pageIndex,pageSize);
        HashMap<String,Object> result = new HashMap<String, Object>();
        ArrayList<HashMap<String,String>> content = new ArrayList<HashMap<String, String>>();
        for (BaseEntity entity : list) {
            GoodType goodType = (GoodType)entity;
            HashMap<String,String> line = new HashMap<String, String>();
            line.put("id",goodType.getId()+"");
            line.put("name",goodType.getName());
            line.put("img",goodType.getTypeimg()+"");
            content.add(line);
        }
        result.put("content",content);
        return result;
    }

    /**
     * 更改类型名称
     * @param typeid
     * @param name
     */
    public void changeTypeName(int typeid,String name) throws OutException{
        GoodType value = new GoodType();
        GoodType param = new GoodType();

        value.put("name",name);
        param.put("id",typeid);

        try {
            goodTypeDao.update(value,param);
        } catch (IllegalArguementException e){
            e.printStackTrace();
            throw new OutException(GOODTYPE_NAME_UPDATE_FAIL,GOODTYPE_NAME_UPDATE_FAIL_STR);
        }

    }

    /**
     * 删除商品种类
     * @param typeid
     */
    public void deleteType(int typeid){
        GoodType goodType = new GoodType();
        goodType.setId(typeid);
        goodTypeDao.delete(goodType);
    }


    /**
     * 添加一个商品
     * @param name
     * @param goodtype
     * @param store
     * @param guige
     * @param txm
     * @param viewPrice
     * @param inPrice
     * @param other
     * @param userid
     * @param sotreid
     * @param content
     * @param typeList
     * @param cpbh
     * @param pachage
     * @param color
     * @param uptime
     * @param lrl
     * @throws OutException
     */
    public void addGood(String name,int goodtype,int store,String guige,String txm,
                        int viewPrice,int inPrice,String other,int userid,
                        int sotreid,String content,String typeList,
                        String cpbh,String pachage,String color,Timestamp uptime,
                        double lrl) throws OutException{
        try {
            goodDao.addGood(name, goodtype, store, guige, txm, viewPrice, inPrice, other, userid, sotreid, content, typeList, cpbh, pachage, color, uptime, lrl);
        } catch (BaseException e){
            e.printStackTrace();
            throw new OutException(GOOD_ADD_FAIL,GOOD_ADD_FAIL_STR);
        }
    }


    public void deleteGood(int userid,int goodid) throws OutException{
        try {
            goodDao.deleteGood(goodid,userid);
        } catch (BaseException e){
            e.printStackTrace();
            throw new OutException(GOOD_DELETE_FAIL,GOOD_DELETE_FAIL_STR);
        }
    }

    public HashMap<String,Object> getAllGood(){
        List<BaseEntity> goods = goodDao.getAll(Good.class,0,0);
        List<HashMap<String,String>> content = new ArrayList<HashMap<String, String>>();
        for (BaseEntity entity :
                goods) {
            Good good = (Good)entity;
            content.add(good.toHashMap());
        }
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("content",content);
        return result;
    }

    public HashMap<String,Object> getGoodsByUserid(int userid){
        Good good = new Good();
        good.put("userid",userid);
        List<BaseEntity> goods = goodDao.getAll(good,0,0);
        //转换成json
        List<HashMap<String,String>> content = new ArrayList<HashMap<String, String>>();
        for (BaseEntity entity :
                goods) {
            good = (Good)entity;
            content.add(good.toHashMap());
        }
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("content",content);
        return result;
    }

}

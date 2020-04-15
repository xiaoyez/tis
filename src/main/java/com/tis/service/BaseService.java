package com.tis.service;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public abstract class BaseService<B, M extends Mapper<B>> {

    @Autowired
    protected M mapper;

    public B getByPrimaryKey(Object key) {
        B bean = mapper.selectByPrimaryKey(key);
        if (bean == null)
            return null;
        return bean;
    }

    public B get(B bean) {
        bean = mapper.selectOne(bean);
        return bean;
    }

    public List<B> gets(B bean) {
        List<B> beans = mapper.select(bean);
        if (beans == null)
            return null;
        return beans;
    }

    public List<B> getAll() {
        List<B> beans = mapper.selectAll();
        if (beans == null)
            return null;
        return beans;
    }

    protected Class<B> getBeanClass() {
        try {
            String name = this.getClass().getName();
            String className = name.substring(name.lastIndexOf("."), name.lastIndexOf("Service"));
            String packageName = name.substring(0, name.lastIndexOf("."));
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
            packageName += ".bean";
            className = packageName + className;
            Class<B> beanClass = (Class<B>) Class.forName(className);
            return beanClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected B createBeanObject() {
        try {
            return getBeanClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(B bean) {
        mapper.insertSelective(bean);
        return 0;
    }

    public void update(B bean) {

        mapper.updateByPrimaryKeySelective(bean);
    }

    public boolean delete(Object primaryKey) {
        mapper.deleteByPrimaryKey(primaryKey);
        return true;
    }

    protected List<B> getsByExample(Example example) {
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", 0);
        example.and(criteria);
        List<B> beans = mapper.selectByExample(example);
        return beans;
    }

    protected B getByExample(Example example) {
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", 0);
        example.and(criteria);
        B bean = mapper.selectOneByExample(example);
        return bean;
    }


}

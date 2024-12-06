package com.itheima.service;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class BrandService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 查询所有的功能(管理员)
     * @return
     */
    public List<Brand>selectAll(){
        //调用BrandMapper.selectAll()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.调用方法
        List<Brand> brands = mapper.selectAll();/*查询,不需要提交事务*/
        sqlSession.close();
        //System.out.println(brands);

        return brands;
    }


    /**
     * 模糊搜索
     * @return
     */
/*
    public List<Brand>selectByBrandName(String brandName){
        //调用BrandMapper.selectByBrandName()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        List<Brand> brands=new ArrayList<>();

        //获取并判断用户类型

        //4.调用方法
        //判断字符串为空、为空字符串或者只包含空白字符
        if (StringUtils.isBlank(brandName)){
            //为空时,查询所有
            brands = mapper.selectAll();
        }else{
            brands = mapper.selectByBrandName(brandName);
        }


        */
/*查询,不需要提交事务*//*

        sqlSession.close();

        return brands;
    }
*/
    public List<Brand> selectByBrandName(String brandName, String userRole) {
        // 打开 SqlSession
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        List<Brand> brands;
        try {
            if (StringUtils.isBlank(brandName)) {
                // 如果 brandName 为空，且是普通用户，则只查询启用的商品（status=1）
                if ("admin".equals(userRole)) {
                    brands = mapper.selectAll(); // 管理员可以查看所有商品
                } else {
                    brands = mapper.selectByStatus();
                   // System.out.println(brands);// 普通用户只查看启用商品
                }
            } else {
                // 如果 brandName 不为空，根据用户角色进行不同查询
                if ("admin".equals(userRole)) {
                    brands = mapper.selectByBrandName(brandName); // 管理员可以根据品牌名查询所有商品
                } else {
                    brands = mapper.selectActiveByBrandName(brandName, 1); // 普通用户只查看启用的品牌商品
                }
            }
        } finally {
            sqlSession.close();
        }

        return brands;
    }


    /**
     * 添加数据的功能
     */
    public void add(Brand brand){
        //调用BrandMapper.add()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.调用方法
        mapper.add(brand);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    /**
     * 根据id查询的功能
     */
    public Brand selectById(int id){
        //调用BrandMapper.selectById()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.调用方法
        Brand brand = mapper.selectById(id);

        //释放资源
        sqlSession.close();

        return brand;
    }


    /**
     * 修改数据的功能
     */
        public void update(Brand brand){
        //调用BrandMapper.update()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.调用方法
        mapper.update(brand);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

    }

    /**
     * 通过id删除数据的功能
     */
    public void deleteById(int id){
        //调用BrandMapper.deleteById()


        //2.获取SqlSession
        SqlSession sqlSession= factory.openSession();
        //3.获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //4.调用方法
        mapper.deleteById(id);

        //提交事务
        sqlSession.commit();

        //释放资源
        sqlSession.close();


    }

    /**
     *
     * 更新库存
     * @param brandId
     * @param quantity
     * @return
     */
/*    public boolean reduceStock(Integer brandId, Integer quantity) {
        try (SqlSession session = factory.openSession()) {
            BrandMapper brandMapper = session.getMapper(BrandMapper.class);

            // 调用 Mapper 更新库存
            int rowsUpdated = brandMapper.decreaseStock(brandId, quantity);

            if (rowsUpdated > 0) {
                session.commit(); // 提交事务
                return true;
            } else {
                session.rollback(); // 回滚事务
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/
    public boolean reduceStock(Integer brandId, Integer quantity) {
        try (SqlSession session = factory.openSession()) {
            BrandMapper brandMapper = session.getMapper(BrandMapper.class);

            // 调用 Mapper 更新库存
            int rowsUpdated = brandMapper.decreaseStock(brandId, quantity);

            if (rowsUpdated > 0) {
                // 获取更新后的品牌信息
                Brand brand = brandMapper.selectById(brandId);

                // 如果库存为0，更新状态为0
                if (brand.getNumber() == 0) {
                    brand.setStatus(0); // 设置为禁用状态
                    brandMapper.updateStatus(brand); // 更新品牌状态
                }

                session.commit(); // 提交事务
                return true;
            } else {
                session.rollback(); // 回滚事务
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void restoreStock(Integer brandId, Integer quantity) {
        SqlSession session = factory.openSession();
        BrandMapper brandMapper = session.getMapper(BrandMapper.class);
        brandMapper.restoreStock(brandId, quantity);
        session.commit();
    }

}

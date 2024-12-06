package com.itheima.mapper;

import com.itheima.pojo.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BrandMapper {

    /*查询所有*/
    @Select("select * from tb_brand;")/*sql语句简单,使用注解*/
    @ResultMap("brandResultMap")
    List<Brand>selectAll();

    /*根据商品名字模糊搜索*/
    @Select("SELECT * FROM tb_brand WHERE brand_name LIKE CONCAT('%', #{brandName}, '%')")
    @ResultMap("brandResultMap")
    List<Brand>selectByBrandName(String brandName);

    /*添加数据*/
    @Insert("insert into tb_brand values (null,#{brandName},#{companyName},#{ordered},#{description},#{status},#{price},#{number});")
    @ResultMap("brandResultMap")
    void add(Brand brand);

    /*修改数据:回显数据,修改数据*/
    /*根据id查询*/
    @Select("select * from tb_brand where id=#{id};")
    @ResultMap("brandResultMap")
    Brand selectById(int id);

    /*修改数据*/
    @Update("update tb_brand set brand_name=#{brandName}," +
            "company_name=#{companyName}," +
            "ordered=#{ordered}," +
            "description=#{description}," +
            "status=#{status}," +
            "price=#{price}," +
            "number=#{number} where id=#{id}")
    @ResultMap("brandResultMap")
    void update(Brand brand);

    /*删除数据,应该返回的是影响的行数,而不是实体对象*/
    @Update("delete from tb_brand where id=#{id};")
    void deleteById(int id);

   /* // 更新商品库存
    @Update("UPDATE tb_brand SET number = number - #{quantity} WHERE id = #{brandId}")
    void updateStock(Integer brandId, Integer quantity);*/

    //更新商品库存
    @Update("UPDATE tb_brand SET number = number - #{quantity} WHERE id = #{brandId} AND number >= #{quantity}")
    @ResultMap("brandResultMap")
    int decreaseStock(@Param("brandId") Integer brandId, @Param("quantity") Integer quantity);

    //用户查询
    @Select("SELECT * FROM tb_brand WHERE brand_name LIKE CONCAT('%', #{brandName}, '%') AND status = #{status}")
    @ResultMap("brandResultMap")
    List<Brand> selectActiveByBrandName(@Param("brandName") String brandName, @Param("status") Integer status);

    // 根据商品状态查询品牌
    @Select("SELECT * FROM tb_brand WHERE status = 1")
    @ResultMap("brandResultMap")
    List<Brand> selectByStatus();

    //更新商品状态
    // 更新商品状态
    @Update("UPDATE tb_brand SET status = #{status} WHERE id = #{id}")
    @ResultMap("brandResultMap")
    void updateStatus(Brand brand);

    // 恢复库存
    @Update("UPDATE tb_brand SET number = number + #{quantity} WHERE id = #{brandId}")
    @ResultMap("brandResultMap")
    void restoreStock(@Param("brandId") Integer brandId, @Param("quantity") Integer quantity);
}

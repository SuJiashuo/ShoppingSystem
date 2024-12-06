package com.itheima.mapper;

import com.itheima.pojo.Administer;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdministerMapper {
    /**
     * 根据用户名和密码查询用户对象
     * @param adname
     * @param adpassword
     * @return
     */
    @Select("select * from tb_administer where adname = #{adname} and adpassword = #{adpassword}")
    Administer select(@Param("adname") String adname, @Param("adpassword")  String adpassword);
}

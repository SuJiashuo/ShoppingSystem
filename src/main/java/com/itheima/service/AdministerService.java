package com.itheima.service;

import com.itheima.mapper.AdministerMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Administer;
import com.itheima.pojo.User;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class AdministerService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    //登录方法
    public Administer login(String adname, String adpassword) {
        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3.获取UserMapper
        AdministerMapper mapper = sqlSession.getMapper(AdministerMapper.class);
        //4.调用方法
        Administer administer = mapper.select(adname, adpassword);

        //释放资源
        sqlSession.close();

        return administer;
    }

}

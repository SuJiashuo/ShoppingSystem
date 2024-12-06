package com.itheima.service;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;


public class UserService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    //登录方法
    public User login(String username, String password) {
        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3.获取UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.调用方法
        User user = mapper.select(username, password);

        //释放资源
        sqlSession.close();

        return user;
    }

    //注册方法
    public boolean register(User user) {
        //2.获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //3.获取UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4.判断用户名是否有效 非空且存在
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            sqlSession.close();
            return false; // 用户名为空，返回 false
        }

        //5.判断用户名是否存在
        User u = mapper.selectByUsername(user.getUsername());
        if(u==null){
            //用户名不存在且非空,注册
            mapper.add(user);
            //提交事务
            sqlSession.commit();
        }

        //释放资源
        sqlSession.close();

        return u==null;
    }

    //查询所有的用户
    public List<User> getAllUsers(){
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> allUsers = mapper.getAllUsers();
        sqlSession.close();
        return allUsers;
    }
}

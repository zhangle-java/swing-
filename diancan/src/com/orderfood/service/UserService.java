package com.orderfood.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.orderfood.mapper.UserMapper;
import com.orderfood.pojo.User;
import com.orderfood.util.SqlSessionUtil;

public class UserService {
    /**
     * @param username 用户名
     * @param password 密码
     * @return int  0为管理员，1为普通永固，2为用户名或密码错误
     * @throws Exception
     * @throws
     * @Title: login
     * @Description: 登录
     */
    public int login(String username, String password) throws Exception {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<User> list = mapper.findUserByUsernameAndPassword(user);
        sqlSession.commit();
        sqlSession.close();
        if (null != list && list.size() > 0) {
            User user2 = list.get(0);
            return user2.getRole();
        }
        return 2;
    }

    public List<User> findUsers() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.findUsers();
        sqlSession.commit();
        sqlSession.close();
        return list;
    }

    public int updateUser(User user) throws Exception {
        // TODO Auto-generated method stub
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int id = 0;
        if (null != user.getId()) {
            mapper.updateByPrimaryKey(user);
            id = user.getId();
        } else {
            mapper.insert(user);
            id= user.getId();
        }

        sqlSession.commit();

        sqlSession.close();
        return id;
    }

    public void remove(User user) {
        try (SqlSession sqlSession = SqlSessionUtil.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.deleteByPrimaryKey(user.getId());
            sqlSession.commit();
            sqlSession.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


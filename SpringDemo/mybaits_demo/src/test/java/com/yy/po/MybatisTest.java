package com.yy.po;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @Author : YangY
 * @Description :  这里有一个贼大的坑，数据库的版本一定不要太高（当时引入的是mysql 6.02），报时差错误
 * @Time : Created in 16:14 2019/8/7
 */

public class MybatisTest {

    private SqlSessionFactory  sqlSessionFactory = null;

    @Before
    public void testInit(){
        //acquire sqlSessionFactory
        String mybatisConfigFile = "mybatis/sqlMapConfig.xml";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(mybatisConfigFile);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //execute relevant operation  CRUD
    @Test
    public void queryUserById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession);
        try {
            User user = sqlSession.selectOne("test1.queryUserById",1);
            System.out.println(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}

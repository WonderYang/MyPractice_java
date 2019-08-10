package com.yy.po;

import com.yy.mapper.IuserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @Author : YangY
 * @Description :  mapper代理来实现
 * @Time : Created in 8:28 2019/8/10
 */
public class Test3 {
    private SqlSessionFactory sqlSessionFactory = null;

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

    @Test
    public void queryUserById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //iuser即是一个代理对象
            IuserMapper iuser = sqlSession.getMapper(IuserMapper.class);
            User user = iuser.queryUserById(1);
            System.out.println(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

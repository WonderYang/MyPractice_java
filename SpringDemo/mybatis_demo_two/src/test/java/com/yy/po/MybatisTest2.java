package com.yy.po;

import com.yy.mapper.IuserMapper;
import com.yy.mapper.OrdersMapper;
import com.yy.pack.UserPack;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.TreeSet;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:00 2019/8/10
 */
public class MybatisTest2 {
    private SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void testInit(){
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
            OrdersMapper ordersMapper = sqlSession.getMapper(OrdersMapper.class);
            List<OrdersEx> treeSet = ordersMapper.queryOrdersToUser();
            System.out.println(treeSet);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
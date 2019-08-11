package com.yy.po;

import com.yy.mapper.IuserMapper;
import com.yy.pack.UserPack;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : YangY
 * @Description :  mapper代理来实现
 * @Time : Created in 8:28 2019/8/10
 */
public class TestMybatis {
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
    //测试的是IuserMapper中的queryUserBySN方法
    public void queryUserByIdTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //iuser即是一个代理对象
            IuserMapper iuser = sqlSession.getMapper(IuserMapper.class);
            UserEx userEx = new UserEx();
            userEx.setUsername("y");
            userEx.setSex('1');
            UserPack userPack = new UserPack();
            userPack.setUserEx(userEx);
            List<UserEx> list = iuser.queryUserBySN(null);
            //int res = iuser.queryUserCount(userPack);
            System.out.println(list);
            //System.out.println(res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void queryUserBySN_oneTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //iuser即是一个代理对象
            IuserMapper iuser = sqlSession.getMapper(IuserMapper.class);
            UserEx userEx = new UserEx();
            userEx.setSex('1');
            userEx.setUsername("y");
            UserPack userPack = new UserPack();
            userPack.setUserEx(userEx);
            List<UserEx> list = iuser.queryUserBySN_one(userPack);
            System.out.println(list);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    //通过别名来查询
    public void queryUserByAlias_two(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            IuserMapper iuser = sqlSession.getMapper(IuserMapper.class);
            List<User> list = iuser.queryUserByAlias_two();
            for(User user: list) {
                System.out.println(user);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Test
    //查询多个指定id的结果（即in子句的转换）
    /**
     * 注意，在测试的时候，我new了一个UserEx通过setter方法赋给了UserPack，查询下来始终为空
     * 因为new的UserEx导致会执行第一个if块，而，虽然没有设置UserEx的username和sex属性，但类的属性会
     * 初始化，username为String（是类），所以初始化为null，但sex是char类型，初始化为'\u0000'，所以还会执行那个if
     * 块，导致查询结果肯定为空；
     */
    public void queryUserByMultyIds() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IuserMapper iuserMapper = sqlSession.getMapper(IuserMapper.class);
        UserPack userPack = new UserPack();

        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(16);
        userPack.setUserIdList(list);
        try {
            List<User> list1 = iuserMapper.queryUserByMultyIds(userPack);
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.yy.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:20 2019/8/1
 */
public class JdbcTest {
    ApplicationContext context;
    JdbcTemplate jdbcTemplate;
    @Before
    public void testInit() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    @Test
    public void testJdbc() {
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        System.out.println(dataSource);
    }
    @Test
    public void testUpdate() {
        jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
        String sql = "update user set username=? where id=?";
        int res = jdbcTemplate.update(sql,"yangyun",1);
        System.out.println("有"+res+"行收到影响");
    }

    @Test
    public void testAdd() {
        jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
        //插入单个数据
//        String sql = "insert into user(username,password) values(?,?)";
//        jdbcTemplate.update(sql,"lsl","123");
        //插入多个数据
        String sql = "insert into user(username,password) values (?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{"zx","123"});
        list.add(new Object[]{"ycs","1234"});
        jdbcTemplate.batchUpdate(sql,list);
    }
}

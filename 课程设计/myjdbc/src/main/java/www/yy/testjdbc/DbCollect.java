package www.yy.testjdbc;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:35 2019/7/3
 */
public class DbCollect {
    static Connection getMyConnection() {
        String driveClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/management_system?characterEncoding=UTF8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
        String user = "root";
        String password = "yangyun199969";
        Connection con = null;
        try {
            //加载驱动
            Class.forName(driveClassName);
            //获取连接对象
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

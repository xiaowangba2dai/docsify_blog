```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {

    public void findStudent() {
        Connection conn = null; // 数据库连接对象
        Statement stmt = null; // 操作数据库，实现增删改查
        ResultSet rs = null;

        try {
            // 1. 加载 mysql 驱动程序

            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 连接数据的基本信息 url, username, password
            String url = "jdbc:mysql://localhost:3306/springdb";
            String username = "root";
            String password = "339339";

            // 3. 创建连接对象
            conn = DriverManager.getConnection(url, username, password);

            // 4. 操作数据库
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name, age FROM student");

            // 5. 如果有数据，rs.next()返回true
            while (rs.next()) {
                System.out.println(rs.getString("name") + ", " + rs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭资源
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new JdbcTest().findStudent();
    }
}

```

## 优点

代码简单很直观

好理解

## 缺点

需要创建很多对象

需要注册驱动

需要执行sql语句

结果转为对象

关闭资源




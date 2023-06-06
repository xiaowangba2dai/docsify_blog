## 第一个例子

1） 创建数据库表

2）新建maven项目

3）修改 pom.xml: 加入mybatis依赖, mysql驱动，junit，资源插件，

4）创建实体类，定义属性，属性名与列名保持一致

5）创建Dao接口，定义操作数据库方法

6）创建xml文件（mapper文件），写sql语句。

​	mybatis框架推荐把sql语句和java代码分开

​	mapper文件：定义和dao接口在同一目录，一个表一个mapper文件

7）创建mybatis的主配置文件（xml文件），一个，放在resourses目录下

​	定义创建连接实例的数据源对象

​	指定其他mapper文件的位置

8）测试

​	可以使用main方法

​	也可以使用junit访问数据库
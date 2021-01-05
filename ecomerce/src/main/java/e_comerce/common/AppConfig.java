package e_comerce.common;

public class AppConfig {

        //chứa các cấu hình project: các cấu hình sẽ không đổi
        public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // chứa đường dẫn đến thư viện jdbc
        public static final String URL_DATABASE = "jdbc:mysql://localhost:3306/stock"; // đường dẫn để kết nối đến schema
        public static final String USERNAME = "root";
        public static final String PASSWORD = "root"; // mật khẩu của mysql
    }



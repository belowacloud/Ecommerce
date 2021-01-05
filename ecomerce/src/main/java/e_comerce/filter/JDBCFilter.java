package e_comerce.filter;

import e_comerce.Connection.MyConnection;
import e_comerce.Connection.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {

    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig fConfig)  {

    }

    @Override
    public void destroy() {

    }

    // Kiểm tra mục tiêu của request hiện tại là 1 Servlet?
    private boolean needJDBC(HttpServletRequest request) {
        System.out.println("JDBC Filter");
        //
        // Servlet Url-pattern: /spath/*
        //
        // => /spath
        String servletPath = request.getServletPath();

        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }

        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();

        // Tập hợp tất cả các Servlet trong WebApp
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // Chỉ mở connection (kết nối) đối với các request có đường dẫn servlet,jsp

        if (this.needJDBC(req)) {

            System.out.println("Open Connection for: " + req.getServletPath());

            Connection connection = null;
            try {
                // Tạo đối tượng Connection kết nối database.
                connection = MyConnection.connectDB();
                // Sét tự động commit false, để chủ động điều khiển.
                connection.setAutoCommit(false);

                // Lưu trữ đối tượng Connection vào attribute của request.
                Utils.storeConnection(request, connection);

                // Cho phép request đi tiếp.
                // (Đi tới Filter tiếp theo hoặc đi tới mục tiêu).
                chain.doFilter(request, response);

                // Gọi phương thức commit() để hoàn thành giao dịch với DB.
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    MyConnection.rollBackConnection(connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                throw new ServletException();
            } finally {
                try {
                    MyConnection.closeConnection(connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // Với các request thông thường (image,css,html,..)
        // không cần mở connection.
        else {
            // Cho phép request đi tiếp.
            // (Đi tới Filter tiếp theo hoặc đi tới mục tiêu).
            chain.doFilter(request, response);
        }

    }
}
package e_comerce.filter;


import e_comerce.Connection.DBUtils;
import e_comerce.Connection.Utils;
import e_comerce.beans.AdminAccount;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



    @WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
    public class CookieFilter implements Filter {

        public CookieFilter() {
        }

        @Override
        public void init(FilterConfig fConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();

            AdminAccount userInSession = Utils.getLoginedUser(session);
            //
            if (userInSession != null) {
                session.setAttribute("COOKIE_CHECKED", "CHECKED");
                chain.doFilter(request, response);
                return;
            }

            // Connection đã được tạo trong JDBCFilter.
            Connection connection = Utils.getStoredConnection(request);

            // Cờ (flag) để kiểm tra Cookie.
            String checked = (String) session.getAttribute("COOKIE_CHECKED");
            if (checked == null && connection != null) {
                String username = Utils.getUsernameInCookie(req);
                try {
                    AdminAccount user = DBUtils.findUser(connection, username);
                    Utils.storeLoginedUser(session, user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // Đánh dấu đã kiểm tra Cookie.
                session.setAttribute("COOKIE_CHECKED", "CHECKED");
            }

            chain.doFilter(request, response);
        }

    }


package e_comerce.controller.request;

import e_comerce.Connection.DBUtils;
import e_comerce.Connection.Utils;
import e_comerce.beans.AdminAccount;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(urlPatterns = { "/login" })
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    // Hiển thị trang Login.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forward tới trang /WEB-INF/views/loginView.jsp
        // (Người dùng không thể truy cập trực tiếp
        // vào các trang JSP đặt trong thư mục WEB-INF).
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/view/loginView.jsp");

        dispatcher.forward(request, response);

    }

    // Khi người nhập userName & password, và nhấn Submit.
    // Phương thức này sẽ được thực thi.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*String username = request.getParameter("userName");
        String password = request.getParameter("password");*/
        String username = "hoangthuha";
        String password = "123456";


        AdminAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            hasError = true;
            System.out.println( "Required username and password!");
        } else {
            Connection conn = Utils.getStoredConnection(request);
            try {
                // Tìm user trong DB.
                user = DBUtils.findUser(conn, username, password);

                if (user == null) {
                    hasError = true;
                    System.out.println("User Name or password invalid");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        // Trong trường hợp có lỗi,
        // forward (chuyển hướng) tới /WEB-INF/views/login.jsp
        if (hasError) {
            user = new AdminAccount();
            user.setUsername(username);
            user.setPassword(password);

            // Lưu các thông tin vào request attribute trước khi forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);

            // Forward (Chuyển tiếp) tới trang /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/view/loginView.jsp");

            dispatcher.forward(request, response);
            System.out.println("có lỗi xảy ra");
        }
        // Trường hợp không có lỗi.
        // Lưu thông tin người dùng vào Session.
        // Và chuyển hướng sang trang userInfo.
        else {
            System.out.println("đăng nhập thành công");
            HttpSession session = request.getSession();
            Utils.storeLoginedUser(session, user);


            // Redirect (Chuyển hướng) sang trang /userInfo.
            response.sendRedirect("http://localhost:8080/ecomerce_war/userInfor");
        }
    }

}
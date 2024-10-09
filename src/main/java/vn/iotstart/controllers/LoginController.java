package vn.iotstart.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstart.models.UserModel;
import vn.iotstart.service.IUserService;
import vn.iotstart.service.impl.UserServiceImpl;
import vn.iotstart.ultis.Constant;
	
@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession(false); 
	    if (session != null && session.getAttribute("account") != null) {
	        resp.sendRedirect(req.getContextPath() + "/waiting"); 
	        return; 
	    }

	    // Kiểm tra cookie
	    Cookie[] cookies = req.getCookies(); 
	    if (cookies != null) {
	        for (Cookie cookie : cookies) { 
	            if (cookie.getName().equals("username")) {
	                session = req.getSession(true); 
	                session.setAttribute("username", cookie.getValue()); 
	                resp.sendRedirect(req.getContextPath() + "/waiting");
	                return; 
	            }
	        } 
	    }
	    req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	    // mã hóa UTF-8
	    resp.setCharacterEncoding("UTF-8");
	    req.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html");

	    // lấy tham số từ view
	    String username = req.getParameter("uname");
	    String password = req.getParameter("psw");
	    String remember = req.getParameter("remember");

	    // Xử lý bài toán
	    String alertMsg = null;  // Chỉ set khi có lỗi
	    boolean isRememberMe = false;
	    if ("on".equals(remember)) {
	        isRememberMe = true;
	    }

	    if (username.isEmpty() || password.isEmpty()) {
	        alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
	    } else {
	        UserModel user = service.login(username, password);
	        if (user != null) {
	            HttpSession session = req.getSession(true);
	            session.setAttribute("account", user);
	            if (isRememberMe) {
	                saveRemeberMe(resp, username);
	            }
	            resp.sendRedirect(req.getContextPath() + "/waiting");
	            return;
	        } else {
	            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
	        }
	    }

	    if (alertMsg != null) {
	        req.setAttribute("alert", alertMsg);
	    }
	    req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
	}


	private void saveRemeberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}
}
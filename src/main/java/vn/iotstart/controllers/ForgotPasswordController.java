package vn.iotstart.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstart.models.UserModel;
import vn.iotstart.service.IUserService;
import vn.iotstart.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/forgotpassword"})
public class ForgotPasswordController extends HttpServlet{
		IUserService service = new UserServiceImpl();
		private static final long serialVersionUID = -5239443831057636103L;
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.getRequestDispatcher("/view/forgotpassword.jsp").forward(req, resp);
		}
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		    resp.setContentType("text/html");
		    resp.setCharacterEncoding("UTF-8");
		    req.setCharacterEncoding("UTF-8");
		    
		    String username = req.getParameter("username");
		    String email = req.getParameter("email");

		    UserModel user = service.findByUsername(username);

		    if (user != null && user.getEmail().equals(email)) {

		    req.getRequestDispatcher("/view/forgotpassword.jsp").forward(req, resp);
		    }
		}
}

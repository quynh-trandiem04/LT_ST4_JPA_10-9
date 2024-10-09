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

@WebServlet(name = "ProfileController", value = "/profile")
public class ProfileController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3752061204314698272L;
	/**
	 * 
	 */
	IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    	HttpSession session = request.getSession();
    	UserModel account = (UserModel) session.getAttribute("account");

    	String username = account.getUsername();
    	String image = account.getImage();
    	String fullname = account.getFullname();
    	String email = account.getEmail();
    	String phone = account.getPhone();

        UserModel user = userService.findByUsername(username);
        if (user == null) {
        	System.out.println(user);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    	request.setAttribute("image", image);
    	request.setAttribute("username", username);
    	request.setAttribute("fullname", fullname);
    	request.setAttribute("email", email);
    	request.setAttribute("phone", phone);
        request.getRequestDispatcher("/view/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get parameters from the request
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/profile?error=Passwords do not match");
            return;
        }

        UserModel user = userService.findByUsername(username);
        user.setPhone(phone);
        user.setFullname(fullname);
        user.setEmail(email);
        userService.update(user);

        userService.updatePassword(username, password);

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}

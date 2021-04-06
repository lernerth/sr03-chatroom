package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import services.LoginService;
import services.impl.LoginServiceImpl;

/**
 * Servlet implementation class UserManager
 */
@WebServlet("/UserManager")
public class UserManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String method = req.getParameter("method");

		if ("login".equals(method))
			login(req, resp);
		else if ("regist".equals(method))
			regist(req, resp);
		else {
			resp.getWriter().write("Requête inconnue");
		}

	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		String userLogin = req.getParameter("login");
		String userPwd = req.getParameter("pwd");
		String remember = req.getParameter("rememberMe");

		LoginService ls = new LoginServiceImpl();
		User u = ls.findUser(userLogin);
		if (u != null) {
			if (u.getPwd().equals(userPwd)) {
				resp.getWriter().write("success!");
				// 重定向到主页
				// resp.sendRedirect("");
			} else {
				req.setAttribute("msg", "Your login or password is not correct.");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
			// login n'existe pas
		} else {
			req.setAttribute("msg", "Login doesn't exist.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

	private void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		String userLogin = req.getParameter("login");
		String userPwd = req.getParameter("pwd");
		String userFName = req.getParameter("first name");
		String userLName = req.getParameter("last name");
		String userEmail = req.getParameter("email");
		String userGender = req.getParameter("gender");

		LoginService ls = new LoginServiceImpl();

		if (userLogin.equals("") || userPwd.equals("") || userFName.equals("") || userLName.equals("")
				|| userEmail.equals("") || userGender.equals("")) {
			req.setAttribute("msg", "Please check your inputs");
			req.getRequestDispatcher("regist.jsp").forward(req, resp);
			// essayer de créer un login
		} else {
			User u = ls.findUser(userLogin);
			// login occupé
			if (u != null) {
				req.setAttribute("msg", "Login name already used!");
				req.getRequestDispatcher("regist.jsp").forward(req, resp);
			} else {
				//créer un utilisateur
				User newUser = new User();
				newUser.setLogin(userLogin);
				newUser.setFname(userFName);
				newUser.setLname(userLName);
				newUser.setGender(userGender);
				newUser.setPwd(userPwd);
				newUser.setEmail(userEmail);
				int rows = ls.addUser(newUser);
				if (rows > 0) {
					req.setAttribute("msg", "Login created successfully!");
					req.setAttribute("defaultLogin", "ss");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
				} else {
					req.setAttribute("msg", "User creation failed!");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
				}
			}
		}
	}
}

package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import services.DataService;
import services.impl.DataServiceImpl;

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
		else if ("logout".equals(method))
			logout(req, resp);
		else {
			resp.getWriter().write("Requ��te inconnue");
		}

	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		String userLogin = req.getParameter("login");
		String userPwd = req.getParameter("pwd");
		String remember = req.getParameter("rememberMe");

		DataService ds = new DataServiceImpl();
		User u = ds.findUser(userLogin);
		if (u != null) {
			if (u.getPwd().equals(userPwd)) {
				// cr��er Cookies
				if ("rememberMe".equals(remember)) {
					Cookie c = new Cookie("uid", u.getId() + "");
					c.setMaxAge(3 * 24 * 60 * 60);
					c.setPath("/");
					resp.addCookie(c);
				}
				// cr��er la session
				HttpSession hs = req.getSession();
				hs.setMaxInactiveInterval(15 * 60);
				hs.setAttribute("user", u);
				// acc��der �� la page d'accueil
				resp.sendRedirect("main.jsp");
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

		DataService ds = new DataServiceImpl();

		if (userLogin.equals("") || userPwd.equals("") || userFName.equals("") || userLName.equals("")
				|| userEmail.equals("") || userGender.equals("")) {
			req.setAttribute("msg", "Please check your inputs");
			req.getRequestDispatcher("regist.jsp").forward(req, resp);
			// essayer de cr��er un login
		} else {
			User u = ds.findUser(userLogin);
			// login occup��
			if (u != null) {
				req.setAttribute("msg", "Login name already used!");
				req.getRequestDispatcher("regist.jsp").forward(req, resp);
			} else {
				// cr��er un utilisateur
				User newUser = new User();
				newUser.setLogin(userLogin);
				newUser.setFname(userFName);
				newUser.setLname(userLName);
				newUser.setGender(userGender);
				newUser.setPwd(userPwd);
				newUser.setEmail(userEmail);
				int rows = ds.addUser(newUser);
				if (rows > 0) {
					req.setAttribute("msg", "Login created successfully!");
					req.setAttribute("defaultLogin", newUser.getLogin());
					req.getRequestDispatcher("login.jsp").forward(req, resp);
				} else {
					req.setAttribute("msg", "User creation failed!");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
				}
			}
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// supprimer cookie
		Cookie c = new Cookie("uid", null + "");
		c.setMaxAge(0);
		c.setPath("/");
		resp.addCookie(c);

		// supprimer session
		HttpSession hs = req.getSession(false);
		hs.removeAttribute("user");

		resp.sendRedirect("login.jsp");
	}
}

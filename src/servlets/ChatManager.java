package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Chat;
import models.User;
import services.DataService;
import services.impl.DataServiceImpl;

/**
 * Servlet implementation class ChatManager
 */
@WebServlet("/ChatManager")
public class ChatManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if ("create".equals(method))
			createRoom(req, resp);
		else if ("invite".equals(method))
			inviteUsers(req, resp);
		else if ("delete".equals(method))
			deleteRoom(req, resp);
		else {
			resp.getWriter().write("Requ¨ºte inconnue");
		}
	}

	private void createRoom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession hs = req.getSession();
		String roomName = req.getParameter("roomName");
		String desc = req.getParameter("desc");

		User u = (User) hs.getAttribute("user");
		DataService ds = new DataServiceImpl();

		if (roomName.equals("") || u == null) {
			req.setAttribute("msg", "Creation failed.");
			req.getRequestDispatcher("roomCreation.jsp").forward(req, resp);
		} else {
			Chat chat = ds.findChat(roomName);
			if (chat != null) {
				req.setAttribute("msg", "Name of room occupied!");
				req.getRequestDispatcher("roomCreation.jsp").forward(req, resp);
			} else {
				Chat newChat = new Chat();
				newChat.setName(roomName);
				newChat.setOwnerId(u.getId());
				newChat.setDesc(desc);
				int rows = ds.addChat(newChat, u.getId());
				if (rows > 0) {
					req.setAttribute("msg", "Room created successfully!");
					req.getRequestDispatcher("main.jsp").forward(req, resp);
				} else {
					req.setAttribute("msg", "Room created failed!");
					req.getRequestDispatcher("main.jsp").forward(req, resp);
				}
			}
		}
	}

	private void inviteUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession hs = req.getSession();
		String roomName = req.getParameter("roomName");
		String[] userIdsStr = req.getParameterValues("invitedUserIds");
		if(userIdsStr == null)
			return;
		int[] userIds = new int[userIdsStr.length];

		for (int i = 0; i < userIdsStr.length; i++) {
			userIds[i] = Integer.valueOf(userIdsStr[i]);
		}

		DataService ds = new DataServiceImpl();
		Chat chat = ds.findChat(roomName);

		if (userIds.length > 0 && !"".equals(roomName)) {
			if (chat != null) {
				int rows = ds.addUsersInChat(chat.getId(), userIds);
				if (rows > 0) {
					req.setAttribute("msg", "Invitations sent.");
					req.getRequestDispatcher("chatroom.jsp").forward(req, resp);
					return;
				}
			}
		}
		req.setAttribute("msg", "Invitations failed.");
		req.getRequestDispatcher("chatroom.jsp").forward(req, resp);

	}

	private void deleteRoom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		String roomName = req.getParameter("roomName");
		HttpSession hs = req.getSession();
		User u = (User) hs.getAttribute("user");
		DataService ds = new DataServiceImpl();
		if (u == null) {
			req.setAttribute("msg", "Failed to delete");
			req.getRequestDispatcher("main.jsp").forward(req, resp);
		} else {
			int uId = u.getId();
			if (ds.ifUserOwnChat(uId, roomName)) {
				if (ds.deleteChat(roomName)) {
					req.setAttribute("msg", "Room " + roomName + " Successfully deleted");
					req.getRequestDispatcher("main.jsp").forward(req, resp);
				} else {
					req.setAttribute("msg", "Room " + roomName + " Failed to delete");
					req.getRequestDispatcher("main.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute("msg", "Permission denied!");
				req.getRequestDispatcher("main.jsp").forward(req, resp);
			}
		}

	}

}

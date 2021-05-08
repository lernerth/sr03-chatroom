package services;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import models.Chat;
import models.User;
import services.DataService;
import services.impl.DataServiceImpl;

@ServerEndpoint(value = "/chatserver/{roomName}", configurator = ChatServer.EndpointConfigurator.class)
public class ChatServer {

	private static ChatServer singleton = new ChatServer();
	private static Hashtable<String, Set<Session>> rooms = new Hashtable<String, Set<Session>>();

	private ChatServer() {
	}

	/**
	 * Acquisition de notre unique instance ChatServer
	 */
	public static ChatServer getInstance() {
		return ChatServer.singleton;
	}

	/**
	 * Cette méthode est déclenchée ?chaque connexion d'un utilisateur.
	 */
	@OnOpen
	public void connect(Session session, @PathParam("roomName") String roomName) {
		DataService ds = new DataServiceImpl();
		Chat chat = null;
		session.getUserProperties().put("roomName", roomName);
		String login = session.getQueryString().substring("login=".length());
		session.getUserProperties().put("login", login);
		User u = ds.findUser(login);

		// login n'existe pas
		if (u == null) {
			sendErrMsg(session, "Permission denied!");
			return;
		}

		if (!rooms.containsKey(roomName)) {
			chat = ds.findChat(roomName);
			if (chat == null || !ds.isUserInChat(chat, u.getId())) {
				sendErrMsg(session, "Permission denied!");
				return;
			}

			Set<Session> room = new HashSet<>();
			room.add(session);
			rooms.put(chat.getName(), room);
		} else {
			rooms.get(roomName).add(session);
		}
		boardcast(roomName, login + " joined " + roomName);

	}

	/**
	 * Cette méthode est déclenchée ?chaque déconnexion d'un utilisateur.
	 */
	@OnClose
	public void disconnect(Session session) {
		String roomName = (String) session.getUserProperties().get("roomName");
		String login = (String) session.getUserProperties().get("login");
		rooms.get(roomName).remove(session);
		boardcast(roomName, login + " left " + roomName);
	}

	/**
	 * Cette méthode est déclenchée en cas d'erreur de communication.
	 */
	@OnError
	public void onError(Throwable error) {
		System.out.println("Error: " + error.getMessage());
	}

	/**
	 * Cette méthode est déclenchée ?chaque réception d'un message utilisateur.
	 */
	@OnMessage
	public void receiveMessage(String message, Session session) {
		String roomName = (String) session.getUserProperties().get("roomName");
		String login = (String) session.getUserProperties().get("login");
		String fullMessage = login + " >>> " + message;

		boardcast(roomName, fullMessage);
	}

	/**
	 * Une méthode privée, spécifique ?notre exemple. Elle permet l'envoie d'un message
	 * aux participants de la discussion.
	 */
	private void boardcast(String roomName, String fullMessage) {
		// Affichage sur la console du server Web.
		System.out.println(fullMessage);

		// On envoie le message ?tout le monde.
		for (Session session : rooms.get(roomName)) {
			try {
				session.getBasicRemote().sendText(fullMessage);
			} catch (Exception exception) {
				System.out.println("ERROR: cannot send message to " + session.getId());
			}
		}
	}

	/**
	 * Envoyer ¨¤ l'utilisateur current un message erreur
	 * 
	 * @param session
	 * @param errMsg
	 */
	private void sendErrMsg(Session session, String errMsg) {
		// Affichage sur la console du server Web.
		System.out.println(errMsg);

		// On envoie le message ?tout le monde.
		try {
			session.getBasicRemote().sendText(errMsg);
		} catch (Exception exception) {
			System.out.println("ERROR: cannot send message to " + session.getId());
		}

	}

	/**
	 * Permet de ne pas avoir une instance différente par client. ChatServer est donc
	 * gérer en "singleton" et le configurateur utilise ce singleton.
	 */
	public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
		@Override
		@SuppressWarnings("unchecked")
		public <T> T getEndpointInstance(Class<T> endpointClass) {
			return (T) ChatServer.getInstance();
		}
	}
}
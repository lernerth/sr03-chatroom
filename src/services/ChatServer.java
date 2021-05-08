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
	 * Cette m�thode est d�clench�e ?chaque connexion d'un utilisateur.
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
	 * Cette m�thode est d�clench�e ?chaque d�connexion d'un utilisateur.
	 */
	@OnClose
	public void disconnect(Session session) {
		String roomName = (String) session.getUserProperties().get("roomName");
		String login = (String) session.getUserProperties().get("login");
		rooms.get(roomName).remove(session);
		boardcast(roomName, login + " left " + roomName);
	}

	/**
	 * Cette m�thode est d�clench�e en cas d'erreur de communication.
	 */
	@OnError
	public void onError(Throwable error) {
		System.out.println("Error: " + error.getMessage());
	}

	/**
	 * Cette m�thode est d�clench�e ?chaque r�ception d'un message utilisateur.
	 */
	@OnMessage
	public void receiveMessage(String message, Session session) {
		String roomName = (String) session.getUserProperties().get("roomName");
		String login = (String) session.getUserProperties().get("login");
		String fullMessage = login + " >>> " + message;

		boardcast(roomName, fullMessage);
	}

	/**
	 * Une m�thode priv�e, sp�cifique ?notre exemple. Elle permet l'envoie d'un message
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
	 * Envoyer �� l'utilisateur current un message erreur
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
	 * Permet de ne pas avoir une instance diff�rente par client. ChatServer est donc
	 * g�rer en "singleton" et le configurateur utilise ce singleton.
	 */
	public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
		@Override
		@SuppressWarnings("unchecked")
		public <T> T getEndpointInstance(Class<T> endpointClass) {
			return (T) ChatServer.getInstance();
		}
	}
}
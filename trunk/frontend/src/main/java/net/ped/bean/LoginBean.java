package net.ped.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.ped.constante.ConstantesWeb;
import net.ped.model.User;


@ManagedBean(name="loginBean")
@ViewScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String password;
	private boolean loginSuccess = false;
	private String errorMsg;
	private boolean connectFailed = false;
	private User userLogged;
	
	public String connect() {
		if(login.equals("login") && password.equals("login")) {
			this.loginSuccess = true;
			this.connectFailed = false;
		}
		else {
			this.loginSuccess = false;
			this.connectFailed = true;
		}
		
		return "login";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		userLogged = null;
		this.loginSuccess = false;
		
		return "logout";
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isLoginSuccess() {
		return loginSuccess;
	}


	public void setLoginSuccess(boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}


	public String getErrorMsg() {
		if(!loginSuccess && connectFailed) {
			errorMsg = ConstantesWeb.ERROR_MESSAGE_LOGIN;
		}
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public boolean isConnectFailed() {
		return connectFailed;
	}


	public void setConnectFailed(boolean connectFailed) {
		this.connectFailed = connectFailed;
	}


	public User getUserLogged() {
		return userLogged;
	}


	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}
	

}

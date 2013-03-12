package net.ped.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import net.ped.constante.ConstantesWeb;
import net.ped.model.User;
import net.ped.service.front.FrontPartyService;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String password;
	private boolean loginSuccess;
	private String errorMsg;
	private boolean connectFailed;
	private User userLogged;
	private boolean userAdmin;
	
	public LoginBean() {
		this.errorMsg = ConstantesWeb.ERROR_MESSAGE_LOGIN;
	
		this.loginSuccess = false;
		this.connectFailed = false;
		this.userAdmin = false;
		
	}
	
	public String connect() {
		
		if(login != null && password != null ) {
			
			userLogged = FrontPartyService.getInstance().login(login, password);
			//userLogged = FrontPartyService.getInstance().login("jean", "123");
			
			if(userLogged != null) {
			
				this.loginSuccess = true;
				this.connectFailed = false;
				
				if(userLogged.getRole().equals("admin")) {
					this.userAdmin = true;
				}
			}
			else {
				this.loginSuccess = false;
				this.connectFailed = true;
				
			}
		}
		
		return "login";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		userLogged = null;
		this.loginSuccess = false;
		this.userAdmin = false;
		
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

	public boolean isUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}
	

}

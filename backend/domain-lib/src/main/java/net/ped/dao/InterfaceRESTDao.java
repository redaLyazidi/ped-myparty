package net.ped.dao;


import net.ped.model.User;

public interface InterfaceRESTDao {

	/**
	 * Vérifie le login et mdp de l'utilisateur.
	 * Retourne le User concerné en cas de succès
	 * Retourne un User vide sinon
	 *
	 * @param u
	 */
	public User connexion(String login, String password);
	
}

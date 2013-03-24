package net.ped.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
	
	private static PropertyLoader instance;
	
	public static PropertyLoader getInstance() {
		if (instance == null)
			instance = new PropertyLoader();
		return instance;
	}
	
	/**
    * Charge la liste des propriétés contenu dans le fichier spécifié
    *
    */
   public static Properties load(String filename) throws IOException, FileNotFoundException{
	   Properties properties = new Properties();
	   InputStream propsURL = getInstance().getClass().getResourceAsStream(filename);
	   
	   properties.load(propsURL);
	
	   return properties;
//	      FileInputStream input = new FileInputStream(filename);
//
//	      try{
//	         properties.load(input);
//	         return properties;
//	      }
//	              finally{
//	         input.close();
//	      }
   }

}

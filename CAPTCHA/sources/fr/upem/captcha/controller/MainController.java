package fr.upem.captcha.controller;

public class MainController {
    
    /** Instance unique pré-initialisée */
    private static MainController instance = new MainController();
    
    /** Controlleur privé **/
	private MainController(){
	    	
	    }
     
    /** Point d'accès pour l'instance unique du singleton */
    public static MainController getInstance(){   
    	return instance;
    }
}

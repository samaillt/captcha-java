package fr.upem.captcha.controller;

import java.net.URL;
import java.util.ArrayList;
import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.MainCategory;
import java.util.Collections;

public class MainController {
	
	private static int difficultyLevel;
	private static int imageNumber;
	private static int correctImagesNumber;
	private static MainCategory mainCategory;
	private static Category correctCategory;
	private static ArrayList<URL> displayedImages = new ArrayList<URL>();
	private static ArrayList<URL> correctImages = new ArrayList<URL>();
	private static ArrayList<URL> falseImages = new ArrayList<URL>();
    
    /** Instance unique pré-initialisée */
    private static MainController instance = new MainController();
    
    /** Controlleur privé **/
	private MainController(){
		this.difficultyLevel = 1;
		this.imageNumber = 9;
		this.correctImagesNumber = 4;
		this.mainCategory = new MainCategory();
		this.correctCategory = randomCategory(this.mainCategory.getSubCategories());
		this.fillCorrectImages(correctCategory);
		this.fillFalseImages(mainCategory);
	}
	
	public static ArrayList<URL> getFalseImages() {
		return falseImages;
	}

	public static void setFalseImages(ArrayList<URL> falseImages) {
		MainController.falseImages = falseImages;
	}

	public static void fillCorrectImages(Category correctCategory) {
		if (correctCategory.getSubCategories().isEmpty()) {
			for (URL image : correctCategory.getImages()) {
				correctImages.add(image);
			}
		} else {
			for (Category cat : correctCategory.getSubCategories()) {
				fillCorrectImages(cat);
			}
		}
	}
	
	public static void fillFalseImages(Category category) {
		if (category == correctCategory) {
			return;
		}
		if (category.getSubCategories().isEmpty()) {
			for (URL image : category.getImages()) {
				falseImages.add(image);
			}
		} else {
			for (Category cat : category.getSubCategories()) {
				fillFalseImages(cat);
			}
		}
	}
     
    public static int getDifficultyLevel() {
		return difficultyLevel;
	}

	public static void setDifficultyLevel(int difficultyLevel) {
		MainController.difficultyLevel = difficultyLevel;
	}

	public static int getImageNumber() {
		return imageNumber;
	}

	public static void setImageNumber(int imageNumber) {
		MainController.imageNumber = imageNumber;
	}

	public static int getCorrectImagesNumber() {
		return correctImagesNumber;
	}

	public static void setCorrectImagesNumber(int correctImagesNumber) {
		MainController.correctImagesNumber = correctImagesNumber;
	}

	public static MainCategory getMainCategory() {
		return mainCategory;
	}

	public static void setMainCategory(MainCategory mainCategory) {
		MainController.mainCategory = mainCategory;
	}

	public static Category getCorrectCategory() {
		return correctCategory;
	}

	public static void setCorrectCategory(Category correctCategory) {
		MainController.correctCategory = correctCategory;
	}

	public static ArrayList<URL> getDisplayedImages() {
		return displayedImages;
	}

	public static void setDisplayedImages(ArrayList<URL> displayedImages) {
		MainController.displayedImages = displayedImages;
	}

	public static ArrayList<URL> getCorrectImages() {
		return correctImages;
	}

	public static void setCorrectImages(ArrayList<URL> correctImages) {
		MainController.correctImages = correctImages;
	}

	public static void setInstance(MainController instance) {
		MainController.instance = instance;
	}

	/** Point d'accès pour l'instance unique du singleton */
    public static MainController getInstance(){   
    	return instance;
    }
    
    private static Category randomCategory(ArrayList<Category> catArray) {
    	return catArray.get(randomFromZero(catArray.size()));
    }
    
    private static int randomFromZero(int number) {
		int n = (int)(Math.random() * number);
        return n;
	}
}

package fr.upem.captcha.controller;

import java.net.URL;
import java.util.ArrayList;
import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.MainCategory;
import java.util.Collections;

public class MainController {
	
	private int difficultyLevel;
	private int imageNumber;
	private int correctImagesNumber;
	private MainCategory mainCategory;
	private Category correctCategory;
	private ArrayList<URL> displayedImages = new ArrayList<URL>();
	private ArrayList<URL> correctImages = new ArrayList<URL>();
	private ArrayList<URL> falseImages = new ArrayList<URL>();
    
    /** Instance unique pré-initialisée */
    private static MainController instance = new MainController();
    
    /** Constructeur privé **/
	private MainController(){
		this.difficultyLevel = 1;
		this.imageNumber = 9;
		this.correctImagesNumber = randomFromZero(4) + 1;
		this.mainCategory = new MainCategory();
		this.correctCategory = randomCategory(this.mainCategory.getSubCategories());
		this.fillCorrectImages(correctCategory);
		this.fillFalseImages(mainCategory);
		this.filldisplayedImages();
	}
	
	private MainController(int difficultyLevel, Category category){
		this.difficultyLevel = difficultyLevel;
		this.imageNumber = 9;
		this.correctImagesNumber = randomFromZero(4) + 1;
		this.mainCategory = new MainCategory();
		this.correctCategory = category;
		this.fillCorrectImages(correctCategory);
		this.fillFalseImages(mainCategory);
		this.filldisplayedImages();
	}
	
	public ArrayList<URL> getFalseImages() {
		return falseImages;
	}

	public void setFalseImages(ArrayList<URL> falseImages) {
		this.falseImages = falseImages;
	}

	public void fillCorrectImages(Category correctCategory) {
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
	
	public  void fillFalseImages(Category category) {
		if ((category.equals(correctCategory))) {
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
	
	public void filldisplayedImages() {
		ArrayList<URL> correctImages = getCorrectImages();
		Collections.shuffle(correctImages);
		displayedImages.addAll(correctImages.subList(0, correctImagesNumber));
		
		ArrayList<URL> falseImages = getFalseImages();
		Collections.shuffle(falseImages);
		displayedImages.addAll(falseImages.subList(0, imageNumber - correctImagesNumber));
		
		Collections.shuffle(displayedImages);
	}
	
	public boolean verifySelectedImages(ArrayList<URL> selectedImages) {
		System.out.println("correctImages" + correctImages);
		System.out.println("selectedImages" + selectedImages);
		if(selectedImages.size() != correctImagesNumber) {
			return false;
		}
		for (URL url : selectedImages) {
			if (!correctImages.contains(url)){
				return false;
			}
		}
		return true;
	}
		
    public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public  int getImageNumber() {
		return imageNumber;
	}

	public  void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public  int getCorrectImagesNumber() {
		return correctImagesNumber;
	}

	public  void setCorrectImagesNumber(int correctImagesNumber) {
		this.correctImagesNumber = correctImagesNumber;
	}

	public  MainCategory getMainCategory() {
		return mainCategory;
	}

	public  void setMainCategory(MainCategory mainCategory) {
		this.mainCategory = mainCategory;
	}

	public  Category getCorrectCategory() {
		return correctCategory;
	}

	public  void setCorrectCategory(Category correctCategory) {
		this.correctCategory = correctCategory;
	}

	public  ArrayList<URL> getDisplayedImages() {
		return displayedImages;
	}

	public  void setDisplayedImages(ArrayList<URL> displayedImages) {
		this.displayedImages = displayedImages;
	}

	public  ArrayList<URL> getCorrectImages() {
		return correctImages;
	}

	public  void setCorrectImages(ArrayList<URL> correctImages) {
		this.correctImages = correctImages;
	}

	/** Point d'accès pour l'instance unique du singleton */
    public static  MainController getInstance(){   
    	return instance;
    }
    
    private  Category randomCategory(ArrayList<Category> catArray) {
    	return catArray.get(randomFromZero(catArray.size()));
    }
    
    private  int randomFromZero(int number) {
		int n = (int)(Math.random() * number);
        return n;
	}
    
    public void reloadCaptcha(boolean difficultyChanged) {
    	displayedImages.clear();
    	correctImages.clear();
    	falseImages.clear();
    	if (difficultyChanged) {
    		if(this.difficultyLevel == 1)
    			instance = new MainController(2, randomCategory(getCorrectCategory().getSubCategories()));
    		else {
    			instance = new MainController(2, getCorrectCategory());
    		}
    	} else {
    		instance = new MainController();
    	}
    	
    }
}

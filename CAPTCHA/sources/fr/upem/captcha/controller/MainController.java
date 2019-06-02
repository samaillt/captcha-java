/**
 * @author Noélie Bravo - Tom Samaille
 * @file MainController.java
 * @package fr.upem.captcha.controller
 */
package fr.upem.captcha.controller;

import java.net.URL;
import java.util.ArrayList;
import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.MainCategory;
import java.util.Collections;

/**
 * Main controller : manages the application, implements singleton.
 */
public class MainController {
	
	private int difficultyLevel;
	private int maxDifficulties;
	private int imageNumber;
	private int correctImagesNumber;
	private MainCategory mainCategory;
	private Category correctCategory;
	private ArrayList<URL> displayedImages = new ArrayList<URL>();
	private ArrayList<URL> correctImages = new ArrayList<URL>();
	private ArrayList<URL> falseImages = new ArrayList<URL>();
    
    /** unique instance */
    private static MainController instance = new MainController();
    
    /**
     * Private constructor
     */
	private MainController(){
		this.difficultyLevel = 1;
		this.imageNumber = 9;
		this.correctImagesNumber = randomFromZero(4) + 1;
		this.mainCategory = new MainCategory();
		this.maxDifficulties = Category.hauteur(mainCategory);
		System.out.println(maxDifficulties);
		this.correctCategory = randomCategory(this.mainCategory.getSubCategories());
		this.fillCorrectImages(correctCategory);
		this.fillFalseImages(mainCategory);
		this.filldisplayedImages();
	}
	
	/**
	 * 
	 * @param difficultyLevel difficulty "1" : easy, "2"  : hard
	 * @param category Actual category
	 * 
	 */
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

	/**
	 * Fills correctImages with the images of the category passed in parameter which will be considered as the right answers
	 * @param correctCategory
	 */
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
	
	/**
	 * Fills falseImages with the images of the category passed in parameter which will be considered as the wrong answers
	 * @param category 
	 */
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
	
	/**
	 * Fills displayedImages with images selected and shuffles displayedImages
	 */
	public void filldisplayedImages() {
		ArrayList<URL> correctImages = getCorrectImages();
		Collections.shuffle(correctImages);
		displayedImages.addAll(correctImages.subList(0, correctImagesNumber));
		
		ArrayList<URL> falseImages = getFalseImages();
		Collections.shuffle(falseImages);
		displayedImages.addAll(falseImages.subList(0, imageNumber - correctImagesNumber));
		
		Collections.shuffle(displayedImages);
	}
	
	/**
	 * Verifies images selected by the user
	 * @param selectedImages
	 * @return boolean
	 */
	public boolean verifySelectedImages(ArrayList<URL> selectedImages) {
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
	
	public  int getImageNumber() {
		return imageNumber;
	}

	public  Category getCorrectCategory() {
		return correctCategory;
	}

	public  ArrayList<URL> getDisplayedImages() {
		return displayedImages;
	}

	public  ArrayList<URL> getCorrectImages() {
		return correctImages;
	}

	/**
	 * Get the singleton's instance
	 * @return
	 */
    public static  MainController getInstance(){   
    	return instance;
    }
    
    /**
     * Return a random category
     * @param catArray ArrayList of categories
     * @return
     */
    private  Category randomCategory(ArrayList<Category> catArray) {
    	return catArray.get(randomFromZero(catArray.size()));
    }
    
    /**
     * Return a random number between zero and number
     * @param number
     * @return
     */
    private  int randomFromZero(int number) {
		int n = (int)(Math.random() * number);
        return n;
	}
    
    /**
     * Reload the application 
     * @param difficultyChanged must be set to true if the difficulty as been changed, else false
     */
    public void reloadCaptcha(boolean difficultyChanged) {
    	displayedImages.clear();
    	correctImages.clear();
    	falseImages.clear();
    	if (difficultyChanged) {
    		if(this.difficultyLevel != this.maxDifficulties)
    			instance = new MainController(this.difficultyLevel + 1, randomCategory(getCorrectCategory().getSubCategories()));
    		else {
    			instance = new MainController(this.maxDifficulties, getCorrectCategory());
    		}
    	} else {
    		instance = new MainController();
    	}
    }
    
    /**
     * Closes the application - 0 exit value if succeeds, else 1
     * @param succeed
     */
    public void closeApplication(boolean succeed) {
    	if (succeed) {
    		System.exit(0); //close the application because the user succeed
    	}
    	else {
    		System.exit(1); //close the application because the user gives up
    	}
    }
}

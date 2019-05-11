package fr.upem.captcha.images;

import java.net.URL;
import java.util.ArrayList;

public class Category implements Images {

	private ArrayList<URL> images = new ArrayList<URL>();

	@Override
	public ArrayList<URL> getImages() {
		return images;
	}

}

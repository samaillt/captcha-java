package fr.upem.captcha.images;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public interface Images {

	public ArrayList<URL> getImages();
	public ArrayList<Category> getSubCategories();
	public void fillImages();
	public void fillCategories();
	public Path getPath();
	public String getName();
	public List<String> getSubDirectories();
}

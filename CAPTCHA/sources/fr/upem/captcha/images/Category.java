package fr.upem.captcha.images;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import java.util.stream.Collectors;
import java.io.IOException;
import java.lang.StringBuilder;

public class Category implements Images {

	private ArrayList<URL> images = new ArrayList<URL>();
	private ArrayList<Category> subCategories = new ArrayList<Category>();
	
	public Path getPath() {
		String path = this.getClass().getPackage().getName().replace('.', '/');
		StringBuilder fileName = new StringBuilder(this.getClass().getSimpleName());
		fileName.append(".class");
		URL catUrl = this.getClass().getResource(fileName.toString()); 
		File classFile =  new File(catUrl.getPath());
		return Paths.get(classFile.getParent());
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public void fillCategories() {
		Path path = this.getPath();
		List<String> subDirectories = null;
		try {
			subDirectories = Files.walk(path, 1)
			        .map(Path::getFileName)
			        .map(Path::toString)
			        .filter(n -> !n.contains("."))
			        .collect(Collectors.toList());
			subDirectories.remove(0);	// Removing current directory
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String subDirectory : subDirectories) {
			System.out.println(subDirectory);
		}
	}

	@Override
	public ArrayList<URL> getImages() {
		return this.images;
	}
	
	@Override
	public ArrayList<Category> getSubCategories() {
		return this.subCategories;
	}

}

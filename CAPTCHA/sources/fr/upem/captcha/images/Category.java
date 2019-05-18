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
import java.lang.reflect.InvocationTargetException;
import java.lang.Class;

public class Category implements Images {

	private ArrayList<URL> images = null;
	private ArrayList<Category> subCategories = null;
	
	public Category() {
		this.images = new ArrayList<URL>();
		this.subCategories = new ArrayList<Category>();
		this.fillImages();
		this.fillCategories();
	}
	
	public void fillImages() {
		Path path = this.getPath();
		List<String> images = null;
		try {
			images = Files.walk(path, 1)
	            .map(Path::getFileName) // Get filename
	            .map(Path::toString) // Set "image" to string
	            .filter(n -> n.contains(".jpg") || n.contains(".jpeg") || n.contains(".png")) // Only if has .jpg or jpeg .png extension
	            .collect(Collectors.toList()); // Add to list
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String image : images) {
			this.images.add(this.getClass().getResource(image));
		}
	}
	
	public void fillCategories() {
		Path path = this.getPath();
		List<String> subDirectories = this.getSubDirectories();
		for (String subDirectory : subDirectories) {
			List<String> classes = null; // List of subClasses names
			Path subDirectoryPath = Paths.get(path + "/" + subDirectory);
			String subPackageName = this.getClass().getPackage().getName() + "." + subDirectoryPath.getFileName();
			try {
				classes = Files.walk(subDirectoryPath, 1)
			        .map(Path::getFileName) // Get filename
			        .map(Path::toString) // Set filename to string
			        .filter(n -> n.contains(".class")) // Only if has .class extension
			        .map(n -> subPackageName + '.' + n.replace(".class", ""))
			        .collect(Collectors.toList()); // Add to list
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (String className : classes) {
				Object object = null;
				try {
					object = Class.forName(className).getDeclaredConstructor().newInstance();
					// OK JUSQUE LA System.out.println(object);
				} catch (ClassNotFoundException e) // Class doesn't exist
			    {
					e.printStackTrace();
			    }
			    catch (InstantiationException e) // Class is abstract or interface or has no specified constructor
			    {
			    	e.printStackTrace();
			    }
			    catch (IllegalAccessException e) // Class not accessible
			    {
			    	e.printStackTrace();
			    }
				catch (InvocationTargetException e) // Failure with called constructor
			    {
			    	e.printStackTrace();
			    }
				catch (NoSuchMethodException e) // Class has no declared constructor
			    {
			    	e.printStackTrace();
			    }
				if (this.getClass().isInstance(object)) {
					this.subCategories.add((Category)object); // Adding object to subCategories
		        }
			}
		}
	}
	
	public Path getPath() {
		// String path = this.getClass().getPackage().getName().replace('.', '/');
		StringBuilder fileName = new StringBuilder(this.getClass().getSimpleName());
		fileName.append(".class");
		URL catUrl = this.getClass().getResource(fileName.toString()); 
		File classFile =  new File(catUrl.getPath());
		return Paths.get(classFile.getParent());
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public List<String> getSubDirectories() {
		Path path = this.getPath();
		List<String> subDirectories = null; // List of subCategories names
		try {
			subDirectories = Files.walk(path, 1)
		        .map(Path::getFileName) // Get filename
		        .map(Path::toString) // Set filename to string
		        .filter(n -> !n.contains(".")) // Only if doesn't have extension
		        .collect(Collectors.toList()); // Add to list
			subDirectories.remove(0); // Removing current directory
		} catch (IOException e) {
			e.printStackTrace();
		}
		return subDirectories;
	}
	
	@Override
	public ArrayList<URL> getImages() {
		return this.images;
	}
	
	@Override
	public ArrayList<Category> getSubCategories() {
		return this.subCategories;
	}
	
	@Override
	public String toString() {
		StringBuilder  str = new StringBuilder(this.getName());
		return str.toString();
	}

}

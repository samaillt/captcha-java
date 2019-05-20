package fr.upem.captcha.ui;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.controller.MainController;
import fr.upem.captcha.images.Category;
import fr.upem.captcha.images.MainCategory;
import fr.upem.captcha.images.anime.Anime;
import fr.upem.captcha.images.anime.princess.Princess;
import fr.upem.captcha.images.anime.titeuf.Titeuf;
import fr.upem.captcha.images.game.Game;
import fr.upem.captcha.images.game.clash.Clash;
import fr.upem.captcha.images.game.fortnite.Fortnite;
import fr.upem.captcha.images.hero.Hero;
import fr.upem.captcha.images.hero.dc.Dc;
import fr.upem.captcha.images.hero.marvel.Marvel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainUi {
	
	private static int windowWidth = 800;
	private static int windowHeight = 800;
	
	private static int gridLines = 4;
	private static int gridColumns = 3;
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	private static ArrayList<URL> displayedImages = new ArrayList<URL>();
	
	public static void main(String[] args) throws IOException {

		MainController mainController = MainController.getInstance();
		
		Category cat = new Category();
		System.out.println(cat);
		
		JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
		
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(windowWidth, windowHeight); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		
		JButton okButton = createOkButton();
		
		JButton reinitButton = createReloadButton();

		frame.add(createLabelImage("centre ville.jpg")); //ajouter des composants à la fenêtre
		frame.add(createLabelImage("le havre.jpg"));
		frame.add(createLabelImage("panneau 70.jpg"));
		frame.add(createLabelImage("panneaubleu-carre.jpeg"));
		frame.add(createLabelImage("parking.jpg"));
		frame.add(createLabelImage("route panneau.jpg"));
		frame.add(createLabelImage("tour eiffel.jpg"));
		frame.add(createLabelImage("ville espace verts.jpg"));
		frame.add(createLabelImage("voie pieton.jpg"));
		
		frame.add(new JTextArea("Cliquez n'importe sur les images !"));
		
		frame.add(okButton);
		
		frame.add(reinitButton);
		
		frame.setVisible(true);
	}
	
	private static int randomFromZero(int number) {
		int n = (int)(Math.random() * number);
        return n;
	}
	
	private static GridLayout createLayout(){
		return new GridLayout(gridLines,gridColumns);
	}
	
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						System.out.println("J'ai cliqué sur valider");
					}
				});
			}
		});
	}
	
	private static JButton createReloadButton(){
		return new JButton(new AbstractAction("Réinitialiser") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						System.out.println("J'ai cliqué sur reload");
						displayedImages.clear();
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(String imageLocation) throws IOException{
		
		final URL url = MainUi.class.getResource(imageLocation); //Aller chercher les images !! IMPORTANT 
		
		System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(windowWidth/gridColumns,windowHeight/gridLines, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}

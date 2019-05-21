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
import java.awt.Frame;
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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MainUi {
	
	private static int windowWidth = 800;
	private static int windowHeight = 800;
	
	private static int gridLines = 4;
	private static int gridColumns = 3;
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	JFrame frame = new JFrame("Captcha"); // Cr�ation de la fen�tre principale
	
	public MainUi() throws IOException{
		MainController mainController = MainController.getInstance();		
		
		GridLayout layout = createLayout();  // Cr�ation d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fen�tre.
		frame.setSize(windowWidth, windowHeight); // d�finition de la taille
		frame.setResizable(false);  // On d�finit la fen�tre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fen�tre on quitte le programme.
		
		JButton okButton = createOkButton();
		
		JButton reinitButton = createReloadButton();
		
		fillGridDisplayedImages();
		
		frame.add(okButton);
		
		frame.add(reinitButton);
		
		frame.setVisible(true);
	}
	
	private static GridLayout createLayout(){
		return new GridLayout(gridLines,gridColumns);
	}
	
	private JButton createOkButton(){
		return new JButton(new AbstractAction("V�rifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des �v�nements
					
					@Override
					public void run() { // c'est un runnable
						if (MainController.getInstance().verifySelectedImages(selectedImages)) {
							JOptionPane.showMessageDialog(getFrame(), "Vous avez r�ussis !");
						} else {
							MainController.getInstance().reloadCaptcha(true);
							
							selectedImages.clear();
							JOptionPane.showMessageDialog(getFrame(), "Vous avez rat� !", null, JOptionPane.ERROR_MESSAGE);
							try {
								fillGridDisplayedImages();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		});
	}
	
	public JFrame getFrame() {
		return frame;
	}

	private JButton createReloadButton(){
		return new JButton(new AbstractAction("R�initialiser") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des �v�nements
					
					@Override
					public void run() { // c'est un runnable
						selectedImages.clear();
						MainController.getInstance().reloadCaptcha(false);
						try {
							fillGridDisplayedImages();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	public void fillGridDisplayedImages() throws IOException {
		int i = 0;
		for ( ; i < MainController.getInstance().getImageNumber() ; i++) {
			if(frame.getContentPane().getComponentCount()-1 >= i) {
				frame.getContentPane().remove(i);
			}
			frame.add(createLabelImage(MainController.getInstance().getDisplayedImages().get(i)), i);
		}
		if (frame.getContentPane().getComponentCount()-1 >= i) {
			frame.getContentPane().remove(i);
		}
		frame.add(new JTextArea("Click on images which represents : " + MainController.getInstance().getCorrectCategory().getName()), i);
		frame.repaint();
		frame.revalidate();
	}
	
	private static JLabel createLabelImage(URL url) throws IOException{
		
		System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(windowWidth/gridColumns,windowHeight/gridLines, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // cr�er le composant pour ajouter l'image dans la fen�tre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'�venement de souris
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
			public void mouseClicked(MouseEvent arg0) { //ce qui nous int�resse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
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
	
	public static void main(String[] args) throws IOException {
		MainUi main = new MainUi();
	}
}
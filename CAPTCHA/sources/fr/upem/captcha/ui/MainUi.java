/**
 * @author Noélie Bravo - Tom Samaille
 * @file MainUi.java
 * @package fr.upem.captcha.ui
 */
package fr.upem.captcha.ui;

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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import fr.upem.captcha.controller.MainController;

/**
 * Main UI : 
 *
 */
public class MainUi {
	
	private static int windowWidth = 800;
	private static int windowHeight = 800;
	
	private static int gridLines = 4;
	private static int gridColumns = 3;
	
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
	
	/**
	 * Management and display of the window
	 * @throws IOException
	 */
	public MainUi() throws IOException{
		
		GridLayout layout = createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(windowWidth, windowHeight); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		
		JButton okButton = createOkButton();
		
		JButton reinitButton = createReloadButton();
		
		fillGridDisplayedImages();
		
		frame.add(okButton);
		
		frame.add(reinitButton);
		
		frame.setVisible(true);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Etes-vous sur de vouloir abandonner ?", "Fermeture", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            MainController.getInstance().closeApplication(false);
		        }
		    }
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	/**
	 * Create new layout
	 * @return new GridLayout
	 */
	private static GridLayout createLayout(){
		return new GridLayout(gridLines,gridColumns);
	}
	
	/**
	 * Creation of the "ok" button
	 * @return new JButton
	 */
	private JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						if (MainController.getInstance().verifySelectedImages(selectedImages)) {
							JOptionPane.showMessageDialog(getFrame(), "Vous avez réussis !");
							MainController.getInstance().closeApplication(true);
						} else {
							MainController.getInstance().reloadCaptcha(true);
							
							selectedImages.clear();
							JOptionPane.showMessageDialog(getFrame(), "Vous avez raté !", "Message", JOptionPane.ERROR_MESSAGE);
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
	
	/**
	 * Creation of the "Reload" button
	 * @return new Jbutton
	 */
	private JButton createReloadButton(){
		return new JButton(new AbstractAction("Réinitialiser") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
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
	
	/**
	 * Fill displayed images grid
	 * @throws IOException
	 */
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
		frame.add(new JTextArea("Cliquez sur les images représentant : \n" + MainController.getInstance().getCorrectCategory().getName()), i);
		frame.repaint();
		frame.revalidate();
	}
	
	/**
	 * Creation of the label image
	 * @param url
	 * @return label image
	 * @throws IOException
	 */
	private static JLabel createLabelImage(URL url) throws IOException{
				
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
	
	/**
	 * Main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		MainUi main = new MainUi();
	}
}
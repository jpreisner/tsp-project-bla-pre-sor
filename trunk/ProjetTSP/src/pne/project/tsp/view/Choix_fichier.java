package pne.project.tsp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File ;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog ;
import javax.swing.JFrame ;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class Choix_fichier extends JDialog {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Déclaration des différentes variables
	 */
	
	// Correspond au répertoire
    private String directory = null ;
    
    // Correspond au fichier
    private String file = null ;
    
    // Correspond à une "boite" qui permet d'afficher un menu déroulant
    private JComboBox combo =  new JComboBox();
    
    // Correspond à ce qu'il y a dans un item de la combobox
    private JList list = new JList();
    
    // Scroll pane est un panel qui contient une liste et affiche la barre a droite qui permet de monter et descendre
    private JScrollPane scrollpane;
    
    // Déclaration des boutons Cancel et Open
    private JButton buttonCancel;
    private JButton buttonOpen;
    
    // Déclaration des panels
    private JPanel panelButton;
    
    /**
     *  Constructeur de la classe FileSelector
     */
    
    public Choix_fichier(String title, String lbl_cancel, String lbl_ok) {
        super((JFrame)null, title, true /* modal */) ;
        
        // Initialisation des boutons Cancel et Open
        buttonCancel = new JButton(lbl_cancel);
		buttonOpen = new JButton(lbl_ok);
		
		// Initialisation de la combobox et de la list : on se place dans le dossier où s'enregistre les fichiers IHM
        show(System.getProperty("user.dir")) ;
        
        // init permet l'affichage et appel les listeners 
        init();
        
        pack() ;
        setVisible(true) ;
    }
    
    /**
     * Listeners : cancel, open, combobox et le double clique
     */
    
    // Listener du bouton "Cancel" : ActionListener (car action sur le bouton)
    private ActionListener buttonCancelListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("L'user a cliqué sur Cancel");
			// Permet de quitter lors de l'appuie sur le bouton "Cancel"
			dispose();
		}
    };
 
    // Listener du bouton "Open" : ActionListener (car action sur le bouton)
    private ActionListener buttonOpenListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Mise a jour de l'item sélectionné et de la combobox
			updateItem();
		}	
    };
    
    // Listener de la ComboBox : ActionListener (car agit lorsqu'on sélectionne un item)
    private ActionListener comboListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// On met dans une variable l'item (=répertoire) sélectionné
			Object item = combo.getSelectedItem();
			
			// On enregistre sa position
			int position = combo.getSelectedIndex();
			
			// Appel d'une fonction qui met a jour la liste du nouveau répertoire
			System.out.println("On est dans le combolistener");
			System.out.println("item = " + (String)item + " et position = " + position);
			
			// Création du chemin
			String path;
			path = createPath(position);
			
			// On supprime les items présents dans la combobox car ils sont copié à chaque choix d'un nouvel item
			combo.removeAllItems();
			// On supprime la list présente dans l'item précédent : pas nécéssaire?
			list.removeAll();
			
			// Update les nouveaux items présent dans la Combobox
			show(path);
		}
    };
    
    
    // Listener de la list : MouseListener (car on va double-cliquer sur un élément de la list)
    private MouseListener doubleClicListener = new MouseListener(){

		@Override
		// Cette méthode est appelée quand l'utilisateur a cliqué (appuyé puis relâché) sur le 	composant écouté
		public void mouseClicked(MouseEvent mouse_event) {
			// Lorsqu'on double-clic
			if(mouse_event.getClickCount() == 2){
				// Mise a jour de l'item selectionné et de la combobox
				updateItem();
			}
		}

		@Override
		// Cette méthode est appelée quand la souris entre dans la zone du composant écouté
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		// Cette méthode est appelée quand la souris sort de la zone du composant écouté
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		// Cette méthode est appelée quand l'utilisateur appuie le bouton sur le composant écouté
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		// Cette méthode est appelée quand l'utilisateur relâche le bouton sur le composant écouté
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    
    
    /**
     * Méthodes permettant de mettre à jour au début et à chaque action la combobox et la list
     */
    
    
    
    /** Met a jour la ComboBox et la List : utilisé pour le listener de la ComboBox
     * pas utilisé pour le listener du button Open car on a pas besoin de tout (a partir de path.list)
     */
    Boolean show(String path) {
    	// dir est un fichier qui va nous permettre de tester si path existe et si c'est bien un répertoire
        File dir = new File(path) ;
        
        if (!dir.exists() || !dir.isDirectory()){
        	System.out.println("return false");
        	return false ;
       }
       
        // On sélectionne le répertoire où l'on est
        directory = dir.getAbsolutePath() ;
        
        // Affiche le répertoire où on est la : dans le dossier IHM
        System.out.println("DIRECTORY = " + directory) ;
        
        file = null ;
      
        // Files : tableau de string qui contient les éléments contenu dans chaque répertoire
        String[] files = dir.list() ;
        
        if (files!=null) {
            for(int i=0; i<files.length; i++) {
            	
            	/** NB : Le constructeur File(chaine1, chaine2) ajoute la chaine2 à la chaine1
            	 *  en les séparant d'un séparateur (\ ou /)
            	 *  f va nous permettre de tester si pour l'élément i, on a un fichier ou répertoire
            	 */
                File f = new File(path, files[i]); 
                
                /** f.isDirectory() : test si le fichier est un dossier */
                
                // Cas ou le fichier est un dossier
                if (f.isDirectory()){
                		files[i] = files[i]+File.separator ;
                		System.out.format("  directory : %s\n",files[i]);
                }
                // Cas ou le fichier est un fichier
                else{
                		System.out.format("  file: %s\n",files[i]) ;  
                }
            }
            // On ajoute a la liste list le tableau files contenant tous les fichiers/dossier présent dans le répertoire
            list.setListData(files);
        }
     
        // dirs : tableau string qui corresponds aux répertoires à la "racine"
        String[] dirs = path.split("\\\\") ;
        
        // On attribue à "position" la position initiale (on aurait pu mettre position = 0)
        int position = combo.getSelectedIndex();
  
        // On regarde les répertoires "parents" (ce qui sont avant) et on les ajoute à la combobox
        for (String p : dirs) {
        	if (p.equals("")) continue ;
        	System.out.format("  parentdir: %s\n",p) ;
        	
        	// On ajoute l'item p (qui correspond à UN répertoire) dans le ComboBox
        	combo.addItem(p);
        	
        	// On incrémente la position dès qu'on ajoute un item à la comboBox
        	// Position va donc nous permettre de nous positionner sur le dernier item (qui a été choisi)
        	position++;
        }		
       
        // On place la combobox à l'item où l'on se trouve : celui qu'on a choisi
        combo.setSelectedIndex(position);
        
        // A la fin du for, combo contient tous les répertoires qui sont dans le tableau dirs
        return true ;
    }
    
    /** Met à jour la nouvelle list et ajoute le nouvel item dans la combobox 
     * 	pour le listener du bouton open
     */
    public void updateItem(){
    	// élément sélectionné : soit un fichier, soit un répertoire
    	String element = (String) list.getSelectedValue();
    	
    	// On enregistre sa position
    	int position = combo.getSelectedIndex();
    	
    	// Création du chemin
    	String path;
    	path = createPath(position);	// Créer le chemin jusqu'à l'item précédent
    	    	
    	/* Le path est composé du chemin jusqu'à l'item. Il faut lui rajouter l'objet sur lequel 
    	 *  on vient de cliquer. Si c'est un dossier, il y aura dossier/
    	 */
    
    	path = path + File.separator + element;
    	
    	File f1 = new File(path);	// permet de tester si l'objet est un répertoire/fichier
    	
    	// Cas ou l'objet est un répertoire
    	if(f1.isDirectory()){
    		System.out.println("EST DIRECTORY");
    		
    		/* NE SERT A RIEN ?
    		 * On enleve le caractère en trop qui est a la fin : le "/" (car on en a pas besoin)
    		 */
    		//	path = path.substring(0, path.length()-1);
    		
    		// Update les nouveaux items présent dans la Combobox
    		// On supprime les éléments qui été présent dans "l'ancienne" liste
    		list.removeAll();
    		
    		/* NE SERT A RIEN ?
    		 * On enlève le caractère "/" à la fin de l'élément
    		 */
    		//element = element.substring(0, element.length()-1);

    		// Fichier nous permettant de savoir de quel type est le path : s'il existe et si c'est bien un répertoire
    		File dir = new File(path) ;
    		
    		if (dir.exists() && dir.isDirectory()){
    			// files contient toute la liste présente dans le nouveau répertoire
    			String[] files = dir.list() ;
    			
    			if (files!=null){
    				for(int i=0; i<files.length; i++) {
    					// f va nous permettre de savoir si l'objet de la liste à la position i est un dossier ou un fichier
    					File f = new File(path, files[i]);
    					// Si c'est un dossier, il faut lui rajouter le "/" à la fin de son nom pour indiquer que c'est un dossier
    					if (f.isDirectory()) files[i] = files[i]+File.separator ;
    				}
    			}
    			// Mise à jour de la liste : on lui attribue toutes les fichiers/dossiers qui sont dans le nouvel item
    			list.setListData(files);
    			
    			// On ajoute à la combobox le dossier dans lequel on est rentré
    			/* combo.addItem(String) : rajoute un item dans la combobox en lui attribuant 
    			 * le nom passé en paramètre
    			 */
    			combo.addItem(element);
    			// On se met sur le nouvel item (qui a pour nom le string contenu dans objet)
    			combo.setSelectedItem(element);	
    		}
    		
    	}
    	// L'objet est un fichier
    	else{
    		System.out.println("N'EST PAS DIRECTORY");
    		System.out.println("Vous avez sélectionner le fichier : " + path);
    		dispose();
		}
    }
    

    // Créer le chemin pour un item choisi a partir de sa position
    public String createPath(int positionItem){
    	/**  NB:
    	 * File.separator = / pour Windows
		 * ComboBox.getItemAt(i) permet d'obtenir l'item a la position i
		 */
    	int i;
    	// On met le cas i=0 a part car sinon le chemin sera : C://etc... au lieu de C:/etc...
    	String path = (String)combo.getItemAt(0);
    	for(i=1; i<=positionItem; i++){
    			path = path + File.separator + combo.getItemAt(i);
    	}
    	return path;
    }
    
    /**
     * La méthode init() permet de construire la fenetre en y ajoutant les différents panels, bouton,
     * la combobox et la list qui sera inclut dans le scrollpane
     */
    public void init(){
    	
        // Initialisation du JScrollPane : panel qui contient la liste list et qui en plus a une barre de défilement si nécéssaire
        scrollpane = new JScrollPane(list);
    	
    	// Initialisation du panelButton : on lui met les buttons Cancel et Open
    	panelButton = new JPanel();
    	
    	//Attribution des ActionListeners aux buttons Cancel et Open
    	combo.addActionListener(comboListener);
    	buttonCancel.addActionListener(buttonCancelListener);
    	buttonOpen.addActionListener(buttonOpenListener);
    	list.addMouseListener(doubleClicListener);
    	
    	/**
    	 * Création du panelButton qui contiendra : les boutons Cancel et Open et sa mise en forme
    	 * Utilisation de BoxLayout qui nous permettra d'aligner les boutons selon l'axe des X
    	 * Utilisation d'une horizontalGlue qui nous permet de placer les boutons sur la droite
    	 * Utilisation de rigideArea qui nous permet de mettre un espace entre les 2 boutons
    	 */
    	
    	// Pour le panel des Buttons, on utilise BoxLayout pour les aligner sur l'axe des X
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		
		// Crée une barre horizontal vide qui marche comme un ressort
		panelButton.add(Box.createHorizontalGlue());
		
		panelButton.add(buttonCancel);
		// Crée une zone vide qu'on position entre les 2 buttons
		panelButton.add(Box.createRigidArea(new Dimension(10, 0)));	/* 10 longueur, 0 hauteur */
		panelButton.add(buttonOpen);
		
		
		/**
		 *  Fenetre principal : on ajoute les différents objets dans la fenetre principale
		 *  Utilisation d'un BorderLayout pour organiser les panels en haut (ComboBox), 
		 *  				au centre (ScrollPane) et en bas (panelButton)
		 */
		
		// Attribution à la fenetre de l'organisation : borderlayout
		setLayout(new BorderLayout());
		// On ajoute la comboBox en haut de la fenetre
		add(combo, BorderLayout.NORTH);
		// On ajoute le scrollpane contenant la list au centre de la fenetre
		add(scrollpane, BorderLayout.CENTER);
		// on ajoute le panel des boutons en bas de la fenetre
		add(panelButton, BorderLayout.SOUTH);
		
		
    }
    
    // Indique quel fichier nous avons choisi
    public String getFilePath() {
        if (directory==null || file==null) return null ;
        return directory+File.separator+file ;
    }
    
    static public void main(String args[]) {
        Choix_fichier fs = new Choix_fichier("File open...","Cancel","Open") ;
        System.out.println(fs.getFilePath()) ;
        System.exit(1) ;
    }
    
   /** RIEN A VOIR AVEC LE TP : 
    * 
    * EXEMPLE AVEC JFILECHOOSER QUI CONSTRUIT LUI MEME
    * 
    * 	// création de la boîte de dialogue
   	*	JFileChooser dialogue = new JFileChooser();
    *	
   	*	// affichage
   	*	dialogue.showOpenDialog(null);
    *
   	*	// récupération du fichier sélectionné
   	*	System.out.println("Fichier choisi : " + dialogue.getSelectedFile()); 
    */
}
package pne.project.tsp.view;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class Choix_fichier extends JDialog {
	JFileChooser dialogue;

	public Choix_fichier(String path) {
		dialogue = new JFileChooser(path);
	}

	public File selectFile() {
		dialogue.showOpenDialog(null);

		File fileSelected = dialogue.getSelectedFile();
		if (fileSelected != null) {
			// System.out.println("Le fichier choisi est : " +
			// fileSelected.getName());

			if (!isXml(fileSelected.getName())) {
				// GERER L'ERREUR DANS LE CAS OU LE FILE SELECTED N'EST PAS UN
				// XML
				return null;
			}
		}
		return fileSelected;
	}

	public boolean isXml(String f) {
		int t = f.length();
		return f.substring(t - 3, t).toLowerCase().equals("xml");
	}

	/*
	 * static public void main(String args[]) { Choix_fichier fs = new
	 * Choix_fichier(); }
	 */

}
package pne.project.tsp.view;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class Choix_fichier {
	JFileChooser dialogue;

	public Choix_fichier(String path) {
		dialogue = new JFileChooser(path);
	}

	public File selectFile() {
		dialogue.showOpenDialog(null);

		File fileSelected = dialogue.getSelectedFile();
		if (fileSelected != null) {
			if (!isXml(fileSelected.getName())) {
				return null;
			}
		}
		return fileSelected;
	}

	public boolean isXml(String f) {
		int t = f.length();
		return f.substring(t - 3, t).toLowerCase().equals("xml");
	}
}
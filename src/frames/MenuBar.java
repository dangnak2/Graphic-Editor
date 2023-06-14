package frames;

import menus.EditMenu;
import menus.FileMenu;

import javax.swing.*;

public class MenuBar extends JMenuBar {

	// attribute

	// components
	private FileMenu fileMenu;
	private EditMenu editMenu;

	// association
	private DrawingPanel drawingPanel;
	private MainFrame mainFrame;

	public MenuBar() {
		
		this.fileMenu = new FileMenu("File");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("Edit");
		this.add(this.editMenu);

	}
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.fileMenu.associate(this.drawingPanel);
	}

	public void associate(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
}

package frames;

import frames.DrawingPanel;
import frames.MenuBar;
import frames.ToolBar;

import java.awt.*;

import javax.swing.*;

public class MainFrame extends JFrame {

	// attribute
	private static final long serialVersionUID = 1L;

	// components
	private frames.MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;
	
	public MainFrame() {
		// attribute
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);

		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);

		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);

		// association
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
	}


}

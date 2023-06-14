package menus;

import javax.swing.*;

import frames.DrawingPanel;
import global.Constants.EEditMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenu extends JMenu {

	DrawingPanel drawingPanel;
	
	private static final long serialVersionUID = 1L;

	public EditMenu(String s) {
		super(s);

		ActionHandler actionHandler = new ActionHandler();
		for(EEditMenu editMenu : EEditMenu.values()) {
			JMenuItem editItem = new JMenuItem(editMenu.getLabel());
			editItem.addActionListener(actionHandler);
			editItem.setActionCommand(editMenu.name());
			editItem.setToolTipText(editMenu.getTooltip());
			this.add(editItem);
		}
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}

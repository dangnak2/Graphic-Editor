package frames;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import global.Constants.ETools;
import shapes.*;

public class ToolBar extends JToolBar {
	//attribute

	// components

	// association
	private DrawingPanel drawingPanel;


	public ToolBar() {
		// components
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();

		for(ETools eTools : ETools.values()){
			JRadioButton btnTool = new JRadioButton();
			btnTool.setActionCommand(eTools.name());
			btnTool.addActionListener(actionHandler);
			btnTool.setIcon(new ImageIcon(eTools.getIconFileName()));
			btnTool.setSelectedIcon(new ImageIcon(eTools.getIconSELFileName()));
			btnTool.setToolTipText(eTools.getToolTip());
			this.add(btnTool);
			buttonGroup.add(btnTool);
		}
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
//		툴바의 도형을 선택하지 않았을 때 기본값으로 RectangleTool으로 설정
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick();
	}

	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}
	}
}

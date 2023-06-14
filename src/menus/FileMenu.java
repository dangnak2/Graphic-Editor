package menus;

import frames.DrawingPanel;

import javax.swing.*;

import global.Constants.EFileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	// association
	private DrawingPanel drawingPanel;
	private File dir, file;

	public FileMenu(String s) {
		super(s);

		this.dir = new File("test");
		this.file = null;

		ActionHandler actionHandler = new ActionHandler();
		for(EFileMenu eMenuItem : EFileMenu.values()){
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.setToolTipText(eMenuItem.getToolTip());
			this.add(menuItem);
		}
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void createNew(){
		if(checkSave()){
			this.drawingPanel.clearShapes();
			this.file = null;
		}
	}

	public void open() {
		if(checkSave()){
			JFileChooser chooser = new JFileChooser(this.dir);
			int result = chooser.showOpenDialog(this.drawingPanel);

			if(result == JFileChooser.APPROVE_OPTION){
				this.drawingPanel.clearShapes();
				this.dir = chooser.getCurrentDirectory();
				this.file = chooser.getSelectedFile();
				MFile mfile = new MFile();
				Object shapes = mfile.load(file);
				this.drawingPanel.setShapes(shapes);
			}
		}
	}

	private void save(){
		if (this.file == null) {
			this.saveAs();
		} else {
			MFile mFile = new MFile();
			mFile.store(this.drawingPanel.getShapes(), this.file);
			this.drawingPanel.setUpdated(false);
		}
	}

	public void saveAs() {
		JFileChooser chooser = new JFileChooser(this.dir);
		int result = chooser.showSaveDialog(this.drawingPanel);
		if(result == JFileChooser.APPROVE_OPTION){
			this.dir = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			checkOverlap();
		}
	}

	public void close(){
		if(checkSave()){
			this.drawingPanel.clearShapes();
		}
	}

	public void quit(){
		if(checkSave()){
			System.exit(0);
		}
	}

	public boolean checkSave(){
		if(drawingPanel.isUpdated()){
			int ans = JOptionPane.showConfirmDialog(drawingPanel, "변경사항이 존재합니다. 저장하시겠습니까?\n" + "저장하지 않으면 변경사항이 유실됩니다.", "confirm", JOptionPane.YES_NO_CANCEL_OPTION);
			if(ans == JOptionPane.YES_OPTION) this.save();
			else if(ans == JOptionPane.NO_OPTION) return true;
			else return false;
		}
		return true;
	}

	public void checkOverlap(){
		if(file.exists()){
			JOptionPane.showMessageDialog(this.drawingPanel, "중복된 파일이 존재합니다.", "error", JOptionPane.ERROR_MESSAGE);
			System.out.println("파일이 중복됨");
			saveAs();
		} else {
			save();
		}
	}

	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EFileMenu.eNew.name())){
				createNew();
			} else if(e.getActionCommand().equals(EFileMenu.eOpen.name())){
				open();
			} else if(e.getActionCommand().equals(EFileMenu.eSave.name())){
				save();
			} else if(e.getActionCommand().equals(EFileMenu.eSaveAs.name())){
				saveAs();
			} else if(e.getActionCommand().equals(EFileMenu.eClose.name())){
				close();
			} else if(e.getActionCommand().equals(EFileMenu.eQuit.name())){
				quit();
			}

		}
	}
}

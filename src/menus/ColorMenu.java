package menus;

import frames.DrawingPanel;
import frames.MenuBar;
import global.Constants;

import javax.swing.*;
import global.Constants.EColorMenuItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorMenu extends JMenu {

  private DrawingPanel drawingPanel;

  public ColorMenu(String text) {
    super(text);
    ActionHandler actionHandler = new ActionHandler();
    for(EColorMenuItem eColorMenuItem : EColorMenuItem.values()) {
      JMenuItem menuItem = new JMenuItem(eColorMenuItem.getText());
      menuItem.setActionCommand(eColorMenuItem.name());
      menuItem.setToolTipText(eColorMenuItem.getToolTip());
      menuItem.addActionListener(actionHandler);
      this.add(menuItem);
    }
  }

  public void associate(DrawingPanel drawingPanel) {
    this.drawingPanel = drawingPanel;

  }

  public void initialize() {
  }

  private void setLineColor() {
    JColorChooser chooser=new JColorChooser();
    @SuppressWarnings("static-access")
    Color selectedColor = chooser.showDialog(null, "Color", Color.BLACK);

    this.drawingPanel.setLineColor(selectedColor);
  }

  private void setFillColor() {
    JColorChooser chooser=new JColorChooser();
    @SuppressWarnings("static-access")
    Color selectedColor = chooser.showDialog(null, "Color", Color.BLACK);

    this.drawingPanel.setFillColor(selectedColor);
  }


  private class ActionHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      EColorMenuItem eColorMenuItem = EColorMenuItem.valueOf(e.getActionCommand());
      switch(eColorMenuItem) {

        case eLineColor:
          setLineColor();
          break;

        case eFillColor:
          setFillColor();
          break;

        default:
          break;
      }
    }
  }

}

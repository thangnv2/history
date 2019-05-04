package demo2.menu;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import demo2.uity.Common;
import rabitmq.draw.model.Item;

public class MenuData extends JFrame {

	private static final long serialVersionUID = 1L;

	public MenuData(Item item) {
		super();
		setBounds(Common.width_main, 0, 300, 200);
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(10);
		layout.setVgap(10);
		setLayout(layout);
		add(new JLabel("Name: "));
		add(new JLabel(item.getName()));
		add(new JLabel("Note: "));
		add(new JLabel(item.getNote()));
		add(new JLabel("status: "));
		add(new JLabel(String.valueOf(item.getStatus())));
		setVisible(true);
	}

}

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class mainForm extends JFrame{
	
	public void formInit(){
		final JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		JMenuBar menubar = new JMenuBar();			//Creating of menu bar
		
		JMenu endDay = new JMenu("Завершение дня");				//Creating of menu
		
		JMenuItem _endDay = new JMenuItem("Завершение дня");		//Creating of menu item
		
		JMenu conn = new JMenu("Соединение");
		
		JMenuItem connStop = new JMenuItem("Разорвать соединение");
		JMenuItem exit = new JMenuItem("Выход");
		
		JMenu contr = new JMenu("Договоры");
		
		JMenuItem depContr = new JMenuItem("Депозиты");
		
		exit.addActionListener(new ActionListener(){			//Adding of action listener(makes a component to do something)
			@Override
			public void actionPerformed(ActionEvent event){
				System.exit(0);			//Just exit
			}
			
		});
		
		connStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){			//Return to auth form
				setVisible(false);
				Interface ui = new Interface();
				ui.setVisible(true);
				dispose();
			}
			
		});
		
		depContr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Object[] headers = {"Счёт"};
				JTable depTable = new JTable(dbInteraction.depList(), headers);
				depTable.setBounds(100,100,100,100);
				JScrollPane jscrlp = new JScrollPane(depTable);
		        depTable.setPreferredScrollableViewportSize(new Dimension(250, 100));
		        panel.add(jscrlp);
			}
		});
		
		
		conn.add(connStop);			//Adding a menus to the bar
		conn.add(exit);
		
		endDay.add(_endDay);			//Adding an items
		
		menubar.add(conn);
		menubar.add(contr);
		menubar.add(endDay);
		
		contr.add(depContr);
		
		setJMenuBar(menubar);		//Setting the menu bar
		
		panel.setLayout(null);
		setTitle("Завершение дня");
		setSize(700,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}

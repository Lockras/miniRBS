import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Interface extends JFrame {
	
	public Interface(){
		initUI();
	}
	
	
	private void initUI(){
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		panel.setLayout(null);
		
		final JTextField loginTxt = new JTextField();
		loginTxt.setColumns(10);
		loginTxt.setBounds(80,30,120,25);
		
		final JTextField passTxt = new JTextField();
		passTxt.setColumns(10);
		passTxt.setBounds(80,60,120,25);
		
		JLabel loginLabel = new JLabel("Логин");
		loginLabel.setBounds(20, 30, 100, 25);
		
		JLabel passLabel = new JLabel("Пароль");
		passLabel.setBounds(20, 60, 100, 25);
		
		final JLabel errLabel = new JLabel("Ошибка доступа");
		errLabel.setVisible(false);
		errLabel.setBounds(90, 120, 100, 25);
		
		JButton loginBtn = new JButton("Войти");
		loginBtn.setBounds(100, 100, 80, 25);
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				if (dbInteraction.userAuth(loginTxt.getText(), passTxt.getText())){
					setVisible(false);
					mainForm form = new mainForm();
					form.formInit();
					form.setVisible(true);
					dispose();
				}
				else{
					errLabel.setVisible(true);
					return;
				}
			}
		});
		
		panel.add(loginBtn);
		panel.add(loginTxt);
		panel.add(passTxt);
		panel.add(loginLabel);
		panel.add(passLabel);
		panel.add(errLabel);
		
		setTitle("Авторизация");
		setSize(290,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}

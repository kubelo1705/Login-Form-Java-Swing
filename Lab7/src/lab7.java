import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;

public class lab7 extends JFrame {
	private JTextField txtNotice;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private static HashMap<String, String> accounts;
	private JTextField txtUsernameError;
	private JTextField txtPasswordError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lab7 frame = new lab7();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		accounts = new HashMap<String, String>();
	}

	/**
	 * Create the frame.
	 */
	public lab7() {
		setTitle("\u0110\u0102NG NH\u1EACP");
		setLocationRelativeTo(null);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblInfo = new JLabel(
				"TR\u1EA6N T\u1EA4N PH\u00C1T");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(lblInfo);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(10, 31, 99, 13);
		panel.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsername.setBounds(218, 23, 255, 30);
		panel.add(txtUsername);
		txtUsername.setColumns(255);

		txtUsernameError = new JTextField();
		txtUsernameError.setEditable(false);
		txtUsernameError.setBounds(228, 53, 234, 19);
		txtUsernameError.setVisible(false);
		revalidate();
		panel.add(txtUsernameError);
		txtUsernameError.setColumns(255);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(10, 31, 105, 13);
		panel_1.add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword.setBounds(218, 23, 256, 30);
		panel_1.add(txtPassword);

		txtPasswordError = new JTextField();
		txtPasswordError.setEditable(false);
		txtPasswordError.setBounds(228, 53, 239, 19);
		panel_1.add(txtPasswordError);
		txtPasswordError.setColumns(255);
		txtPasswordError.setVisible(false);
		revalidate();

		JPanel panel_1_1 = new JPanel();
		getContentPane().add(panel_1_1);

		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accountString = checkUsernamePassword();
				if (accountString.length() != 0) {
					String[] s = accountString.split("\\s");
					String usernameString = s[0];
					String passwordString = s[1];
					accounts=readDataFromFile();
					if (accounts.containsKey(usernameString)) {
						txtUsernameError.setVisible(true);
						revalidate();
						txtUsernameError.setText("Username already used.");
						txtNotice.setVisible(true);
						revalidate();
						txtNotice.setText("Register error.");
					} else {
						writeDataToFile(usernameString, passwordString);
						txtNotice.setVisible(true);
						revalidate();
						txtNotice.setText("Register successfully");
						txtPassword.setText("");
						txtUsername.setText("");
						
					}
				}
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegister.setBounds(111, 26, 111, 36);
		panel_1_1.add(btnRegister);

		JButton btnSignIn = new JButton("SIGN IN");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accountString = checkUsernamePassword();
				if (accountString.length() != 0) {
					String[] s = accountString.split("\\s");
					String usernameString = s[0];
					String passwordString = s[1];
					accounts=readDataFromFile();
					try {
						if (accounts.get(usernameString).equals(passwordString)) {
							txtNotice.setVisible(true);
							revalidate();
							txtNotice.setText("Sign up successfully.");
							txtPassword.setText("");
							txtUsername.setText("");
						} else {
							txtNotice.setVisible(true);
							revalidate();
							txtNotice.setText("Invalid login or password.");
						}
					} catch (Exception e1) {
						txtNotice.setVisible(true);
						revalidate();
						txtNotice.setText("Invalid login or password.");
					}
				}
			}
		});

		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(328, 26, 111, 36);
		panel_1_1.add(btnSignIn);

		JPanel panel_1_1_1 = new JPanel();
		getContentPane().add(panel_1_1_1);
		panel_1_1_1.setLayout(null);

		txtNotice = new JTextField();
		txtNotice.setForeground(Color.RED);
		txtNotice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNotice.setEditable(false);
		txtNotice.setVisible(false);
		revalidate();
		txtNotice.setBounds(10, 10, 566, 39);
		panel_1_1_1.add(txtNotice);
		txtNotice.setColumns(10);
		txtNotice.setHorizontalAlignment(JTextField.CENTER);
	}

	public boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + ".{8,20}$";

		Pattern p = Pattern.compile(regex);

		if (password == null) {
			return false;
		}

		Matcher m = p.matcher(password);

		return m.matches();
	}

	public boolean isValidUsername(String name) {
		String regex = "^[A-Za-z]\\w{5,29}$";

		Pattern p = Pattern.compile(regex);

		if (name == null) {
			return false;
		}

		Matcher m = p.matcher(name);

		return m.matches();
	}

	public String checkUsernamePassword() {
		txtUsernameError.setVisible(false);
		txtPasswordError.setVisible(false);
		txtNotice.setVisible(false);
		revalidate();
		String username = "";
		String password = "";
		if (txtUsername.getText().length() != 0 && String.valueOf(txtPassword.getPassword()).length() != 0) {
			if (isValidUsername(txtUsername.getText())) {
				username = txtUsername.getText();
			} else {
				txtUsernameError.setVisible(true);
				txtUsernameError.setText("Username is invalid.");
				return "";
			}
			if (isValidPassword(String.valueOf(txtPassword.getPassword()))) {
				password = String.valueOf(txtPassword.getPassword());
			} else {
				txtPasswordError.setVisible(true);
				txtPasswordError.setText("Password is invalid.");
				return "";
			}
			return username + " " + password;
		} else {
			txtNotice.setText("Please enter your full username and password.");
			txtNotice.setVisible(true);
			return "";
		}
	}

	public HashMap<String, String> readDataFromFile() {
		String fileName = "src/accounts.txt";
		HashMap<String, String> accounts = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.trim().split(" ");
				accounts.put(attributes[0], attributes[1]);
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return accounts;
	}

	public void writeDataToFile(String userName, String password) {
		String fileName = "src/accounts.txt";
		BufferedWriter br;
		try {
			br = new BufferedWriter(new FileWriter(fileName,true));
			br.write(userName + " " + password + "\n");
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class MyFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel input, key;
	private JTextField inputText, inputKey;
	private JButton encrypt, decrypt, clear;
	private JTextPanel panel;
	DES des;
	String result = null;
	byte[] keys = null;

	public MyFrame() {
		super("DES");
		getRootPane().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.BLACK));
		setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		// -----------defintions---------------//
		inputText = new JTextField(20);
		inputKey = new JTextField(20);
		encrypt = new JButton("ENCRYPT");
		decrypt = new JButton("DECRYPT");
		input = new JLabel("INPUT(hexadecimal): ");
		key = new JLabel("KEY");
		panel = new JTextPanel();
		clear = new JButton("CLEAR OUTPUT");

		// ---border--------------//

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(input, gc);

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(inputText, gc);

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.gridy = 1;

		gc.anchor = GridBagConstraints.LINE_END;
		add(key, gc);

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(inputKey, gc);

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		add(encrypt, gc);

		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		add(decrypt, gc);

		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 20, 0);
		add(panel, gc);

		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridx = 1;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(clear, gc);

		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panel.textArea.setText("");
				inputText.setText("");
				inputKey.setText("");
			}

		});

	}

	/**
	 * This method is used to check whether the given input is hexadecimal or not.
	 * 
	 * @param String
	 * @return bool
	 */
	public static boolean isHexadecimal(String text) {
		Objects.requireNonNull(text);
		if (text.length() < 1)
			throw new IllegalArgumentException("Text cannot be empty.");

		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
				'C', 'D', 'E', 'F' };

		for (char symbol : text.toCharArray()) {
			boolean found = false;
			for (char hexDigit : hexDigits) {
				if (symbol == hexDigit) {
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String oriText = inputText.getText().toString();
		if (isHexadecimal(oriText)) {
			if (e.getSource() == encrypt) {

				String key = DES.convertStringToHex(inputKey.getText().toString());
				keys = DES.parseBytes(key);
				panel.textArea.append("Key:  " + DES.hex(keys) + "\n");
				panel.textArea.append("ORIGINAL TEXT:  " + oriText.toUpperCase() + "\n");

				byte[] ptext;

				if (oriText.length() % 32 != 0) {
					while (oriText.length() % 32 != 0) {
						oriText = oriText.concat("0");
					}
				}

				ptext = DES.parseBytes(oriText);

				result = DES.hex(DES.encryptCBC(ptext, keys));

				panel.textArea.append("Encryption result:  " + result);

			}

			if (e.getSource() == decrypt) {
				byte[] encResult = DES.parseBytes(result);
				String decResult = DES.hex(DES.decryptCBC(encResult, keys));
				panel.textArea.append("\nDecryption result:  " + decResult + "\n");
			}

		} else {
			panel.textArea.setText("".toString());
			panel.textArea.append("Enter the valid hexadecimal input");
		}

	}
}

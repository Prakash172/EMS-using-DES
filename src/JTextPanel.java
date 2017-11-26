import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
	/** This is used for creating a panel containing the textArea to display output. 
	 * @author erprakash
	 */

public class JTextPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public JTextArea textArea;

	public JTextPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 200;
		dim.width = 480;
		setPreferredSize(dim);

		setBorder(BorderFactory.createTitledBorder("OUTPUT"));
		textArea = new JTextArea(10, 40);
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(new JScrollPane(textArea));
	}

}

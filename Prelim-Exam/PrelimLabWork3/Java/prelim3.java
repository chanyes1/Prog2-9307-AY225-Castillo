//1. Ask for inputs
	   //- Number of attendance
	   //- Lab Work 1 grade
	   //- Lab Work 2 grade
       //- Lab Work 3 grade
//2. Compute for:
//	Lab Work Average
//		La=(L1+L2+L3)/3
//	Class Standing
//		CS= 0.4att + 0.6 La
//	Prelim Grade
//		PG=0.7exam + 0.3CS
//			Find "exam" for "Passing PG=75" and "Excellent Ex=100"
//3. Output the:
//	- attendance
//	- all lab work grades
//	- lab work average 
//	- class standing
//	- prelim exam score needed for passing and excellent
//4. Extra Requirements
//	Java
//		use Scanner class
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class prelim3 {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrelimFrame().setVisible(true));
	}

	static class PrelimFrame extends JFrame {
		private final JTextField attendanceField = new JTextField(6);
		private final JTextField l1Field = new JTextField(6);
		private final JTextField l2Field = new JTextField(6);
		private final JTextField l3Field = new JTextField(6);

		private final JTextArea resultsArea = new JTextArea();
		private final DecimalFormat df = new DecimalFormat("#.##");

		PrelimFrame() {
			super("Prelim Grade Calculator");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(540, 360);
			setLocationRelativeTo(null);

			JPanel input = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(6, 6, 6, 6);
			c.anchor = GridBagConstraints.WEST;

			int row = 0;
			addLabeled(input, "Attendance (0-100):", attendanceField, c, row++);
			addLabeled(input, "Lab Work 1 (0-100):", l1Field, c, row++);
			addLabeled(input, "Lab Work 2 (0-100):", l2Field, c, row++);
			addLabeled(input, "Lab Work 3 (0-100):", l3Field, c, row++);

			attendanceField.setToolTipText("Enter attendance as a number from 0 to 100");
			l1Field.setToolTipText("Enter Lab Work 1 grade (0-100)");
			l2Field.setToolTipText("Enter Lab Work 2 grade (0-100)");
			l3Field.setToolTipText("Enter Lab Work 3 grade (0-100)");

			// Visual polish: background and spacing
			input.setBackground(new Color(235, 245, 255));
			resultsArea.setBackground(new Color(30, 34, 40));
			resultsArea.setForeground(new Color(230, 230, 230));
			resultsArea.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

			// Allow Enter to trigger calculation from fields
			attendanceField.addActionListener(e -> onCalculate());
			l1Field.addActionListener(e -> onCalculate());
			l2Field.addActionListener(e -> onCalculate());
			l3Field.addActionListener(e -> onCalculate());

			JButton calcBtn = new JButton("Calculate");
			JButton clearBtn = new JButton("Clear");

			JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			btnPanel.add(clearBtn);
			btnPanel.add(calcBtn);

			// Button colors
			calcBtn.setBackground(new Color(0, 123, 255));
			calcBtn.setForeground(Color.WHITE);
			calcBtn.setOpaque(true);
			calcBtn.setBorderPainted(false);
			clearBtn.setBackground(new Color(220, 53, 69));
			clearBtn.setForeground(Color.WHITE);
			clearBtn.setOpaque(true);
			clearBtn.setBorderPainted(false);

			resultsArea.setEditable(false);
			resultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			JScrollPane sp = new JScrollPane(resultsArea);

			Container cp = getContentPane();
			cp.setLayout(new BorderLayout(8, 8));
			cp.add(input, BorderLayout.NORTH);
			cp.add(sp, BorderLayout.CENTER);
			cp.add(btnPanel, BorderLayout.SOUTH);

			calcBtn.addActionListener(e -> onCalculate());
			clearBtn.addActionListener(e -> onClear());
			getRootPane().setDefaultButton(calcBtn);
		}

		private void addLabeled(JPanel p, String label, JTextField field, GridBagConstraints c, int row) {
			c.gridx = 0; c.gridy = row; c.weightx = 0;
			JLabel lbl = new JLabel(label);
			lbl.setForeground(new Color(20, 60, 120));
			lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
			p.add(lbl, c);
			c.gridx = 1; c.gridy = row; c.weightx = 1;
			field.setBackground(Color.WHITE);
			p.add(field, c);
		}

		private void onClear() {
			attendanceField.setText("");
			l1Field.setText("");
			l2Field.setText("");
			l3Field.setText("");
			resultsArea.setText("");
		}

		private void onCalculate() {
			double attendance, l1, l2, l3;
			try {
				attendance = parseField(attendanceField, "attendance");
				l1 = parseField(l1Field, "Lab Work 1");
				l2 = parseField(l2Field, "Lab Work 2");
				l3 = parseField(l3Field, "Lab Work 3");
			} catch (NumberFormatException ex) {
				return;
			}

			// Validate ranges 0-100
			if (!inRange(attendance, 0, 100)) {
				JOptionPane.showMessageDialog(this, "Attendance must be between 0 and 100.", "Out of range", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!inRange(l1, 0, 100) || !inRange(l2, 0, 100) || !inRange(l3, 0, 100)) {
				JOptionPane.showMessageDialog(this, "All lab grades must be between 0 and 100.", "Out of range", JOptionPane.ERROR_MESSAGE);
				return;
			}

			double la = (l1 + l2 + l3) / 3.0;
			double cs = 0.4 * attendance + 0.6 * la;

			double examForPassing = (75.0 - 0.3 * cs) / 0.7;
			double examForExcellent = (100.0 - 0.3 * cs) / 0.7;

			StringBuilder sb = new StringBuilder();
			sb.append("--- Results ---\n");
			sb.append("Attendance: " + df.format(attendance) + "\n");
			sb.append("Lab Work 1: " + df.format(l1) + "\n");
			sb.append("Lab Work 2: " + df.format(l2) + "\n");
			sb.append("Lab Work 3: " + df.format(l3) + "\n");
			sb.append("Lab Work Average: " + df.format(la) + "\n");
			sb.append("Class Standing: " + df.format(cs) + "\n");

			// Prelim exam input/results removed; show only required exam scores
			sb.append("Prelim Exam: (handled separately)\n");

			sb.append("Exam needed for Passing (PG=75): " + df.format(examForPassing) + "\n");
			sb.append("Exam needed for Excellent (PG=100): " + df.format(examForExcellent) + "\n");

			resultsArea.setText(sb.toString());
		}

		private boolean inRange(double v, double min, double max) {
			return v >= min && v <= max;
		}

		private double parseField(JTextField f, String name) throws NumberFormatException {
			String s = f.getText().trim();
			if (s.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please enter " + name + ".", "Input required", JOptionPane.WARNING_MESSAGE);
				throw new NumberFormatException("Missing " + name);
			}
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid number for " + name + ": '" + s + "'", "Invalid input", JOptionPane.ERROR_MESSAGE);
				throw ex;
			}
		}
	}
}
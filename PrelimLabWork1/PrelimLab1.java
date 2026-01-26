import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.swing.*;

/**
 * PrelimLab1 - Attendance Tracker
 *
 * Displays a JFrame with labeled fields for Attendance Name, Course/Year,
 * Time In (auto-populated with system date/time) and an E-Signature
 * (generated programmatically using UUID).
 *
 * Compile: javac PrelimLab1.java
 * Run:     java PrelimLab1
 */
public class PrelimLab1 {
	public static void main(String[] args) {
		// Ensure GUI work runs on the Event Dispatch Thread
		SwingUtilities.invokeLater(PrelimLab1::createAndShowGUI);
	}

	/**
	 * Create and show the main attendance window.
	 */
	private static void createAndShowGUI() {
		final JFrame frame = new JFrame("Attendance Tracker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// Top-level layout: form on the left, log on the right
		frame.getContentPane().setLayout(new BorderLayout(12, 12));

		// Use GridBagLayout for readable alignment of labels/fields
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		formPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Labels and fields
		JLabel nameLabel = new JLabel("Attendance Name:");
		JTextField nameField = new JTextField(20);

		JLabel courseLabel = new JLabel("Course/Year:");
		JTextField courseField = new JTextField(20);

		JLabel timeLabel = new JLabel("Time In:");
		JTextField timeField = new JTextField(20);
		timeField.setEditable(false); // auto-updated on submit

		JLabel signLabel = new JLabel("E-Signature:");
		JTextField signField = new JTextField(36);
		signField.setEditable(false);
		signField.setVisible(false);

		// Apply improved styling to text fields for better visibility
		styleField(nameField);
		styleField(courseField);
		styleField(timeField);
		styleField(signField);

		// Row 0 - Name
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		formPanel.add(nameLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0; // allow field to grow
		formPanel.add(nameField, gbc);

		// Row 1 - Course/Year
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		formPanel.add(courseLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		formPanel.add(courseField, gbc);

		// Row 2 - Time In
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		formPanel.add(timeLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		formPanel.add(timeField, gbc);

		// Row 3 - E-Signature label (hidden initially)
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0;
		formPanel.add(signLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		formPanel.add(signField, gbc);

		// Row 4 - Buttons: Submit and Show Signature
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
		buttonRow.setOpaque(false);

		JButton submitBtn = new JButton("Submit Attendance");
		JButton showSigBtn = new JButton("Show E-Signature");
		styleButton(submitBtn);
		styleButton(showSigBtn);

		buttonRow.add(submitBtn);
		buttonRow.add(showSigBtn);
		formPanel.add(buttonRow, gbc);
		gbc.gridwidth = 1; // reset

		// Attendance log (right side)
		JTextArea logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		JScrollPane logScroll = new JScrollPane(logArea);
		logScroll.setBorder(BorderFactory.createTitledBorder("Attendance Log"));
		logScroll.setPreferredSize(new Dimension(360, 320));

		// Initial welcome text
		logArea.append("Time\t\tName\tCourse\tE-Signature\n");
		logArea.append("---------------------------------------------------------------------\n");

		// Wire up actions
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		submitBtn.addActionListener(e -> {
			String name = nameField.getText().trim();
			String course = courseField.getText().trim();
			if (name.isEmpty() || course.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter both Name and Course/Year.", "Missing Data",
					JOptionPane.WARNING_MESSAGE);
				return;
			}

			String timeNow = LocalDateTime.now().format(fmt);
			String eSignature = UUID.randomUUID().toString();
			timeField.setText(timeNow);
			signField.setText(eSignature);
			signField.setVisible(true);

			// Append a compact, readable log entry
			String entry = String.format("%s\t%s\t%s\t%s\n", timeNow, name, course, eSignature);
			logArea.append(entry);

			// clear input fields for next entry
			nameField.setText("");
			courseField.setText("");
			frame.revalidate();
		});

		showSigBtn.addActionListener(e -> {
			signField.setVisible(true);
			frame.pack();
			JOptionPane.showMessageDialog(frame, "E-Signature:\n" + signField.getText(), "E-Signature",
				JOptionPane.INFORMATION_MESSAGE);
		});

		// Put it together
		JPanel content = new JPanel(new BorderLayout(12, 12));
		content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		content.setBackground(new Color(0xF4F6F8));
		formPanel.setBackground(Color.WHITE);
		content.add(formPanel, BorderLayout.CENTER);
		content.add(logScroll, BorderLayout.EAST);

		frame.getContentPane().add(content, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(980, 420);
		frame.setMinimumSize(new Dimension(720, 360));
		frame.setVisible(true);
	}

	/**
	 * Apply consistent, readable styling to text fields.
	 */
	private static void styleField(JTextField field) {
		field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		field.setBackground(Color.WHITE);
		field.setForeground(Color.DARK_GRAY);
		field.setPreferredSize(new Dimension(field.getPreferredSize().width, 28));
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180, 180, 180)),
				BorderFactory.createEmptyBorder(4, 6, 4, 6)));
		field.setOpaque(true);
	}

	private static void styleButton(JButton btn) {
		btn.setFocusPainted(false);
		btn.setFont(new Font("SansSerif", Font.BOLD, 13));
		btn.setBackground(new Color(0x667eea));
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x5a67d8)),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
	}
}


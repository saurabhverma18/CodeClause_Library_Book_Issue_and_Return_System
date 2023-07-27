import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LibrarySystem extends Frame implements ActionListener {
	Label lblTitle, lblAuthor, lblYear, lblStatus, lblName;
	TextField txtTitle, txtAuthor, txtYear, txtName;
	Button btnIssue, btnReturn;
	TextArea txtArea;

	public LibrarySystem() {
		setLayout(new GridLayout(6, 2));
		setTitle("Library Book Issue and Return System");
		setSize(400, 300);

		lblTitle = new Label("Book Title:");
		add(lblTitle);

		txtTitle = new TextField(20);
		add(txtTitle);

		lblAuthor = new Label("Author:");
		add(lblAuthor);

		txtAuthor = new TextField(20);
		add(txtAuthor);

		lblYear = new Label("Year Published:");
		add(lblYear);

		txtYear = new TextField(4);
		add(txtYear);

		lblName = new Label("Issued/Returned by:");
		add(lblName);

		txtName = new TextField(69);
		add(txtName);

		btnIssue = new Button("Issue");
		add(btnIssue);
		btnIssue.addActionListener(this);

		btnReturn = new Button("Return");
		add(btnReturn);
		btnReturn.addActionListener(this);

		lblStatus = new Label("Status:");
		add(lblStatus);

		txtArea = new TextArea(5, 20);
		add(txtArea);

		setVisible(true);
		addWindowListener(new MyWindowAdapter());
	}

	public void actionPerformed(ActionEvent e) {
		String title = txtTitle.getText();
		String author = txtAuthor.getText();
		int year = Integer.parseInt(txtYear.getText());
		String name = txtName.getText();
		String action = e.getActionCommand();

		if (action.equals("Issue")) {
			issueBook(name, title, author, year);
			txtArea.setText("Book: " + title + ", " + author + " (" + year + "), is issued by " + name + ".");
		} else if (action.equals("Return")) {
			returnBook(name, title, author, year);
			txtArea.setText("Book: " + title + ", " + author + " (" + year + "), is returned by " + name + ".");
		}
	}

	private void issueBook(String name, String title, String author, int year) {
		try {
			File file = new File("books.txt");
			FileWriter writer = new FileWriter(file, true);
			writer.write(title + ", " + author + ", " + year + ", is issued to " + name + ".\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("Exception Caught" + e);
		}
	}

	private void returnBook(String name, String title, String author, int year) {
		try {
			File file = new File("books.txt");
			File tempFile = new File("temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String[] bookInfo = currentLine.split(", ");
				if (bookInfo[0].equals(title) && bookInfo[1].equals(author) && bookInfo[2].equals(String.valueOf(year))
						&& bookInfo[3].equals("is issued to " + name + ".")) {
					writer.write(title + ", " + author + ", " + year + ", is returned by " + name + ".\n");
				} else {
					writer.write(currentLine + "\n");
				}
			}

			writer.close();
			reader.close();

			file.delete();
			tempFile.renameTo(file);
		} catch (IOException e) {
			System.out.println("Exception Caught" + e);
		}
	}

	public static void main(String args[]) {
		new LibrarySystem();
	}
}

class MyWindowAdapter extends WindowAdapter {
	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}
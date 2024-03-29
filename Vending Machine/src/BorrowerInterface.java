import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Set;


class BorrowerInterface extends JFrame {
    private final JTextField nicTextField;
    private final JTextField isbnTextField;
    private final JButton borrowButton;
    private final JButton returnButton;
    private final JLabel availableQuantity;
    private final JTextArea bookDetailsArea; 
    private final Library library;

    public BorrowerInterface(Library library) {
        this.library = library;

        setTitle("Book Wending Machine - Samudra Book Shop");
        setSize(300, 250); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

               
        nicTextField = new JTextField(20);
        isbnTextField = new JTextField(15);
        borrowButton = new JButton("Borrow Book");
        returnButton = new JButton("Return Book");
        availableQuantity = new JLabel("0");
        bookDetailsArea = new JTextArea(5, 20); 
        bookDetailsArea.setEditable(false);

        borrowButton.addActionListener((ActionEvent e) -> {
            borrowBook();
        });

        returnButton.addActionListener((ActionEvent e) -> {
            returnBook();
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter NIC: "));
        panel.add(nicTextField);
        panel.add(new JLabel("Enter Book ISBN: "));
        panel.add(isbnTextField);
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(new JLabel("                  Available Quantity: "));
        panel.add(availableQuantity);
        panel.add(new JLabel("    "));
        
        panel.add(new JLabel("   Book Details: "));
        panel.add(new JScrollPane(bookDetailsArea)); 
       
        add(panel);

        updateAvailableQuantity(); 
    }

    private void borrowBook() {
        String nic = nicTextField.getText();
        String isbn = isbnTextField.getText();

        if (!isValidNIC(nic) || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid NIC and ISBN.");
            return;
        }

        Set<Book> books = library.getAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getQuantity() > 0) {
                    book.updateQuantity(book.getQuantity() - 1);
                    book.addBorrower(nic);
                    JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Sorry, the book is out of stock.");
                }
                updateAvailableQuantity();
                displayBookDetails(book); // Display book details
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Book with ISBN " + isbn + " not found.");
        clearBookDetails(); // Clear book details if book is not found
    }

    private void returnBook() {
        String nic = nicTextField.getText();
        String isbn = isbnTextField.getText();

        if (!isValidNIC(nic) || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid NIC and ISBN.");
            return;
        }

        Set<Book> books = library.getAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getBorrowers().contains(nic)) {
                    book.updateQuantity(book.getQuantity() + 1);
                    book.removeBorrower(nic);
                    JOptionPane.showMessageDialog(this, "Book returned successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "You did not borrow this book.");
                }
                updateAvailableQuantity();
                displayBookDetails(book); // Display book details
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Book with ISBN " + isbn + " not found.");
        clearBookDetails(); // Clear book details if book is not found
    }

    private void updateAvailableQuantity() {
        String isbn = isbnTextField.getText();
        Set<Book> books = library.getAllBooks();

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                availableQuantity.setText(Integer.toString(book.getQuantity()));
                return;
            }
        }
        
        // If the book is not found, display a error message.
        availableQuantity.setText("Book not found");
        clearBookDetails(); // Clear book details if book is not found
    }

    private void displayBookDetails(Book book) {
        // Display book details in the JTextArea
        String details = "Title: " + book.getTitle() + "\n"
                + "Author: " + book.getAuthor() + "\n"
                + "Quantity: " + book.getQuantity() + "\n"
                + "Borrowers: " + book.getBorrowers().toString();
        bookDetailsArea.setText(details);
    }

    private void clearBookDetails() {
        // Clear the TextArea for book details.
        bookDetailsArea.setText("");
    }

    private boolean isValidNIC(String nic) {
        // NIC should be a number and it has a length of 12.
        return nic.matches("\\d{12}");
    }
}
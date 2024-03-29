import javax.swing.SwingUtilities;

    
public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books to the library
        Book book1 = new Book("012", "Book 1", "Author 1", 10);
        Book book2 = new Book("345", "Book 2", "Author 2", 5);
        Book book3 = new Book("678", "Book 3", "Author 3", 4);
        Book book4 = new Book("910", "Book 4", "Author 2", 8);
        Book book5 = new Book("111", "Book 5", "Author 1", 12);
        
              
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Display the GUI for the borrower
        SwingUtilities.invokeLater(() -> {
            new BorrowerInterface(library).setVisible(true);
        });
    }
}
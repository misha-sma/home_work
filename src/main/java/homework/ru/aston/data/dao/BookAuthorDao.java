package homework.ru.aston.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import homework.ru.aston.data.DBConnection;

public class BookAuthorDao {
	private static final String ADD_BOOK_AUTHOR_SQL = "INSERT INTO books_authors(id_book, id_author) VALUES (?, ?)";

	public static void addBooksAuthors(int idBook, List<Integer> authorsIds) {
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(ADD_BOOK_AUTHOR_SQL)) {
			for (Integer idAuthor : authorsIds) {
				ps.setInt(1, idBook);
				ps.setInt(2, idAuthor);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

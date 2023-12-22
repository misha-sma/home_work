package homework.ru.aston.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import homework.ru.aston.data.DBConnection;
import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import homework.ru.aston.data.entity.Book;

public class BookDao {
	private static final String ADD_BOOK_SQL = "INSERT INTO books(title, id_publisher) VALUES (?, ?) RETURNING id_book";

	private static final String GET_BOOKS_BY_AUTHORS = "select authors.name as author_name, t2.title, t2.name as publisher_name from authors left join "
			+ "(select books_authors.id_author, t1.title, t1.name from books_authors inner join "
			+ "(select books.id_book, books.title, publishers.name from books inner join publishers on books.id_publisher=publishers.id_publisher) as t1 "
			+ "on books_authors.id_book=t1.id_book) as t2 "
			+ "on authors.id_author=t2.id_author order by authors.name, t2.title";

	public static void addBook(Book book) {
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(ADD_BOOK_SQL)) {
			ps.setString(1, book.getTitle());
			ps.setInt(2, book.getIdPublisher());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				book.setIdBook(rs.getInt("id_book"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<AuthorBookPublisherDto> getAllBooks() {
		List<AuthorBookPublisherDto> result = new ArrayList<AuthorBookPublisherDto>();
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(GET_BOOKS_BY_AUTHORS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				result.add(new AuthorBookPublisherDto(rs.getString("author_name"), rs.getString("title"),
						rs.getString("publisher_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}

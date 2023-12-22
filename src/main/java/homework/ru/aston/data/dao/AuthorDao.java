package homework.ru.aston.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import homework.ru.aston.data.DBConnection;
import homework.ru.aston.data.entity.Author;

public class AuthorDao {
	private static final String GET_AUTHOR_BY_NAME_SQL = "SELECT * FROM authors WHERE name ILIKE ?";
	private static final String ADD_AUTHOR_SQL = "INSERT INTO authors(name) VALUES (?) RETURNING id_author";

	public static int addAuthor(String name) {
		int idAuthor = 0;
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(ADD_AUTHOR_SQL)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idAuthor = rs.getInt("id_author");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idAuthor;
	}

	public static Author getAuthorByName(String name) {
		Author result = null;
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(GET_AUTHOR_BY_NAME_SQL)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Author(rs.getInt("id_author"), rs.getString("name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}

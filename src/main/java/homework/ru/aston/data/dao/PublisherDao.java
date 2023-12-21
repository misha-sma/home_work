package homework.ru.aston.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import homework.ru.aston.data.DBConnection;
import homework.ru.aston.data.entity.Publisher;

public class PublisherDao {
	private static final String GET_PUBLISHER_BY_NAME_SQL = "SELECT * FROM publishers WHERE name ILIKE ?";
	private static final String ADD_PUBLISHER_SQL = "INSERT INTO publishers(name) VALUES (?) RETURNING id_publisher";

	public static int addPublisher(String name) {
		int idPublisher = 0;
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(ADD_PUBLISHER_SQL)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				idPublisher = rs.getInt("id_publisher");
			}
			rs.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idPublisher;
	}

	public static Publisher getPublisherByName(String name) {
		Publisher result = null;
		try (Connection con = DBConnection.getDbConnection();
				PreparedStatement ps = con.prepareStatement(GET_PUBLISHER_BY_NAME_SQL)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Publisher(rs.getInt("id_publisher"), rs.getString("name"));
			}
			rs.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}

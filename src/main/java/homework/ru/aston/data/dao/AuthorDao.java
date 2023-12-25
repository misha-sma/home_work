package homework.ru.aston.data.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import homework.ru.aston.data.entity.Author;
import homework.ru.aston.util.HibernateSessionFactoryUtil;

public class AuthorDao {

	public static Author addAuthor(String name) {
		Author author = new Author(0, name, null);
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(author);
		tx.commit();
		session.close();
		return author;
	}

	public static Author getAuthorByName(String name) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Author author = session.byNaturalId(Author.class).using("name", name).load();
		session.close();
		return author;
	}
}

package homework.ru.aston.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import homework.ru.aston.data.entity.Publication;
import homework.ru.aston.util.HibernateSessionFactoryUtil;

public class PublicationDao {
	private static final String GET_PUBLICATIONS_BY_AUTHORS = "select authors.name as author_name, t2.title, t2.name as publisher_name, t2.isbn, t2.issn, t2.d_type from authors left join "
			+ "(select publications_authors.id_author, t1.title, t1.name, t1.isbn, t1.issn, t1.d_type from publications_authors inner join "
			+ "(select publications.id_publication, publications.title, publications.isbn, publications.issn, publications.d_type, publishers.name from publications inner join publishers on publications.id_publisher=publishers.id_publisher) as t1 "
			+ "on publications_authors.id_publication=t1.id_publication) as t2 "
			+ "on authors.id_author=t2.id_author order by authors.name, t2.title";

	public static void addPublication(Publication book) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.merge(book);
		tx.commit();
		session.close();
	}

	public static List<AuthorBookPublisherDto> getAllPublications() {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		List<AuthorBookPublisherDto> result = session
				.createNativeQuery(GET_PUBLICATIONS_BY_AUTHORS, AuthorBookPublisherDto.class).list();
		session.close();
		return result;
	}

	public static List<Publication> getPolymorphBooks() {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		List<Publication> publications = session
				.createQuery("select p from Publication p join fetch p.authors", Publication.class).getResultList();
		session.close();
		return publications;
	}
}

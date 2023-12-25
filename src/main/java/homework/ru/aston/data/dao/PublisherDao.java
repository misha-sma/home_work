package homework.ru.aston.data.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import homework.ru.aston.data.entity.Publisher;
import homework.ru.aston.util.HibernateSessionFactoryUtil;

public class PublisherDao {

	public static int addPublisher(String name) {
		Publisher publisher = new Publisher(0, name);
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(publisher);
		tx.commit();
		session.close();
		return publisher.getIdPublisher();
	}

	public static Publisher getPublisherByName(String name) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Publisher publisher = session.byNaturalId(Publisher.class).using("name", name).load();
		session.close();
		return publisher;
	}
}

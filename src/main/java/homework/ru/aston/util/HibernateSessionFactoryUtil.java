package homework.ru.aston.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import homework.ru.aston.data.entity.Author;
import homework.ru.aston.data.entity.Book;
import homework.ru.aston.data.entity.EntityForIndex;
import homework.ru.aston.data.entity.Magazine;
import homework.ru.aston.data.entity.Publication;
import homework.ru.aston.data.entity.Publisher;

public class HibernateSessionFactoryUtil {
	private static SessionFactory sessionFactory;

	private HibernateSessionFactoryUtil() {
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration().configure();
				configuration.addAnnotatedClass(Author.class);
				configuration.addAnnotatedClass(Publisher.class);
				configuration.addAnnotatedClass(Publication.class);
				configuration.addAnnotatedClass(Book.class);
				configuration.addAnnotatedClass(Magazine.class);
				configuration.addAnnotatedClass(EntityForIndex.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}

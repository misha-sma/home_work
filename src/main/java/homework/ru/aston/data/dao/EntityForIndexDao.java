package homework.ru.aston.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import homework.ru.aston.data.entity.EntityForIndex;
import homework.ru.aston.util.HibernateSessionFactoryUtil;

public class EntityForIndexDao {
	public static void saveEntities(List<EntityForIndex> entities) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (EntityForIndex entity : entities) {
			session.persist(entity);
		}
		tx.commit();
		session.close();
	}

	public static List<EntityForIndex> getBigEntities(int value) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
		List<EntityForIndex> entities = session
				.createQuery("select e from EntityForIndex e where e.value > :value", EntityForIndex.class)
				.setParameter("value", value).getResultList();
		session.close();
		return entities;
	}
}

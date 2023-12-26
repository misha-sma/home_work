package homework.ru.aston.util;

import java.util.List;

import homework.ru.aston.data.dao.AuthorDao;
import homework.ru.aston.data.dao.EntityForIndexDao;
import homework.ru.aston.data.entity.EntityForIndex;

public class EntitiesGetter {
	public static void main(String[] args) {
		AuthorDao.getAuthorByName("qwerty");
		long startTime = System.currentTimeMillis();
		List<EntityForIndex> entities = EntityForIndexDao.getBigEntities(499900);
		System.out.println(
				"Get " + entities.size() + " entities. Time=" + (System.currentTimeMillis() - startTime) + " ms");
	}
}

package homework.ru.aston.util;

import java.util.ArrayList;
import java.util.List;

import homework.ru.aston.data.dao.EntityForIndexDao;
import homework.ru.aston.data.entity.EntityForIndex;

public class EntitiesGenerator {
	public static final int BATCH_SIZE = 1000;

	public static void main(String[] args) {
		int n = 10000000;
		List<EntityForIndex> entities = new ArrayList<EntityForIndex>();
		for (int i = 0; i < n; ++i) {
			EntityForIndex entity = new EntityForIndex();
			entity.setValue((int) (Math.random() * 1000000) - 500000);
			entities.add(entity);
			if (entities.size() >= BATCH_SIZE) {
				EntityForIndexDao.saveEntities(entities);
				System.out.println("Save " + (i + 1) + " entities");
				entities = new ArrayList<EntityForIndex>();
			}
		}
		if (!entities.isEmpty()) {
			EntityForIndexDao.saveEntities(entities);
			System.out.println("Save " + n + " entities");
		}
		System.out.println("ENNNDDDD!!!!!");
	}
}

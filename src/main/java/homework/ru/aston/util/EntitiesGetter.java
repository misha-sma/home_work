package homework.ru.aston.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import homework.ru.aston.data.entity.EntityForIndex;
import homework.ru.aston.data.repository.AuthorRepository;
import homework.ru.aston.data.repository.EntityForIndexRepository;

public class EntitiesGetter {
	@Autowired
	private EntityForIndexRepository entityForIndexRepository;

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		EntitiesGetter entitiesGetter = new EntitiesGetter();
		entitiesGetter.authorRepository.findByName("qwerty");
		long startTime = System.currentTimeMillis();
		List<EntityForIndex> entities = entitiesGetter.entityForIndexRepository.findByValueGreaterThan(499900);
		System.out.println(
				"Get " + entities.size() + " entities. Time=" + (System.currentTimeMillis() - startTime) + " ms");
	}
}

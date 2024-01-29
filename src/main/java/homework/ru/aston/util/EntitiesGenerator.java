package homework.ru.aston.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import homework.ru.aston.Starter;
import homework.ru.aston.data.entity.EntityForIndex;
import homework.ru.aston.data.repository.EntityForIndexRepository;

@SpringBootApplication
public class EntitiesGenerator {
	public static final int BATCH_SIZE = 1000;

	@Autowired
	private EntityForIndexRepository entityForIndexRepository;

	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
		EntitiesGenerator entitiesGenerator = new EntitiesGenerator();
		int n = 10000000;
		List<EntityForIndex> entities = new ArrayList<EntityForIndex>();
		for (int i = 0; i < n; ++i) {
			EntityForIndex entity = new EntityForIndex();
			entity.setValue((int) (Math.random() * 1000000) - 500000);
			entities.add(entity);
			if (entities.size() >= BATCH_SIZE) {
				entitiesGenerator.entityForIndexRepository.saveAll(entities);
				System.out.println("Save " + (i + 1) + " entities");
				entities = new ArrayList<EntityForIndex>();
			}
		}
		if (!entities.isEmpty()) {
			entitiesGenerator.entityForIndexRepository.saveAll(entities);
			System.out.println("Save " + n + " entities");
		}
		System.out.println("ENNNDDDD!!!!!");
	}
}

package homework.ru.aston.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import homework.ru.aston.data.entity.EntityForIndex;

@Repository
public interface EntityForIndexRepository extends JpaRepository<EntityForIndex, Integer> {
	public List<EntityForIndex> findByValueGreaterThan(int value);
}

package homework.ru.aston.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import homework.ru.aston.data.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
	public Author findByName(String name);
}

package homework.ru.aston.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import homework.ru.aston.data.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {
	@Query("select p from Publication p join fetch p.authors")
	public List<Publication> getPolymorphBooks();
}

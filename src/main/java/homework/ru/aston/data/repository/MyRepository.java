package homework.ru.aston.data.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class MyRepository {
	public static final String GET_PUBLICATIONS_BY_AUTHORS = "select row_number() over(), * from "
			+"(select authors.name as author_name, t2.title, t2.name as publisher_name, t2.isbn, t2.issn, t2.d_type from authors left join "
			+ "(select publications_authors.id_author, t1.title, t1.name, t1.isbn, t1.issn, t1.d_type from publications_authors inner join "
			+ "(select publications.id_publication, publications.title, publications.isbn, publications.issn, publications.d_type, publishers.name from publications inner join publishers on publications.id_publisher=publishers.id_publisher) as t1 "
			+ "on publications_authors.id_publication=t1.id_publication) as t2 "
			+ "on authors.id_author=t2.id_author order by authors.name, t2.title) as t10";

	@PersistenceContext
	private EntityManager em;

	public List<AuthorBookPublisherDto> getAllPublications() {
		Query query = em.createNamedQuery("MyRepository.getAllPublications", AuthorBookPublisherDto.class);
		return query.getResultList();
	}
}

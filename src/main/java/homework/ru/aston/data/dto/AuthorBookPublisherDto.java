package homework.ru.aston.data.dto;

import org.hibernate.annotations.NamedNativeQuery;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import homework.ru.aston.data.repository.MyRepository;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@NamedNativeQuery(name = "MyRepository.getAllPublications", query = MyRepository.GET_PUBLICATIONS_BY_AUTHORS, resultSetMapping = "Mapping.AuthorBookPublisherDto")

@SqlResultSetMapping(name = "Mapping.AuthorBookPublisherDto", entities = {
		@EntityResult(entityClass = AuthorBookPublisherDto.class, fields = {
				@FieldResult(name = "rowNumber", column = "row_number"),
				@FieldResult(name = "authorName", column = "author_name"),
				@FieldResult(name = "title", column = "title"),
				@FieldResult(name = "publisherName", column = "publisher_name"),
				@FieldResult(name = "isbn", column = "isbn"), @FieldResult(name = "issn", column = "issn"),
				@FieldResult(name = "dType", column = "d_type"), }) })

@Entity
public class AuthorBookPublisherDto {
	@Id
	private int rowNumber;
	private String authorName;
	private String title;
	private String publisherName;
	private String isbn;
	private String issn;
	private String dType;
}

package homework.ru.aston.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorBookPublisherDto {
	private String authorName;
	private String title;
	private String publisherName;
	private String isbn;
	private String issn;
	private String dType;
}

package homework.ru.aston.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorBookPublisher {
	private String authorName;
	private String title;
	private String publisherName;
}

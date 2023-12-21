package homework.ru.aston.data.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBookEntity {
	private String title;
	private String publisherName;
	private List<String> authors;
}

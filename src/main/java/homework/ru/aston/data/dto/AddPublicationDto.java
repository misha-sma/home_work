package homework.ru.aston.data.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPublicationDto {
	private String title;
	private String publisherName;
	private List<String> authors;
	private String type;
	private String isbn;
	private String issn;
}

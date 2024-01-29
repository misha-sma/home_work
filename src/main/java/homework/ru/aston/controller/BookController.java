package homework.ru.aston.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import homework.ru.aston.data.dto.AddPublicationDto;
import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import homework.ru.aston.data.entity.Author;
import homework.ru.aston.data.entity.Book;
import homework.ru.aston.data.entity.Magazine;
import homework.ru.aston.data.entity.Publication;
import homework.ru.aston.data.entity.Publisher;
import homework.ru.aston.data.repository.AuthorRepository;
import homework.ru.aston.data.repository.MyRepository;
import homework.ru.aston.data.repository.PublicationRepository;
import homework.ru.aston.data.repository.PublisherRepository;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class BookController {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private MyRepository myRepository;

	@GetMapping("/")
	public ResponseEntity<String> getHomePage() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Главная страница приложения с сервлетами");
		builder.append("\n</body>\n</html>");
		return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
	}

	@GetMapping("/books")
	protected ResponseEntity<String> getAllBooks(HttpServletResponse resp) throws IOException {
		List<AuthorBookPublisherDto> books = myRepository.getAllPublications();
		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Все книги сгруппированные по авторам\n<br>\n");
		builder.append("<table border=\"1\">\n");
		builder.append(
				"<tr><th>Автор</th><th>Название книги</th><th>Издательство</th><th>ISBN</th><th>ISSN</th><th>Тип</th></tr>\n");
		for (AuthorBookPublisherDto book : books) {
			builder.append("<tr><td>" + book.getAuthorName() + "</td><td>" + book.getTitle() + "</td><td>"
					+ book.getPublisherName() + "</td><td>" + book.getIsbn() + "</td><td>" + book.getIssn()
					+ "</td><td>" + book.getDType() + "</td></tr>\n");
		}
		builder.append("</table>\n");
		builder.append("</body>\n</html>");
		return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
	}

	@PostMapping("/books")
	public ResponseEntity<String> addBook(@RequestBody String body) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		AddPublicationDto addPublicationDto = mapper.readValue(body, AddPublicationDto.class);
		Publication publication = null;
		if ("book".equalsIgnoreCase(addPublicationDto.getType())) {
			Book book = new Book();
			book.setIsbn(addPublicationDto.getIsbn());
			publication = book;
		} else {
			Magazine magazine = new Magazine();
			magazine.setIssn(addPublicationDto.getIssn());
			publication = magazine;
		}
		Publisher publisher = publisherRepository.findByName(addPublicationDto.getPublisherName());
		int idPublisher = publisher == null
				? publisherRepository.save(new Publisher(0, addPublicationDto.getPublisherName())).getIdPublisher()
				: publisher.getIdPublisher();
		publication.setIdPublisher(idPublisher);
		publication.setTitle(addPublicationDto.getTitle());
		List<Author> authors = new ArrayList<Author>();
		for (String authorName : addPublicationDto.getAuthors()) {
			Author author = authorRepository.findByName(authorName);
			if (author == null) {
				author = new Author(0, authorName, null);
			}
			authors.add(author);
		}
		publication.setAuthors(authors);
		publicationRepository.save(publication);

		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Книга успешно добавлена");
		builder.append("\n</body>\n</html>");
		return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
	}

	@GetMapping("/books-polymorph")
	public ResponseEntity<String> getBooksPolymorph() {
		List<Publication> publications = publicationRepository.getPolymorphBooks();
		List<Book> books = publications.stream().filter(p -> p instanceof Book).map(p -> (Book) p)
				.collect(Collectors.toList());
		List<Magazine> magazines = publications.stream().filter(p -> p instanceof Magazine).map(p -> (Magazine) p)
				.collect(Collectors.toList());
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");

		builder.append("Книги\n<br>\n");
		builder.append("<table border=\"1\">\n");
		builder.append("<tr><th>Название книги</th><th>Авторы</th><th>ISBN</th></tr>\n");
		for (Book book : books) {
			builder.append("<tr><td>" + book.getTitle() + "</td><td>"
					+ book.getAuthors().stream().map(a -> a.getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("")
					+ "</td><td>" + book.getIsbn() + "</td></tr>\n");
		}
		builder.append("</table>\n");

		builder.append("<br>Журналы\n<br>\n");
		builder.append("<table border=\"1\">\n");
		builder.append("<tr><th>Название журнала</th><th>Авторы</th><th>ISSN</th></tr>\n");
		for (Magazine magazine : magazines) {
			builder.append("<tr><td>" + magazine.getTitle() + "</td><td>"
					+ magazine.getAuthors().stream().map(a -> a.getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("")
					+ "</td><td>" + magazine.getIssn() + "</td></tr>\n");
		}
		builder.append("</table>\n");
		builder.append("</body>\n</html>");
		return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
	}

}

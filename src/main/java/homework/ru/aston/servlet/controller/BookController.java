package homework.ru.aston.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.http.;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import homework.ru.aston.data.dao.AuthorDao;
import homework.ru.aston.data.dao.PublicationDao;
import homework.ru.aston.data.dao.PublisherDao;
import homework.ru.aston.data.dto.AddPublicationDto;
import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import homework.ru.aston.data.entity.Author;
import homework.ru.aston.data.entity.Book;
import homework.ru.aston.data.entity.Magazine;
import homework.ru.aston.data.entity.Publication;
import homework.ru.aston.data.entity.Publisher;
import jakarta.servlet.http.HttpServletResponse;

//import homework.ru.aston.servlet.HttpServletRequest;
//import homework.ru.aston.servlet.HttpServletResponse;

@RestController
//@Controller
public class BookController {

//	@GetMapping("/")
//	public String getHomePage(HttpServletResponse response) {
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		StringBuilder builder = new StringBuilder();
//		builder.append("<!doctype html><html lang=\"ru\">\n");
//		builder.append("<head><meta charset=\"utf-8\"></head>");
//		builder.append("<body>\n");
//		builder.append("Главная страница приложения с сервлетами");
//		builder.append("\n</body>\n</html>");
////		PrintWriter printWriter = resp.getWriter();
////		printWriter.write(builder.toString());
////		printWriter.close(); 
//	return builder.toString();
//	}

//	@GetMapping("/")
//	public void getHomePage(HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/html");
//		StringBuilder builder = new StringBuilder();
//		builder.append("<html>\n<body>\n");
//		builder.append("Главная страница приложения с сервлетами");
//		builder.append("\n</body>\n</html>");
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write(builder.toString());
//		printWriter.close();
//	}

	@GetMapping("/")
	public ResponseEntity<String> getHomePage() {
//		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Главная страница приложения с сервлетами");
		builder.append("\n</body>\n</html>");
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write(builder.toString());
//		printWriter.close();
		return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
	}
	
	@GetMapping("/books")
	protected String getAllBooks(HttpServletResponse resp) throws IOException {
		List<AuthorBookPublisherDto> books = PublicationDao.getAllPublications();
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
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
		return builder.toString();
	}

//	@PostMapping(value="/books", headers ="Content-Type=application/json;charset=UTF-8")
	@PostMapping("/books")
	public void addBook(@RequestBody String body, HttpServletResponse resp) throws IOException {
//	String body = req.getReader().lines().collect(Collectors.joining("\n"));
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
		Publisher publisher = PublisherDao.getPublisherByName(addPublicationDto.getPublisherName());
		int idPublisher = publisher == null ? PublisherDao.addPublisher(addPublicationDto.getPublisherName())
				: publisher.getIdPublisher();
		publication.setIdPublisher(idPublisher);
		publication.setTitle(addPublicationDto.getTitle());
		List<Author> authors = new ArrayList<Author>();
		for (String authorName : addPublicationDto.getAuthors()) {
			Author author = AuthorDao.getAuthorByName(authorName);
			if (author == null) {
				author = new Author(0, authorName, null);
			}
			authors.add(author);
		}
		publication.setAuthors(authors);
		PublicationDao.addPublication(publication);

		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Книга успешно добавлена");
		builder.append("\n</body>\n</html>");
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}

	@GetMapping("/books-polymorph")
	public void getBooksPolymorph(HttpServletResponse resp) throws IOException {
		List<Publication> publications = PublicationDao.getPolymorphBooks();
		List<Book> books = publications.stream().filter(p -> p instanceof Book).map(p -> (Book) p)
				.collect(Collectors.toList());
		List<Magazine> magazines = publications.stream().filter(p -> p instanceof Magazine).map(p -> (Magazine) p)
				.collect(Collectors.toList());
		resp.setContentType("text/html");
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
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}

	@GetMapping("/test-jsp")
	public ModelAndView testJsp() {
		return new ModelAndView("jsp/index.jsp");
	}

}

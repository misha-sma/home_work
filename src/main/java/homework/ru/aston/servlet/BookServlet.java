package homework.ru.aston.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import homework.ru.aston.data.dao.AuthorDao;
import homework.ru.aston.data.dao.BookAuthorDao;
import homework.ru.aston.data.dao.BookDao;
import homework.ru.aston.data.dao.PublisherDao;
import homework.ru.aston.data.dto.AddBookDto;
import homework.ru.aston.data.entity.Author;
import homework.ru.aston.data.dto.AuthorBookPublisherDto;
import homework.ru.aston.data.entity.Book;
import homework.ru.aston.data.entity.Publisher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<AuthorBookPublisherDto> books = BookDao.getAllBooks();
		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Все книги сгруппированные по авторам\n<br>\n");
		builder.append("<table border=\"1\">\n");
		builder.append("<tr><th>Автор</th><th>Название книги</th><th>Издательство</th></tr>\n");
		for (AuthorBookPublisherDto book : books) {
			builder.append("<tr><td>" + book.getAuthorName() + "</td><td>" + book.getTitle() + "</td><td>"
					+ book.getPublisherName() + "</td></tr>\n");
		}
		builder.append("</table>\n");
		builder.append("</body>\n</html>");
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining("\n"));
		ObjectMapper mapper = new ObjectMapper();
		AddBookDto addBookEntity = mapper.readValue(body, AddBookDto.class);
		Publisher publisher = PublisherDao.getPublisherByName(addBookEntity.getPublisherName());
		int idPublisher = publisher == null ? PublisherDao.addPublisher(addBookEntity.getPublisherName())
				: publisher.getIdPublisher();
		Book book = new Book();
		book.setIdPublisher(idPublisher);
		book.setTitle(addBookEntity.getTitle());
		BookDao.addBook(book);
		List<Integer> authorsIds = new ArrayList<Integer>();
		for (String authorName : addBookEntity.getAuthors()) {
			Author author = AuthorDao.getAuthorByName(authorName);
			int idAuthor = author == null ? AuthorDao.addAuthor(authorName) : author.getIdAuthor();
			authorsIds.add(idAuthor);
		}
		BookAuthorDao.addBooksAuthors(book.getIdBook(), authorsIds);

		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Книга успешно добавлена");
		builder.append("\n</body>\n</html>");
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}
}

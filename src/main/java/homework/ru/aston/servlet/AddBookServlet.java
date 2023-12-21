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
import homework.ru.aston.data.entity.AddBookEntity;
import homework.ru.aston.data.entity.Author;
import homework.ru.aston.data.entity.Book;
import homework.ru.aston.data.entity.Publisher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining("\n"));
		ObjectMapper mapper = new ObjectMapper();
		AddBookEntity addBookEntity = mapper.readValue(body, AddBookEntity.class);
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

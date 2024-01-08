//package homework.ru.aston.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import homework.ru.aston.data.dao.AuthorDao;
//import homework.ru.aston.data.dao.PublicationDao;
//import homework.ru.aston.data.dao.PublisherDao;
//import homework.ru.aston.data.dto.AddPublicationDto;
//import homework.ru.aston.data.entity.Author;
//import homework.ru.aston.data.dto.AuthorBookPublisherDto;
//import homework.ru.aston.data.entity.Book;
//import homework.ru.aston.data.entity.Magazine;
//import homework.ru.aston.data.entity.Publication;
//import homework.ru.aston.data.entity.Publisher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/books")
//public class BookServlet extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		List<AuthorBookPublisherDto> books = PublicationDao.getAllPublications();
//		resp.setContentType("text/html");
//		StringBuilder builder = new StringBuilder();
//		builder.append("<html>\n<body>\n");
//		builder.append("Все книги сгруппированные по авторам\n<br>\n");
//		builder.append("<table border=\"1\">\n");
//		builder.append(
//				"<tr><th>Автор</th><th>Название книги</th><th>Издательство</th><th>ISBN</th><th>ISSN</th><th>Тип</th></tr>\n");
//		for (AuthorBookPublisherDto book : books) {
//			builder.append("<tr><td>" + book.getAuthorName() + "</td><td>" + book.getTitle() + "</td><td>"
//					+ book.getPublisherName() + "</td><td>" + book.getIsbn() + "</td><td>" + book.getIssn()
//					+ "</td><td>" + book.getDType() + "</td></tr>\n");
//		}
//		builder.append("</table>\n");
//		builder.append("</body>\n</html>");
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write(builder.toString());
//		printWriter.close();
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String body = req.getReader().lines().collect(Collectors.joining("\n"));
//		ObjectMapper mapper = new ObjectMapper();
//		AddPublicationDto addPublicationDto = mapper.readValue(body, AddPublicationDto.class);
//		Publication publication = null;
//		if ("book".equalsIgnoreCase(addPublicationDto.getType())) {
//			Book book = new Book();
//			book.setIsbn(addPublicationDto.getIsbn());
//			publication = book;
//		} else {
//			Magazine magazine = new Magazine();
//			magazine.setIssn(addPublicationDto.getIssn());
//			publication = magazine;
//		}
//		Publisher publisher = PublisherDao.getPublisherByName(addPublicationDto.getPublisherName());
//		int idPublisher = publisher == null ? PublisherDao.addPublisher(addPublicationDto.getPublisherName())
//				: publisher.getIdPublisher();
//		publication.setIdPublisher(idPublisher);
//		publication.setTitle(addPublicationDto.getTitle());
//		List<Author> authors = new ArrayList<Author>();
//		for (String authorName : addPublicationDto.getAuthors()) {
//			Author author = AuthorDao.getAuthorByName(authorName);
//			if (author == null) {
//				author = new Author(0, authorName, null);
//			}
//			authors.add(author);
//		}
//		publication.setAuthors(authors);
//		PublicationDao.addPublication(publication);
//
//		resp.setContentType("text/html");
//		StringBuilder builder = new StringBuilder();
//		builder.append("<html>\n<body>\n");
//		builder.append("Книга успешно добавлена");
//		builder.append("\n</body>\n</html>");
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write(builder.toString());
//		printWriter.close();
//	}
//}

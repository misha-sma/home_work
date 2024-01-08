//package homework.ru.aston.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import homework.ru.aston.data.dao.PublicationDao;
//import homework.ru.aston.data.entity.Book;
//import homework.ru.aston.data.entity.Magazine;
//import homework.ru.aston.data.entity.Publication;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/books-polymorph")
//public class PolymorphBookServlet extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		List<Publication> publications = PublicationDao.getPolymorphBooks();
//		List<Book> books = publications.stream().filter(p -> p instanceof Book).map(p -> (Book) p)
//				.collect(Collectors.toList());
//		List<Magazine> magazines = publications.stream().filter(p -> p instanceof Magazine).map(p -> (Magazine) p)
//				.collect(Collectors.toList());
//		resp.setContentType("text/html");
//		StringBuilder builder = new StringBuilder();
//		builder.append("<html>\n<body>\n");
//
//		builder.append("Книги\n<br>\n");
//		builder.append("<table border=\"1\">\n");
//		builder.append("<tr><th>Название книги</th><th>Авторы</th><th>ISBN</th></tr>\n");
//		for (Book book : books) {
//			builder.append("<tr><td>" + book.getTitle() + "</td><td>"
//					+ book.getAuthors().stream().map(a -> a.getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("")
//					+ "</td><td>" + book.getIsbn() + "</td></tr>\n");
//		}
//		builder.append("</table>\n");
//
//		builder.append("<br>Журналы\n<br>\n");
//		builder.append("<table border=\"1\">\n");
//		builder.append("<tr><th>Название журнала</th><th>Авторы</th><th>ISSN</th></tr>\n");
//		for (Magazine magazine : magazines) {
//			builder.append("<tr><td>" + magazine.getTitle() + "</td><td>"
//					+ magazine.getAuthors().stream().map(a -> a.getName()).reduce((s1, s2) -> s1 + ", " + s2).orElse("")
//					+ "</td><td>" + magazine.getIssn() + "</td></tr>\n");
//		}
//		builder.append("</table>\n");
//
//		builder.append("</body>\n</html>");
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write(builder.toString());
//		printWriter.close();
//	}
//
//}

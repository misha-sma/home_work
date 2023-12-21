package homework.ru.aston.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import homework.ru.aston.data.dao.BookDao;
import homework.ru.aston.data.entity.AuthorBookPublisher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getAllBooks")
public class GetAllBooksServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<AuthorBookPublisher> books = BookDao.getAllBooks();
		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Все книги сгруппированные по авторам\n<br>\n");
		builder.append("<table border=\"1\">\n");
		builder.append("<tr><th>Автор</th><th>Название книги</th><th>Издательство</th></tr>\n");
		for (AuthorBookPublisher book : books) {
			builder.append("<tr><td>" + book.getAuthorName() + "</td><td>" + book.getTitle() + "</td><td>"
					+ book.getPublisherName() + "</td></tr>\n");
		}
		builder.append("</table>\n");
		builder.append("</body>\n</html>");
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}
}

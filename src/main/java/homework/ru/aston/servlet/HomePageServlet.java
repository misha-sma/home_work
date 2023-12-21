package homework.ru.aston.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n<body>\n");
		builder.append("Главная страница приложения с сервлетами");
		builder.append("\n</body>\n</html>");
		PrintWriter printWriter = resp.getWriter();
		printWriter.write(builder.toString());
		printWriter.close();
	}
}

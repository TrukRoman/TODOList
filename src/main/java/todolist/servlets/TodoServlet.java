package todolist.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import todolist.models.Item;
import todolist.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Создания/редактирование элементов БД.
 */
public class TodoServlet extends HttpServlet {
    private ValidateService validator = ValidateService.getInstance();

    /**
     * Метод получает из запроса объект Item и добавляет / редактирует его в БД.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        String content = sb.toString();
        StringReader stringReader = new StringReader(content);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Item item = mapper.readValue(stringReader, Item.class);
            validator.addOrUpdateItem(item);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
package todolist.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import todolist.models.FilterJson;
import todolist.models.ItemsJson;
import todolist.validation.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Вывод списка заданий из БД.
 */
public class ShowServlet extends HttpServlet {
    private ValidateService validator = ValidateService.getInstance();

    /**
     * Метод получает список элементов для показа, добавляет их в объект ItemsJson,
     * преобразует в Json строку и передает в ответе.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List list = this.getShowList(req, resp);
            ItemsJson items = new ItemsJson(list);
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, items);
            String json = sw.toString();
            resp.setContentType("text/json");
            PrintWriter pw = new PrintWriter(resp.getOutputStream());
            pw.append(json);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод получает из запроса FilterJson объект, который показывает какие
     * элементы нужно получить в ответе (все или с фильтром).
     * В зависимости от запроса возвращется нужный список элементов.
     */
    private List getShowList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        String content = sb.toString();
        StringReader stringReader = new StringReader(content);

        ObjectMapper mapper = new ObjectMapper();
        FilterJson filterJson = mapper.readValue(stringReader, FilterJson.class);
        List list;
        if (filterJson.isShowAll()) {
            list = this.validator.findAll();
        } else {
            list = this.validator.findFiltered();
        }
        return list;
    }
}

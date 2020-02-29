package todolist.models;

import java.util.List;

/**
 * Класс, который используется в ShowServlet,
 * содержит список элементов, которые необходимо вернуть в ответе.
 */
public class ItemsJson {
    private List items;

    public ItemsJson(List items) {
        this.items = items;
    }

    public ItemsJson() {
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
package todolist.validation;

import todolist.models.Item;
import todolist.store.DbStore;

import java.util.List;

public class ValidateService {
    /**
     * Ссылка на объект MemoryStore, в котором находится хранилище пользователей.
     */
    private final DbStore logic = DbStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final ValidateService INSTANCE = new ValidateService();
    }

    public void addOrUpdateItem(Item item) throws Exception {
        this.logic.addOrUpdateItem(item);
    }

    public List findAll() throws Exception {
        return this.logic.findAll();
    }

    public List findFiltered() throws Exception {
        return this.logic.findFiltered();
    }

    public void removeDone() throws Exception {
        this.logic.removeDone();
    }

    public void removeAll() throws Exception {
        this.logic.removeAll();
    }
}
package todolist.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс определяет задание из списка дел.
 */
public class Item {
    /**
     * Уникальный номер задания.
     */
    private int id;

    /**
     * Описание задания.
     */
    private String desc;

    /**
     * Время создания задания (по умолчанию текущее).
     */
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    /**
     * Статус выполнения задания (по умолчанию - false).
     */
    private boolean done = false;

    public Item() {

    }

    public Item(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

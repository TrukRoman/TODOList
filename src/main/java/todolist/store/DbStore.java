package todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import todolist.models.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс для работы с базой данных.
 */
public class DbStore {
    private static final DbStore INSTANCE = new DbStore();
    private static SessionFactory factory;
    private DbStore() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Метод для добавления / редактирования задания.
     */
    public Item addOrUpdateItem(Item item) {
        return tx(
                session -> {
                    session.saveOrUpdate(item);
                    return item;
                }
        );
    }

    /**
     * Метод возвращает список всех заданий в БД.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("from Item").list()
        );
    }

    /**
     * Метод возвращает список невыполненных заданий из БД.
     */
    public List findFiltered() {
        return this.tx(
                session -> session.createQuery("from Item where done = false").list()
        );
    }
    /**
     * Метод удаляет выполненые задания из БД.
     */
    public void removeDone() {
        this.tx(
                session -> session.createQuery("delete Item where done = true").executeUpdate()
        );
    }

    /**
     * Метод удаляет все задания из БД
     */
    public void removeAll() {
        this.tx(
                session -> session.createQuery("delete from Item").executeUpdate()
        );
    }
}

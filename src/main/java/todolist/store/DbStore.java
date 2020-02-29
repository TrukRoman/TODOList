package todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import todolist.models.Item;

import java.util.List;

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

    /**
     * Метод для добавления / редактирования задания.
     */
    public void addOrUpdateItem(Item item) {
        Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.saveOrUpdate(item);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    /**
     * Метод возвращает список всех заданий в БД.
     */
    public List findAll() {
        Session session = factory.openSession();
        List items = null;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            items = session.createQuery("from Item").list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return items;
    }

    /**
     * Метод возвращает список невыполненных заданий из БД.
     */
    public List findFiltered() {
        Session session = factory.openSession();
        List items = null;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            items = session.createQuery("from Item where done = false").list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return items;
    }

    public void removeDone() {
        Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query q = session.createQuery("delete Item where done = true");
            q.executeUpdate();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public void removeAll() {
        Session session = factory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.createSQLQuery("truncate table Item").executeUpdate();
                tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}

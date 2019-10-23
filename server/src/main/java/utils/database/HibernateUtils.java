package utils.database;

import database.models.Question;
import database.models.Test;
import database.models.TestResult;
import database.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    /**
     * Gets the an instance of hibernate application.utils class
     * @return an instance
     */
    public static HibernateUtils getInstance(){

        if(persistenceUtils == null){
            synchronized (HibernateUtils.class){
                if(persistenceUtils == null){
                    persistenceUtils = new HibernateUtils();
                }
            }
        }
        return persistenceUtils;
    }

    /**
     * Get an object though it we can make sessions with hibernate
     * @return an instance of that object
     */

    public SessionFactory getSessionFactory(){
        return  sessionFactory;
    }

    /**
     * Creates the session factory
     */
    private HibernateUtils(){
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Test.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(TestResult.class)
                .buildSessionFactory();
    }

    private  SessionFactory sessionFactory;
    private static volatile HibernateUtils persistenceUtils = null;
}

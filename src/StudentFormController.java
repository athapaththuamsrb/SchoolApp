import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.io.Serializable;
import java.util.List;


public class StudentFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public TableView tblStudent;
    public TableColumn colId;
    public TableColumn colContact;
    public TableColumn colName;
    public TableColumn colAddress;

    private void loadAllStudents() {
        Configuration configuration =
                new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Student");/*HQL(hibernate query language)*/
                List list = query.list();
        System.out.println(list);
    }
    public void btnSaveStudetOnAction(ActionEvent actionEvent) {
        Student s1= new Student(
                txtId.getText(),
                txtName.getText(),
                txtContact.getText(),
                txtAddress.getText()
        );
        System.out.println(s1);
        Configuration configuration =
                new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Serializable save = session.save(s1);
        System.out.println(save);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void btnUpDateStudetOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteStudetOnAction(ActionEvent actionEvent) {
    }
}

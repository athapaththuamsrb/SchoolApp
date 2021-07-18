import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TableView tblStudents;
    public TableColumn colId;
    public TableColumn colContact;
    public TableColumn colName;
    public TableColumn colAddress;

    public  void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadAllStudents();
    }
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
        ObservableList<StudentTM> observableList = FXCollections.observableArrayList();

        for (Object student : list) {
            observableList.add(new StudentTM(
                    ((Student)student).getId(),((Student)student).getName(),((Student)student).getContact(),((Student)student).getAddress()
            ));
        }

        tblStudents.setItems(observableList);
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
        Student s1 = new Student(
                txtId.getText(),
                txtName.getText(),
                txtContact.getText(),
                txtAddress.getText()
        );

        Configuration configuration =
                new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(s1);
        transaction.commit();
        loadAllStudents();
        session.close();
        sessionFactory.close();
    }

    public void btnDeleteStudetOnAction(ActionEvent actionEvent) {
        Student s1 = new Student(
                txtId.getText(),
                txtName.getText(),
                txtContact.getText(),
                txtAddress.getText()
        );
        Configuration configuration =
                new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Student.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(s1);
        transaction.commit();
        loadAllStudents();
        session.close();
        sessionFactory.close();
    }
}

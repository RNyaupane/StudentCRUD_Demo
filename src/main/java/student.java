import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import jakarta.faces.context.FacesContext;

    @ManagedBean
    @RequestScoped
    public class student {
        String name;
        String address;
        String classes;
        String[] subjects;
        ArrayList studentList ;
        private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Connection connection;

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name=name;
        }
        public String getAddress(){
            return address;
        }
        public void setAddress(String address){
            this.address=address;
        }
        public String getClasses(){
            return classes;
        }
        public void setClasses(String classes){
            this.classes=classes;
        }
        public void setSubjects(String[] subjects) {
            this.subjects = subjects;
        }
        public String[] getSubjects() {
            return subjects;
        }

        //Connection Establish
        public Connection getConnection(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db","root","root");
            }
            catch(Exception e){
                System.out.println(e);
            }
            return connection;
        }
        //fetch all records
        public ArrayList studentList(){
            try{
                studentList= new ArrayList();
                connection = getConnection();
                Statement stmt=getConnection().createStatement();
                ResultSet rs=stmt.executeQuery("select * from users");

                while(rs.next()){
                    student student = new student();
                    student.setName(rs.getString(1));
                    student.setAddress(rs.getString(2));
                    student.setName(rs.getString(3));
                    student.setAddress(rs.getString(3));

                    studentList.add(student);
                }
                connection.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
            return studentList;
        }
        public String save(){
            int result = 0;
            try{
                connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "insert into users(name,email,password,gender,address) values(?,?,?,?,?)");
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, classes);
                stmt.setString(4, Arrays.toString(subjects));
                result = stmt.executeUpdate();
                connection.close();
            }catch(Exception e){
                System.out.println(e);
            }
            if(result !=0)
                return "index.xhtml?faces-redirect=true";
            else return "create.xhtml?faces-redirect=true";

        }
        //DELETE
        public void delete(String name){
            try{
                connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("delete from users where name = "+name);
                stmt.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
            }
        }


    }

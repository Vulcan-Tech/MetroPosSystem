package Model;

public class Employee {
    private int employeeId; // Just to show in staff
    private String name;
    private String email;
    private double salary;
    private String role;

    public Employee(int employeeId,String name,String email,double salary,String role){
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.role = role;
    }
    public Employee() {

    }

    public int getEmployeeId(){
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for 'salary'
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Getter and Setter for 'role'
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}

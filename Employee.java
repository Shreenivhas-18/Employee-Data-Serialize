import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int id;
    private double salary;
    private Date hireDate;

    public Employee() {}

    public Employee(String name, int id, double salary, Date hireDate) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', salary=%.2f, hireDate=%s}",
                id, name, salary, hireDate);
    }
}

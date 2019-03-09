package vikash.kumar.tvsautomobilesolutions.model_pojo;

public class DataModel {
    private String name,post,location,code,date_of_joining,salary;
    public DataModel(){}
    public DataModel(String name, String post, String location, String code, String date_of_joining, String salary) {
        this.name = name;
        this.post = post;
        this.location = location;
        this.code = code;
        this.date_of_joining = date_of_joining;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}

package vanier.com.programmingpatternslia.Model;

import vanier.com.programmingpatternslia.Error.UserError;

public class User {
    private int userId;
    String fullName;
    int age;
    String role;

    public User(int userId, String fullName, int age, String role) {
       if((age < 0 || age > 100) && (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("PARTICIPANT"))) {
           this.userId = userId;
           this.fullName = fullName;
           this.age = age;
           this.role = role.toUpperCase();
       }
       else throw new UserError("Incorrect age or role");
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if(age < 0 || age > 100) throw new UserError("Incorrect age");
        this.age = age;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        if(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("PARTICIPANT"))
            this.role = role;
        else throw new UserError("Incorrect role");
    }
}

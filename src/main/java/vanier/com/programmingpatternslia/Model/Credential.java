package vanier.com.programmingpatternslia.Model;

public class Credential {
    private int cred_ID;
    private int u_ID;
    private String email;
    private String password;

    public Credential(int cred_ID, int u_ID, String email, String password) {
        this.cred_ID = cred_ID;
        this.u_ID = u_ID;
        this.email = email;
        this.password = password;
    }
    public int getCred_ID() {
        return cred_ID;
    }
    public void setCred_ID(int cred_ID) {
        this.cred_ID = cred_ID;
    }
    public int getU_ID() {
        return u_ID;
    }
    public void setU_ID(int u_ID) {
        this.u_ID = u_ID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

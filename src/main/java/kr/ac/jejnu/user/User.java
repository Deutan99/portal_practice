package kr.ac.jejnu.user;

public class User {
    private Long id;
    private String name;
    private String password;



    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

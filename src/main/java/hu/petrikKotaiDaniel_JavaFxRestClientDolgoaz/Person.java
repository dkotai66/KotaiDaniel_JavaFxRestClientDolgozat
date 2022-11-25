package hu.petrikKotaiDaniel_JavaFxRestClientDolgoaz;

import com.google.gson.annotations.Expose;

public class Person {
    @Expose
    private int id;
    @Expose
    private int height;
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private int age;



    public Person( String name, String email, int age, int height, int id) {
        this.height = height;
        this.name = name;
        this.email = String.valueOf(email);
        this.age = age;
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

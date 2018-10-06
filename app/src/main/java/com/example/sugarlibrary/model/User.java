package com.example.sugarlibrary.model;

import com.orm.SugarRecord;

public class User extends SugarRecord<User> {

    /**
     * Camel case words will be saved with an underscore
     * Like userName will be saved as user_name
     */
    public int id;
    public String userName;
    public String password;

    public User() {
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}

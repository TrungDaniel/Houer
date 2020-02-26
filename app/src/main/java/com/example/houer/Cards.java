package com.example.houer;

public class Cards {
    private String userId;
    private String Name;

    public Cards(String userId, String name) {
        this.userId = userId;
        Name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

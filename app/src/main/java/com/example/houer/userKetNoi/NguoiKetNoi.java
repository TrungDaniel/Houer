package com.example.houer.userKetNoi;

public class NguoiKetNoi {
    private String userId;
    private String Name;
    private String profileImageUrl;

    public NguoiKetNoi(String userId, String name, String profileImageUrl) {
        this.userId = userId;
        Name = name;
        this.profileImageUrl = profileImageUrl;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

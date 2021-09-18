package com.xiongz.wanjava.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 登录用户信息
 *
 * @author xiongz
 * @date 2017/12/14
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {

    @Id(autoincrement = true)
    private Long id;
    private boolean admin;
    private int coinCount;
    private String email;
    private String icon;
    private int userId;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    private String username;

    @Generated(hash = 636094633)
    public UserProfile(Long id, boolean admin, int coinCount, String email,
            String icon, int userId, String nickname, String password,
            String publicName, String token, int type, String username) {
        this.id = id;
        this.admin = admin;
        this.coinCount = coinCount;
        this.email = email;
        this.icon = icon;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.publicName = publicName;
        this.token = token;
        this.type = type;
        this.username = username;
    }

    @Generated(hash = 968487393)
    public UserProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAdmin() {
        return this.admin;
    }
}
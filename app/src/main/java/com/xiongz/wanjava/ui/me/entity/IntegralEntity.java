package com.xiongz.wanjava.ui.me.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 积分实体
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class IntegralEntity implements Parcelable {

    private int coinCount;
    private int level;
    private String nickname;
    private String rank;
    private int userId;
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.coinCount);
        dest.writeInt(this.level);
        dest.writeString(this.nickname);
        dest.writeString(this.rank);
        dest.writeInt(this.userId);
        dest.writeString(this.username);
    }

    public void readFromParcel(Parcel source) {
        this.coinCount = source.readInt();
        this.level = source.readInt();
        this.nickname = source.readString();
        this.rank = source.readString();
        this.userId = source.readInt();
        this.username = source.readString();
    }

    public IntegralEntity() {
    }

    protected IntegralEntity(Parcel in) {
        this.coinCount = in.readInt();
        this.level = in.readInt();
        this.nickname = in.readString();
        this.rank = in.readString();
        this.userId = in.readInt();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<IntegralEntity> CREATOR = new Parcelable.Creator<IntegralEntity>() {
        @Override
        public IntegralEntity createFromParcel(Parcel source) {
            return new IntegralEntity(source);
        }

        @Override
        public IntegralEntity[] newArray(int size) {
            return new IntegralEntity[size];
        }
    };
}

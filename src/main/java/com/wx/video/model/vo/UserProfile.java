package com.wx.video.model.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class UserProfile {

    private String avatarUrl;
    private String nickName;

    public UserProfile(String avatarUrl, String nickName) {
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

    public static UserProfile instanceEmptyProfile() {
        return new UserProfile("", "");
    }

    public static UserProfile instanceUserProfile(String avatarUrl, String nickName) {
        return new UserProfile(
                StringUtils.isEmpty(avatarUrl) ? "" : avatarUrl,
                StringUtils.isEmpty(nickName) ? "" : nickName);
    }

}

package com.adg.homeboy.repository.user;

import com.adg.homeboy.repository.model.UserModel;

/**
 * Created by liuxiaoyu on 2018/3/10.
 */

public interface UserStore {

     boolean isUserExits();

     UserModel getLocalUser();

     boolean saveUser(String json);

    String getUserAvatar();

    String getUserName();

    String getToken();

    void clear();
}

package com.adg.homeboy.repository.user;

import com.adg.homeboy.App;
import com.adg.homeboy.repository.model.UserModel;
import com.adg.homeboy.util.LocalJsonReadUtil;
import com.adg.homeboy.util.SharedPreferencesHelper;

/**
 * Created by liuxiaoyu on 2018/3/10.
 */

public class UserJsonStore implements UserStore {

    public static final String USER_STORE_NAME = "user_json";
    SharedPreferencesHelper helpr;
    private static UserJsonStore store;

    public static UserJsonStore getInstance() {
        if (store == null) {
            store = new UserJsonStore();
        }
        return store;
    }

    public UserJsonStore() {
        helpr = SharedPreferencesHelper.getInstance(App.globalContext);
    }

    @Override
    public boolean isUserExits() {
        String json = helpr.getValue(USER_STORE_NAME);
        if (json == null || json.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public UserModel getLocalUser() {
        if (isUserExits()) {
            String json = helpr.getValue(USER_STORE_NAME);
            UserModel user = LocalJsonReadUtil.getModelFromJson(json, UserModel.class);
            return user;
        }
        return null;
    }

    @Override
    public boolean saveUser(String json) {
        helpr.setValue(USER_STORE_NAME, json);
        return true;
    }

    @Override
    public String getUserAvatar() {
        UserModel user = getLocalUser();
        if(user != null){
            return user.avatar;
        }
        return null;
    }

    @Override
    public String getUserName() {
        UserModel user = getLocalUser();
        if(user != null){
            return user.nickname;
        }
        return null;
    }

    @Override
    public String getToken() {
        UserModel user = getLocalUser();
        if(user != null){
            return user.token;
        }
        return null;
    }

    @Override
    public void clear() {
        helpr.remove(USER_STORE_NAME);
    }
}

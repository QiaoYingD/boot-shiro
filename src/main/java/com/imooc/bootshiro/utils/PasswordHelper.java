package com.imooc.bootshiro.utils;

import com.imooc.bootshiro.model.UserModel;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

    private static final String algorithmName="md5";

    private static final Integer hashIterations=2;

    public static void encryptPassword(UserModel userModel){

        String newPassword = new SimpleHash(algorithmName, userModel.getPassword(), ByteSource.Util.bytes(userModel.getUserName()), hashIterations).toHex();

        userModel.setPassword(newPassword);
    }


}

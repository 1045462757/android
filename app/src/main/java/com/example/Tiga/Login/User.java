package com.example.Tiga.Login;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {

    private String Account;

    private String PassWord;

    private String Name;

    private String Sex;

    private int Age;

    private String Introduction;

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    @Override
    public String toString() {
        return "账号:"+getAccount()+"  密码:"+getPassWord();
    }
}

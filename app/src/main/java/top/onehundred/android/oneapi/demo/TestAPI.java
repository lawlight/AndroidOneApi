package top.onehundred.android.oneapi.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import top.onehundred.android.oneapi.OneAPI;

public class TestAPI extends OneAPI {

    public String user;

    public List<Repo> list = new ArrayList<>();

    @Override
    public String getHostname() {
        return "https://api.github.com/";
    }

    @Override
    public String getUrl() {
        return "users/" + user + "/repos";
    }

    @Override
    public void putInputs() {

    }

    @Override
    public void parseOutput(String output) throws Exception {
        list.clear();
        list.addAll(JSON.parseArray(output, Repo.class));
    }
}

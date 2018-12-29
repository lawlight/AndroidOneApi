package top.onehundred.android.oneapi.demo;

public class Repo {
    public String id;
    public String node_id;
    public String name;
    public String full_name;

    @Override
    public String toString() {
        return id + "=" + name;
    }
}

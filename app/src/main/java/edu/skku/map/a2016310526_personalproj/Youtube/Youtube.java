package edu.skku.map.a2016310526_personalproj.Youtube;

public class Youtube {
    private String id;
    private String key;
    private String name;
    private String size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Youtube(String id, String key, String name, String size) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.size = size;
    }
}

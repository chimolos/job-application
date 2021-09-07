package com.chidee.back.appuser.uploadtypes;

public class ResponseFile {
    private String url;
    private String name1;
    private String type1;
    private long size1;
    private String name2;
    private String type2;
    private long size2;

    public ResponseFile(String url, String name1, String type1, long size1, String name2, String type2, long size2) {
        this.url = url;
        this.name1 = name1;
        this.type1 = type1;
        this.size1 = size1;
        this.name2 = name2;
        this.type2 = type2;
        this.size2 = size2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public long getSize1() {
        return size1;
    }

    public void setSize1(long size1) {
        this.size1 = size1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public long getSize2() {
        return size2;
    }

    public void setSize2(long size2) {
        this.size2 = size2;
    }
}

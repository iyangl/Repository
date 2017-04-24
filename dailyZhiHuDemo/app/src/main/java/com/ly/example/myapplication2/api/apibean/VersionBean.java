package com.ly.example.myapplication2.api.apibean;

public class VersionBean {

    /**
     * 0 代表软件为最新版本，1 代表软件需要升级
     */
    private int status;
    /**
     * 软件最新版本的版本号（数字的第二段会比最新的版本号低 1）
     */
    private String latest;
    /**
     * 仅出现在软件需要升级的情形下，提示用户升级软件的对话框中显示的消息
     */
    private String msg;
    /**
     * 最新版本url
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
}

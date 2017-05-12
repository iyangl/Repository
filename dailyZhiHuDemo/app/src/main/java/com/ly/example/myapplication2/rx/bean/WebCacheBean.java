package com.ly.example.myapplication2.rx.bean;

import java.util.List;

public class WebCacheBean {

    private int id;
    private int cssCount;
    private int jsCount;
    private List<String> cssLinks;
    private List<String> jsLinks;
    private CSSCacheBean cssCacheBean;
    private JSCacheBean jsCacheBean;
    private String htmlData;

    public String getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData;
    }

    public CSSCacheBean getCssCacheBean() {
        return cssCacheBean;
    }

    public void setCssCacheBean(CSSCacheBean cssCacheBean) {
        this.cssCacheBean = cssCacheBean;
    }

    public JSCacheBean getJsCacheBean() {
        return jsCacheBean;
    }

    public void setJsCacheBean(JSCacheBean jsCacheBean) {
        this.jsCacheBean = jsCacheBean;
    }

    public int getCssCount() {
        return cssCount;
    }

    public void setCssCount(int cssCount) {
        this.cssCount = cssCount;
    }

    public int getJsCount() {
        return jsCount;
    }

    public void setJsCount(int jsCount) {
        this.jsCount = jsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCssLinks() {
        return cssLinks;
    }

    public void setCssLinks(List<String> cssLinks) {
        this.cssLinks = cssLinks;
    }

    public List<String> getJsLinks() {
        return jsLinks;
    }

    public void setJsLinks(List<String> jsLinks) {
        this.jsLinks = jsLinks;
    }

    public static class CSSCacheBean {

        private int id;
        private String cssLink;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCssLink() {
            return cssLink;
        }

        public void setCssLink(String cssLink) {
            this.cssLink = cssLink;
        }
    }

    public static class JSCacheBean {
        private int id;
        private String jsLink;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJsLink() {
            return jsLink;
        }

        public void setJsLink(String jsLink) {
            this.jsLink = jsLink;
        }
    }
}

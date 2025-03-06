package com.gatosapp;

public class Gatos {

    private String id;
    private String url;
    private String apiKey = "live_0eA3K0CvN0JbAOukVELkQlF9KgdvVm4PpBdYbqvrgd5xGOVZSDu2Ac64hjVMiOd7";
    private String images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

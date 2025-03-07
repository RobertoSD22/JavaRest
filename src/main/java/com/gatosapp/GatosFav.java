package com.gatosapp;

public class GatosFav {

    private String id;
    private String image_id;
    private String api_key = "live_0eA3K0CvN0JbAOukVELkQlF9KgdvVm4PpBdYbqvrgd5xGOVZSDu2Ac64hjVMiOd7";
    private Imagex image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public Imagex getImage() {
        return image;
    }

    public void setImage(Imagex image) {
        this.image = image;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}

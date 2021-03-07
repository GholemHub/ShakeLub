package com.example.shakelab;

public class Note {
    private String shakeName;
    private int countOfLayers;
    private String shakeImage;

    public Note(){}

   /* public Note(String shakeName, String ingredients,int layers){
        this.shakeName = shakeName;

        this.countOfLayers = layers;
    }
    public Note(String shakeName, String ingredients,int layers, String shakeImage){
        this.shakeName = shakeName;
        this.shakeImage = shakeImage;
        this.countOfLayers = layers;
    }*/
    public Note(String shakeName,int layers, String shakeImage){
        this.shakeName = shakeName;
        this.shakeImage = shakeImage;
        this.countOfLayers = layers;
    }

    public String getShakeName()
    {
        return shakeName;
    }
    public String getshakeImage()
    {
        return shakeImage;
    }
    public int getCountOfLayers() {
        return countOfLayers;
    }

    public void setShakeName(String shakeName){
        this.shakeName = shakeName;
    }

    public void setShakeImage(String shakeImage){
        this.shakeImage = shakeImage;
    }

    public void setCountOfLayers(int countOfLayers){
        this.countOfLayers = countOfLayers;
    }

}

package com.example.shakelab;

public class NewShakeClass {
    private String Name;
    private String Image;
    private String CountOfIngredients;

    public NewShakeClass(){}

    public NewShakeClass(String name, String image, String countOfIngredients) {
        Name = name;
        Image = image;
        CountOfIngredients = countOfIngredients;
    }

    public NewShakeClass(String name, String image) {
        Name = name;
        Image = image;
    }

    public NewShakeClass(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCountOfIngredients() {
        return CountOfIngredients;
    }

    public void setCountOfIngredients(String countOfIngredients) {
        CountOfIngredients = countOfIngredients;
    }
}

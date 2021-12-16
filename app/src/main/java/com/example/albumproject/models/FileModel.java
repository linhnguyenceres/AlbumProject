package com.example.albumproject.models;

import com.example.albumproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileModel {
    public String name;
    public Integer url;
    public String date;

   public FileModel(String name, Integer url,String date){
        this.name = name;
        this.url = url;
        this.date = date;
   }

   public static FileModel[] fakeData(){
        FileModel[] list = new FileModel[5];
        list[0] = new FileModel("Anh 1", R.mipmap.ic_example,"16/12/2021");
        list[1] = new FileModel("Anh 2", R.mipmap.ic_example,"16/12/2021");
        list[2] = new FileModel("Anh 3", R.mipmap.ic_example,"16/12/2021");
        list[3] = new FileModel("Anh 4", R.mipmap.ic_example,"16/12/2021");
        list[4] = new FileModel("Anh 5", R.mipmap.ic_example,"16/12/2021");

        return list;
   }
}

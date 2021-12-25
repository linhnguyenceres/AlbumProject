package com.example.albumproject.models;

import com.example.albumproject.data.ImageData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileMainModel {
    public LocalDate date;
    public String dateDisplay;
    public ArrayList<ImageData> files;

    public FileMainModel(LocalDate date) {
        this.date = date;
        this.dateDisplay = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(date);
        this.files = new ArrayList<>();
    }
}

package com.example.aibooksummaryapp.Model;

import java.io.Serializable;

public class Book implements Serializable {

    private String id;
    private VolumeInfo volumeInfo;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}

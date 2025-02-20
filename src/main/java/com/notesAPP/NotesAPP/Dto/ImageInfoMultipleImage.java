package com.notesAPP.NotesAPP.Dto;

import jakarta.persistence.Embeddable;

@Embeddable
public class ImageInfoMultipleImage {
    private String publicId;
    private String imageUrl;

    public ImageInfoMultipleImage() {}

    public ImageInfoMultipleImage(String publicId, String imageUrl) {
        this.publicId = publicId;
        this.imageUrl = imageUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

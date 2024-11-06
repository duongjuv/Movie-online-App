package com.example.movieonlineapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;


public class Movie {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("year")
    private int year;

    @SerializedName("content")
    private String content;

    @SerializedName("quality")
    private String quality;

    @SerializedName("duration")
    private String duration;

    @SerializedName("rating")
    private float rating;

    @SerializedName("url_image")
    private String urlImage;

    @SerializedName("url_video")
    private String urlVideo;

    @SerializedName("slug")
    private String slug;

    @SerializedName("account_can_view")
    private String TypeAccount;

    @SerializedName("createdAt")
    private Timestamp createdAt;

    @SerializedName("updatedAt")
    private Timestamp updatedAt;

    @SerializedName("deletedAt")
    private Timestamp deletedAt;

    @SerializedName("categories")
    private List<Category> categories;

    @SerializedName("actors")
    private List<Actor> actors;


    public Movie(int id, String title, int year, String content, String quality, String duration,
                 float rating, String urlImage, String urlVideo, String slug, String typeAccount,
                 Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt,
                 List<Category> categories, List<Actor> actors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.content = content;
        this.quality = quality;
        this.duration = duration;
        this.rating = rating;
        this.urlImage = urlImage;
        this.urlVideo = urlVideo;
        this.slug = slug;
        TypeAccount = typeAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.categories = categories;
        this.actors = actors;
    }

    public Movie() {

    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTypeAccount() {
        return TypeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        TypeAccount = typeAccount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}

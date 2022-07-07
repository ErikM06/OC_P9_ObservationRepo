package com.mediscreen.observationrepo.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 *
 */
@Document (collection = "patient")

public class Patient {
    @Id
    private String id;
    @Field("patId")
    @JsonProperty ("patId")
    private long patId;
    @Field("recommendations")
    @JsonProperty ("recommendations")
    private String content;

    @Field("Patient")
    @JsonProperty("Patient")
    private String family;



    public Patient() {
    }

    public Patient(String family,String content) {
        this.content = content;
        this.family=family;

    }

    public Patient(String id, long patId, String content, String family) {
        this.id = id;
        this.patId = patId;
        this.content = content;
        this.family = family;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPatId() {
        return patId;
    }

    public void setPatId(long patId) {
        this.patId = patId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}

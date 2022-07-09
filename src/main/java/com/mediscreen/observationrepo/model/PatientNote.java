package com.mediscreen.observationrepo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 */
@Document (collection = "patient")

public class PatientNote {
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
    private String lastName;



    public PatientNote() {
    }

    public PatientNote(String lastName, String content) {
        this.content = content;
        this.lastName = lastName;

    }

    public PatientNote(String id, long patId, String content, String lastName) {
        this.id = id;
        this.patId = patId;
        this.content = content;
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

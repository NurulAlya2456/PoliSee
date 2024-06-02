package com.example.polisee;

public class Politician {
    private int id;
    private String name;
    private String position;
    private String dob;
    private String party;
    private String years_in_office;
    private String country;
    private String biography;
    private String image;
    private long last_added_timestamp;

    public Politician(int id, String name, String position, String dob, String party, String years_in_office, String country, String biography, String image, long last_added_timestamp) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dob = dob;
        this.party = party;
        this.years_in_office = years_in_office;
        this.country = country;
        this.biography = biography;
        this.image = image;
        this.last_added_timestamp = last_added_timestamp;
    }

    public Politician() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getYears_in_office() {
        return years_in_office;
    }

    public void setYears_in_office(String years_in_office) {
        this.years_in_office = years_in_office;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getLast_added_timestamp() {
        return last_added_timestamp;
    }

    public void setLast_added_timestamp(long last_added_timestamp) {
        this.last_added_timestamp = last_added_timestamp;
    }
}
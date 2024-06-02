package com.example.polisee.response;

import com.example.polisee.Politician;

import java.util.ArrayList;
import java.util.List;

public class DataResponse {
    private List<Politician> data;

    public Politician getData() {
        return (Politician) data;
    }
}
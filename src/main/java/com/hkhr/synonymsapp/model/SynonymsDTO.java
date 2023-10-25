package com.hkhr.synonymsapp.model;

import lombok.Data;

import java.util.List;

@Data
public class SynonymsDTO {
    private String key;
    private List<String> values;
}

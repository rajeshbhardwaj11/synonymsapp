package com.hkhr.synonymsapp.controller;

import com.hkhr.synonymsapp.model.Synonyms;
import com.hkhr.synonymsapp.model.SynonymsDTO;
import com.hkhr.synonymsapp.service.SynonymsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/synonyms")
public class SynonymsController {

    @Autowired
    SynonymsService synonymsService;

    //Add synonyms to database
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addSynonyms(@RequestBody SynonymsDTO synonymsDTO) {
        return synonymsService.addSynonyms(synonymsDTO);
    }

    //Add List synonyms to database
    @PostMapping("/addMultiple")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addSynonymsList(@RequestBody List<SynonymsDTO> synonymsDTOList) {
        return synonymsService.addSynonymsList(synonymsDTOList);
    }

    //Get synonyms by word
    @GetMapping("/get/{word}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<SynonymsDTO> getSynonymsByWord(@PathVariable String word) {
        return synonymsService.getSynonymsByWord(word);
    }

    //Get all synonyms
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<SynonymsDTO>> getAllSynonyms() {
        return synonymsService.getAllSynonyms();
    }

    //Edit the synonyms by word
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateSynonyms(@RequestBody SynonymsDTO synonymsDTO) {
        return synonymsService.updateSynonyms(synonymsDTO);
    }

    //Delete the synonyms by word
    @DeleteMapping("/delete/{word}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteSynonyms(@PathVariable String word) {
        return synonymsService.deleteSynonysByWord(word);
    }

}

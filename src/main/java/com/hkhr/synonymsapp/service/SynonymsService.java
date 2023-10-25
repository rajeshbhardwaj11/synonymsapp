package com.hkhr.synonymsapp.service;

import com.hkhr.synonymsapp.dao.SynonymsDao;
import com.hkhr.synonymsapp.model.Synonyms;
import com.hkhr.synonymsapp.model.SynonymsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SynonymsService {

    @Autowired
    SynonymsDao synonymsDao;

    public ResponseEntity<String> addSynonyms(SynonymsDTO synonymsDTO) {
        Set<String> synonymsSet = getSynonymsSet(synonymsDTO);
        try {
            for (String word : synonymsSet) {
                Synonyms synonymFromDB = synonymsDao.findSynonymsByWord(word);
                if (synonymFromDB == null) {
                    String commaSeparatedString = synonymsSet.stream().filter(str -> !str.equals(word)).collect(Collectors.joining(","));
                    Synonyms synonyms = new Synonyms();
                    synonyms.setWord(word);
                    synonyms.setSynonyms(commaSeparatedString);
                    synonymsDao.save(synonyms);
                }

            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addSynonymsList(List<SynonymsDTO> synonymsDTOList) {

        try {
            for (SynonymsDTO synonymsDTO : synonymsDTOList) {
                Set<String> synonymsSet = getSynonymsSet(synonymsDTO);
                for (String word : synonymsSet) {
                    Synonyms synonymsFromDB = synonymsDao.findSynonymsByWord(word);
                    if (synonymsFromDB == null) {
                        String commaSeparatedString = synonymsSet.stream().filter(str -> !str.equals(word)).collect(Collectors.joining(","));
                        Synonyms synonyms = new Synonyms();
                        synonyms.setWord(word);
                        synonyms.setSynonyms(commaSeparatedString);
                        synonymsDao.save(synonyms);
                    }
                }
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<SynonymsDTO> getSynonymsByWord(String word) {
        try {
            Synonyms synonyms = synonymsDao.findSynonymsByWord(word);
            SynonymsDTO synonymsDTO = new SynonymsDTO();
            synonymsDTO.setKey(synonyms.getWord());
            synonymsDTO.setValues(Arrays.asList(synonyms.getSynonyms()));
            return new ResponseEntity<>(synonymsDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new SynonymsDTO(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<SynonymsDTO>> getAllSynonyms() {
        try {
            List<Synonyms> synonymsList = synonymsDao.findAll();
            List<SynonymsDTO> synonymsDTOS = new ArrayList<>();
            for (Synonyms synonyms : synonymsList) {
                SynonymsDTO synonymsDTO = new SynonymsDTO();
                synonymsDTO.setKey(synonyms.getWord());
                synonymsDTO.setValues(Arrays.asList(synonyms.getSynonyms()));
                synonymsDTOS.add(synonymsDTO);
            }

            return new ResponseEntity<>(synonymsDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<SynonymsDTO>(), HttpStatus.NOT_FOUND);
    }

    private Set<String> getSynonymsSet(SynonymsDTO synonymsDTO) {
        Set<String> stringSet = new TreeSet<>();
        if (synonymsDTO.getKey() != null && synonymsDTO.getValues().size() > 0) {
            stringSet.add(synonymsDTO.getKey());
            Synonyms synonymsFromDB = synonymsDao.findSynonymsByWord(synonymsDTO.getKey());

            if (synonymsFromDB != null) {
                stringSet.add(synonymsFromDB.getWord());
                String commaSeparatedStr = synonymsFromDB.getSynonyms();
                for (String s : Arrays.asList(commaSeparatedStr.split(","))) {
                    stringSet.add(s);
                }
            }
            for (String s:synonymsDTO.getValues()) {
                stringSet.add(s);
            }
        }
        return stringSet;
    }

    public ResponseEntity<String> updateSynonyms(SynonymsDTO synonymsDTO) {
        try {
            Synonyms synonymFromRequest = null;
            Set<String> stringSet = getSynonymsSet(synonymsDTO);
            for (String str : stringSet) {
                synonymFromRequest = synonymsDao.findSynonymsByWord(str);
                if (synonymFromRequest == null) {
                    synonymFromRequest = new Synonyms();
                }

                String commaSeparatedString = stringSet.stream().filter(s -> !s.equals(str)).collect(Collectors.joining(","));
                synonymFromRequest.setWord(str);
                synonymFromRequest.setSynonyms(commaSeparatedString);
                synonymsDao.save(synonymFromRequest);
            }
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteSynonysByWord(String word) {
        try {
            synonymsDao.deleteSynonysByWord(word);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.NOT_FOUND);
    }
}

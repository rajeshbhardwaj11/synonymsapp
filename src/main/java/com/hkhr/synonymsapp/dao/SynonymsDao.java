package com.hkhr.synonymsapp.dao;

import com.hkhr.synonymsapp.model.Synonyms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SynonymsDao extends JpaRepository<Synonyms, Integer> {

    @Query(value = "Select * from synonyms s where s.word=:word order by synonyms", nativeQuery = true)
    Synonyms findSynonymsByWord(String word);

    @Modifying
    @Transactional
    @Query(value = "Delete from synonyms s where s.word=:word", nativeQuery = true)
    void deleteSynonysByWord(String word);
}

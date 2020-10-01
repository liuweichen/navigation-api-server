package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Long> {
  @Query("select a from Answer a where a.questionId = :questionId")
  List<Answer> getAllByQuestionId(@Param("questionId") Long questionId);
}
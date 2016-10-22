package com.uaijia.joke.dao;

import com.uaijia.core.db.BaseMapper;
import com.uaijia.entity.Joke;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JokeMapper extends BaseMapper<Joke> {

    List<Joke> selectAll();

    List<Joke> selectAllNormal();

    List<Joke> selectAllNormalBeforeId(Long libId);

    List<Joke> selectAllNormalAfterId(Long libId);

    void insertBatch(@Param("jokes") List<Joke> jokes);


    void addDigg(@Param("id")Long id,@Param("num")int num);

    void addBury(@Param("id")Long id,@Param("num")int num);
}
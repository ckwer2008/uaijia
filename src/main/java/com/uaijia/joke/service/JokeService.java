package com.uaijia.joke.service;

import com.uaijia.entity.Joke;

import java.util.List;
import java.util.Map;

/**
 * 段子
 * Created by chenkaiwen on 16/10/19.
 */
public interface JokeService {

    List<Joke> findAllJoke();

    List<Joke> findAllNormalJoke();

    List<Joke> findAllLibJoke();

    List<Joke> findAllNewJoke();

    void save(Joke joke);

    void saveBatch(List<Joke> jokes);

    void initNewJoke();





    Map<String, List<Joke>> getNewJokeMap();
}

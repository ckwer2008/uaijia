package com.uaijia.joke.service.impl;

import com.uaijia.entity.Joke;
import com.uaijia.joke.dao.JokeMapper;
import com.uaijia.joke.service.JokeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 段子
 * Created by chenkaiwen on 16/10/19.
 */
@Service
public class JokeServiceImpl implements JokeService{

    @Resource
    private JokeMapper jokeMapper;

    private final static long libId = 39500;

    private Map<String,List<Joke>> newJokeMap = new HashMap<>();

    @Override
    public Joke find(Long id) {
        return jokeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Joke> findAllJoke() {
        return jokeMapper.selectAll();
    }

    @Override
    public List<Joke> findAllNormalJoke() {
        return jokeMapper.selectAllNormal();
    }

    @Override
    public List<Joke> findAllLibJoke() {
        return jokeMapper.selectAllNormalBeforeId(libId);
    }

    @Override
    public List<Joke> findAllNewJoke() {
        return jokeMapper.selectAllNormalAfterId(libId);
    }

    @Override
    public void save(Joke joke) {
        jokeMapper.insertSelective(joke);
    }

    @Override
    public void update(Joke joke) {
        jokeMapper.updateByPrimaryKeySelective(joke);
    }

    @Override
    public void saveBatch(List<Joke> jokes) {
        jokeMapper.insertBatch(jokes);
    }

    @Override
    @PostConstruct
    public void initNewJoke() {
        List<Joke> newJokes = findAllNewJoke();
        List<Joke> newJokesTmp = null;
        for(int i = 0; i< newJokes.size() ;i++){
            if(i%25==0) {
                if(CollectionUtils.isNotEmpty(newJokesTmp)){
                    newJokeMap.put(i/25+"",newJokesTmp);
                }
                newJokesTmp = new ArrayList<>();
            }
            newJokesTmp.add(newJokes.get(i));
        }
    }

    @Override
    public void addDigg(Long jokeId) {
        jokeMapper.addDigg(jokeId,1);
    }

    @Override
    public void addBury(Long jokeId) {
        jokeMapper.addBury(jokeId,1);
    }

    public Map<String, List<Joke>> getNewJokeMap() {
        return newJokeMap;
    }
}

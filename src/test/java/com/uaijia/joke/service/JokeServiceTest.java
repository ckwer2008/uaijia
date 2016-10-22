package com.uaijia.joke.service;

import com.uaijia.entity.Joke;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

@ActiveProfiles(profiles = { "dev" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = { "classpath*:applicationContext*.xml" })
public class JokeServiceTest {

    @Resource
    private JokeService jokeService;

    @Test
    public void findAllJoke() throws Exception {
//        List<Joke> list = jokeService.findAllJoke();
//        for (Joke joke:list) {
//            System.out.println(joke.getContent());
//        }
    }

    @Test
    public void save() throws Exception {
//        Joke joke = new Joke();
//        joke.setContent("xx");
//        joke.setContentMd5("xx");
//        joke.setSource("xx");
//        jokeService.save(joke);
    }
}
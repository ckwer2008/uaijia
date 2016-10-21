package com.uaijia.joke.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uaijia.entity.Joke;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.junit.Assert.*;

@ActiveProfiles(profiles = { "dev" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = { "classpath*:applicationContext*.xml" })
public class JokeServiceTest {

    @Resource
    private JokeService jokeService;

    @Test
    public void findAllJoke() throws Exception {
        List<Joke> list = jokeService.findAllJoke();
        System.out.println(list.size());

        for (Joke joke:list) {
            System.out.println(joke.getContent());

        }
    }

    @Test
    public void save() throws Exception {
        Joke joke = new Joke();
        joke.setContent("xx");
        joke.setContentMd5("xx");
        joke.setSource("xx");
        jokeService.save(joke);
    }




}
package com.uaijia;

import com.uaijia.entity.Joke;
import com.uaijia.joke.service.JokeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by chenkaiwen on 16/10/20.
 */
public class Crawler {

    private static JokeService jokeService;

    private static int PAGE =290;


    public static void main(String[] args) {
        System.setProperty("http.maxRedirects", "50");
        System.getProperties().setProperty("proxySet", "true");
// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
        String ip = "202.108.2.42";
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", "80");


        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        jokeService = (JokeService)ac.getBean("jokeServiceImpl");


        Set md5Set = new HashSet();
        List<Joke> list = jokeService.findAllJoke();
        for (Joke joke:list) {
            md5Set.add(joke.getContentMd5());
        }

        Counter counter = new Crawler.Counter();
        while (true) {
            try {
                insertJoke2(md5Set, counter);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("数据库原先:"+list.size()+"网络获取:"+counter.total+" 成功:"+counter.success+"  失败:"+counter.fail);
        }

        System.err.println("采集完成");
    }


    /**
     * 挖段子网 296-604
     * @throws Exception
     */
    public static void insertJoke2(Set md5Set, Counter counter) throws Exception {
        List<Joke> jokes;


        while(true) {
            System.err.println("请求第"+counter.page+"页============");
            Document doc = Jsoup.connect("http://www.waduanzi.com/page/"+counter.page).timeout(20000).get();
            counter.total+=doc.select(".item-content").size();
            if(counter.total==0) throw new NoSuchElementException();
            Iterator<Element> iterator = doc.select(".item-content").iterator();
            jokes = new ArrayList<>();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String content = element.html();

                Joke joke = new Joke();
                joke.setContent(content);
                String filterContent = filterChinese(content);
                joke.setContentMd5(DigestUtils.md5Hex(filterContent));
                joke.setSource("挖段子网");

                if (!md5Set.contains(joke.getContentMd5())) {
                    md5Set.add(joke.getContentMd5());
                    jokes.add(joke);
                    try {
                        counter.success++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        counter.fail++;
                        System.out.println("FAIL===:" + content);
                    }
                }
            }
            jokeService.saveBatch(jokes);

            System.err.println("完成第"+(counter.page++)+"页,等待1秒");
            Thread.sleep(1000);
        }



    }

    private static String filterChinese(String chin) {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    public static class Counter{
        public int total = 0;
        public int success = 0;
        public int fail = 0;
        public int page = PAGE;
    }

}

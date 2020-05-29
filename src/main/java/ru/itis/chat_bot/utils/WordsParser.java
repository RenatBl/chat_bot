package ru.itis.chat_bot.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordsParser {

    private static final String URL = "http://slova.textologia.ru/chast-rechi/suschestvitelnie/?q=1040&chst=%F1%F3%F9%E5%F1%F2%E2%E8%F2%E5%EB%FC%ED%FB%E5";
    private static final String PATH = "files/words.txt";

    private static List<String> words = new ArrayList<>(1000);

    private static void parseWords() {
        Document doc;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        Elements divs = doc.body().getElementsByClass("list_aut");
        for (Element div : divs) {
            String word = div.getElementsByTag("a").text();
            words.add(word.split(" ")[0]);
        }


    }

    public static List<String> getWords() {
        if (words.isEmpty()) {
            parseWords();
            fileRead();
        }
        return words;
    }

    private static void fileRead() {
        FileReader fr;
        try {
            fr = new FileReader(PATH);
            Scanner scan = new Scanner(fr);

            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                words.add(s);
            }

            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

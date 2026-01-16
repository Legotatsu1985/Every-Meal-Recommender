package com.github.Legotatsu1985.emr;

import com.github.Legotatsu1985.emr.ai.Gemini;
import com.github.Legotatsu1985.emr.util.Config;

public class App {
    public static Config CFG;

    static void main() {
        CFG = new Config();


        Gemini gemini = new Gemini();
        System.out.println(gemini.ask("Hello world!"));
    }
}

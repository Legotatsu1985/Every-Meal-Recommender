package com.github.Legotatsu1985.emr.ai;

import com.github.Legotatsu1985.emr.App;
import com.github.Legotatsu1985.emr.util.Config;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;

public class Gemini {
    private String apiKey;
    private ChatModel model;
    private ResponseFormat responseFormat;

    public Gemini(@NotNull ResponseFormat responseFormat) {
        if (Files.exists(Config.CFG_PATH)) {
            if (!(App.CFG.getApiKey() == null)) {
                this.apiKey = App.CFG.getApiKey();
                this.responseFormat = responseFormat;
                setModel();
            } else {
                throw new RuntimeException("API key is not set in " + Config.CFG_PATH);
            }
        } else {
            throw new RuntimeException(Config.CFG_PATH + " does not exist");
        }
    }

    public Gemini(@NotNull String apiKey, @NotNull ResponseFormat responseFormat) {
        this.apiKey = apiKey;
        this.responseFormat = responseFormat;
        setModel();
    }

    private void setModel() {
        this.model = GoogleAiGeminiChatModel.builder()
                .apiKey(this.apiKey)
                .modelName("gemini-2.5-flash")
                .responseFormat(this.responseFormat != null ? this.responseFormat : ResponseFormat.TEXT)
                .build();
    }

    public String ask(@NotNull String question) {return this.model.chat(question);}
}

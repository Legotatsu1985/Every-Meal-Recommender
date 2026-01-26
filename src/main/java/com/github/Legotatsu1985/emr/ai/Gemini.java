package com.github.Legotatsu1985.emr.ai;

import com.github.Legotatsu1985.emr.App;
import com.github.Legotatsu1985.emr.util.Config;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;

public class Gemini {
    private static final String DEFAULT_MODEL_NAME = "gemini-2.5-flash";
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
                App.LOGGER.warn("API key is missing in the configuration file.");
            }
        } else {
            App.LOGGER.warn("The configuration file does not exist.");
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
                .modelName(DEFAULT_MODEL_NAME)
                .responseFormat(this.responseFormat != null ? this.responseFormat : ResponseFormat.TEXT)
                .build();
        App.LOGGER.info("API Key = {}, Model Name = {}, Response Format = {}",
                this.apiKey != null ? this.apiKey : "null",
                DEFAULT_MODEL_NAME,
                this.responseFormat != null ? this.responseFormat : ResponseFormat.TEXT
        );
    }

    public String ask(@NotNull String question) {
        App.LOGGER.info("Sent question = {}", question);
        return this.model.chat(question);
    }
}

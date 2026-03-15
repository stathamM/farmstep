package com.farmstep.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

@Service
public class AiService {

    private final OpenAIClient client;

    public AiService(@Value("${openai.api.key}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String generateDiagnosisComment(
            String type,
            String experience,
            String migration,
            int budget,
            String stability,
            String independence
    ) {
        String prompt = """
                あなたは就農支援アドバイザーです。
                以下の診断結果に基づいて、ユーザー向けに日本語でわかりやすくアドバイスを書いてください。

                条件:
                - 200文字以内
                - やさしい日本語
                - 上から目線にしない
                - 最後に次の一歩を1つ入れる

                診断タイプ: %s
                農業経験: %s
                移住意思: %s
                初期資金: %d
                安定収入重視: %s
                独立志向: %s
                """.formatted(type, experience, migration, budget, stability, independence);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1_MINI)
                .addUserMessage(prompt)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);

        if (completion.choices().isEmpty()) {
            return "AIコメントを生成できませんでした。";
        }

        return completion.choices().get(0).message().content()
                .orElse("AIコメントを生成できませんでした。");
    }
}
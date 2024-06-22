package org.example.apisimple_dy.commonIO;

// ChatResponse.java
public class ChatResponse {
    private String content;

    public ChatResponse(String s) {
        this.content =s;
    }

    // getter 和 setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
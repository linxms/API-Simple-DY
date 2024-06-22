package org.example.apisimple_dy.commonIO;

import lombok.Data;

import java.util.List;

@Data
public class ChatListResponse {
    private List<ChatList> chatList;
    private int num;
    private int code;
    private String message;

    public ChatListResponse( String message) {
        this.message=message;
    }

    @Data
    public static class ChatList {
        private int listId;
        private String updateTime;
        private List<Message> messageList;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}

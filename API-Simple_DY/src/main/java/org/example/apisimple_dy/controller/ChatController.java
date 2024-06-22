package org.example.apisimple_dy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.core.annotation.Timed;
import org.example.apisimple_dy.commonIO.ChatListResponse;
import org.example.apisimple_dy.commonIO.ChatRequest;
import org.example.apisimple_dy.commonIO.ChatResponse;
import org.example.apisimple_dy.entity.ChatHistory;
import org.example.apisimple_dy.entity.User;
import org.example.apisimple_dy.mapper.ChatHistoryMapper;
import org.example.apisimple_dy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    //    private static final String API_URL = "https://api.chatanywhere.com.cn/v1/chat/completions";
    private static final String API_URL = "https://api.chatanywhere.com.cn/v1/chat/completions";

    private static final String API_KEY = "sk-3pBAXdmPWDdruZqaiM7VaGQKMhPg4VYcaITHImVgGDeIflsr"; //

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    @Autowired
    private UserMapper userMapper;



    @Timed(value = "chatController.post")
    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestParam("userID") Integer userId,
            @RequestBody ChatRequest chatRequest
    ) {

        User user = userMapper.selectById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ChatResponse("User not found"));
        }

        String content = chatRequest.getContent();
        Integer listId = chatRequest.getListId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        JSONObject paramMap = new JSONObject();
        paramMap.put("model", "gpt-3.5-turbo-0125");

        JSONArray messagesArray = new JSONArray();

        List<ChatHistory> chatHistories = chatHistoryMapper.selectByUserIdAndListId(userId.toString(), listId.toString());

        for (ChatHistory history : chatHistories) {
            JSONObject message = new JSONObject();
            message.put("role", history.getRole());
            message.put("content", history.getContent());
            messagesArray.add(message);
        }

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", content);
        messagesArray.add(userMessage);

        paramMap.put("messages", messagesArray);

        System.out.println(paramMap.toJSONString());

        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(paramMap, headers);

        RestTemplate restTemplate = new RestTemplate();
        JSONObject body = restTemplate.postForEntity(API_URL, requestEntity, JSONObject.class).getBody();

        // 检查body是否为空
        if (body == null || body.getJSONArray("choices") == null || body.getJSONArray("choices").isEmpty()) {
            System.out.println("响应体为空或不包含有效数据");
            // 返回错误响应
            ChatResponse errorResponse = new ChatResponse("");
            errorResponse.setContent("Error: No valid response received from the chat API.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        System.out.println(body.toJSONString());

        // 提取返回的 content 字段值
        String responseContent = body.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        // 去掉多余的双引号
        responseContent = responseContent.replace("\"", "");

        saveChatHistory(userId.toString(), listId.toString(), "user", content);
        saveChatHistory(userId.toString(), listId.toString(), "assistant", responseContent);

        ChatResponse chatResponse = new ChatResponse("");
        chatResponse.setContent(responseContent);

        return ResponseEntity.ok(chatResponse);
    }

    @Timed(value = "chatController.get")
    @GetMapping("/list")
    public ResponseEntity<ChatListResponse> getChatList(@RequestParam("userID") Integer userId) {

        User user = userMapper.selectById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ChatListResponse("User not found") );
        }
        List<ChatHistory> chatHistories = chatHistoryMapper.selectByUserId(userId.toString());
        Map<Integer, List<ChatHistory>> chatLists = new HashMap<>();

        // 分组 chatHistories 按 listId
        for (ChatHistory history : chatHistories) {
            int listId = Integer.parseInt(history.getListId());
            chatLists.computeIfAbsent(listId, k -> new ArrayList<>()).add(history);
        }

        List<ChatListResponse.ChatList> chatList = new ArrayList<>();

        for (Map.Entry<Integer, List<ChatHistory>> entry : chatLists.entrySet()) {
            ChatListResponse.ChatList chatListEntry = new ChatListResponse.ChatList();
            chatListEntry.setListId(entry.getKey());

            List<ChatListResponse.ChatList.Message> messageList = new ArrayList<>();
            for (ChatHistory history : entry.getValue()) {
                ChatListResponse.ChatList.Message message = new ChatListResponse.ChatList.Message();
                message.setRole(history.getRole());
                message.setContent(history.getContent());
                messageList.add(message);
            }

            chatListEntry.setMessageList(messageList);

            // 设置最后一条对话的时间
            ChatHistory lastMessage = entry.getValue().get(entry.getValue().size() - 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chatListEntry.setUpdateTime(sdf.format(lastMessage.getTimestamp()));

            chatList.add(chatListEntry);
        }

        ChatListResponse response = new ChatListResponse("");
        response.setChatList(chatList);
        response.setNum(chatList.size());
        response.setCode(200);
        response.setMessage("success");

        return ResponseEntity.ok(response);
    }

    private void saveChatHistory(String userId, String listId, String role, String content) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setUserId(userId);
        chatHistory.setListId(listId);
        chatHistory.setRole(role);
        chatHistory.setContent(content);
        chatHistory.setTimestamp(new Timestamp(System.currentTimeMillis()));
        chatHistoryMapper.insert(chatHistory);
    }
}
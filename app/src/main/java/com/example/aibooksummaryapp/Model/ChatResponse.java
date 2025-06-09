//Bhanumahesh Pasham
package com.example.aibooksummaryapp.Model;

import java.net.ContentHandler;
import java.util.List;

public class ChatResponse {
    public List<Choice> choices;

    public static class Choice {
        public Message message;

        public Message getMessage() {
            return message;
        }
    }

    public static class Message {
        public String role;
        public String content;

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }

    public List<Choice> getChoices() {
        return choices;
    }
}


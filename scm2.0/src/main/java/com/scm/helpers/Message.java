package com.scm.helpers;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private MessageType type;

    // Constructor, getters, setters, and builder

    private Message(Builder builder) {
        this.content = builder.content;
        this.type = builder.type;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String content;
        private MessageType type;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
    

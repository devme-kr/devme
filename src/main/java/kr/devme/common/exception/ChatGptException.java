package kr.devme.common.exception;

public class ChatGptException extends RuntimeException{
    public ChatGptException(String message) {
        super(message);
    }

    public ChatGptException(String message, Throwable e) {
        super(message, e);
    }
}

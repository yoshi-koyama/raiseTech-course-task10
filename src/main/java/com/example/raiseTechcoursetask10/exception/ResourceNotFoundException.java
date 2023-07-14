package com.example.raisetechcoursetask10.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }

    // 指定されたメッセージと原因を持つ例外を作成する
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // 指定されたメッセージを持つ例外を作成する
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // 指定された原因を持つ例外を作成する
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}

package me.rockintuna.tpiratesdemo.config.exceptions;

public class BusinessTimeInvalidException extends RuntimeException {
    public BusinessTimeInvalidException() {
        super("시간을 제대로 입력해주세요.");
    }
}

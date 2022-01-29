package com.app.payoneertest.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @Nullable
    public T data;

    @NonNull
    public Status status;

    @Nullable
    public String message;

    private Resource(@Nullable T data, @Nullable String message, @NonNull Status status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }


    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(data, null, Status.SUCCESS);
    }

    public static <T> Resource<T> error(@Nullable String message) {
        return new Resource<>(null, message, Status.ERROR);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(null, null, Status.LOADING);
    }

    public enum Status {
        LOADING,
        ERROR,
        SUCCESS
    }
}

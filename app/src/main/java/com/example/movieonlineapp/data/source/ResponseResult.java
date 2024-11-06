package com.example.movieonlineapp.data.source;
// chua ket qua tra ve
public abstract class ResponseResult<T> {

    public static class Success<T> extends ResponseResult<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static class Error<T> extends ResponseResult<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}

package com.example.movieonlineapp.data.request;

public class TransactionRequest {
    private int userId;
    private int amount;
    private String content;

    public TransactionRequest(int userId, int amount, String content) {
        this.userId = userId;
        this.amount = amount;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

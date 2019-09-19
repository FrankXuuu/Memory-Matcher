package com.example.shopifymemorymatcher.service.model;

import com.example.shopifymemorymatcher.ui.adapter.card.CardState;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("src")
    @Expose
    private String src;

    private CardState cardState = CardState.HIDDEN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }
}

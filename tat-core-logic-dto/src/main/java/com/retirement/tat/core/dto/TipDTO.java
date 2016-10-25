package com.retirement.tat.core.dto;

import java.io.Serializable;

/**
 * Created by khanhcq on 25-Oct-16.
 */
public class TipDTO implements Serializable {
    private static final long serialVersionUID = -8802744335878693315L;
    private Long tipId;
    private String title;
    private String content;

    public Long getTipId() {
        return tipId;
    }

    public void setTipId(Long tipId) {
        this.tipId = tipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

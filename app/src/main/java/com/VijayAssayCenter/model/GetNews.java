package com.VijayAssayCenter.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by root on 30/1/16.
 */
public class GetNews {

    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Newslist")
    @Expose
    public List<Newslist> newslist = null;

    public GetNews withStatus(String status) {
        this.status = status;
        return this;
    }

    public GetNews withNewslist(List<Newslist> newslist) {
        this.newslist = newslist;
        return this;
    }


    public class Newslist {

        @SerializedName("NewsId")
        @Expose
        public String newsId;
        @SerializedName("Title")
        @Expose
        public String title;
        @SerializedName("Sortnews")
        @Expose
        public String sortnews;
        @SerializedName("Description")
        @Expose
        public String description;
        @SerializedName("NewsImage")
        @Expose
        public String newsImage;
        @SerializedName("Cdate")
        @Expose
        public String cdate;
        @SerializedName("Mdate")
        @Expose
        public String mdate;
        @SerializedName("IsActive")
        @Expose
        public String isActive;
        @SerializedName("Header")
        @Expose
        public String header;

        public Newslist withNewsId(String newsId) {
            this.newsId = newsId;
            return this;
        }

        public Newslist withTitle(String title) {
            this.title = title;
            return this;
        }

        public Newslist withSortnews(String sortnews) {
            this.sortnews = sortnews;
            return this;
        }

        public Newslist withDescription(String description) {
            this.description = description;
            return this;
        }

        public Newslist withNewsImage(String newsImage) {
            this.newsImage = newsImage;
            return this;
        }

        public Newslist withCdate(String cdate) {
            this.cdate = cdate;
            return this;
        }

        public Newslist withMdate(String mdate) {
            this.mdate = mdate;
            return this;
        }

        public Newslist withIsActive(String isActive) {
            this.isActive = isActive;
            return this;
        }

        public Newslist withHeader(String header) {
            this.header = header;
            return this;
        }

    }

}

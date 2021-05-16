package com.github.caijh.framework.wechat.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@XmlRootElement
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxNewsMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "news";

    @XmlElement(name = "ArticleCount")
    private int articleCount = 1;

    @XmlElement(name = "Articles")
    private List<News> articles;

    @Override
    public String type() {
        return WxNewsMessage.MESSAGE_TYPE;
    }

    @Data
    public static class News {

        private NewsItem item;

    }

    @Data
    public static class NewsItem {

        @XmlElement(name = "Title")
        private String title;

        @XmlElement(name = "Description")
        private String description;

        @XmlElement(name = "PicUrl")
        private String picUrl;

        @XmlElement(name = "Url")
        private String url;

    }

}

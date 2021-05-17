package com.github.caijh.framework.wechat.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.caijh.commons.util.Collections;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@XmlRootElement(name = "xml")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxNewsMessage extends BaseWxMessage {

    public static final String MESSAGE_TYPE = "news";
    private static final long serialVersionUID = -1473934345387573293L;

    @XmlElement(name = "ArticleCount")
    private int articleCount = 1;

    @XmlElement(name = "Articles")
    private List<News> articles;

    @Override
    public String type() {
        return WxNewsMessage.MESSAGE_TYPE;
    }

    @Data
    public static class News implements Serializable {

        private static final long serialVersionUID = 8168778278188230336L;
        private NewsItem item;

    }

    @Data
    public static class NewsItem implements Serializable {

        private static final long serialVersionUID = -1645458179564012858L;
        @XmlElement(name = "Title")
        private String title;

        @XmlElement(name = "Description")
        private String description;

        @XmlElement(name = "PicUrl")
        private String picUrl;

        @XmlElement(name = "Url")
        private String url;

    }

    public WxNewsMessage(String fromUserName, String toUserName, List<News> articles) {
        super(fromUserName, toUserName);
        this.articleCount = Collections.isNotEmpty(articles) ? articles.size() : 0;
        this.articles = articles;
    }

}

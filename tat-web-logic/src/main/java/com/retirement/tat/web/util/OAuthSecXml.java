package com.retirement.tat.web.util;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Hieu Le on 6/28/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "urls")
public class OAuthSecXml implements Serializable {

    @XmlElement(required = true, name = "intercept-url")
    private List<InterceptUrlXml> interceptUrls;

    public List<InterceptUrlXml> getInterceptUrls() {
        return interceptUrls;
    }

    public void setInterceptUrls(List<InterceptUrlXml> interceptUrls) {
        this.interceptUrls = interceptUrls;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class InterceptUrlXml {
        @XmlAttribute
        private String pattern;
        @XmlAttribute
        private String access;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }
    }
}

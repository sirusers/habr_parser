package ipiad.dz.Tasks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ipiad.dz.Daemon;
import ipiad.dz.MyLogger;
import ipiad.dz.News;
import ipiad.dz.HabrParser;

import java.util.Optional;

import org.elasticsearch.core.List;

public class ParseNewsTask extends Task {
    ParseNewsTask() {}
    public ParseNewsTask(String htmlData, String url) {
        this.html = htmlData;
        this.url = url;
        this.type = Type.PARSE_NEWS;
    }
    protected String html;
    protected String url;

    public String getHtml() { return  html; }
    public String getUrl() { return  url; }

    @Override
    public void run() {
        try {
            java.util.List<News> news = HabrParser.parseNews(this.html, this.url);
            news.forEach(value -> {
                MyLogger.info("%s parsed successfully", value.url);
                if (Daemon.getElasticSearchManager().getDocumentByUrl(url).isEmpty()) {
                    if (Daemon.getElasticSearchManager().indexNews(value)) {
                        MyLogger.info("News %s successfully saved", value.url);
                    } else {
                        MyLogger.err("Error while saving %s", value.url);
                    }
                } else {
                    MyLogger.info("%s has already parsed!", url);
                }
            });
        } catch (Exception ex) {
            MyLogger.logException(ex);
        }
    }
}
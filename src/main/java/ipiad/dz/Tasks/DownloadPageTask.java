package ipiad.dz.Tasks;


import ipiad.dz.Daemon;
import ipiad.dz.MyLogger;
import ipiad.dz.News;
import ipiad.dz.Utils;
import java.util.Optional;


public class DownloadPageTask extends Task {
    DownloadPageTask() {

    }
    public DownloadPageTask(String url, Task.Type nextTaskType) {
        this.type = Type.DOWNLOAD_PAGE;
        this.url = url;
        this.nextTaskType = nextTaskType;
    }
    protected Type nextTaskType;
    protected String url;

    public Type getNextTaskType() { return nextTaskType; }
    public String getUrl() { return url; }

    @Override
    public void run() {
        Optional<String> data = Utils.getHtmlString(url);
        if (data.isEmpty()) {
            return;
        }
        String html = data.get();
        Daemon.addTask(new ParseNewsTask(html, url));
    }
}

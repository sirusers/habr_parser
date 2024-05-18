package ipiad.dz;

import ipiad.dz.Tasks.DownloadPageTask;
import ipiad.dz.Tasks.Task;

import static java.lang.System.exit;

public class Main {
    public static final String BASE_URL = "https://habr.com/ru/rss/articles/top/?fl=ru&limit=100";

    public static void main(String[] args) {
        Constants.Init();
        Daemon daemon = new Daemon();
        Daemon.addTask(new DownloadPageTask(BASE_URL, Task.Type.PARSE_MAIN));
        daemon.runDaemon();

        var collection = Daemon.getElasticSearchManager().getNewsCollection(1000);
        MyLogger.info("News count in es: %d", collection.size());
        exit(0);
    }

}
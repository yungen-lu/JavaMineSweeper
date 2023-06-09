package tw.edu.ncku.csie.javaminesweeper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rank implements Serializable {
    private static Map<Level, List<RankItem>> ranking = null;
    private static final String RANK_FILE = "rank.dat";
    public static int recentIndex = 0;

    private static void initialRanking() {
        ranking = new HashMap<>();
        for (Level level : Level.values()) {
            ranking.put(level, new ArrayList<RankItem>());
        }
    }

    private static void loadFile() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(new File(RANK_FILE)))) {
            ranking = (Map<Level, List<RankItem>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            initialRanking();
        }
    }

    private static void saveFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(new File(RANK_FILE)))) {
            oos.writeObject(ranking);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateScore(RankItem rankItem) {
        if (ranking == null) {
            loadFile();
        }
        Level currLevel = Level.currLevel;
        List<RankItem> rankList = ranking.get(currLevel);
        boolean hasAdd = false;
        for (int i = 0; i < rankList.size(); i++) {
            RankItem item = rankList.get(i);
            if (item.getTime() > rankItem.getTime()) {
                rankList.add(i, rankItem);
                recentIndex = i;
                hasAdd = true;
                break;
            }
        }
        if (!hasAdd && rankList.size() < 10) {
            rankList.add(rankItem);
            recentIndex = rankList.size() - 1;
            hasAdd = true;
        }
        if (rankList.size() > 10) {
            rankList.remove(rankList.size() - 1);
        }
        if (hasAdd) {
            ranking.put(currLevel, rankList);
            System.out.println("updateScore saveFile");
            saveFile();
        }
    }

    public static void showRanking() {
        if (ranking == null) {
            loadFile();
        }
        try {
            Main.addFxml("RankScene");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<RankItem> getRankList() {
        return ranking.get(Level.currLevel);
    }
}

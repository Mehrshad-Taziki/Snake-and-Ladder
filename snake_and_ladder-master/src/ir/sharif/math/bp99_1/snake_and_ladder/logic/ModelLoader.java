package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ModelLoader {
    private File boardFile;
    private final File playersDirectory;
    private final File archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    private static int lastId = 0;

    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    private File getGameFile(String name) throws IOException {
        File savedGames = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/savedGames");
        File[] Gameslist = savedGames.listFiles();
        assert Gameslist != null;
        for (File Game : Gameslist) {
            if (Game.getName().equals(name)) {
                return Game;
            }
        }
        return null;
    }
    public Board loadBord(File boardFile) {
        try {
            Scanner scanner = new Scanner(boardFile);
            BoardBuilder boardCreator = new BoardBuilder(scanner);
            return boardCreator.build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber, boolean started) {
        try {
            File playerFile = getPlayerFile(name);
            if (playerFile == null) {
                lastId++;
                String Filepath = playersDirectory.getPath() + "\\" + name + ".txt";
                File newPlayer = new File(Filepath);
                newPlayer.createNewFile();
                playerFile = getPlayerFile(name);
                PrintStream printStream = new PrintStream(new FileOutputStream(playerFile));
                printStream.println("Name: " + name);
                printStream.println("ID: " + lastId);
                printStream.println("TotalPoint: 0");
                printStream.println("Games: 0");
                printStream.println("Wins: 0");
                printStream.println("Loses: 0");
                printStream.println("Ties: 0");
                printStream.flush();
                printStream.close();
            }
            Scanner scanner = new Scanner(playerFile);
            scanner.next();
            String PlayersName = scanner.next();
            scanner.next();
            int PlayersID = scanner.nextInt();
            scanner.next();
            int PlayersPoints = scanner.nextInt();
            scanner.close();
            Player ThisPlayer = new Player(PlayersName, 0, PlayersID, playerNumber);
            return ThisPlayer;
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) throws IOException {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            Scanner scanner = new Scanner(file);
            scanner.next();
            String PlayersName = scanner.next();
            scanner.next();
            int PlayersID = scanner.nextInt();
            scanner.next();
            int PlayersPoints = scanner.nextInt();
            scanner.next();
            int GamesCount = scanner.nextInt();
            scanner.next();
            int WinsCount = scanner.nextInt();
            scanner.next();
            int LosesCount = scanner.nextInt();
            scanner.next();
            int TiesCount = scanner.nextInt();
            GamesCount++;
            if (player.getScore() < player.getRival().getScore()) {
                LosesCount++;
            } else if (player.getScore() > player.getRival().getScore()) {
                WinsCount++;
            } else {
                TiesCount++;
            }
            scanner.close();
            PlayersPoints += player.getScore();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println("Name: " + PlayersName);
            printStream.println("ID: " + PlayersID);
            printStream.println("TotalPoint: " + PlayersPoints);
            printStream.println("Games: " + GamesCount);
            printStream.println("Wins: " + WinsCount);
            printStream.println("Loses: " + LosesCount);
            printStream.println("Ties: " + TiesCount);
            printStream.flush();
            printStream.close();
            this.WinRating();
            this.ScoreRating();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    public void WinRating() throws FileNotFoundException {
        File[] PlayersStats = playersDirectory.listFiles();
        Map<String, Integer> Rating = new HashMap<>();
        assert PlayersStats != null;
        for (File PlayerStat :
                PlayersStats) {
            Scanner scanner = new Scanner(PlayerStat);
            scanner.next();
            String PlayersName = scanner.next();
            scanner.next();
            int PlayersID = scanner.nextInt();
            scanner.next();
            int PlayersPoints = scanner.nextInt();
            scanner.next();
            int GamesCount = scanner.nextInt();
            scanner.next();
            int WinsCount = scanner.nextInt();
            scanner.next();
            int LosesCount = scanner.nextInt();
            scanner.next();
            int TiesCount = scanner.nextInt();
            Rating.put(PlayersName, WinsCount);
            scanner.close();
        }

        ArrayList<Map.Entry<String, Integer>> NewRating = new ArrayList<>(Rating.entrySet());
        NewRating.sort(Map.Entry.comparingByValue());
        String Filepath = playersDirectory.getPath();
        Filepath = Filepath.substring(0, Filepath.length() - 16);
        Filepath += "TopWins";
        File RatingGame = new File(Filepath);
        PrintStream printStream = new PrintStream(new FileOutputStream(RatingGame));
        for (int i = NewRating.size()-1; i >= 0 ; i--) {
            printStream.print("Rank " + (NewRating.size()-i) + " Is ");
            printStream.print(NewRating.get(i).getKey());
            printStream.print(" With ");
            printStream.print(NewRating.get(i).getValue());
            printStream.println(" Wins");
        }
        printStream.flush();
        printStream.close();
    }
    public void ScoreRating() throws FileNotFoundException {
        File[] PlayersStats = playersDirectory.listFiles();
        Map<String, Integer> Rating = new HashMap<>();
        assert PlayersStats != null;
        for (File PlayerStat :
                PlayersStats) {
            Scanner scanner = new Scanner(PlayerStat);
            scanner.next();
            String PlayersName = scanner.next();
            scanner.next();
            int PlayersID = scanner.nextInt();
            scanner.next();
            int PlayersPoints = scanner.nextInt();
            scanner.next();
            int GamesCount = scanner.nextInt();
            scanner.next();
            int WinsCount = scanner.nextInt();
            scanner.next();
            int LosesCount = scanner.nextInt();
            scanner.next();
            int TiesCount = scanner.nextInt();
            Rating.put(PlayersName, PlayersPoints);
            scanner.close();
        }

        ArrayList<Map.Entry<String, Integer>> NewRating = new ArrayList<>(Rating.entrySet());
        NewRating.sort(Map.Entry.comparingByValue());
        String Filepath = playersDirectory.getPath();
        Filepath = Filepath.substring(0, Filepath.length() - 16);
        Filepath += "TopScore";
        File RatingGame = new File(Filepath);
        PrintStream printStream = new PrintStream(new FileOutputStream(RatingGame));
        for (int i = NewRating.size()-1; i >= 0 ; i--) {
            printStream.print("Rank " + (NewRating.size()-i) + " Is ");
            printStream.print(NewRating.get(i).getKey());
            printStream.print(" With ");
            printStream.print(NewRating.get(i).getValue());
            printStream.println(" Point");
        }
        printStream.flush();
        printStream.close();
    }
    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) throws IOException {
        File[] PlayersStats = playersDirectory.listFiles();
        lastId = PlayersStats.length;
        boolean fileExists = false;
        for (File playersStat : PlayersStats) {
            if (playersStat.getName().equals(name + ".txt")) {
                fileExists = true;
                return playersStat;
            }
        }
        return null;

    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            if (player1.getScore() < player2.getScore()) {
                printStream.print(player1.getName());
                printStream.printf(" - %d : %d - " , player1.getScore(), player2.getScore());
                printStream.println(player2.getName());
                printStream.printf("%s Won With %d More Points", player2.getName(), (player2.getScore() - player1.getScore()));
                printStream.println("");
                printStream.println("---------------------------------------------");
            } else if (player1.getScore() > player2.getScore()) {
                printStream.print(player1.getName());
                printStream.printf(" - %d : %d - " , player1.getScore(), player2.getScore());
                printStream.println(player2.getName());
                printStream.printf("%s Won With %d More Points", player1.getName(), (player1.getScore() - player2.getScore()));
                printStream.println("");
                printStream.println("---------------------------------------------");
            } else {
                printStream.print(player1.getName());
                printStream.printf(" - %d : %d - " , player1.getScore(), player2.getScore());
                printStream.println(player2.getName());
                printStream.print("Game Ended In A Tie");
                printStream.println("");
                printStream.println("---------------------------------------------");
            }
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

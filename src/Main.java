import com.entity.Bowler;
import com.entity.Delivery;
import com.entity.Match;


import java.sql.*;
import java.util.*;

public class Main {
    public static final String url = "jdbc:postgresql://localhost:5432/ipldatabase";
    public static final String user = "postgres";
    public static final String password = "0000";

    public static final int MATCH_ID = 1;
    public static final int MATCH_SEASON = 2;
    public static final int CITY = 3;
    public static final int DATE = 4;
    public static final int TEAM1 = 5;
    public static final int TEAM2 = 6;
    public static final int TOSS_WINNER = 7;
    public static final int TOSS_DECISION = 8;
    public static final int RESULT = 9;
    public static final int DL_APPLIED = 10;
    public static final int WINNER = 11;
    public static final int WIN_BY_RUN = 12;
    public static final int WIN_BY_WICKET = 13;
    public static final int PLAYER_OF_MATCH = 14;
    public static final int VENUE = 15;
    public static final int UMPIRE1 = 16;
    public static final int UMPIRE2 = 17;
    public static final int UMPIRE3 = 18;

    private static final String MATCH_FILE_PATH = "src/matches.csv";
    private static final String DELIVERIES_FILE_PATH = "src/deliveries.csv";

    public static final int DELIVERY_ID = 1;
    public static final int INNING = 2;
    public static final int BATTING_TEAM = 3;
    public static final int BOWLING_TEAM = 4;
    public static final int OVER = 5;
    public static final int BALL = 6;
    public static final int BATSMAN = 7;
    public static final int NON_STRIKER = 8;
    public static final int BOWLER = 9;
    public static final int IS_SUPER_OVER = 10;
    public static final int WIDE_RUN = 11;
    public static final int BYE_RUN = 12;
    public static final int LEG_BYE_RUN = 13;
    public static final int NO_BALL_RUN = 14;
    public static final int PENALTY_RUN = 15;
    public static final int BATS_MAN_RUN = 16;
    public static final int EXTRA_RUN = 17;
    public static final int TOTAL_RUN = 18;
    public static final int PLAYER_DISMISSED = 19;
    public static final int DISMISSAL_KIND = 20;
    public static final int FIELDER = 21;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Match> matchData = getMatchesData();
        List<Delivery> deliveriesData = getDeliveriesData();

        System.out.println("\tHello welcome to IPL DATA");
        System.out.println("====================================================");

        do {
            System.out.println("\n\t1. Number of matches played per year of all the years in IPL." +
                    "\n\t2. Number of matches won of all teams over all the years of IPL" +
                    "\n\t3. For the year 2016 get the extra runs conceded per team."
                    + "\n\t4. For the year 2015 get the top economical bowlers" +
                    "\n\t5. For Total Number of Sixer by Batsman in Year\n\t6. " +
                    "For Total Number of Player Score Highest In Mumbai"+"\n\t7. Exit");
            System.out.print("Enter your choice: ");

            int choise = sc.nextInt();
            switch (choise) {
                case 1:
                    findTotalMatchesPlayed();
                    System.out.println("=================================================");
                    break;
                case 2:
                    findTotalMatchWon();
                    System.out.println("=================================================");
                    break;
                case 3:
                    findExtraRunIn2016(matchData, deliveriesData);
                    System.out.println("=================================================");
                    break;
                case 4:
                    findEconomicalBowler(matchData, deliveriesData);
                    System.out.println("=================================================");
                    break;
                case 5:
                    findNumberOfSixer(matchData, deliveriesData);
                    System.out.println("=================================================");
                    break;
                case 6:
                    findPlayerScoreHighestInMumbai(matchData, deliveriesData);
                    System.out.println("=================================================");
                    break;
                case 7:
                    return;
            }

        } while (true);
    }

    public static List<Match> getMatchesData()  {
        List<Match> matchData = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url,user,password);){
            String query = "SELECT * FROM matches";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Match match = setMatchData(resultSet);
                matchData.add(match);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return matchData;
    }

    public static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveriesData = new ArrayList<>();
//        try {
//            BufferedReader bf = new BufferedReader(new FileReader(DELIVERIES_FILE_PATH));
//            String singleLine = "";
//            singleLine = bf.readLine();
//            singleLine = bf.readLine();
//
//            while (singleLine != null) {
//                String data[] = singleLine.split(",");
//
//                Delivery delivery = new Delivery();
//                delivery.setId(data[MATCH_ID]);
//                delivery.setBowlingTeam(data[BOWLING_TEAM]);
//                delivery.setExtraRun(data[EXTRA_RUN]);
//                delivery.setBowler(data[BOWLER]);
//                delivery.setBall(data[BALL]);
//                delivery.setTotalRun(data[TOTAL_RUN]);
//                delivery.setBatsman(data[BATSMAN]);
//                delivery.setBatsmanRun(data[BATS_MAN_RUN]);
//                deliveriesData.add(delivery);
//
//                singleLine = bf.readLine();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        try(Connection connection = DriverManager.getConnection(url,user,password);){
            String query = "SELECT * FROM deliveries";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Delivery delivery = setdeliveriesData(resultSet);
                deliveriesData.add(delivery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveriesData;
    }

    public static void findTotalMatchesPlayed() {

        try (Connection connection = DriverManager.getConnection(url,user,password)){
            String query = "SELECT season,count(season) FROM matches GROUP BY season ORDER BY season";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println(resultSet.getString(1) +" -> "+ resultSet.getInt(2));
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    public static void findTotalMatchWon() {
        printSubHeading(" Number of matches won of all teams over all the years of IPL.", "Team         Won Times");
        try (Connection connection = DriverManager.getConnection(url,user,password)){
            String query = "SELECT winner,count(winner) as WON FROM matches GROUP BY winner ORDER BY WON DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) +" -> "+ resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findExtraRunIn2016(List<Match> matchData, List<Delivery> deliveriesData) {
        printSubHeading("get the extra runs conceded per team.", "Team             Extra Run");
        try(Connection connection = DriverManager.getConnection(url,user,password)){
            String query = "SELECT bowlingteam,sum(cast(extrarun as Integer)) FROM matches as m " +
                    "INNER JOIN deliveries as d on cast(m.id as varchar) = d.matchid " +
                    "where m.season = '2016' GROUP BY bowlingteam" ;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println(resultSet.getString(1) +" -> "+ resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findEconomicalBowler(List<Match> matchData, List<Delivery> deliveriesData) {

        try (Connection connection = DriverManager.getConnection(url,user,password)){
            String query = "SELECT ";
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void findNumberOfSixer(List<Match> matchData, List<Delivery> deliveriesData) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Year between 2008 and 2017 : ");
        String season = sc.next();

        Set<String> idListInSeason = getMatchIdOfSeason(season, matchData);
        HashMap<String, Integer> batsManBySixeCount = new HashMap<>();

        for (Delivery delivery : deliveriesData) {
            if (idListInSeason.contains(delivery.getId())) {
                int run = Integer.parseInt(delivery.getBatsmanRun());
                if (run != 6) continue;
                String batsManName = delivery.getBatsman();
                batsManBySixeCount.put(batsManName,
                        batsManBySixeCount.getOrDefault(batsManName, 0) + 1);
            }
        }

        int corrospondingNumber = 1;
        HashMap<Integer, String> batsmanNameWithNo = new HashMap<>();
        for (String batsman : batsManBySixeCount.keySet()) {
            batsmanNameWithNo.put(corrospondingNumber, batsman);
            System.out.println(batsman + " -> " + corrospondingNumber);
            corrospondingNumber++;
        }
        System.out.print("Enter Given Number For Selecting BatsMan -> ");
        int batsmanChoise = sc.nextInt();

        String batsmanChosenByUser = batsmanNameWithNo.get(batsmanChoise);
        int totalSixerCount = batsManBySixeCount.get(batsmanChosenByUser);

        System.out.println("Total Number of Sixer by " +
                batsmanChosenByUser + " in " + season + " is " + totalSixerCount);
    }

    public static void printSubHeading(String s1, String s2) {
        System.out.println(s1);
        System.out.println("=============================");
        System.out.println(s2);
        System.out.println("------------------------------");
    }

    public static Set<String> getMatchIdOfSeason(String season, List<Match> matchData) {
        Set<String> idListOfSeason = new HashSet<>();
        for (Match match : matchData) {
            if (match.getSeason().equals(season)) idListOfSeason.add(match.getId());
        }
        return idListOfSeason;

    }

    //==========
    public static Map<String, String> getMatchOfCity(String city, List<Match> matchData) {
        Map<String, String> idListOfCity = new HashMap();

        for (Match match : matchData) {
            String matchId = match.getId();
            String seaason = match.getSeason();
            if (city.equals(match.getCity())) idListOfCity.put(matchId, seaason);
        }
        return idListOfCity;
    }

    public static void findPlayerScoreHighestInMumbai(List<Match> matchData, List<Delivery> deliveriesData) {
        Map<String, String> idListOfCity = getMatchOfCity("Mumbai", matchData);

        HashMap<String, HashMap<String, Integer>> batsmanScoreById = new HashMap<>(); //id -> batsmanname, totalrun

        for (Delivery deliveries : deliveriesData) {
            if (idListOfCity.containsKey(deliveries.getId())) {
                String id = deliveries.getId();
                String batsmanName = deliveries.getBatsman();
                int totalRun = Integer.parseInt(deliveries.getTotalRun());

                if (!batsmanScoreById.containsKey(deliveries.getId())) {
                    HashMap<String, Integer> innerMap = new HashMap<>();
                    innerMap.put(batsmanName, totalRun);
                    batsmanScoreById.put(id, innerMap);
                } else {
                    HashMap<String, Integer> innerMap = batsmanScoreById.get(id);
                    if (!innerMap.containsKey(batsmanName)) {
                        innerMap.put(batsmanName, totalRun);
                    } else {
                        int existingRun = innerMap.get(batsmanName);
                        innerMap.put(batsmanName, existingRun + totalRun);
                    }
                    batsmanScoreById.put(id, innerMap);
                }
            }
        }

        for (String id : batsmanScoreById.keySet()) {
            HashMap<String, Integer> batsmanAndRun = batsmanScoreById.get(id);
            int maxRun = Integer.MIN_VALUE;
            String maxScoreBatsman = "";
            for (String batsman : batsmanAndRun.keySet()) {
                int totalRun = batsmanAndRun.get(batsman);
                if (maxRun < totalRun) {
                    maxScoreBatsman = batsman;
                    maxRun = totalRun;
                }
            }
            System.out.println(idListOfCity.get(id) + " -> " + maxScoreBatsman + " -> " + maxRun);
        }
    }

    public static Delivery setdeliveriesData(ResultSet deliveriesArray) {
        Delivery deliveries = new Delivery();

        try {
            deliveries.setId(deliveriesArray.getString(DELIVERY_ID));
            deliveries.setInning(deliveriesArray.getString(INNING));
            deliveries.setBattingTeam(deliveriesArray.getString(BATTING_TEAM));
            deliveries.setBowlingTeam(deliveriesArray.getString(BOWLING_TEAM));
            deliveries.setOver(deliveriesArray.getString(OVER));
            deliveries.setBall(deliveriesArray.getString(BALL));
            deliveries.setBatsman(deliveriesArray.getString(BATSMAN));
            deliveries.setNonStriker(deliveriesArray.getString(NON_STRIKER));
            deliveries.setBowler(deliveriesArray.getString(BOWLER));
            deliveries.setIsSuperOver(deliveriesArray.getString(IS_SUPER_OVER));
            deliveries.setWideRun(deliveriesArray.getString(WIDE_RUN));
            deliveries.setByeRun(deliveriesArray.getString(BYE_RUN));
            deliveries.setLegByeRun(deliveriesArray.getString(LEG_BYE_RUN));
            deliveries.setNoBallRun(deliveriesArray.getString(NO_BALL_RUN));
            deliveries.setPenaltyRun(deliveriesArray.getString(PENALTY_RUN));
            deliveries.setBatsmanRun(deliveriesArray.getString(BATS_MAN_RUN));
            deliveries.setExtraRun(deliveriesArray.getString(EXTRA_RUN));
            deliveries.setTotalRun(deliveriesArray.getString(TOTAL_RUN));
            deliveries.setPlayerDismissed(deliveriesArray.getString(PLAYER_DISMISSED));
            deliveries.setDismissalKind(deliveriesArray.getString(DISMISSAL_KIND));
            deliveries.setFielder(deliveriesArray.getString(FIELDER));
        }catch (SQLException e){
            e.printStackTrace();
        }


        return deliveries;
    }

    public static Match setMatchData(ResultSet matchDATA) {
        Match match = new Match();

        try {
            match.setId(matchDATA.getString(MATCH_ID));
            match.setSeason(matchDATA.getString(MATCH_SEASON));
            match.setCity(matchDATA.getString(CITY));
            match.setDate(matchDATA.getString(DATE));
            match.setTeam1(matchDATA.getString(TEAM1));
            match.setTeam2(matchDATA.getString(TEAM2));
            match.setTossWinner(matchDATA.getString(TOSS_WINNER));
            match.setTossDecision(matchDATA.getString(TOSS_DECISION));
            match.setResult(matchDATA.getString(RESULT));
            match.setDlApplied(matchDATA.getString(DL_APPLIED));
            match.setWinner(matchDATA.getString(WINNER));
            match.setWinByRun(matchDATA.getString(WIN_BY_RUN));
            match.setWinByWicket(matchDATA.getString(WIN_BY_WICKET));
            match.setPlayerOfMatch(matchDATA.getString(PLAYER_OF_MATCH));
            match.setVenue(matchDATA.getString(VENUE));
            match.setUmpire1(matchDATA.getString(UMPIRE1));
            match.setUmpire2(matchDATA.getString(UMPIRE2));
            match.setUmpire3(matchDATA.getString(UMPIRE3));

        }catch (SQLException e){
            e.printStackTrace();
        }


        return match;
    }

}
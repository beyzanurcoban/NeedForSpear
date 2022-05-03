package domain;

import domain.abilities.HollowPurple;
import domain.obstacle.*;
import domain.path.CircularPath;
import domain.path.StraightVerticalBFPath;
import util.PosVector;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Saver {

    private String name = Game.getInstance().gameState.getPlayer().getUserName();
    private String num = Game.getInstance().gameState.getPlayer().GameNum + "";
    private String FILEPATH = "src/saves/" + name + "_" + num + ".json";
    private String LOADPATH = "src/saves/loadfilenames.json";
    private int WALLMARIASTEINS_VEL = 5;
    private double PANDORAS_VEL = 0.2;

    public void saveGame(HashMap<Obstacle, PosVector> list) {
        Document doc = new Document();
        ArrayList<String> temp;
        ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
        doc.put("PaddlePositionX", Game.getInstance().getPaddle().getPosVector().getX());
        doc.put("PaddlePositionY", Game.getInstance().getPaddle().getPosVector().getY());
        doc.put("BallPositionX", Game.getInstance().getBall().posVector.getX() );
        doc.put("BallPositionY", Game.getInstance().getBall().posVector.getY());
        doc.put("Username", Game.getInstance().gameState.getPlayer().getUserName());
        doc.put("ChancePoints", Game.getInstance().gameState.getPlayer().getChance_points());
        doc.put("Score", Game.getInstance().getOldScore());
        doc.put("ChanceGivingAbility", Game.getInstance().gameState.getPlayer().getAbilities().get(1));
        doc.put("PaddleExpansionAbility", Game.getInstance().gameState.getPlayer().getAbilities().get(2));
        doc.put("UnstoppableAbility", Game.getInstance().gameState.getPlayer().getAbilities().get(3));
        doc.put("RocketAbility", Game.getInstance().gameState.getPlayer().getAbilities().get(4));
        doc.put("Ymirfreq", Game.getInstance().Ymirfreq);
        doc.put("YmirProb1", Game.getInstance().YmirProb1);
        doc.put("YmirProb2", Game.getInstance().YmirProb2);
        doc.put("YmirProb3", Game.getInstance().YmirProb3);

        for (Entry<Obstacle, PosVector> o : list.entrySet()) {
            temp = new ArrayList<>();
            temp.add(String.valueOf(o.getKey()));
            temp.add(String.valueOf(o.getValue()));
            temp.add(String.valueOf(o.getKey().isFrozen()));
            temp.add(String.valueOf("ealth " +o.getKey().getHealth()));

            if(o.getKey().is_moving) {
                temp.add(String.valueOf("vmoving1"));
            }else{
                temp.add(String.valueOf("vmoving0"));

            }

            temp.add(String.valueOf("Qeft" + o.getKey().getEndLeft() + "<"));
            temp.add(String.valueOf("righT" + o.getKey().getEndRight() + ">"));
            map.add(temp);
        }

        doc.put("ObjectList", map);

        try {
            FileWriter file = new FileWriter(FILEPATH);
            file.write(doc.toJson());
            file.close();

            updateLoadNames();

            System.out.println("Saved to local directory successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(Paddle paddle, Ball ball, String loadgameName) {

        HashMap<Obstacle, PosVector> loadObsPos = new HashMap<>();
        Layout.setObstaclePositions(loadObsPos);

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(loadgameName)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;
            long z = (long) doc.get("PaddlePositionX");
            int x = ((int) z);
            long t = (long) doc.get("PaddlePositionY");
            int y = ((int) t);
            PosVector paddleLoc = new PosVector(x, y);
            long c = (long) doc.get("ChancePoints");
            String user = (String) doc.get("Username");
            Game.getInstance().gameState.setChance((int) c);
            long f = (long) doc.get("Score");
            Game.getInstance().gameState.getPlayer().setUserName(user);
            Game.getInstance().gameState.getPlayer().setScore((int) f);
            Game.getInstance().setScore((int) f);
            Game.getInstance().getPaddle().setPosVector(paddleLoc);
            z = (long) doc.get("BallPositionX");
            t = (long) doc.get("BallPositionY");
            x = ((int) z);
            y = ((int) t);

            z = (long) doc.get("ChanceGivingAbility");
            Game.getInstance().gameState.getPlayer().getAbilities().put(1, (int) z);

            z = (long) doc.get("PaddleExpansionAbility");
            Game.getInstance().gameState.getPlayer().getAbilities().put(2, (int) z);

            z = (long) doc.get("UnstoppableAbility");
            Game.getInstance().gameState.getPlayer().getAbilities().put(3, (int) z);

            z = (long) doc.get("RocketAbility");
            Game.getInstance().gameState.getPlayer().getAbilities().put(4, (int) z);

            z = (long) doc.get("Ymirfreq");
            Game.getInstance().Ymirfreq = (int) z;

            double j = (double) doc.get("YmirProb1");
            Game.getInstance().YmirProb1 = j;

            j = (double) doc.get("YmirProb2");
            Game.getInstance().YmirProb2 = j;

            j = (double) doc.get("YmirProb3");
            Game.getInstance().YmirProb3 = j;


            PosVector ballLoc = new PosVector(x, y);
            ball.setPosVector(ballLoc);

            JSONArray jsonlist = (JSONArray) doc.get("ObjectList");

            Map<Obstacle, PosVector> list = new HashMap<>();
            ArrayList<DomainObject> listDO = new ArrayList<DomainObject>();
            int obsLen = Game.getInstance().getPaddle().length / 5;
            PosVector vec;


            for (int i = 0; i < jsonlist.size(); i++) {

                String s = jsonlist.get(i).toString();

                int a = s.indexOf("x");
                int b = s.indexOf(",");
                int p = (Integer.parseInt(s.substring(a + 2, b)));

                int k = s.indexOf("y");
                int l = s.indexOf("}");
                int m = (Integer.parseInt(s.substring(k + 2, l)));
                int h = s.indexOf("h");
                int health = (Integer.parseInt(s.substring(h + 2, h+3)));
                int v = s.indexOf("v");
                int wismoving =(Integer.parseInt(s.substring(v + 7, v + 8)));
                int index_cap_q = s.indexOf("Q");
                int index_lesser = s.indexOf("<");
                int index_greater = s.indexOf(">");
                int index_cap_T = s.indexOf("T");
                int pathEndLeft = (Integer.parseInt(s.substring(index_cap_q + 4, index_lesser)));
                int pathEndRight = (Integer.parseInt(s.substring(index_cap_T + 1, index_greater)));
                //System.out.println("health is "+ health);
                //System.out.println("moving is "+ wismoving);
                System.out.println("lef is "+ pathEndLeft);
                System.out.println("right is "+ pathEndRight);

                if (s.contains("WallMaria")) {
                    WallMaria obs = new WallMaria(p, m);
                    if (s.contains("true")) {
                        obs.setIsFrozen(true);
                    }
                    if(wismoving == 1){
                        obs.is_moving = true;
                        StraightVerticalBFPath path = new StraightVerticalBFPath(p, m, WALLMARIASTEINS_VEL);
                        path.setEndLeft(pathEndLeft);
                        path.setEndRight(pathEndRight);
                        obs.setPathBehaviour(path);


                    }else{
                        obs.is_moving = false;
                    }


                    listDO.add(obs);
                    vec = new PosVector(p, m);
                    Layout.getObstaclePositions().put(obs, vec);
                }
                if (s.contains("GiftOfUranus")) {
                    GiftOfUranus obs = new GiftOfUranus(p, m);
                    if (s.contains("true")) {
                        obs.setIsFrozen(true);
                    }
                    listDO.add(obs);
                    vec = new PosVector(p, m);
                    Layout.getObstaclePositions().put(obs, vec);
                }
                if (s.contains("PandorasBox")) {
                    PandorasBox obs = new PandorasBox(p, m);
                    if (s.contains("true")) {
                        obs.setIsFrozen(true);
                    }
                    if(wismoving == 1){
                        obs.is_moving = true;
                        CircularPath path = new CircularPath(obs, PANDORAS_VEL);

                        obs.setPathBehaviour(path);

                    }else{
                        obs.is_moving = false;
                    }


                    listDO.add(obs);
                    vec = new PosVector(p, m);
                    Layout.getObstaclePositions().put(obs, vec);
                }
                if (s.contains("SteinsGate")) {
                    SteinsGate obs = new SteinsGate(p, m);
                    if (s.contains("true")) {
                        obs.setIsFrozen(true);
                    }
                    if(wismoving == 1){
                        obs.is_moving = true;
                        StraightVerticalBFPath path = new StraightVerticalBFPath(p, m, WALLMARIASTEINS_VEL);
                        path.setEndLeft(pathEndLeft);
                        path.setEndRight(pathEndRight);
                        obs.setPathBehaviour(path);

                    }else{
                        obs.is_moving = false;
                    }
                    listDO.add(obs);
                    vec = new PosVector(p, m);
                    Layout.getObstaclePositions().put(obs, vec);
                }
                if (s.contains("HollowPurple")) {
                    Hollow obs = new Hollow(p, m);
                    if (s.contains("true")) {
                        obs.setIsFrozen(true);
                    }
                    listDO.add(obs);
                    vec = new PosVector(p, m);
                    Layout.getObstaclePositions().put(obs, vec);
                }
            }

            Game.getInstance().setDomainObjectArr(listDO);
            System.out.println("Loaded from local directory successfully.");

        } catch (FileNotFoundException e) {


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void updateLoadNames() {
        Document doc2 = new Document();
        JSONParser jsonParser = new JSONParser();
        ArrayList<String> temp = new ArrayList<>();

        try (FileReader reader = new FileReader(LOADPATH)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;

            JSONArray filenameList = (JSONArray)doc.get("FileNames");

            for(int i=0; i<filenameList.size(); i++) {
                String s = filenameList.get(i).toString();
                temp.add(s);
            }
            temp.add(FILEPATH);
            doc2.put("FileNames", temp);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            FileWriter file = new FileWriter(LOADPATH);
            file.write(doc2.toJson());
            file.close();

            System.out.println("Saved to filenamelist successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


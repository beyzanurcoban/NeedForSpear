package UI;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import domain.*;
import domain.controller.LayoutController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@SuppressWarnings("serial")
public class BuildModeScreen extends JFrame {

    /////////////////////////////////////////////////////////////////////////////////////

    // Build Mode Screen features
    public static final int FRAME_WIDTH = 1368;
    public static final int FRAME_HEIGHT = 766;

    // Initial Obstacle Counts
    static final int SIMPLE_COUNT = 75;
    static final int FIRM_COUNT = 10;
    static final int EXPLOSIVE_COUNT = 5;
    static final int GIFT_COUNT = 10;

    private JTextField simpleObstacle;
    private JTextField firmObstacle;
    private JTextField explosiveObstacle;
    private JTextField giftObstacle;

    private JTextField YmirProbability1;
    private JTextField YmirProbability2;
    private JTextField YmirProbability3;

    private JLabel ymirProb1 = new JLabel("Infinite Void Prob.");
    private JLabel ymirProb2 = new JLabel("Double Acceleration Prob.");
    private JLabel ymirProb3 = new JLabel("Hollow Purple Prob.");
    private JLabel ymirFreqLabel = new JLabel("Ymir Frequency");

    private JButton gameStartButton;
    private JButton obstacleButton;
    private JButton loadGameButton;

    private HashMap<String, Integer> obstacleSettings;

    private HashMap<String, Integer> runSettings;
    private List<IRunListener> runModeListeners = new ArrayList<>();
    private List<ILoadListener> loadModeListeners = new ArrayList<>();
    private String LOADPATH = "src/saves/loadfilenames.json";

    // Layout Controller
    private LayoutController LC;

    private JPanel obstacleSettingsPanel;
    private JPanel viewPanel;

    private String  username;
    private  String id;
    private Integer gameNum;
    private int YmirFrequency;
    private double YmirProb1;
    private double YmirProb2;
    private double YmirProb3;
    private ArrayList<String> temp = new ArrayList<>();

    // Load bool
    private boolean isLoad = false;

    /////////////////////////////////////////////////////////////////////////////////////

    public void addListener(IRunListener listener) {
        runModeListeners.add(listener);
    }

    public void removeListener(IRunListener listener) {
        runModeListeners.remove(listener);
    }

    public void addLoadListener(ILoadListener listener) { loadModeListeners.add(listener) ;}

    public void setRunSettings() {
        HashMap<String, Integer> runSettings = new HashMap<String, Integer>();

        runSettings.put("screenWidth", (int) FRAME_WIDTH);
        runSettings.put("screenHeight", (int) FRAME_HEIGHT);

        this.runSettings = runSettings;
    }

    // Take the obstacle counts according to their types, and send them to the LayoutController
    public void setObstacleSettings() {
        HashMap<String, Integer> obstacleSettings = new HashMap<String, Integer>();

        obstacleSettings.put("simpleObstacleCount", Integer.parseInt(simpleObstacle.getText()));
        obstacleSettings.put("firmObstacleCount", Integer.parseInt(firmObstacle.getText()));
        obstacleSettings.put("explosiveObstacleCount", Integer.parseInt(explosiveObstacle.getText()));
        obstacleSettings.put("giftObstacleCount", Integer.parseInt(giftObstacle.getText()));

        LC.setObstacleSettings(obstacleSettings);
        this.obstacleSettings = obstacleSettings;
    }

    public BuildModeScreen() {
        initializeBuildScreen();
        GridBagConstraints c = new GridBagConstraints();
        LC = new LayoutController();

        obstacleSettingsPanel = initializeObstacleSettingsPanel();
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 100;
        c.ipadx = 0;
        add(obstacleSettingsPanel, c);

        c.gridx = 3;
        c.gridy = 1;
        c.gridheight = 2;
        c.ipady = 0;
        c.fill = GridBagConstraints.VERTICAL;
        add(runGamePanel(this),c);

        viewPanel = initializeLayoutPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 0;
        add(viewPanel, c);


    }


    // Initialize the outer JFrame of Build Mode Screen
    private void initializeBuildScreen() {
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
    }

    // This provides a panel that the player can set the obstacle numbers (Orange Panel)
    private JPanel initializeObstacleSettingsPanel(){
        GridLayout gameObjLayout = new GridLayout(5,3); // #Type of obstacles + Button
        JPanel GameObjectPanel = new JPanel(gameObjLayout);
        GameObjectPanel.setBackground(Color.orange);

        // Text Fields
        simpleObstacle = new JTextField(Integer.toString(SIMPLE_COUNT), 30);
        firmObstacle = new JTextField(Integer.toString(FIRM_COUNT), 30);
        explosiveObstacle = new JTextField(Integer.toString(EXPLOSIVE_COUNT), 30);
        giftObstacle = new JTextField(Integer.toString(GIFT_COUNT), 30);

        // Simple Obstacle Row
        ImageIcon simpleObsIcon = new ImageIcon(new ImageIcon("src/assets/simple_obstacle.png").getImage().getScaledInstance(80, 50, Image.SCALE_DEFAULT));
        GameObjectPanel.add(new JLabel(simpleObsIcon));
        GameObjectPanel.add(new JLabel("Number of simple obstacles"));
        GameObjectPanel.add(simpleObstacle);

        // Firm Obstacle Row
        ImageIcon firmObsIcon = new ImageIcon(new ImageIcon("src/assets/firm_obstacle_empty.png").getImage().getScaledInstance(80, 50, Image.SCALE_DEFAULT));
        GameObjectPanel.add(new JLabel(firmObsIcon));
        GameObjectPanel.add(new JLabel("Number of firm obstacles"));
        GameObjectPanel.add(firmObstacle);

        // Explosive Obstacle Row
        ImageIcon expObsIcon = new ImageIcon(new ImageIcon("src/assets/explosive_obstacle.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        GameObjectPanel.add(new JLabel(expObsIcon));
        GameObjectPanel.add(new JLabel("Number of explosive obstacles"));
        GameObjectPanel.add(explosiveObstacle);

        // Gift Obstacle Row
        ImageIcon giftObsIcon = new ImageIcon(new ImageIcon("src/assets/gift_obstacle.png").getImage().getScaledInstance(80, 50, Image.SCALE_DEFAULT));
        GameObjectPanel.add(new JLabel(giftObsIcon));
        GameObjectPanel.add(new JLabel("Number of gift obstacles"));
        GameObjectPanel.add(giftObstacle);

        // Obstacle Button
        obstacleButton = new JButton("Set Obstacles");
        obstacleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setObstacleSettings();
                LC.setFramePanelWidthHeight(FRAME_WIDTH,FRAME_HEIGHT);
                LC.craftRandomLayout();
                gameStartButton.setEnabled(true);
                //obstacleButton.setEnabled(false);
                loadGameButton.setEnabled(false);
            }
        });

        GameObjectPanel.add(new JLabel(""));
        GameObjectPanel.add(obstacleButton);
        GameObjectPanel.add(new JLabel(""));

        return GameObjectPanel;

    }

    private LayoutPanel initializeLayoutPanel() {return new LayoutPanel(LC,FRAME_WIDTH,FRAME_HEIGHT);}

    // This panel provides an option to load an existing game, and start to game
    private JPanel runGamePanel(JFrame frame) {
        GridLayout panelLayout = new GridLayout(5, 2);
        JPanel runGamePanel = new JPanel(panelLayout);

        loadGameButton = new JButton("Load Game");
        gameStartButton = new JButton("Click to start the game!");
        JLabel headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Set frequency for Ymir: ");
        headerLabel.setVisible(true);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setValue(30);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("0"));
        labels.put(25, new JLabel("25"));
        labels.put(50, new JLabel("50"));
        labels.put(75, new JLabel("75"));
        labels.put(100, new JLabel("100"));
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);

        YmirProbability1 = new JTextField("5", 10);
        YmirProbability2 = new JTextField("5", 10);
        YmirProbability3 = new JTextField("5", 10);


        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                YmirFrequency = ((JSlider)e.getSource()).getValue();
            }
        });


        gameStartButton.setEnabled(false);

        gameStartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (LC.isLayoutSatisfies() || isLoad) {
                    setRunSettings();
                    YmirProb1 = Double.parseDouble(YmirProbability1.getText());
                    YmirProb2 = Double.parseDouble(YmirProbability2.getText());
                    YmirProb3 = Double.parseDouble(YmirProbability3.getText());

                    notifyButtonisClickedListeners(username, id, gameNum, YmirFrequency, YmirProb1, YmirProb2, YmirProb3);
                }else{
                    JOptionPane.showMessageDialog(PlaygroundScreen.jf,
                            "Enter 75("+ Layout.getWallMariaCount()+")at least for Wall Maria\n" +
                                    "Enter 10("+ Layout.getSteinsGateCount()+") at least for Steins Gate\n" +
                                    "Enter 5("+ Layout.getPandoraBoxCount()+") at least for Pandoras Box\n" +
                                    "Enter 10("+ Layout.getUranusCount()+")at least for Gift of Uranus\n",
                            "Minimum criteria is not satisfied",
                            JOptionPane.INFORMATION_MESSAGE);
                }


            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                LC.craftEmptyLayout();
                isLoad = true;
                for (ILoadListener listener : loadModeListeners) {
                    System.out.println("Myload listeners" + listener);
                    getFileOptions();


                    //String[] options = temp.toArray(new String[0]);
                    String[] options= getUserFileOptions();
                    ImageIcon icon = new ImageIcon("src/assets/sphere.png");
                    String n = (String)JOptionPane.showInputDialog(null, "Which game you want to load "+ username+ "?",
                            "GameLoadOptions", JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
                    System.out.println(n);
                    listener.onClickEventDo(n);
                    gameStartButton.setEnabled(true);
                }

            }
        });

        runGamePanel.add(loadGameButton);
        runGamePanel.add(gameStartButton);
        runGamePanel.add(ymirFreqLabel);
        runGamePanel.add(slider);
        runGamePanel.add(ymirProb1);
        runGamePanel.add(YmirProbability1);
        runGamePanel.add(ymirProb2);
        runGamePanel.add(YmirProbability2);
        runGamePanel.add(ymirProb3);
        runGamePanel.add(YmirProbability3);
        return runGamePanel;
    }

    public void notifyButtonisClickedListeners(String username, String id,Integer num, Integer freq,Double prob1, Double prob2, Double prob3) {
        System.out.println("ALL LISTENERS ARE NOTIFIED THAT THE BUTTON IS CLICKED \n\n\n");

        for (IRunListener listener : runModeListeners) {
            System.out.println(listener);
            listener.onRunEvent(this.runSettings, username, id,num,freq,prob1,prob2,prob3);
        }
        this.setVisible(false);
        this.dispose();
    }

    public void setID(String  ID){
        this.id = ID;
    }

    public void setUserName(String  username){
        this.username = username;
    }

    public void setGameNum(Integer num){
        this.gameNum = num;
    }

    public String[] getUserFileOptions(){
        String[] temp2 = temp.toArray(new String[0]);

        String[] temp3 = new String[temp2.length];
        int i =0;

        for(String name : temp2){
            if(name.contains(username)){
                temp3[i] = name;
                i++;

            }
        }
        return temp3;

    }
    public void getFileOptions() {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(LOADPATH)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;

            JSONArray filenameList = (JSONArray) doc.get("FileNames");

            for (int i = 0; i < filenameList.size(); i++) {
                String s = filenameList.get(i).toString();
                temp.add(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
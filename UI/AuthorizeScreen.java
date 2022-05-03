package UI;

import domain.IAuthorizeListener;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizeScreen extends JFrame  {

    /////////////////////////////////////////////////////////////////////////////////////

    // Authorize Screen Features
    static final int SCREEN_WIDTH = 1368;
    static final int SCREEN_HEIGHT = 766;

    // Initial Username and Password JTextField Arguments
    static final String USERNAME = "Attila";
    static final String PASSWORD = "1111";

    // Filepath to save the players' usernames and passwords
    String FILEPATH = "src/saves/players.json";

    private JTextField userName;
    private JTextField ID;

    private JPanel info;
    private JPanel buttons;

    private boolean isUsernameExist = false;
    private boolean isLogin = false;
    private boolean isSignup = false;
    protected int GameNum = 0;
    protected int GameNumIndex =0;

    private java.util.List<IAuthorizeListener> autoModeListeners = new ArrayList<>();
    protected PlaygroundScreen nfs;

    /////////////////////////////////////////////////////////////////////////////////////

    public AuthorizeScreen(PlaygroundScreen nfs){
        this.nfs = nfs;
        initializeAuthorizeScreen();
        info = initializeInfoPanel();
        buttons = initializeButtonPanel();
        ImageIcon icon = new ImageIcon(new ImageIcon("src/assets/intro.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        add(new JLabel(icon));
        add(info,BorderLayout.EAST);
        add(buttons,BorderLayout.SOUTH);

    }

    // Initialize the outer JFrame of Authorization Screen
    private void initializeAuthorizeScreen() {
        this.setTitle("Need For Spear");
        this.setLayout(new GridLayout(3, 0));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
    }

    // Username and password fields, images
    private JPanel initializeInfoPanel(){
        GridLayout infoLayout = new GridLayout(2, 2);
        JPanel infoPanel = new JPanel(infoLayout);

        JLabel usernameLabel = new JLabel("  Username: ", SwingConstants.CENTER);
        ImageIcon usernameIcon = new ImageIcon(new ImageIcon("src/assets/user.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        usernameLabel.setIcon(usernameIcon);
        infoPanel.add(usernameLabel);
        userName = new JTextField(USERNAME, 30);
        infoPanel.add(userName);

        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        ImageIcon passwordIcon = new ImageIcon(new ImageIcon("src/assets/password.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        passwordLabel.setIcon(passwordIcon);
        infoPanel.add(passwordLabel);
        ID = new JTextField(PASSWORD, 8);
        infoPanel.add(ID);

        return infoPanel;
    }

    // Sign Up and Login buttons and their functionalities
    private JPanel initializeButtonPanel(){
        GridLayout buttonLayout = new GridLayout(1, 2);
        JPanel buttonPanel = new JPanel(buttonLayout);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPlayerLogs();
                String username = userName.getText();
                String id = ID.getText();
                if(isLogin){
                    saveNum();
                    for (IAuthorizeListener listener : autoModeListeners) {
                        listener.onClickEvent(nfs, username, id,GameNum);
                    }
                    System.out.println(username +" in autoscreen");
                }
                dispose();
            }
        });

        // Sign Up Button
        JButton signButton = new JButton("Sign Up");
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean succSign = savePlayerLogs();
                String username = userName.getText();
                String id = ID.getText();
                if(succSign){
                    for (IAuthorizeListener listener : autoModeListeners) {
                        listener.onClickEvent(nfs,username, id,GameNum);
                    }
                }
                dispose();
            }

        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signButton);

        return buttonPanel;
    }

    /* Checks all existing player usernames and passwords.
     * If a username-password pair exists in the log file, system allows to login.
     * If a username does not exist, or password is wrong, system does not allow to login.
     * */
    private void checkPlayerLogs() {

        String checking = userName.getText();
        String id = ID.getText();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(FILEPATH)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;

            JSONArray usernameList = (JSONArray) doc.get("Username");
            JSONArray IDList = (JSONArray) doc.get("ID");
            JSONArray gameNumList = (JSONArray) doc.get("GameNumber");

            for(int i=0; i<usernameList.size(); i++){
                String s = usernameList.get(i).toString();
                String m = IDList.get(i).toString();
                String k = gameNumList.get(i).toString();

                if(s.equals(checking)){
                    isUsernameExist = true;
                    if(m.equals(id)){
                        isLogin = true;
                        int a = Integer.parseInt(k);
                        a = a+1;
                        GameNum=a;
                        GameNumIndex =i;
                        System.out.println("User is found.");
                        break;
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* First it checks whether the provided username already exists in the log file.
     * If it exists, the system does not allow to sign up.
     * If it does not, system takes the username-password pair and puts it into the log file.
     */
    private boolean savePlayerLogs() {
        checkPlayerLogs();

        if(isUsernameExist){
            System.out.println("User is already exist");
            return false;
        }

        String signing = userName.getText();
        String id = ID.getText();
        String gameNumbers = GameNum+"";

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(FILEPATH)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;

            JSONArray usernameList = (JSONArray) doc.get("Username");
            JSONArray IDList = (JSONArray) doc.get("ID");
            JSONArray gameNumList = (JSONArray) doc.get("GameNumber");

            Document document = new Document();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<String> tempID = new ArrayList<>();
            ArrayList<String> tempNum = new ArrayList<>();

            for(int i=0; i<usernameList.size(); i++){
                String s = usernameList.get(i).toString();
                String m = IDList.get(i).toString();
                String k = gameNumList.get(i).toString();
                temp.add(s);
                tempID.add(m);

                if(i == GameNumIndex){
                    tempNum.add(gameNumbers);
                }

            }

            temp.add(signing);
            tempID.add(id);
            tempNum.add("0");
            document.put("Username", temp);
            document.put("ID", tempID);
            document.put("GameNumber", tempNum);

            try {
                FileWriter file = new FileWriter(FILEPATH);
                file.write(document.toJson());
                file.close();
                System.out.println("Signed up successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void saveNum() {
        String signing = userName.getText();
        String id = ID.getText();
        String gameNumbers = GameNum + "";

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(FILEPATH)) {
            Object obj = jsonParser.parse(reader);
            JSONObject doc = (JSONObject) obj;

            JSONArray usernameList = (JSONArray) doc.get("Username");
            JSONArray IDList = (JSONArray) doc.get("ID");
            JSONArray gameNumList = (JSONArray) doc.get("GameNumber");

            Document document = new Document();
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<String> tempID = new ArrayList<>();
            ArrayList<String> tempNum = new ArrayList<>();

            for (int i = 0; i < usernameList.size(); i++) {
                String s = usernameList.get(i).toString();
                String m = IDList.get(i).toString();
                String k = gameNumList.get(i).toString();
                temp.add(s);
                tempID.add(m);

                if (i == GameNumIndex) {
                    tempNum.add(gameNumbers);
                } else {
                    tempNum.add(k);
                }

            }
            document.put("Username", temp);
            document.put("ID", tempID);
            document.put("GameNumber", tempNum);

            try {
                FileWriter file = new FileWriter(FILEPATH);
                file.write(document.toJson());
                file.close();
                System.out.println("Update num sucessfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addListener(IAuthorizeListener listener) {
        autoModeListeners.add(listener);
    }

    public void removeListener(IAuthorizeListener listener) { autoModeListeners.remove(listener); }




}

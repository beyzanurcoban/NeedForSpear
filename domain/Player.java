package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player{
    // Player attributes
    private String id;
    private String userName;
    protected Integer GameNum;

    // Attributes that this player has
    private int chance_points;
    private int score;
    private HashMap<Integer, Integer> abilities;
    public List<IInventoryListener> inventoryListeners = new ArrayList<>();
    private List<TimeListener> timeListeners = new ArrayList<>();
    private boolean isMagicalAbilityActive = false;

    public Player(){
        this.chance_points = 3;
        this.score = 0;
        abilities = new HashMap<>();

    }

    public Player(String userName, String id) {
        this.id = id;
        this.userName = userName;
        this.chance_points = 3;
        this.score = 0;
        abilities = new HashMap<>();
    }

    public Player(String userName, String id, int chance, int score, HashMap<Integer, Integer> abilities){
        this.id = id;
        this.userName = userName;
        this.chance_points = chance;
        this.score = score;
        this.abilities = abilities;

    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
         this.id = id;
    }

    public void setGameNum(Integer num) {
        this.GameNum = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getChance_points() {
        return chance_points;
    }

    public void setChance_points(int chance_points) {
        this.chance_points = chance_points;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HashMap<Integer, Integer> getAbilities() {
        return abilities;
    }

    public void initializeInventory() {
        this.abilities = new HashMap<>();
        abilities.put(1,0);
        abilities.put(2,0);
        abilities.put(3,0);
        abilities.put(4,0);
    }

    public void notifyAllInventoryListeners(Integer toUpdate) {
        for (IInventoryListener listener : inventoryListeners) {
            listener.onInventoryChange(toUpdate);
        }
    }

    /* EFFECTS: by checking at the currentCount of updateAbilitiesInventory, it updates the currentCount
    and changeInPowerUpCount
     */
    public void updateAbilitiesInventory(int powerupType, int changeInPowerupCount) {
        int currentCount = abilities.get(powerupType);
        abilities.put(powerupType, currentCount + changeInPowerupCount);
        //notifyAllInventoryListeners("powerup");

    }
    public void addInventoryListener( IInventoryListener listener) {
        inventoryListeners.add(listener);
    }

    public void setIsMagicalAbilityActive(Boolean b){
         this.isMagicalAbilityActive =b;
    }
}


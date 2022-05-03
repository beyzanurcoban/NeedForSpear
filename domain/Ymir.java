package domain;

import domain.abilities.DoubleAccel;
import domain.abilities.HollowPurple;
import domain.abilities.InfiniteVoid;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ymir implements Runnable{
    private int period;
    private double[] weights;
    private Random random;
    private boolean running;



    public Ymir() {
        this.period = 30;
        this.weights = new double[]{0.33, 0.33, 0.34};
        long currentTime = System.currentTimeMillis();
        random = new Random(currentTime);
        this.running = true;
    }

    // Dice roll every <period> seconds to decide whether an ability will be used or not
    public Boolean rollDice(){
        return random.nextBoolean();
    }
    public Boolean getRunning(){return this.running; }
    // A double between 0 and 1 is generated and then compared with the normalized weights of abilities to choose among them.
    public YmirAbilities chooseAbility() {
        double threshold = random.nextDouble();
        if (threshold < weights[0]) {return YmirAbilities.InfiniteVoid;}
        else if (threshold < weights[1]) return YmirAbilities.DoubleAccel;
        else return YmirAbilities.HollowPurple;

    }

    public void setPeriod(int period) {
        this.period = period;
    }

    // Weights are doubles which will be normalized
    public void setProbabilities(double w1, double w2, double w3) {
        double sum = w1 + w2 + w3;
        weights[0] = w1 / sum;
        weights[1] = weights[0] + w2 / sum;
        weights[2] = weights[1] + w3 / sum;
    }

    public void terminate() {
        this.running = false;
    }

    @Override
    public void run() {
        while(running) {
            try {
                TimeUnit.SECONDS.sleep(this.period +15);
                if (rollDice()) {
                    YmirAbilities ability = chooseAbility();
                    System.out.println(ability);
                    if (ability == YmirAbilities.DoubleAccel) {
                        DoubleAccel da = new DoubleAccel();
                        Thread t = new Thread(da);
                        t.start();
                    }
                    else if(ability == YmirAbilities.HollowPurple) {
                        System.out.println("Hollow Purple by Ymir");
                        HollowPurple hp = new HollowPurple();
                        hp.initializeHollow();
                    }
                    else {
                        System.out.println("Infinite Void by Ymir");
                        InfiniteVoid iv = new InfiniteVoid();
                        Thread t = new Thread(iv);
                        t.start();
                    }
                } else {
                    System.out.println("No Ymir ability this time!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

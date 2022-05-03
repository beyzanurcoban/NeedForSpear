package domain;
import java.util.HashMap;


public interface IRunListener {

        void onRunEvent(HashMap<String, Integer> runSettings, String username, String id,Integer num, Integer freq,Double prob1, Double prob2, Double prob3);
    }



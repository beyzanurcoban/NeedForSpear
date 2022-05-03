package domain;

import UI.AuthorizeScreen;
import UI.PlaygroundScreen;
import domain.abilities.InfiniteVoid;

public class Main {
    static AuthorizeScreen autoMode;

    public static void main(String arr[]) {
            //main JFrame
            PlaygroundScreen nfs = new PlaygroundScreen();
            autoMode = new AuthorizeScreen(nfs);
            autoMode.setVisible(true);
            autoMode.addListener(nfs);

    }

}

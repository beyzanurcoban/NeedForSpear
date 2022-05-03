package domain;

import UI.PlaygroundScreen;

public interface IAuthorizeListener {
    void onClickEvent(PlaygroundScreen nfs, String username, String id, Integer gameNum);
}

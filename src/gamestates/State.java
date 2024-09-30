package gamestates;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class State {
    
    protected Game game;
    protected BufferedImage backgroundImg;
    
    public State(Game game) {
        this.game = game;
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}

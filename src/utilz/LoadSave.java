package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites_frog.png";
    public static final String LEVEL_ATLAS = "level_atlas.png";
    public static final String LEVEL = "1.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String BACKGROUND = "background.png";

    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/res/" + filename);
        
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];
        
        for (int j = 0; j < img.getHeight(); ++j) {
            for (int i = 0; i < img.getWidth(); ++i) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 192)
                    value = 0;
                levelData[j][i] = value;
            }
        }

        return levelData;
    }
}

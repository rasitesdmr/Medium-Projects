import model.Block;
import model.Map;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    public GameScreen() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        MapLoader mapLoader = new MapLoader();
        Map map = mapLoader.loadStandardMap();
        for (Block wall : map.getWall()) {
            g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
        }
    }

}

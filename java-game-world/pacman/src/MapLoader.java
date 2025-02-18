import model.Block;
import model.Map;

import javax.swing.*;
import java.util.HashSet;

public class MapLoader {

    public Map loadStandardMap() {
        Map map = new Map();
        String[] mapArray = map.getStandardMapArrays();
        HashSet<Block> wallHashSet = map.getWall();
        int rowCount = 5;
        int columnCount = 8;
        int tileSize = 10;

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                String row = mapArray[r];
                char mapChar = row.charAt(c);

                int x = c * (tileSize / 2);
                int y = r * (tileSize / 2);

                if (mapChar == 'X') {
                    Block wall = new Block(x, y, tileSize, tileSize, new ImageIcon(getClass().getResource("./wall.png")).getImage());
                    wallHashSet.add(wall);
                } else if (mapChar == 'B') {
                    Block wall = new Block(x, y, tileSize, tileSize, null);
                    wallHashSet.add(wall);
                }
            }
        }
        map.setWall(wallHashSet);
        return map;
    }
}

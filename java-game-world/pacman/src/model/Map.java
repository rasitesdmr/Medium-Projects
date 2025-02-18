package model;

import java.util.HashSet;

public class Map {

    private int rowCount;

    private int columnCount;

    private HashSet<Block> wall = new HashSet<>();

    private String[] standardMapArrays = {
            "XXXXXBBX",
            "XBBBXBBX",
            "XBBBXBBX",
            "XXXXXBBX",
            "XBBBBBBX",
    };

    public Map() {
    }

    public HashSet<Block> getWall() {
        return wall;
    }

    public void setWall(HashSet<Block> wall) {
        this.wall = wall;
    }

    public String[] getStandardMapArrays() {
        return standardMapArrays;
    }

    public void setStandardMapArrays(String[] standardMapArrays) {
        this.standardMapArrays = standardMapArrays;
    }
}

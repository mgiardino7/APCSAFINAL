public class Block {
    // type of block (e.g., dirt, stone, coal, iron, dilks/diamond)
    private String type;

    // whether the block has been mined or not
    private boolean mined;

    // constructor that sets the block type and marks it as unmined
    public Block(String type) {
        this.type = type;
        this.mined = false;
    }

    // returns the type of the block
    public String getType() {
        return type;
    }

    // returns true if the block has been mined
    public boolean isMined() {
        return mined;
    }

    // sets whether the block has been mined
    public void setMined(boolean mined) {
        this.mined = mined;
    }

    // returns the number of points awarded when this block is mined
    public int getPoints() {
        switch (type) {
            case "coal": return 1; // coal is worth 1 point
            case "iron": return 3; // iron is worth 3 points
            case "diamond": return 5; // diamond is worth 5 points
            default: return 0; // all other types (e.g., dirt, stone) are worth 0
        }
    }
}

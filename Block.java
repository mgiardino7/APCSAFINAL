// Represents a single block in the game grid
public class Block {
    // The type of block (e.g., dirt, stone, coal, iron, diamond)
    private String type;

    // Indicates whether the block has already been mined
    private boolean mined;

    // Constructor to initialize the block with a specific type
    public Block(String type) {
        this.type = type;
        this.mined = false; // Blocks start unmined by default
    }

    // Returns the type of the block
    public String getType() {
        return type;
    }

    // Returns true if the block has been mined, false otherwise
    public boolean isMined() {
        return mined;
    }

    // Sets the mined status of the block
    public void setMined(boolean mined) {
        this.mined = mined;
    }

    // Returns the number of points awarded when this block is mined
    public int getPoints() {
        switch (type) {
            case "coal": return 1;
            case "iron": return 3;
            case "diamond": return 20;
            default: return 0; // Other block types give no points
        }

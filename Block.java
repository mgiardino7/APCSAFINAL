public class Block {
    private String type;
    private boolean mined;

    public Block(String type) {
        this.type = type;
        this.mined = false;
    }

    public String getType() {
        return type;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined(boolean mined) {
        this.mined = mined;
    }

    public int getPoints() {
        switch (type) {
            case "coal": return 1;
            case "iron": return 3;
            case "diamond": return 20;
            default: return 0;
        }
    }
}

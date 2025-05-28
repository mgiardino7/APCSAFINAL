public class UpgradeManager {
    // stores the total score accumulated across all games
    public static int totalScore = 0;
    public static int clickUpgradeLevel = 0;
    public static int pointUpgradeLevel = 0;
    public static int luckUpgradeLevel = 10;

    // returns extra clicks based on upgrade level
    public static int getClickBonus() {
        return clickUpgradeLevel * 5; // +5 clicks per level
    }

    // returns bonus points added to valuable blocks
    public static int getPointBonus() {
        return pointUpgradeLevel; // +1 point per block per level
    }

    // returns a multiplier to affect ore generation chances
    public static double getLuckMultiplier() {
        return 1 + (luckUpgradeLevel * 0.05); // +5% increased chance for ores per level
    }

    // returns the cost to upgrade a specific level
    public static int getUpgradeCost(int level) {
        return 5 + (level * 3); // fair cost scaling per level
    }
}

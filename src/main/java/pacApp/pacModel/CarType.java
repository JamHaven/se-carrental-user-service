package pacApp.pacModel;

public enum CarType {
    SMALL, MEDIUM, LARGE;

    public double getCostMultiplier() {
        //open for extended adaptation
        return this.ordinal() * 0.3;
    }
}

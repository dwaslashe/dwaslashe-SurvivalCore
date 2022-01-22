package xyz.dwaslashe.survivalcore.enums;

public enum CustomHand {
    MAIN(1),
    LEFT(2),
    HAT(3),
    OTHER(4);

    private int id;

    CustomHand(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
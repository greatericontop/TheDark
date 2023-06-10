package io.github.greatericontop.thedark.guns;

public enum GunClassification {
    // this enum contains the classifications of guns (compared to GunType which contains distinct types for
    // each enhancement star), and also contains some characteristics that are constant across enhancement stars

    PISTOL("PISTOL", 30),
    RIFLE("RIFLE", 30),
    SHOTGUN("SHOTGUN", 40),
    SUPER_WEAPON("SUPER_WEAPON", 30),

    ;

    private final String rootName;
    private final int rechargeTicks;

    public int getRechargeTicks() {
        return rechargeTicks;
    }

    public GunType getRootGun() {
        return GunType.valueOf(rootName);
    }

    GunClassification(String rootName, int rechargeTicks) {
        this.rootName = rootName;
        this.rechargeTicks = rechargeTicks;
    }

}

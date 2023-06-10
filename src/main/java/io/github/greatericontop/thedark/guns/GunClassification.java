package io.github.greatericontop.thedark.guns;

public enum GunClassification {
    // this enum contains the classifications of guns (compared to GunType which contains distinct types for
    // each enhancement star), and also contains some characteristics that are constant across enhancement stars

    PISTOL("PISTOL", 30, "§7A basic pistol."),
    RIFLE("RIFLE", 30, "§7A high-powered rifle that fires quickly."),
    SHOTGUN("SHOTGUN", 40, "§7This shotgun damages multiple enemies."),
    SUPER_WEAPON("SUPER_WEAPON", 30, "§4Need I say more?"),

    ;

    private final String rootName;
    private final int rechargeTicks;
    private final String miniDescription;

    public int getRechargeTicks() {
        return rechargeTicks;
    }
    public String getMiniDescription() {
        return miniDescription;
    }

    public GunType getRootGun() {
        return GunType.valueOf(rootName);
    }

    GunClassification(String rootName, int rechargeTicks, String miniDescription) {
        this.rootName = rootName;
        this.rechargeTicks = rechargeTicks;
        this.miniDescription = miniDescription;
    }

}

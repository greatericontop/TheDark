# Plugin Setup

## World File

The default world file can be downloaded from [this link](https://github.com/greatericontop/TheDark/raw/refs/heads/master/r.22.22.mca).

It's a `.mca` file. Place it in the `world/region` folder in your server directory.
This should allow you to keep your existing world file.
(It will only change the blocks near XZ 11000, 11000.)

## config.yml

There's only one section called `valid-spawn-locations`, which contains a list of all the coordinates where zombies should spawn.

You can configure this on your own:

```
valid-spawn-locations:
- ["world", 1.0, 64.0, 1.0] # for world "world" X=1 Y=64 Z=1
```

Or copy-paste this section if you want to use the default map:

```
valid-spawn-locations:
- ["world", 11499.5, 292.0, 11499.5]
- ["world", 11500.5, 292.0, 11499.5]
- ["world", 11501.5, 292.0, 11499.5]
- ["world", 11499.5, 292.0, 11500.5]
- ["world", 11500.5, 292.0, 11500.5]
- ["world", 11501.5, 292.0, 11500.5]
- ["world", 11499.5, 292.0, 11501.5]
- ["world", 11500.5, 292.0, 11501.5]
- ["world", 11501.5, 292.0, 11501.5]
```

## Uncapping health

By default, all mobs can't have more than 2048 health.

To uncap health, find the `attribute:` section, under `settings:` in `spigot.yml`. Add this in that section

```
    maxHealth:
      max: 100000.0
```

## Building your own map

This part is optional, but keep reading if you want to build your own map!

### Spawning locations

As mentioned above, put the coordinates of the spawn locations in the `valid-spawn-locations` section of `config.yml`.

### Custom signs

Use the command `/thedark setSign <TYPE>` to turn a sign into a special sign.

Replace `<TYPE>` with one of the following:
- `startGame` - opens a menu to start the game
- `armor` - opens a menu to buy armor
- `buyGun_PISTOL` - buys a pistol, similar for other guns, `buyGun_RIFLE, buyGun_SHOTGUN, buyGun_FLAMETHROWER, buyGun_MIDAS_PISTOL, buyGun_ROCKET_LAUNCHER, buyGun_ICE_BLASTER`

The command is the only thing you need for a special sign; you can put whatever text or coloring/glow ink you want on the sign.

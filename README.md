# Data Loader

This mod adds a `datapacks` folder inside your game directory where you can put datapacks that will apply to all worlds.

## Configuration

Unlike sargunv's original mod, this port has a completely optional load order configuration located in `config/data-loader.json`.
<br><br><br>
`onlyLoadSpecified`: When set to true Minecraft will only load the datapacks in the `loadOrder` list and the fabric datapack. Unless the vanilla datapack or a suitable replacement is provided the game will crash upon launch.

`loadOrder`: Minecraft will load the datapacks in the order specified in this list. Datapacks in the ´datapacks´ folder needs to be prefixed with ´file/´, the `vanilla`, `fabric` and experimental datapacks don't need to be prefixed with anything.

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

## License

Data loader started as port of [Sargun Vohra's Data Loader](https://gitlab.com/sargunv-mc-mods/data-loader/-/tree/1.16) for 1.18.x, Which is licensed under [Apache License 2.0](https://gitlab.com/sargunv-mc-mods/data-loader/-/blob/1.16/LICENSE)

Data Loader is built on top of [FabricMC's Example mod](https://github.com/FabricMC/fabric-example-mod) which is licensed under [CC0-1.0](https://github.com/FabricMC/fabric-example-mod/blob/1.18/LICENSE)
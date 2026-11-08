# SwingThrough — Forge Port for Minecraft 1.20.1
![Mod loader: Forge](https://img.shields.io/badge/modloader-Forge-orange?style=for-the-badge)
![Minecraft: 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/SwingThrough263?style=for-the-badge)

**Allows targeting living entities through transparent blocks.**

SwingThrough discards the block crosshair target when the block has
empty collision, the entity is living and attackable, you're not
riding it, and it's within reach — so you can hit entities through
grass, crops, and flowers instead of swinging at the block.

This branch is a **Forge port for Minecraft 1.20.1** with the
full original logic ported from the upstream 1.20-era GameRenderer
mixin (Mojang `pick`, the crosshair picking routine).

### Forge port notes (1.20.1)
- Java 17 · Forge 47.4.20 · official mappings
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation
1. Install Forge 47.4.20 for Minecraft 1.20.1
2. Drop `swingthrough-1.20.1-forge-*.jar` into your `mods` folder

### Original mod & credit
Unofficial Forge port by **Itamio** of **SwingThrough** by
**sisby-folk** (6.3M downloads, max 1.21.1, LGPL-3.0-only).
Original: https://modrinth.com/mod/swingthrough ·
https://github.com/sisby-folk/swingthrough · See `NOTICE`.

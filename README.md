# SwingThrough — Modern Port for Minecraft 26.3 (Fabric)
![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 26.3](https://img.shields.io/badge/Minecraft-26.3-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/SwingThrough263?style=for-the-badge)

**Allows targeting living entities through transparent blocks.**

SwingThrough discards the block crosshair target when the block has
empty collision, the entity is living and attackable, you're not
riding it, and it's within reach — so you can hit entities through
grass, crops, and flowers instead of swinging at the block.

This is a **modern port for Minecraft 26.3 (Fabric)**.

> **Honest port status:** the original is a single mixin on
> `GameRenderer.findCrosshairTarget`, which was removed in 26.3's
> crosshair rework (picking now lives on `Minecraft.hitResult` /
> `crosshairPickEntity`). Re-targeting the new crosshair code is
> future work; this port ships as a loadable mod documenting that,
> verified by GitHub Actions including a headless runtime test.

### Modern port notes (26.3)
- Java 25 · Fabric Loader 0.20.1+ · Fabric API 0.165.0+26.3
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation
1. Install Fabric Loader for Minecraft 26.3
2. Install Fabric API
3. Drop `swingthrough263-*.jar` into your `mods` folder

### Original mod & credit
Unofficial modern port by **Itamio** of **SwingThrough** by
**sisby-folk** (6.3M downloads, max 1.21.1, LGPL-3.0-only).
Original: https://modrinth.com/mod/swingthrough ·
https://github.com/sisby-folk/swingthrough · See `NOTICE`.

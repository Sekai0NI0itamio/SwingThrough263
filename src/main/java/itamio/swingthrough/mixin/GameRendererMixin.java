package itamio.swingthrough.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Faithful Mojang-mapped port of the original 1.20-era mixin
 * (folk.sisby.swingthrough.mixin.client.GameRendererMixin, LGPL-3.0-only).
 * Mojang names the Yarn {@code updateTargetedEntity} method {@code pick}:
 * {@code pick(float tickDelta)} — the crosshair picking routine in 1.20.1.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    private Double swingthrough$reach = null;

    @ModifyVariable(method = "pick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;distanceToSqr(Lnet/minecraft/world/phys/Vec3;)D", ordinal = 0), ordinal = 1)
    private double cacheReach(double original) {
        swingthrough$reach = Minecraft.getInstance().hitResult.getType() == HitResult.Type.BLOCK && Minecraft.getInstance().hitResult instanceof BlockHitResult && Minecraft.getInstance().level.getBlockState(((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos()).getCollisionShape(Minecraft.getInstance().level, ((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos()).isEmpty() ? original : null;
        return original;
    }

    @ModifyArg(method = "pick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;"), index = 5)
    private double useOriginalReachForEntityRaycast(double original) {
        return swingthrough$reach == null ? original : swingthrough$reach;
    }

    @ModifyVariable(method = "pick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/EntityHitResult;getLocation()Lnet/minecraft/world/phys/Vec3;"), ordinal = 1)
    private Entity discardEmptyBlockHit(Entity hitEntity) {
        if (swingthrough$reach != null && Minecraft.getInstance().player != null && Minecraft.getInstance().getCameraEntity().position().distanceToSqr(hitEntity.position()) < swingthrough$reach && hitEntity instanceof LivingEntity && !hitEntity.isSpectator() && hitEntity.isAttackable() && !hitEntity.equals(Minecraft.getInstance().player.getVehicle())) {
            Minecraft.getInstance().hitResult = null;
        }
        return hitEntity;
    }
}

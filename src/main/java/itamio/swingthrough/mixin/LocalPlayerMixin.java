package itamio.swingthrough.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Faithful Mojang-mapped port of the single original mixin
 * (folk.sisby.swingthrough.mixin.client.GameRendererMixin, LGPL-3.0-only).
 * In 1.21.11 the picking logic moved from GameRenderer to LocalPlayer;
 * Mojang names the Yarn {@code findCrosshairTarget} method {@code pick}:
 * (static in 1.21.11, so the callbacks below are static as Mixin requires).
 * {@code pick(Entity, double blockInteractionRange, double entityInteractionRange, float tickDelta)}.
 */
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    private static boolean swingthrough$validBlock = false;
    private static boolean swingthrough$validEntity = false;

    @ModifyVariable(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/phys/HitResult;getLocation()Lnet/minecraft/world/phys/Vec3;"), ordinal = 0)
    private static HitResult cacheReach(HitResult hitResult, Entity camera, double blockInteractionRange, double entityInteractionRange, float tickDelta) {
        swingthrough$validBlock = hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult && Minecraft.getInstance().level.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getCollisionShape(Minecraft.getInstance().level, ((BlockHitResult) hitResult).getBlockPos()).isEmpty();
        return hitResult;
    }

    @ModifyReceiver(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"))
    private static HitResult treatTransparentAsMissed(HitResult instance) {
        return swingthrough$validBlock ? BlockHitResult.miss(instance.getLocation(), Direction.EAST, BlockPos.containing(instance.getLocation().x, instance.getLocation().y, instance.getLocation().z)) : instance;
    }

    @ModifyVariable(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;"), ordinal = 0)
    private static EntityHitResult checkEntityValid(EntityHitResult hitResult, Entity camera, double blockInteractionRange, double entityInteractionRange, float tickDelta) {
        Entity hitEntity = hitResult == null ? null : hitResult.getEntity();
        swingthrough$validEntity = hitEntity != null && Minecraft.getInstance().player != null && Minecraft.getInstance().getCameraEntity().position().distanceToSqr(hitEntity.position()) < Mth.square(entityInteractionRange) && hitEntity instanceof LivingEntity && !hitEntity.isSpectator() && hitEntity.isAttackable() && !hitEntity.equals(Minecraft.getInstance().player.getVehicle());
        return hitResult;
    }

    @ModifyVariable(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;distanceToSqr(Lnet/minecraft/world/phys/Vec3;)D", ordinal = 1), ordinal = 4)
    private static double ignoreBlockHit(double original) {
        return swingthrough$validBlock && swingthrough$validEntity ? Double.MAX_VALUE : original;
    }
}

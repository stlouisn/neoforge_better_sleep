package net.sssubtlety.no_sneaking_over_magma.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.sssubtlety.no_sneaking_over_magma.FeatureControl.shouldSneakingProtectFromMagma;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract BlockPos getLandingPosition();

    @Shadow public abstract World getWorld();

    @ModifyExpressionValue(method = "bypassesSteppingEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    private boolean tryNotCountingAsSneakingForMagma(boolean sneaking) {
        return sneaking && (
            shouldSneakingProtectFromMagma() ||
            Blocks.MAGMA_BLOCK != getWorld().getBlockState(this.getLandingPosition()).getBlock()
        );
    }
}

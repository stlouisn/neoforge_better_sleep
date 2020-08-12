package net.sssubtlety.no_sneaking_over_magma.mixin;

import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract boolean bypassesSteppingEffects();

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;bypassesSteppingEffects()Z"))
    boolean bypassesSteppingEffectsExceptMagma(Entity entity) {
        if(((Entity)(Object)this).world.getBlockState(this.callGetLandingPos()).getBlock() instanceof MagmaBlock) {
            return false;
        } else {
            return this.bypassesSteppingEffects();
        }
    }

    @Invoker
    abstract BlockPos callGetLandingPos();
}

package dev.better_sleep.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public class WorldMixin {

  @Final
  @Shadow
  private RegistryEntry<DimensionType> dimensionEntry;

  @Shadow
  public DimensionType getDimension() {
    return this.dimensionEntry.value();
  }

  /**
   * @author STL
   * @reason Overwrite isDay() to control when a player can sleep based on getTimeOfDay instead of ambientDarkness
   */
  @Overwrite
  public boolean isDay() {
    if (this.getDimension().hasFixedTime()) { return true; }

    long timeOfDay = ((World) (Object) this).getTimeOfDay() % 24000L;

    return timeOfDay < 13570L || timeOfDay > 22370L;

  }
}
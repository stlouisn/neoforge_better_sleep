package dev.better_sleep.mixin;

import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SleepFinishedTimeEvent.class)
public class SleepFinishedTimeEventMixin {

  @Shadow
  private long newTime;

  /**
   * @author STL
   * @reason Overwrite getNewTime() to modify the time a player wakes after sleeping through the night.
   */
  @Overwrite
  public long getNewTime() {
    return newTime - 1500L;
  }
}
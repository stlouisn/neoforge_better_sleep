package net.sssubtlety.no_sneaking_over_magma;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

import java.util.Collections;

import static net.minecraft.entity.EntityType.ARMOR_STAND;

public class DummyLivingEntity extends LivingEntity {
    public DummyLivingEntity(World world) {
        super(ARMOR_STAND, world);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) { }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
    }
}

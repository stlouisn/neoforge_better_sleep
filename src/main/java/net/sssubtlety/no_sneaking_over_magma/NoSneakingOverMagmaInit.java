package net.sssubtlety.no_sneaking_over_magma;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NoSneakingOverMagmaInit implements ModInitializer {
	// an instance of our new item
	public static final Item FABRIC_ITEM = new Item(new Item.Settings().group(ItemGroup.MISC));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//Register config class
		AutoConfig.register(NoSneakingOverMagmaConfig.class, GsonConfigSerializer::new);
	}
}

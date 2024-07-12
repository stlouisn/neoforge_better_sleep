package net.sssubtlety.no_sneaking_over_magma;

import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "no_sneaking_over_magma")
public class Config implements ConfigData {
    boolean sneaking_protects_from_magma = FeatureControl.Defaults.sneaking_protects_from_magma;

    boolean frost_walker_protects_from_magma = FeatureControl.Defaults.frost_walker_protects_from_magma;

    boolean magma_damages_non_living_entities = FeatureControl.Defaults.magma_damages_non_living_entities;

    boolean magma_sets_fire_to_entities = FeatureControl.Defaults.magma_sets_fire_to_entities;
}

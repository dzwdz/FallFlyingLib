package net.adriantodt.fallflyinglib.impl;

import net.adriantodt.fallflyinglib.FallFlyingAbility;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class FallFlyingLibInternals {
    /**
     * Overrides the default initAi return value.
     */
    public static BiPredicate<LivingEntity, Boolean> INITAI_OVERRIDE = null;

    private static final Set<Function<LivingEntity, FallFlyingAbility>> accessors = ConcurrentHashMap.newKeySet();

    public static void registerAccessor(Function<LivingEntity, FallFlyingAbility> accessor) {
        accessors.add(accessor);
    }

    public static boolean isFallFlyingAllowed(LivingEntity livingEntity) {
        for (Function<LivingEntity, FallFlyingAbility> accessor : accessors) {
            if (accessor.apply(livingEntity).allowFallFlying()) {
                return true;
            }
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public static boolean shouldHideCape(LivingEntity livingEntity) {
        for (Function<LivingEntity, FallFlyingAbility> accessor : accessors) {
            if (accessor.apply(livingEntity).shouldHideCape()) {
                return true;
            }
        }
        return false;
    }
}

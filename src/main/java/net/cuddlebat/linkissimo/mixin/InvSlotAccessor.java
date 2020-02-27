package net.cuddlebat.linkissimo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.container.Slot;

@Mixin(Slot.class)
public interface InvSlotAccessor
{
	@Accessor
	public int getInvSlot();
}

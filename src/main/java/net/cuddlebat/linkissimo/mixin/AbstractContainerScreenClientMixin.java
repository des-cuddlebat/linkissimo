package net.cuddlebat.linkissimo.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.linkissimo.client.LinkQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Slot;
import net.minecraft.text.Text;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenClientMixin extends Screen
{
	private AbstractContainerScreenClientMixin(Text title)
	{
		super(title);
	}
	
	@Invoker("getSlotAt")
	public abstract Slot slotAt(double x, double y);
	
	@Inject(at = @At("HEAD"), method = "mouseClicked(DDI)Z", cancellable = true)
	public void enqueueLinkedItemSlotAction(double x, double y, int butt, CallbackInfoReturnable<Boolean> cir)
	{
		Slot slot = slotAt(x, y);
		if(slot != null && Screen.hasAltDown() && Screen.hasShiftDown() && butt == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
		{
			// TODO rework this whole if/else, like, really bad
			if(slot.inventory == MinecraftClient.getInstance().player.inventory)
			{
				int invSlot = ((InvSlotAccessor)(Object)slot).getInvSlot();
				LinkQueue.QUEUE.add("[i=" + invSlot + "]");
			}
			else
			{
				LinkQueue.QUEUE.add("[invalid link]");
			}
			cir.setReturnValue(true);
		}
	}
}

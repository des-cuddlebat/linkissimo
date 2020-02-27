package net.cuddlebat.linkissimo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.linkissimo.client.LinkQueue;
import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin
{
	@Accessor("originalChatText")
	public abstract void setOriginalChatText(String originalChatText);
	
	@Inject(at = @At("RETURN"), method = "<init>(Ljava/lang/String;)V")
	public void ctorMixinPutLinkedItems(String originalMessage, CallbackInfo ci)
	{
		if(originalMessage.isEmpty() && !LinkQueue.QUEUE.isEmpty())
		{
			String str = "";
			String next;
			while((next = LinkQueue.QUEUE.poll()) != null)
			{
				str = str + next + " ";
			}
			this.setOriginalChatText(str);
		}
	}
}

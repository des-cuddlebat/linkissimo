package net.cuddlebat.linkissimo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.linkissimo.text.item.BasicLinkContext;
import net.cuddlebat.linkissimo.text.item.ItemParser;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.text.TranslatableText;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin
{
	@Redirect(
		at = @At(
			target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/TranslatableText;", // "Lnet/minecraft/text/TranslatableText;<init>(Ljava/lang/String;[Ljava/lang/Object;)V"
			value = "NEW",
			ordinal = 2
			),
		method = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;onChatMessage(Lnet/minecraft/server/network/packet/ChatMessageC2SPacket;)V"
		)
	private TranslatableText tranlatableTextInitRedirect(String key, Object[] objs, ChatMessageC2SPacket packet)
	{
		ServerPlayNetworkHandler self = (ServerPlayNetworkHandler)(Object)this;
		objs[1] = ItemParser.parse((String)objs[1], new BasicLinkContext(self.player));
		return new TranslatableText(key, objs);
	}
}

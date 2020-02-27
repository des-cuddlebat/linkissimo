package net.cuddlebat.linkissimo.text.item;

import net.minecraft.entity.player.PlayerEntity;

public class BasicLinkContext implements LinkContext
{
	private final PlayerEntity player;
	
	public BasicLinkContext(PlayerEntity player)
	{
		super();
		this.player = player;
	}

	@Override
	public PlayerEntity getPlayer()
	{
		return player;
	}
	
}

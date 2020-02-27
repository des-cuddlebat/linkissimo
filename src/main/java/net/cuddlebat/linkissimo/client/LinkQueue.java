package net.cuddlebat.linkissimo.client;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class LinkQueue
{
	public static final Queue<String> QUEUE = new ConcurrentLinkedQueue<String>();
	
	
}

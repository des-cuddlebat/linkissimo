package net.cuddlebat.linkissimo.text.item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ItemParser
{
	public static final Map<String, BiFunction<LinkContext, String, ItemStack>> PARSERS = new HashMap<>();
	
	static
	{
		PARSERS.put("s", (context, key) -> context.getPlayer()
			.getEquippedStack(EquipmentSlot.byName(key)));
		PARSERS.put("i", (context, key) -> context.getPlayer()
			.inventory.getInvStack(Integer.parseInt(key)));
		PARSERS.put("h", (context, key) -> context.getPlayer()
			.inventory.getInvStack(Integer.parseInt(key) - 1));
	}
	
	public static Text parse(String str, LinkContext context)
	{
		Pattern regex = Pattern.compile("\\[(\\S+?)=(\\S+?)\\]");
		Text text = new LiteralText("");
		Matcher match = regex.matcher(str);
		int last = 0;
		String type, key;
		while(match.find())
		{
			text.append(str.substring(last, match.start()));
			type = match.group(1);
			key  = match.group(2);
			BiFunction<LinkContext, String, ItemStack> parser = PARSERS.get(type);
			ItemStack stack = parser != null ? parser.apply(context, key) : null;
			text.append(stack != null ? itemText(stack) : new LiteralText("[invalid item]"));
			last = match.end();
		}
		text.append(str.substring(last));
		return text;
	}
	
	private static Text itemText(ItemStack stack)
	{
		return stack.toHoverableText().formatted(Formatting.AQUA);
//		return new LiteralText("[").append(stack.toHoverableText()).append("]")
//			.formatted(Formatting.GREEN);
	}
}

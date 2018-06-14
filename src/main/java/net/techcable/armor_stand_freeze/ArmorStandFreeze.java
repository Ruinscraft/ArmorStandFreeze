package net.techcable.armor_stand_freeze;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;

public final class ArmorStandFreeze extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		if (!hasClass("com.destroystokyo.paper.event.entity.EntityAddToWorldEvent")) {
			getLogger().severe("Missing Paper APIs, are you still using Spigot?");
			setEnabled(false);
			return;
		}
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onEntityAdd(EntityAddToWorldEvent event) {
		Entity entity = event.getEntity();
		
		Chunk chunk = entity.getLocation().getChunk();
		
		int amountInChunk = 0;
		
		for (Entity entityInChunk : chunk.getEntities()) {
			if (!(entityInChunk instanceof ArmorStand)) {
				continue;
			}

			amountInChunk++;
			
			if (amountInChunk > 20) {
				((ArmorStand) entity).setCanMove(false);
			}
		}
	}

	public static boolean hasClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
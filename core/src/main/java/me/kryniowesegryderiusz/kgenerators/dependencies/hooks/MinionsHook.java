package me.kryniowesegryderiusz.kgenerators.dependencies.hooks;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.thecrappiest.minions.miner.events.MinerBreakBlockAttemptEvent;

import me.kryniowesegryderiusz.kgenerators.Main;
import me.kryniowesegryderiusz.kgenerators.generators.locations.objects.GeneratorLocation;

/**
 * This minions hook: https://www.mc-market.org/resources/17454/
 */
public class MinionsHook implements Listener {
	
	@EventHandler
	public void onMinerBreakBlockAttemptEvent(final MinerBreakBlockAttemptEvent e){
		Location location = e.getBlock().getLocation();
		GeneratorLocation gLocation = Main.getPlacedGenerators().getLoaded(location);
		if (gLocation != null) {
			if (!gLocation.isBlockPossibleToMine(location))
				e.setCancelled(true);
			else
				gLocation.scheduleGeneratorRegeneration();
		}
	}

}

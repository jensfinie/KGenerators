package me.kryniowesegryderiusz.kgenerators.api.objects;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kryniowesegryderiusz.kgenerators.api.interfaces.IGeneratorLocation;
import me.kryniowesegryderiusz.kgenerators.logger.Logger;

public abstract class AbstractGeneratedObject {
	
	@Getter private String type;
	@Getter private Double chance = 0.0;
	
	public AbstractGeneratedObject(String type) {
		this.type = type;
	}
	
	/**
	 * Compares two generated objects if they are equal.
	 * @return true if its same object in different instances
	 */
	public boolean equals(AbstractGeneratedObject generatedObject) {
		if (!generatedObject.getType().equals(this.type))
			return false;
		return this.compareSameType(generatedObject);
	}
	
	/**
	 * Loads GeneratorObject from config
	 * @param generatedObjectConfig
	 * @return true if loading was successful
	 */
	public boolean load(Map<?, ?> generatedObjectConfig) {
		
		try {
			if (generatedObjectConfig.get("chance") != null)
				this.chance = (Double) generatedObjectConfig.get("chance");
			else {
				Logger.error("Generators file: Cant load chance for one generation! Using 0%");
				return false;
			}
			
			if (!this.loadTypeSpecific(generatedObjectConfig))
				return false;
			
			Logger.debug("Generators file: Loaded GeneratedObject: " + this.toString());
			
		} catch (Exception e) {
			Logger.error("Generators file: Cant load GeneratedObject: " + this.toStringSimple());
			Logger.error(e);
			return false;
		}
		return true;

	}
	
	public String toStringSimple() {
		return "Type: " + this.type + " | Chance: " + String.valueOf(this.chance);
	}
	
	public String toString() {
		return this.toStringSimple() + " | " + this.toStringSpecific();
	}
	
	/**
	 * Regenerates generator
	 * @param GeneratorLocation
	 */
	public abstract void regenerate(IGeneratorLocation generatorLocation);
	
	/**
	 * Gets item displayed in chances gui
	 * @return ItemStack
	 */
	public abstract ItemStack getGuiItem();
	
	/**
	 * Gets GeneratedObjects specific info for logging purposes
	 * @return
	 */
	protected abstract String toStringSpecific();
	
	/**
	 * Compares GeneratedObjects that have same type;
	 * @return true if its same object in different instances
	 */
	protected abstract boolean compareSameType(AbstractGeneratedObject generatedObject);
	
	/**
	 * Loads values specific for GeneratorObject
	 * @param generatedObjectConfig
	 * @return true if loading was successful
	 */
	protected abstract boolean loadTypeSpecific(Map<?, ?> generatedObjectConfig);
}

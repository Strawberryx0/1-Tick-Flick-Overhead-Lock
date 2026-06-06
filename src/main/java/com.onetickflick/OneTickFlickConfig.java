package com.onetickflick;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("onetickflick")
public interface OneTickFlickConfig extends Config
{
	@ConfigItem(
			keyName = "detectPrayerBookClicks",
			name = "Detect prayer book clicks",
			position = 0,
			description = "Whether the overlay should also detect clicks on the prayer book.")
	default boolean detectPrayerBookClicks()
	{
		return true;
	}

	@ConfigItem(
			keyName = "barStyle",
			name = "Style",
			position = 1,
			description = "Choose which visual style the overhead timing bar uses."
	)
	default OneTickFlickBarStyle barStyle()
	{
		return OneTickFlickBarStyle.ORIGINAL;
	}

	@Units(Units.MILLISECONDS)
	@ConfigItem(
			keyName = "clickDelayMilliseconds",
			name = "Click delay - latency",
			position = 2,
			description = "Add a delay to your clicks to account for latency (ping).")
	@Range(min = 0, max = 500)
	default int clickDelayMilliseconds()
	{
		return 0;
	}

	@ConfigSection(
			position = 4,
			name = "Original Style",
			description = "Configure the original red and green timing bar style"
	)
	String originalStyleSection = "originalStyleSection";

	@Units(Units.MILLISECONDS)
	@ConfigItem(
			keyName = "greenStart",
			name = "Target zone start",
			description = "How long into the tick the target zone starts. Used as a visual aid and to determine the combo counter.",
			position = 0,
			section = originalStyleSection
	)
	@Range(min = 0, max = 600)
	default int greenStart()
	{
		return 0;
	}

	@Units(Units.MILLISECONDS)
	@ConfigItem(
			keyName = "greenEnd",
			name = "Target zone end",
			description = "How long into the tick the target zone ends. Used as a visual aid and to determine the combo counter.",
			position = 1,
			section = originalStyleSection
	)
	@Range(min = 0, max = 600)
	default int greenEnd()
	{
		return 500;
	}

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "originalBarWidth",
			name = "Bar width",
			position = 2,
			section = originalStyleSection,
			description = "Width in pixels of the original style overhead bar"
	)
	@Range(min = 30, max = 300)
	default int originalBarWidth()
	{
		return 150;
	}

	@ConfigSection(
			position = 5,
			name = "RuneLite Style",
			description = "Configure the RuneLite prayer bar style"
	)
	String runeliteStyleSection = "runeliteStyleSection";

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "originalBarHeight",
			name = "Bar height",
			position = 3,
			section = originalStyleSection,
			description = "Height in pixels of the original style overhead bar"
	)
	@Range(min = 5, max = 50)
	default int originalBarHeight()
	{
		return 12;
	}

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "originalSwipeLineWidth",
			name = "Swipe line width",
			position = 4,
			section = originalStyleSection,
			description = "Width in pixels of the original style swipe line"
	)
	@Range(min = 1, max = 25)
	default int originalSwipeLineWidth()
	{
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "originalTargetZoneColor",
			name = "Target zone color",
			description = "Color of the original style target zone",
			section = originalStyleSection,
			position = 5
	)
	default Color originalTargetZoneColor()
	{
		return new Color(0, 255, 0, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "originalBackgroundColor",
			name = "Background color",
			description = "Color of the original style background bar",
			section = originalStyleSection,
			position = 6
	)
	default Color originalBackgroundColor()
	{
		return new Color(255, 0, 0, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "originalClickColor",
			name = "Click X color",
			description = "Color of the original style X click markers",
			section = originalStyleSection,
			position = 7
	)
	default Color originalClickColor()
	{
		return new Color(0, 0, 0, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "originalSwipeLineColor",
			name = "Swipe line color",
			description = "Color of the original style swipe line",
			section = originalStyleSection,
			position = 8
	)
	default Color originalSwipeLineColor()
	{
		return new Color(0, 0, 0, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "originalBorderColor",
			name = "Border color",
			description = "Color of the original style border",
			section = originalStyleSection,
			position = 9
	)
	default Color originalBorderColor()
	{
		return new Color(0, 0, 0, 255);
	}

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "runeliteSwipeLineWidth",
			name = "Swipe line width",
			position = 0,
			section = runeliteStyleSection,
			description = "Width in pixels of the RuneLite style swipe line"
	)
	@Range(min = 1, max = 5)
	default int runeliteSwipeLineWidth()
	{
		return 1;
	}

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "runeliteClickFlashWidth",
			name = "Click flash width",
			position = 1,
			section = runeliteStyleSection,
			description = "Width in pixels of the vertical click markers on the RuneLite style bar"
	)
	@Range(min = 1, max = 5)
	default int runeliteClickFlashWidth()
	{
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "runeliteBarFillColor",
			name = "Bar fill color",
			description = "Color of the filled portion of the RuneLite style prayer bar",
			section = runeliteStyleSection,
			position = 2
	)
	default Color runeliteBarFillColor()
	{
		return new Color(0, 149, 151, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "runeliteBarBackgroundColor",
			name = "Bar background color",
			description = "Color of the empty portion of the RuneLite style prayer bar",
			section = runeliteStyleSection,
			position = 3
	)
	default Color runeliteBarBackgroundColor()
	{
		return new Color(0, 0, 0, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "runeliteClickFlashColor",
			name = "Click flash color",
			description = "Color of the RuneLite style vertical click markers",
			section = runeliteStyleSection,
			position = 4
	)
	default Color runeliteClickFlashColor()
	{
		return new Color(255, 255, 255, 255);
	}

	@Alpha
	@ConfigItem(
			keyName = "runeliteSwipeLineColor",
			name = "Swipe line color",
			description = "Color of the RuneLite style swipe line",
			section = runeliteStyleSection,
			position = 5
	)
	default Color runeliteSwipeLineColor()
	{
		return Color.CYAN;
	}

	@ConfigSection(
			position = 6,
			name = "Combo Counter",
			description = "Configure the movable combo counter"
	)
	String comboSection = "comboSection";

	@ConfigItem(
			keyName = "showCombo",
			name = "Show combo counter",
			position = 0,
			section = comboSection,
			description = "Whether to show the combo counter in the overlay. Combo counter is the number of consecutive double clicks within the target zone.")
	default boolean showCombo()
	{
		return true;
	}

	@ConfigItem(
			keyName = "comboText",
			name = "Combo text",
			position = 1,
			section = comboSection,
			description = "Text to display in the combo overlay. Use {combo} where the combo number should appear.")
	default String comboText()
	{
		return "Combo: {combo}";
	}

	@Units(Units.PIXELS)
	@ConfigItem(
			keyName = "comboTextSize",
			name = "Combo text size",
			position = 2,
			section = comboSection,
			description = "Font size of the combo counter text"
	)
	@Range(min = 8, max = 48)
	default int comboTextSize()
	{
		return 16;
	}

	@Range(min = 0, max = 255)
	@ConfigItem(
			keyName = "comboTextOpacity",
			name = "Combo text opacity",
			position = 3,
			section = comboSection,
			description = "Opacity of the combo counter text from 0 to 255"
	)
	default int comboTextOpacity()
	{
		return 255;
	}

	@Alpha
	@ConfigItem(
			keyName = "comboTextColor",
			name = "Combo text color",
			description = "Color of the combo counter text in the overlay",
			section = comboSection,
			position = 4
	)
	default Color comboTextColor()
	{
		return new Color(255, 255, 255, 255);
	}

	@ConfigSection(
			position = 7,
			name = "Overlay Timeout",
			description = "Configure the overlay timeout settings"
	)
	String timeoutSection = "timeoutSection";

	@ConfigItem(
			keyName = "enableTimeout",
			name = "Enable overlay timeout",
			position = 0,
			section = timeoutSection,
			description = "Whether the overlay should automatically hide after a period without clicking the quick prayer orb.")
	default boolean enableTimeout()
	{
		return false;
	}

	@ConfigItem(
			keyName = "timeoutWhilePrayerActive",
			name = "Timeout while prayer active",
			position = 1,
			section = timeoutSection,
			description = "Whether the overlay should timeout even while you are actively praying.")
	default boolean timeoutWhilePrayerActive()
	{
		return false;
	}

	@Units(Units.SECONDS)
	@ConfigItem(
			keyName = "overlayTimeoutSeconds",
			name = "Overlay timeout",
			position = 2,
			section = timeoutSection,
			description = "How long after last clicking the quick prayer orb the overlay should remain visible")
	@Range(min = 1, max = 600)
	default int overlayTimeoutSeconds()
	{
		return 30;
	}
}

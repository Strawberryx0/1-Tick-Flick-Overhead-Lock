package com.onetickflick;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class OneTickFlickOverlay extends Overlay
{
	private static final Color DEFAULT_BAR_FILL_COLOR = new Color(0, 149, 151);
	private static final Color DEFAULT_BAR_BACKGROUND_COLOR = Color.black;
	private static final Color DEFAULT_CLICK_FLASH_COLOR = Color.white;
	private static final Color DEFAULT_SWIPE_LINE_COLOR = Color.CYAN;
	private static final Dimension PRAYER_BAR_SIZE = new Dimension(30, 5);
	private static final int TICK_LENGTH = 600;

	private final OneTickFlickPlugin plugin;
	private final Client client;
	private final List<Integer> clickOffsets = new CopyOnWriteArrayList<>();
	private volatile boolean visible = true;

	private OneTickFlickBarStyle barStyle;
	private Color runeliteBarFillColor;
	private Color runeliteBarBackgroundColor;
	private Color runeliteClickFlashColor;
	private Color runeliteSwipeLineColor;
	private int runeliteSwipeLineWidth;
	private int runeliteClickFlashWidth;


	@Inject
	OneTickFlickOverlay(OneTickFlickPlugin plugin, Client client, OneTickFlickConfig config)
	{
		this.plugin = plugin;
		this.client = client;
		loadConfig(config);

		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	void loadConfig(OneTickFlickConfig config)
	{
		barStyle = config.barStyle();
		runeliteBarFillColor = config.runeliteBarFillColor();
		runeliteBarBackgroundColor = config.runeliteBarBackgroundColor();
		runeliteClickFlashColor = config.runeliteClickFlashColor();
		runeliteSwipeLineColor = config.runeliteSwipeLineColor();
		runeliteSwipeLineWidth = config.runeliteSwipeLineWidth();
		runeliteClickFlashWidth = config.runeliteClickFlashWidth();
	}

	void recordClick(int offset)
	{
		clickOffsets.add(offset);
	}

	void newTick()
	{
		clickOffsets.clear();
	}

	void setVisible(boolean v)
	{
		visible = v;
	}

	boolean isVisible()
	{
		return visible;
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		if (!visible || barStyle != OneTickFlickBarStyle.RUNELITE_PRAYER)
		{
			return null;
		}

		Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null)
		{
			return null;
		}

		LocalPoint localLocation = localPlayer.getLocalLocation();
		if (localLocation == null)
		{
			return null;
		}

		net.runelite.api.Point canvasPoint = Perspective.localToCanvas(
				client,
				localLocation,
				client.getPlane(),
				localPlayer.getLogicalHeight() + 10);
		if (canvasPoint == null)
		{
			return null;
		}

		renderRunelitePrayerStyle(g, canvasPoint);
		return null;
	}

	private void renderRunelitePrayerStyle(Graphics2D g, net.runelite.api.Point canvasPoint)
	{
		final int barX = canvasPoint.getX() - 15;
		final int barY = canvasPoint.getY();
		final int barWidth = PRAYER_BAR_SIZE.width;
		final int barHeight = PRAYER_BAR_SIZE.height;
		final float ratio = (float) client.getBoostedSkillLevel(Skill.PRAYER) / client.getRealSkillLevel(Skill.PRAYER);
		final int progressFill = (int) Math.ceil(Math.min((barWidth * ratio), barWidth));

		g.setColor(visibleColor(runeliteBarBackgroundColor, DEFAULT_BAR_BACKGROUND_COLOR));
		g.fillRect(barX, barY, barWidth, barHeight);
		g.setColor(visibleColor(runeliteBarFillColor, DEFAULT_BAR_FILL_COLOR));
		g.fillRect(barX, barY, progressFill, barHeight);

		final long ms = Math.min(plugin.millisSinceTick(), TICK_LENGTH);
		final int xOffset = (int) (barWidth * ms / (double) TICK_LENGTH);

		final Shape oldClip = g.getClip();
		g.setClip(barX, barY, barWidth, barHeight);
		try
		{
			g.setColor(visibleColor(runeliteSwipeLineColor, DEFAULT_SWIPE_LINE_COLOR));
			g.fillRect(barX + xOffset, barY, Math.max(1, Math.min(runeliteSwipeLineWidth, barWidth - xOffset)), barHeight);

			g.setColor(visibleColor(runeliteClickFlashColor, DEFAULT_CLICK_FLASH_COLOR));
			for (int offset : clickOffsets)
			{
				final int x = barX + barWidth * offset / TICK_LENGTH;
				final int flashWidth = Math.max(1, Math.min(runeliteClickFlashWidth, barWidth));
				final int flashX = Math.max(barX, Math.min(barX + barWidth - flashWidth, x));
				g.fillRect(flashX, barY, flashWidth, barHeight);
			}
		}
		finally
		{
			g.setClip(oldClip);
		}
	}

	private static Color visibleColor(Color color, Color fallback)
	{
		return color == null || color.getAlpha() == 0 ? fallback : color;
	}
}

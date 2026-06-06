package com.onetickflick;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

public class OneTickFlickOriginalOverlay extends Overlay
{
	private static final int DEFAULT_TEXT_SPACE = 20;
	private static final int MARKER_SIZE = 4;
	private static final int MIN_BAR_HEIGHT = 5;
	private static final int MIN_BAR_WIDTH = 30;
	private static final int TICK_LENGTH = 600;

	private final OneTickFlickPlugin plugin;
	private final List<Integer> clickOffsets = new CopyOnWriteArrayList<>();
	private volatile boolean visible = true;

	private OneTickFlickBarStyle barStyle;
	private int greenStart;
	private int greenEnd;
	private int originalBarWidth;
	private int originalBarHeight;
	private Color originalTargetZoneColor;
	private Color originalBackgroundColor;
	private Color originalClickColor;
	private Color originalSwipeLineColor;
	private Color originalBorderColor;
	private int originalSwipeLineWidth;

	@Inject
	OneTickFlickOriginalOverlay(OneTickFlickPlugin plugin, OneTickFlickConfig config)
	{
		this.plugin = plugin;
		loadConfig(config);

		setPosition(OverlayPosition.BOTTOM_LEFT);
		setPreferredSize(defaultSize());
		setMinimumSize(MIN_BAR_HEIGHT + DEFAULT_TEXT_SPACE);
		setResizable(true);
	}

	void loadConfig(OneTickFlickConfig config)
	{
		barStyle = config.barStyle();
		greenStart = config.greenStart();
		greenEnd = config.greenEnd();
		originalBarWidth = config.originalBarWidth();
		originalBarHeight = config.originalBarHeight();
		originalTargetZoneColor = config.originalTargetZoneColor();
		originalBackgroundColor = config.originalBackgroundColor();
		originalClickColor = config.originalClickColor();
		originalSwipeLineColor = config.originalSwipeLineColor();
		originalBorderColor = config.originalBorderColor();
		originalSwipeLineWidth = config.originalSwipeLineWidth();
		setPreferredSize(defaultSize());
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
		if (!visible || barStyle != OneTickFlickBarStyle.ORIGINAL)
		{
			return null;
		}

		Rectangle bounds = getBounds();
		int width = Math.max(MIN_BAR_WIDTH, originalBarWidth);
		int height = Math.max(MIN_BAR_HEIGHT + DEFAULT_TEXT_SPACE, originalBarHeight + DEFAULT_TEXT_SPACE);
		if (bounds != null && bounds.width > 0 && bounds.height > 0)
		{
			width = Math.max(width, bounds.width);
			height = Math.max(height, bounds.height);
		}

		int barHeight = Math.max(MIN_BAR_HEIGHT, height - DEFAULT_TEXT_SPACE);
		int barY = (height - barHeight) / 2;
		int greenX1 = width * greenStart / TICK_LENGTH;
		int greenX2 = width * greenEnd / TICK_LENGTH;

		g.setColor(visibleColor(originalBackgroundColor, Color.red));
		g.fillRect(0, barY, greenX1, barHeight);
		g.fillRect(greenX2, barY, width - greenX2, barHeight);

		g.setColor(visibleColor(originalTargetZoneColor, Color.green));
		g.fillRect(greenX1, barY, greenX2 - greenX1, barHeight);

		long ms = Math.min(plugin.millisSinceTick(), TICK_LENGTH);
		int swipeLineX = (int) (width * ms / (double) TICK_LENGTH);
		g.setColor(visibleColor(originalSwipeLineColor, Color.black));
		g.fillRect(swipeLineX, barY, Math.max(1, Math.min(originalSwipeLineWidth, width - swipeLineX)), barHeight);

		g.setColor(visibleColor(originalBorderColor, Color.black));
		g.drawRect(0, barY, width, barHeight);

		g.setColor(visibleColor(originalClickColor, Color.black));
		int xSize = Math.min(MARKER_SIZE, Math.max(1, (barHeight - 1) / 2));
		int y1 = barY + barHeight / 2 - xSize;
		int y2 = barY + barHeight / 2 + xSize;
		for (int offset : clickOffsets)
		{
			int x = width * offset / TICK_LENGTH;
			g.drawLine(x - xSize, y1, x + xSize, y2);
			g.drawLine(x - xSize, y2, x + xSize, y1);
		}

		return new Dimension(width, height);
	}

	private Dimension defaultSize()
	{
		return new Dimension(Math.max(MIN_BAR_WIDTH, originalBarWidth), Math.max(MIN_BAR_HEIGHT, originalBarHeight + DEFAULT_TEXT_SPACE));
	}

	private static Color visibleColor(Color color, Color fallback)
	{
		return color == null || color.getAlpha() == 0 ? fallback : color;
	}
}

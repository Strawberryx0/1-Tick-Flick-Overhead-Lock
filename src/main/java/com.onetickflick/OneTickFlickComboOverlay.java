package com.onetickflick;

import java.awt.*;
import javax.inject.Inject;
import lombok.Setter;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

public class OneTickFlickComboOverlay extends Overlay
{
	private static final Dimension DEFAULT_SIZE = new Dimension(80, 20);
	private static final Color DEFAULT_COMBO_TEXT_COLOR = Color.white;

	private final OneTickFlickPlugin plugin;
	private volatile boolean visible = true;
	@Setter
	private boolean showCombo;
	@Setter
	private Color comboTextColor;
	@Setter
	private int comboTextSize;
	@Setter
	private int comboTextOpacity;
	@Setter
	private String comboText;

	@Inject
	OneTickFlickComboOverlay(OneTickFlickPlugin plugin, OneTickFlickConfig config)
	{
		this.plugin = plugin;
		showCombo = config.showCombo();
		comboTextColor = config.comboTextColor();
		comboTextSize = config.comboTextSize();
		comboTextOpacity = config.comboTextOpacity();
		comboText = config.comboText();

		setPosition(OverlayPosition.BOTTOM_LEFT);
		setPreferredSize(DEFAULT_SIZE);
		setResizable(true);
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
		if (!visible || !showCombo)
		{
			return null;
		}

		Rectangle bounds = getBounds();
		int width = DEFAULT_SIZE.width;
		int height = DEFAULT_SIZE.height;
		if (bounds != null && bounds.width > 0 && bounds.height > 0)
		{
			width = Math.max(bounds.width, DEFAULT_SIZE.width);
			height = Math.max(bounds.height, DEFAULT_SIZE.height);
		}

		Font oldFont = g.getFont();
		g.setFont(oldFont.deriveFont((float) comboTextSize));
		g.setColor(comboColor());
		String text = formatComboText();
		int tx = (width - g.getFontMetrics().stringWidth(text)) / 2;
		int ty = (height - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
		g.drawString(text, tx, ty);
		g.setFont(oldFont);

		return new Dimension(width, height);
	}

	private String formatComboText()
	{
		String format = comboText == null || comboText.isEmpty() ? "Combo: {combo}" : comboText;
		return format.replace("{combo}", Integer.toString(plugin.getCombo()));
	}

	private Color comboColor()
	{
		Color color = comboTextColor == null || comboTextColor.getAlpha() == 0 ? DEFAULT_COMBO_TEXT_COLOR : comboTextColor;
		int alpha = Math.max(0, Math.min(255, comboTextOpacity));
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}
}

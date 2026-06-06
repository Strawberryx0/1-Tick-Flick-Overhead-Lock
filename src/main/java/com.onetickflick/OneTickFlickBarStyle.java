package com.onetickflick;

public enum OneTickFlickBarStyle
{
	ORIGINAL("Original"),
	RUNELITE_PRAYER("RuneLite");

	private final String label;

	OneTickFlickBarStyle(String label)
	{
		this.label = label;
	}

	@Override
	public String toString()
	{
		return label;
	}
}

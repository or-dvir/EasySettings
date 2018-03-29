package com.hotmail.or_dvir.easysettings.events;

import com.hotmail.or_dvir.easysettings.pojos.SeekBarSettingsObject;

/**
 * an event that is sent whenever the {@link android.widget.SeekBar} of
 * a {@link SeekBarSettingsObject} is being dragged
 * e.g. when the user drags the {@link android.widget.SeekBar} view,
 * but did not yet lift his finger
 */
public class SeekBarSettingsProgressChangedEvent
{
	private SeekBarSettingsObject seekBarObj;

	/**
	 *
	 * @param seekBarObj the {@link SeekBarSettingsObject} whose {@link android.widget.SeekBar}
	 *                   view is being changed (dragged)
	 */
	public SeekBarSettingsProgressChangedEvent(SeekBarSettingsObject seekBarObj)
	{
		this.seekBarObj = seekBarObj;
	}

	/**
	 *
	 * @return the {@link SeekBarSettingsObject} whose {@link android.widget.SeekBar}
	 *                   view is being changed (dragged)
	 */
	public SeekBarSettingsObject getSeekBarObj()
	{
		return seekBarObj;
	}
}
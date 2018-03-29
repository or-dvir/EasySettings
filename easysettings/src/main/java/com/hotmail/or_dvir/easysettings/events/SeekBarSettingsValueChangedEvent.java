package com.hotmail.or_dvir.easysettings.events;

import com.hotmail.or_dvir.easysettings.pojos.SeekBarSettingsObject;

/**
 * an event that is sent whenever the FINAL value of
 * a {@link SeekBarSettingsObject} changes.
 * e.g. when the user lifts his finger off the {@link android.widget.SeekBar} view
 * and the new value has been set
 */
public class SeekBarSettingsValueChangedEvent
{
	private SeekBarSettingsObject seekBarObj;

	/**
	 *
	 * @param seekBarObj the {@link SeekBarSettingsObject} whose value has changed
	 */
	public SeekBarSettingsValueChangedEvent(SeekBarSettingsObject seekBarObj)
	{
		this.seekBarObj = seekBarObj;
	}

	/**
	 *
	 * @return the {@link SeekBarSettingsObject} whose value has changed
	 */
	public SeekBarSettingsObject getSeekBarObj()
	{
		return seekBarObj;
	}
}
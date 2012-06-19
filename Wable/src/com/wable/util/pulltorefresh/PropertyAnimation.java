/*
 * @(#)PropertyAnimation.java $version 2012. 3. 27.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.wable.util.pulltorefresh;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author cranix
 */
public class PropertyAnimation extends Animation {
	public static interface PropertyListener {
		public void onPropertyChanged(PropertyAnimation animation,float now);
	}
	
	private PropertyListener listener = null;
	private float start = 0;
	private float end = 0;
	private float now = 0;
	public PropertyAnimation(float start,float end,PropertyListener listener) {
		this.start = start;
		this.end = end;
		this.listener = listener;
	}
	public void setStart(float start) {
		this.start = start;
	}
	public float getStart() {
		return start;
	}
	
	public void setEnd(float end) {
		this.end = end;
	}
	public float getEnd() {
		return end;
	}
	
	public float getNow() {
		return now;
	}

	public void setListener(PropertyListener listener) {
		this.listener = listener;
	}
	

	/**
	 * @param interpolatedTime
	 * @param t
	 * @see android.view.animation.ScaleAnimation#applyTransformation(float, android.view.animation.Transformation)
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		float gap = end - start;
		now = start + (interpolatedTime * gap);
		if (listener != null) {
			listener.onPropertyChanged(this, now);
		}
	}
}

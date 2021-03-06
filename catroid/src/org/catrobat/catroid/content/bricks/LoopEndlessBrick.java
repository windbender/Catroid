/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class LoopEndlessBrick extends LoopEndBrick implements DeadEndBrick {

	private static final long serialVersionUID = 1L;
	private transient boolean isPuzzleView = true;

	public LoopEndlessBrick() {

	}

	public LoopEndlessBrick(Sprite sprite, LoopBeginBrick loopStartingBrick) {
		super(sprite, loopStartingBrick);
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}
		if (view == null || !isPuzzleView) {
			isPuzzleView = true;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.brick_loop_endless, null);
			checkbox = (CheckBox) view.findViewById(R.id.brick_loop_endless_checkbox);

			final Brick brickInstance = this;

			checkbox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					checked = !checked;
					if (!checked) {
						for (Brick currentBrick : adapter.getCheckedBricks()) {
							currentBrick.setCheckedBoolean(false);
						}
					}
					adapter.handleCheck(brickInstance, checked);
				}
			});
		}
		return view;
	}

	@Override
	public View getViewWithAlpha(int alphaValue) {
		LinearLayout layout = null;
		if (isPuzzleView) {
			layout = (LinearLayout) view.findViewById(R.id.brick_loop_endless_layout);
		} else {
			layout = (LinearLayout) view.findViewById(R.id.brick_loop_endless_layout);
		}
		Drawable background = layout.getBackground();
		background.setAlpha(alphaValue);
		this.alphaValue = (alphaValue);
		return view;
	}

	@Override
	public Brick clone() {
		return new LoopEndlessBrick(getSprite(), getLoopBeginBrick());
	}

	@Override
	public View getNoPuzzleView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (view == null || isPuzzleView) {
			isPuzzleView = false;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.brick_loop_endless_no_puzzle, null);
			checkbox = (CheckBox) view.findViewById(R.id.brick_loop_endless_no_puzzle_checkbox);

			final Brick brickInstance = this;

			checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					checked = !checked;
					adapter.handleCheck(brickInstance, checked);
				}
			});
		}
		return view;
	}
}

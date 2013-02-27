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

import java.util.ArrayList;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.ui.dialogs.NewSpriteDialog;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class PointToBrick implements Brick {

	private static final long serialVersionUID = 1L;
	private Sprite sprite;
	private Sprite pointedSprite;
	private String oldSelectedSprite;

	public PointToBrick(Sprite sprite, Sprite pointedSprite) {
		this.sprite = sprite;
		this.pointedSprite = pointedSprite;
		this.oldSelectedSprite = "";
	}

	public PointToBrick() {

	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void execute() {
		final ArrayList<Sprite> spriteList = (ArrayList<Sprite>) ProjectManager.getInstance().getCurrentProject()
				.getSpriteList();
		if (!spriteList.contains(pointedSprite)) {
			pointedSprite = null;
		}

		if (pointedSprite == null) {
			pointedSprite = this.sprite;
		}

		int spriteXPosition = 0, spriteYPosition = 0;
		int pointedSpriteXPosition = 0, pointedSpriteYPosition = 0;
		double base = 0.0, height = 0.0, value = 0.0;

		sprite.look.aquireXYWidthHeightLock();
		spriteXPosition = (int) sprite.look.getXPosition();
		spriteYPosition = (int) sprite.look.getYPosition();
		sprite.look.releaseXYWidthHeightLock();
		pointedSprite.look.aquireXYWidthHeightLock();
		pointedSpriteXPosition = (int) pointedSprite.look.getXPosition();
		pointedSpriteYPosition = (int) pointedSprite.look.getYPosition();
		pointedSprite.look.releaseXYWidthHeightLock();

		double rotationDegrees;
		if (spriteXPosition == pointedSpriteXPosition && spriteYPosition == pointedSpriteYPosition) {
			rotationDegrees = 90;
		} else if (spriteXPosition == pointedSpriteXPosition || spriteYPosition == pointedSpriteYPosition) {
			if (spriteXPosition == pointedSpriteXPosition) {
				if (spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 180;
				} else {
					rotationDegrees = 0;
				}
			} else {
				if (spriteXPosition > pointedSpriteXPosition) {
					rotationDegrees = 270;
				} else {
					rotationDegrees = 90;
				}
			}

		} else {
			base = Math.abs(spriteYPosition - pointedSpriteYPosition);
			height = Math.abs(spriteXPosition - pointedSpriteXPosition);
			value = Math.toDegrees(Math.atan(base / height));

			if (spriteXPosition < pointedSpriteXPosition) {
				if (spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 90 + value;
				} else {
					rotationDegrees = 90 - value;
				}
			} else {
				if (spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 270 - value;
				} else {
					rotationDegrees = 270 + value;
				}
			}
		}
		sprite.look.rotation = (-(float) rotationDegrees) + 90f;
	}

	@Override
	public View getView(final Context context, int brickId, BaseAdapter adapter) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View brickView = inflater.inflate(R.layout.brick_point_to, null);

		Spinner spinner = (Spinner) brickView.findViewById(R.id.brick_point_to_spinner);
		spinner.setFocusableInTouchMode(false);
		spinner.setFocusable(false);
		spinner.setClickable(true);
		spinner.setEnabled(true);

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAdapter.add(context.getString(R.string.new_broadcast_message));

		final ArrayList<Sprite> spriteList = (ArrayList<Sprite>) ProjectManager.getInstance().getCurrentProject()
				.getSpriteList();
		for (Sprite sprite : spriteList) {
			String spriteName = sprite.getName();
			String temp = this.sprite.getName();
			if (!spriteName.equals(temp)
					&& !spriteName.equals(context.getString(R.string.default_project_backgroundname))) {
				spinnerAdapter.add(sprite.getName());
			}
		}

		SpinnerAdapterWrapper spinnerAdapterWrapper = new SpinnerAdapterWrapper(context, spinner, spinnerAdapter);

		spinner.setAdapter(spinnerAdapterWrapper);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			private boolean start = true;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (start) {
					start = false;
					return;
				}

				String itemSelected = parent.getSelectedItem().toString();

				final ArrayList<Sprite> spriteList = (ArrayList<Sprite>) ProjectManager.getInstance()
						.getCurrentProject().getSpriteList();

				if (itemSelected.equals(context.getString(R.string.new_broadcast_message))) {
					pointedSprite = null;
				}

				for (Sprite sprite : spriteList) {
					String spriteName = sprite.getName();
					if (spriteName.equals(itemSelected)) {
						pointedSprite = sprite;
						break;
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		if (spriteList.contains(pointedSprite)) {
			this.oldSelectedSprite = pointedSprite.getName();
			spinner.setSelection(spinnerAdapter.getPosition(pointedSprite.getName()), true);
		} else {
			if (spinnerAdapterWrapper.getCount() > 1) {
				spinner.setSelection(spinnerAdapter.getPosition(this.oldSelectedSprite), true);
			} else {
				spinner.setSelection(0);
			}
		}

		return brickView;
	}

	@Override
	public View getPrototypeView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.brick_point_to, null);
		return view;
	}

	@Override
	public Brick clone() {
		return new PointToBrick(sprite, pointedSprite);
	}

	private class SpinnerAdapterWrapper implements SpinnerAdapter {

		protected Context context;
		protected Spinner spinner;
		protected ArrayAdapter<String> spinnerAdapter;

		private String currentSpriteName;
		private boolean dataSetObserverToggle;
		private boolean isTouchInDropDownView;

		public SpinnerAdapterWrapper(Context context, Spinner spinner, ArrayAdapter<String> spinnerAdapter) {
			this.context = context;
			this.spinnerAdapter = spinnerAdapter;
			this.spinner = spinner;

			this.currentSpriteName = "";
			this.dataSetObserverToggle = false;
			this.isTouchInDropDownView = false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver paramDataSetObserver) {
			this.dataSetObserverToggle = true;
			spinnerAdapter.registerDataSetObserver(paramDataSetObserver);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver) {
			this.dataSetObserverToggle = false;

			if (this.isTouchInDropDownView) {
				this.isTouchInDropDownView = false;
				if (this.currentSpriteName.equals(context.getString(R.string.new_broadcast_message))) {
					showNewSpriteDialog();
				}
			}

			spinnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
		}

		@Override
		public int getCount() {
			return spinnerAdapter.getCount();
		}

		@Override
		public Object getItem(int paramInt) {
			Object currentObject = spinnerAdapter.getItem(paramInt);
			this.currentSpriteName = currentObject.toString();
			return currentObject;
		}

		@Override
		public long getItemId(int paramInt) {
			if (this.dataSetObserverToggle) {
				this.currentSpriteName = spinnerAdapter.getItem(paramInt).toString();
				if (!this.currentSpriteName.equals(context.getString(R.string.new_broadcast_message))) {
					oldSelectedSprite = this.currentSpriteName;
				}
			}

			return spinnerAdapter.getItemId(paramInt);
		}

		@Override
		public boolean hasStableIds() {
			return spinnerAdapter.hasStableIds();
		}

		@Override
		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			return spinnerAdapter.getView(paramInt, paramView, paramViewGroup);
		}

		@Override
		public int getItemViewType(int paramInt) {
			return spinnerAdapter.getItemViewType(paramInt);
		}

		@Override
		public int getViewTypeCount() {
			return spinnerAdapter.getViewTypeCount();
		}

		@Override
		public boolean isEmpty() {
			return spinnerAdapter.isEmpty();
		}

		@Override
		public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			View dropDownView = spinnerAdapter.getDropDownView(paramInt, paramView, paramViewGroup);

			dropDownView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
					isTouchInDropDownView = true;
					return false;
				}
			});

			return dropDownView;
		}

		protected void showNewSpriteDialog() {
			NewSpriteDialog dialog = new NewSpriteDialog() {

				@Override
				protected boolean handleOkButton() {
					if (super.handleOkButton()) {
						String newSpriteName = (input.getText().toString()).trim();

						if (newSpriteName.length() == 0
								|| newSpriteName.equals(context.getString(R.string.new_broadcast_message))) {
							dismiss();

							return false;
						}

						spinnerAdapter.add(newSpriteName);
						int position = spinnerAdapter.getPosition(newSpriteName);
						spinner.setSelection(position, true);
						oldSelectedSprite = newSpriteName;

						return true;
					}

					return false;
				}
			};

			dialog.show(((ScriptActivity) context).getSupportFragmentManager(), NewSpriteDialog.DIALOG_FRAGMENT_TAG);
		}
	}
}

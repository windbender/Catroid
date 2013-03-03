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
import org.catrobat.catroid.common.MessageContainer;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.ui.dialogs.BrickTextDialog;

import android.content.Context;
import android.database.DataSetObserver;
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

public class BroadcastReceiverBrick extends ScriptBrick {

	private static final long serialVersionUID = 1L;
	private BroadcastScript receiveScript;
	private Sprite sprite;
	private String oldMessage;

	private transient View view;

	public BroadcastReceiverBrick() {

	}

	public BroadcastReceiverBrick(Sprite sprite, BroadcastScript receiveScript) {
		this.sprite = sprite;
		this.receiveScript = receiveScript;
		this.oldMessage = "";
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public void execute() {
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public View getView(final Context context, int brickId, BaseAdapter adapter) {
		if (receiveScript == null) {
			receiveScript = new BroadcastScript(sprite);
		}

		view = View.inflate(context, R.layout.brick_broadcast_receive, null);

		final Spinner broadcastSpinner = (Spinner) view.findViewById(R.id.brick_broadcast_receive_spinner);
		broadcastSpinner.setFocusableInTouchMode(false);
		broadcastSpinner.setFocusable(false);
		broadcastSpinner.setClickable(true);
		broadcastSpinner.setEnabled(true);

		ArrayAdapter<String> spinnerAdapter = MessageContainer.getMessageAdapter(context);
		MessageContainer.addMessage(context.getString(R.string.new_broadcast_message));

		SpinnerAdapterWrapper spinnerAdapterWrapper = new SpinnerAdapterWrapper(context, broadcastSpinner,
				spinnerAdapter);

		broadcastSpinner.setAdapter(spinnerAdapterWrapper);

		broadcastSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			private boolean start = true;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if (start) {
					start = false;
					return;
				}

				String message = ((String) parent.getItemAtPosition(pos)).trim();

				if (message == context.getString(R.string.new_broadcast_message)) {
					receiveScript.setBroadcastMessage("");
				} else {
					receiveScript.setBroadcastMessage(message);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		int position = MessageContainer.getPositionOfMessageInAdapter(receiveScript.getBroadcastMessage());
		if (position > 0) {
			broadcastSpinner.setSelection(position, true);
		} else {
			broadcastSpinner.setSelection(MessageContainer.getPositionOfMessageInAdapter(this.oldMessage), true);
		}

		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_broadcast_receive, null);
	}

	@Override
	public Brick clone() {
		return new BroadcastReceiverBrick(sprite, null);
	}

	@Override
	public Script initScript(Sprite sprite) {
		if (receiveScript == null) {
			receiveScript = new BroadcastScript(sprite);
		}

		return receiveScript;
	}

	private class SpinnerAdapterWrapper implements SpinnerAdapter {

		protected Context context;
		protected Spinner spinner;
		protected ArrayAdapter<String> spinnerAdapter;

		private String currentMessage;
		private boolean dataSetObserverToggle;
		private boolean isTouchInDropDownView;

		public SpinnerAdapterWrapper(Context context, Spinner spinner, ArrayAdapter<String> spinnerAdapter) {
			this.context = context;
			this.spinnerAdapter = spinnerAdapter;
			this.spinner = spinner;

			this.currentMessage = "";
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
				if (this.currentMessage.equals(context.getString(R.string.new_broadcast_message))) {
					showNewMessageDialog();
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
			this.currentMessage = currentObject.toString();
			return currentObject;
		}

		@Override
		public long getItemId(int paramInt) {
			if (this.dataSetObserverToggle) {
				this.currentMessage = spinnerAdapter.getItem(paramInt).toString();
				if (!this.currentMessage.equals(context.getString(R.string.new_broadcast_message))) {
					oldMessage = this.currentMessage;
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

		protected void showNewMessageDialog() {
			BrickTextDialog editDialog = new BrickTextDialog() {
				@Override
				protected void initialize() {
				}

				@Override
				protected boolean handleOkButton() {
					String newMessage = (input.getText().toString()).trim();
					if (newMessage.length() == 0
							|| newMessage.equals(context.getString(R.string.new_broadcast_message))) {
						dismiss();

						return false;
					}

					receiveScript.setBroadcastMessage(newMessage);
					oldMessage = newMessage;
					int position = MessageContainer.getPositionOfMessageInAdapter(newMessage);
					spinner.setSelection(position, true);

					return true;
				}
			};

			editDialog.show(((ScriptActivity) context).getSupportFragmentManager(), "dialog_broadcast_receiver_brick");
		}
	}
}

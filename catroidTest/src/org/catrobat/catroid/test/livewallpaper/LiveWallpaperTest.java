/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010-2011 The Catroid Team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid_license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *   
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.test.livewallpaper;

import java.io.IOException;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.StandardProjectHandler;
import org.catrobat.catroid.common.Values;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ChangeGhostEffectByNBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.SetGhostEffectBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.livewallpaper.WallpaperHelper;
import org.catrobat.catroid.livewallpaper.WallpaperLook;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.test.AndroidTestCase;

public class LiveWallpaperTest extends AndroidTestCase {

	private WallpaperHelper wallpaperHelper;

	private Project defaultProject;
	private Sprite backgroundSprite;
	private Sprite catroidSprite;

	private Bitmap backgroundBitmap;
	private Bitmap normalCatBitmap;
	private Bitmap banzaiCatBitmap;
	private Bitmap chasireCatBitmap;

	@Override
	public void setUp() {
		try {
			super.setUp();
			createDefaultProjectAndInitMembers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tearDown() {
		try {
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createDefaultProjectAndInitMembers() {

		try {
			Values.SCREEN_WIDTH = 480;
			Values.SCREEN_HEIGHT = 800;
			this.defaultProject = StandardProjectHandler.createAndSaveStandardProject(getContext());
			ProjectManager.getInstance().setProject(defaultProject);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.catroidSprite = defaultProject.getSpriteList().get(1);
		this.backgroundSprite = defaultProject.getSpriteList().get(0);

		this.backgroundBitmap = backgroundSprite.getLookDataList().get(0).getLookBitmap();
		this.normalCatBitmap = catroidSprite.getLookDataList().get(0).getLookBitmap();
		this.banzaiCatBitmap = catroidSprite.getLookDataList().get(1).getLookBitmap();
		this.chasireCatBitmap = catroidSprite.getLookDataList().get(2).getLookBitmap();

		this.wallpaperHelper = WallpaperHelper.getInstance();
		wallpaperHelper.setProject(defaultProject);
	}

	private boolean sameBitmaps(Bitmap first, Bitmap second) {

		if (first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight()) {
			return false;
		}

		for (int width = 0; width < first.getWidth(); width++) {
			for (int height = 0; height < first.getHeight(); height++) {
				if (first.getPixel(width, height) != second.getPixel(width, height)) {
					return false;
				}
			}
		}

		return true;

	}

	private Matrix coordinatesMatrix(int x, int y, int costumeWidth, int costumeHeight, boolean isLandscape) {
		int centerX, centerY;

		if (isLandscape) {
			centerX = wallpaperHelper.getCenterXCoord() - y - costumeWidth / 2;
			centerY = wallpaperHelper.getCenterYCoord() - x - costumeHeight / 2;
		} else {
			centerX = wallpaperHelper.getCenterXCoord() + x - costumeWidth / 2;
			centerY = wallpaperHelper.getCenterYCoord() - y - costumeHeight / 2;
		}

		Matrix matrix = new Matrix();
		matrix.postTranslate(centerX, centerY);

		return matrix;

	}

	private Matrix scaleMatrix(float size, int costumeWidth, int costumeHeight) {
		Matrix matrix = new Matrix();
		matrix.postScale(size, size);

		int centerX = wallpaperHelper.getCenterXCoord() - (int) (costumeWidth * size) / 2;
		int centerY = wallpaperHelper.getCenterYCoord() - (int) (costumeHeight * size) / 2;

		matrix.postTranslate(centerX, centerY);

		return matrix;
	}

	private Paint doPaintManipulations(float brightness, float alpha) {
		ColorMatrix cm = new ColorMatrix();
		cm.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, alpha, 0 });

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		return paint;
	}

	public void testSetLookBrick() {

		Brick brick = backgroundSprite.getScript(0).getBrick(0);
		assertTrue("This brick should be an instance of SetLookBrick but it's not", brick instanceof SetLookBrick);
		brick.executeLiveWallpaper();
		WallpaperLook wallpaperLook = backgroundSprite.getWallpaperLook();

		assertTrue("The isBackground flag was not set", wallpaperLook.isBackground());
		assertTrue("The background in the wallpaper is not the same as the default project background",
				sameBitmaps(backgroundBitmap, wallpaperLook.getLook()));

		brick = catroidSprite.getScript(0).getBrick(0);
		assertTrue("This brick should be an instance of SetLookBrick but it's not", brick instanceof SetLookBrick);
		brick.executeLiveWallpaper();
		wallpaperLook = catroidSprite.getWallpaperLook();
		assertTrue("Expected normalCat but was " + wallpaperLook.getLookData().getLookName(),
				sameBitmaps(normalCatBitmap, wallpaperLook.getLook()));

	}

	public void testNextLookBrick() {
		Brick brick = new NextLookBrick(catroidSprite);

		brick.executeLiveWallpaper();
		WallpaperLook wallpaperLook = catroidSprite.getWallpaperLook();
		assertTrue("Expected normalCat but was " + wallpaperLook.getLookData().getLookName(),
				sameBitmaps(normalCatBitmap, wallpaperLook.getLook()));

		brick.executeLiveWallpaper();
		assertTrue("Expected banzaiCat but was " + wallpaperLook.getLookData().getLookName(),
				sameBitmaps(banzaiCatBitmap, wallpaperLook.getLook()));

		brick.executeLiveWallpaper();
		assertTrue("Expected chasireCat but was " + wallpaperLook.getLookData().getLookName(),
				sameBitmaps(chasireCatBitmap, wallpaperLook.getLook()));

		brick.executeLiveWallpaper();
		assertTrue("Expected normalCat but was " + wallpaperLook.getLookData().getLookName(),
				sameBitmaps(normalCatBitmap, wallpaperLook.getLook()));

	}

	public void testHideAndShowBricks() {
		Brick brick = new HideBrick(catroidSprite);
		brick.executeLiveWallpaper();
		WallpaperLook wallpaperLook = catroidSprite.getWallpaperLook();
		assertTrue("The costume was not hidden!", wallpaperLook.isLookHidden());

		brick = new ShowBrick(catroidSprite);
		brick.executeLiveWallpaper();
		assertFalse("The costume was not shown!", wallpaperLook.isLookHidden());
	}

	public void testSetXBrick() {
		int xPosition = 60;
		WallpaperHelper.getInstance().setLandscape(false);
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));

		Brick brick = new SetXBrick(catroidSprite, xPosition);
		brick.executeLiveWallpaper();

		Matrix matrix = coordinatesMatrix(xPosition, 0, costume.getLook().getWidth(), costume.getLook().getHeight(),
				false);
		assertEquals("The X coordinate has not been set properly", matrix, costume.getMatrix());

		WallpaperHelper.getInstance().setLandscape(true);
		WallpaperLook costumeLandscape = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		brick = new SetXBrick(catroidSprite, xPosition);
		brick.executeLiveWallpaper();
		matrix = coordinatesMatrix(xPosition, 0, costumeLandscape.getLook().getWidth(), costumeLandscape.getLook()
				.getHeight(), true);
		assertEquals("The X coordinate has not been set properly in lanscape", matrix, costumeLandscape.getMatrix());

	}

	public void testSetYBrick() {
		int yPosition = 60;
		WallpaperHelper.getInstance().setLandscape(false);
		Brick brick = new SetYBrick(catroidSprite, yPosition);

		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		brick.executeLiveWallpaper();

		Matrix matrix = coordinatesMatrix(0, yPosition, costume.getLook().getWidth(), costume.getLook().getHeight(),
				false);
		assertEquals("The Y coordinate has not been set properly", matrix, costume.getMatrix());

		WallpaperHelper.getInstance().setLandscape(true);
		WallpaperLook costumeLandscape = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		brick = new SetYBrick(catroidSprite, yPosition);
		brick.executeLiveWallpaper();

		matrix = coordinatesMatrix(0, yPosition, costumeLandscape.getLook().getWidth(), costumeLandscape.getLook()
				.getHeight(), true);
		assertEquals("The Y coordinate has not been set properly in lanscape", matrix, costumeLandscape.getMatrix());

	}

	public void testPlaceAtBrick() {
		int xPosition = -36;
		int yPosition = 28;
		WallpaperHelper.getInstance().setLandscape(false);
		Brick brick = new PlaceAtBrick(catroidSprite, xPosition, yPosition);

		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		brick.executeLiveWallpaper();

		Matrix matrix = coordinatesMatrix(xPosition, yPosition, costume.getLook().getWidth(), costume.getLook()
				.getHeight(), false);
		assertEquals("The coordinates have not been set properly", matrix, costume.getMatrix());

		WallpaperHelper.getInstance().setLandscape(true);
		WallpaperLook costumeLandscape = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		brick = new PlaceAtBrick(catroidSprite, xPosition, yPosition);
		brick.executeLiveWallpaper();

		matrix = coordinatesMatrix(xPosition, yPosition, costumeLandscape.getLook().getWidth(), costumeLandscape
				.getLook().getHeight(), true);
		assertEquals("The Y coordinate has not been set properly in lanscape", matrix, costumeLandscape.getMatrix());

	}

	public void testChangeXByBrick() {
		int startingX = 50;
		int movingX = -85;

		WallpaperHelper.getInstance().setLandscape(false);
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		costume.setX(startingX);

		Brick brick = new ChangeXByNBrick(catroidSprite, movingX);
		brick.executeLiveWallpaper();

		Matrix matrix = coordinatesMatrix(startingX + movingX, 0, costume.getLook().getWidth(), costume.getLook()
				.getHeight(), false);
		assertEquals("The x coordinate has not been changed properly", matrix, costume.getMatrix());

		WallpaperHelper.getInstance().setLandscape(true);
		WallpaperLook costumeLandscape = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		costumeLandscape.setX(startingX);
		brick = new ChangeXByNBrick(catroidSprite, movingX);
		brick.executeLiveWallpaper();

		matrix = coordinatesMatrix(startingX + movingX, 0, costumeLandscape.getLook().getWidth(), costumeLandscape
				.getLook().getHeight(), true);
		assertEquals("The X coordinate has not been changed properly in lanscape", matrix, costumeLandscape.getMatrix());

	}

	public void testChangeYByBrick() {
		int startingY = 50;
		int movingY = -85;

		WallpaperHelper.getInstance().setLandscape(false);
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		costume.setY(startingY);

		Brick brick = new ChangeYByNBrick(catroidSprite, movingY);
		brick.executeLiveWallpaper();

		Matrix matrix = coordinatesMatrix(0, startingY + movingY, costume.getLook().getWidth(), costume.getLook()
				.getHeight(), false);
		assertEquals("The Y coordinate has not been changed properly", matrix, costume.getMatrix());

		WallpaperHelper.getInstance().setLandscape(true);
		WallpaperLook costumeLandscape = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		costumeLandscape.setY(startingY);
		brick = new ChangeYByNBrick(catroidSprite, movingY);
		brick.executeLiveWallpaper();

		matrix = coordinatesMatrix(0, startingY + movingY, costumeLandscape.getLook().getWidth(), costumeLandscape
				.getLook().getHeight(), true);
		assertEquals("The Y coordinate has not been changed properly in lanscape", matrix, costumeLandscape.getMatrix());
	}

	public void testSetSizeToBrick() {
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		double size = 50.0;

		Brick brick = new SetSizeToBrick(catroidSprite, size);
		brick.executeLiveWallpaper();

		size = size * 0.01;
		Matrix matrix = this.scaleMatrix((float) size, costume.getLook().getWidth(), costume.getLook().getHeight());
		assertEquals("The size has not been changed properly", matrix, costume.getMatrix());

	}

	public void changeSetSizeByBrick() {
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		double startSize = 150.0;
		double changeSize = 20.0;

		costume.setLookSize(startSize);
		Brick brick = new ChangeSizeByNBrick(catroidSprite, changeSize);
		brick.executeLiveWallpaper();

		startSize = startSize * 0.01;
		changeSize = changeSize * 0.01;
		float size = (float) startSize + (float) changeSize;
		Matrix matrix = this.scaleMatrix(size, costume.getLook().getWidth(), costume.getLook().getHeight());
		assertEquals("The size has not been changed properly", matrix, costume.getMatrix());

	}

	//	public void testSetBrightnessBrick() {
	//		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
	//		double brightness = 50.0d;
	//
	//		Brick brick = new SetBrightnessBrick(catroidSprite, brightness);
	//		brick.executeLiveWallpaper();
	//
	//		float fbrightness = (float) (255 * (brightness / 100)) - 255;
	//
	//		Paint paint = doPaintManipulations(fbrightness, 1f);
	//
	//		assertEquals("The brightness has not been changed properly", paint.getColorFilter(), costume.getPaint()
	//				.getColorFilter());
	//
	//	}

	//
	//	public void testChangeBrightnessBrick() {
	//		WallpaperLook wallpaperLook = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(
	//				0));
	//		float initialBrightness1 = 30;
	//		float initialBrightness2 = 20;
	//		Brick brick1 = new ChangeBrightnessByNBrick(catroidSprite, initialBrightness1);
	//		Brick brick2 = new ChangeBrightnessByNBrick(catroidSprite, initialBrightness2);
	//		brick1.executeLiveWallpaper();
	//		brick2.executeLiveWallpaper();
	//
	//		float brightness = -(100 - (wallpaperLook.getBrightness() * 100));
	//
	//		assertEquals("The brightness was not set properly", brightness, initialBrightness1 + initialBrightness2);
	//	}
	//

	public void testSetGhostEffectBrick() {

		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));
		double alpha = 50.0d;
		Brick brick = new SetGhostEffectBrick(catroidSprite, alpha);
		brick.executeLiveWallpaper();

		float falpha = (100f - (float) alpha) / 100;
		Paint paint = doPaintManipulations(0, falpha);

		assertEquals("The alpha value was not set properly", paint.getAlpha(), costume.getPaint().getAlpha());
	}

	public void testChangeGhostEffectBrick() {
		WallpaperLook costume = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));

		float initialAlpha = 50;
		float changeAlpha = -20;
		costume.setAlphaValue(initialAlpha);

		Brick brick = new ChangeGhostEffectByNBrick(catroidSprite, changeAlpha);
		brick.executeLiveWallpaper();

		float alpha = (100f - initialAlpha + changeAlpha) / 100;
		Paint paint = doPaintManipulations(0, alpha);

		assertEquals("The alpha value was not set properly", paint.getAlpha(), costume.getPaint().getAlpha());
	}

	public void testComeToFrontBrick() {
		WallpaperLook backgroundLook = new WallpaperLook(backgroundSprite, backgroundSprite.getLookDataList().get(0));
		WallpaperLook catroidLook = new WallpaperLook(catroidSprite, catroidSprite.getLookDataList().get(0));

		int backgroundPosition = backgroundLook.getzPosition();
		int catroidPosition = catroidLook.getzPosition();

		assertTrue("The position parameter has not been initialized properly", backgroundPosition == 0
				&& catroidPosition == 1);

		Brick brick = new ComeToFrontBrick(backgroundSprite);
		brick.executeLiveWallpaper();

		backgroundPosition = backgroundLook.getzPosition();
		catroidPosition = catroidLook.getzPosition();

		assertTrue("The position parameter has not been set properly", backgroundPosition == 1 && catroidPosition == 0);

	}
}
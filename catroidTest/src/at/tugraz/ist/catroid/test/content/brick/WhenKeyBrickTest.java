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
package at.tugraz.ist.catroid.test.content.brick;

import android.test.AndroidTestCase;
import at.tugraz.ist.catroid.content.Sprite;
import at.tugraz.ist.catroid.content.WhenKeyScript;
import at.tugraz.ist.catroid.content.bricks.Brick;
import at.tugraz.ist.catroid.content.bricks.PlaceAtBrick;
import at.tugraz.ist.catroid.content.bricks.WhenKeyBrick.Key;

public class WhenKeyBrickTest extends AndroidTestCase {

	public void testWhenKeyBrickPadUp() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite padup");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_PADUP.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickPadDown() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite paddown");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_PADDOWN.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickPadLeft() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite padleft");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_PADLEFT.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickPadRight() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite padright");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_PADRIGHT.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickX() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite x");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_X.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickCircle() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite circle");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_CIRCLE.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickSquare() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite square");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_SQUARE.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickTriangle() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite triangle");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_TRIANGLE.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickLeft() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite left");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_LEFT.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickRight() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite right");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_RIGHT.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickStart() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite start");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_START.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickSelect() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite select");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_SELECT.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

	public void testWhenKeyBrickMenu() throws InterruptedException {
		int testPosition = 100;

		Sprite sprite = new Sprite("key sprite menu");
		WhenKeyScript whenKeyScript = new WhenKeyScript(sprite, Key.KEY_MENU.getKeyCode());
		Brick placeAtBrick = new PlaceAtBrick(sprite, testPosition, testPosition);
		whenKeyScript.addBrick(placeAtBrick);
		sprite.addScript(whenKeyScript);
		sprite.startWhenKeyScripts(whenKeyScript.getKeyCode());

		Thread.sleep(1000);

		assertEquals("Simple broadcast failed", (float) testPosition, sprite.costume.x);
	}

}

/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.physics;

public class PhysicSettings {

	public final static boolean DEBUGFLAG = true;

	public static class Render {
		public final static boolean RENDER_COLLISION_FRAMES = true;
		public final static boolean RENDER_BODIES = true;
		public final static boolean RENDER_JOINTS = false;
		public final static boolean RENDER_AABBs = false;
		public final static boolean RENDER_INACTIVE_BODIES = true;
	}
}
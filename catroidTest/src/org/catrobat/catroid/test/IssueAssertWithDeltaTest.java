package org.catrobat.catroid.test;

import android.test.AndroidTestCase;

public class IssueAssertWithDeltaTest extends AndroidTestCase {

	public void testIssueAssertErrorMessage() {
		// Normal assert with error message
		assertEquals("Error message", 1.0, 1.0);
		// Assert with delta but NO error message
		assertEquals(1.0, 1.0, 0.1);
	}

}

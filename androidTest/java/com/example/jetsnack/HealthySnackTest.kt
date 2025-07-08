

package com.example.jetsnack

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.jetsnack.ui.MainActivity
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.hasScrollAction
import org.junit.Rule
import org.junit.Test

class HealthySnackTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_canScrollAndSearch() {
        // 1. A user scrolls the Home-view vertically till "Newly added" section becomes visible.
        // Checking that app opens correctly.
        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
        composeTestRule.onNodeWithText("HOME").assertExists()

        // Grab all scrollables and pick the first one (the vertical one).
        val verticalList = composeTestRule.onAllNodes(hasScrollAction())[0]
        // Swipe up until “Newly Added” is within reach.
        repeat(10) {
            try {
                composeTestRule.onNodeWithText("Newly Added").assertExists()
                return@repeat
            } catch (_: AssertionError) {
                verticalList.performTouchInput { swipeUp() }
            }
        }
        // Bring it fully on‑screen.
        composeTestRule.onNodeWithText("Newly Added").performScrollTo()
        // Assert it’s visible.
        composeTestRule.onNodeWithText("Newly Added").assertIsDisplayed()

        // 2. Within this section scroll horizontally back and forth.
        // Grab the horizontal scrollable below "Newly Added".
        val horizontalList = composeTestRule.onAllNodes(hasScrollAction())[2]
        // Swipe left and assert Almonds (item furthest right) is displayed.
        horizontalList.performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithText("Almonds").assertIsDisplayed()
        // Swipe right and assert Chips (item furthest left) is displayed.
        horizontalList.performTouchInput { swipeRight() }
        composeTestRule.onNodeWithText("Chips").assertIsDisplayed()

        // 3. Navigate to Search tab and use free text search to find Mango.
        // Navigate to search. Done by clicking on test "SEARCH" and asserting that "Categories" is visible.
        composeTestRule.onNodeWithText("SEARCH").performClick().assertIsDisplayed()
        composeTestRule.onNodeWithText("Categories").assertIsDisplayed()

        // Enter "mango" in search bar. Done by finding a field where you can enter text, clicking on it, and enter the text "mango".
        composeTestRule.onNode(hasSetTextAction()).assertExists().performClick().performTextInput("mango")

        // 4. Tap on the found product to make sure that description to it is in place.
        // Enter product by clicking on text "Mango".
        composeTestRule.onNodeWithText("Mango").performClick().assertIsDisplayed()

        // Making sure that description is in place.
        composeTestRule.onNodeWithText("Lorem ipsum", substring = true).assertIsDisplayed()
    }

}
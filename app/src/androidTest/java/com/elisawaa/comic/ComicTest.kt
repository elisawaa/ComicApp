package com.elisawaa.comic

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.elisawaa.comic.test.TestTags
import org.junit.Rule
import org.junit.Test

class ComicTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testBottomBarAndFavoriteFAb() {
        // Start testing on the comic page
        composeTestRule.onNodeWithText("Comics")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.TEST_TAG_BOTTOM_BAR_FAV)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithText("Favorites").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.TEST_TAG_BOTTOM_BAR_COMIC)
            .assertIsDisplayed()
            .performClick()

        // Enable a new favorite
        composeTestRule.onNodeWithTag(TestTags.TEST_TAG_COMIC_FAB)
            .performClick()

        composeTestRule.onNodeWithTag(TestTags.TEST_TAG_BOTTOM_BAR_FAV)
            .assertIsDisplayed()
            .performClick()
    }
}
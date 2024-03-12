package com.ilhomsoliev.productstest.presentation.search

import androidx.compose.runtime.Composable

@Composable
fun SearchScreen(
    vm: SearchScreenViewModel,
    onBack: () -> Unit
) {

    SearchContent(
        state = SearchState(""),
        callback = object : SearchCallback {
            override fun onPromptChange(value: String) {

            }

            override fun onBack() {
                onBack()
            }

        }
    )

}
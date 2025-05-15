package com.samuelwood.journeys.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JourneysNavigationBar(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null
                )
            },
//            label = {
//                Text(
//                    text = stringResource(R.string.bottom_navigation_home)
//                )
//            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            label = {
//                Text(
//                    text = stringResource(R.string.bottom_navigation_profile)
//                )
            },
            selected = false,
            onClick = {}
        )
    }
}
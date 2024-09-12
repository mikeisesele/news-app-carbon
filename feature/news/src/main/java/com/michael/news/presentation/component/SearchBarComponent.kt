package com.michael.news.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.michael.feature.news.R
import com.michael.ui.extensions.applyIf
import com.michael.ui.extensions.clickable
import com.michael.ui.theme.Dimens
import com.michael.ui.utils.mediumTexStyle


private const val UNFOCUSED_ALPHA = 0.4f

@Composable
internal fun SearchBarComponent(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    searchQuery: String,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    var queryValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = searchQuery,
                selection = TextRange(searchQuery.length),
            ),
        )
    }

    OutlinedTextField(
        textStyle = mediumTexStyle(size = 14),
        value = queryValue,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .height(20.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = color
            )
        },
        trailingIcon = {
            if (queryValue.text.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .height(18.dp)
                        .clickable(
                            onClick = {
                                queryValue = TextFieldValue("")
                                onValueChange("")
                            }
                        ),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = color
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.background,
            unfocusedBorderColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
            .border(
                width = 1.dp, // Custom border width
                color = color, // Border color
                shape = RoundedCornerShape(Dimens.RadiusTriple) // Custom rounded corners
            )
            .clip(RoundedCornerShape(Dimens.RadiusTriple)), // Clip shape to match the border
        placeholder = {
            Text(
                text = stringResource(R.string.search_news),
                color = color.copy(alpha = UNFOCUSED_ALPHA)
            )
        },
        shape = RoundedCornerShape(Dimens.RadiusTriple),
        onValueChange = {
            queryValue = it
            onValueChange(it.text)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}

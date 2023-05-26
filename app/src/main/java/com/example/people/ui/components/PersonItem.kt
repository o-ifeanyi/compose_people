package com.example.people.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.people.model.Person
import com.example.people.util.TestTags

@Composable
fun PersonItem(person: Person, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 3.dp,
        color = MaterialTheme.colorScheme.background,
        onClick = onClick
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            AsyncImage(
                model = person.avatar,
                contentDescription = "Person Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .clip(shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag(TestTags.personItem),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                PillItem(text = "#${person.id}")
                Text(text = "${person.firstName} ${person.lastName}")
            }
        }
    }
}
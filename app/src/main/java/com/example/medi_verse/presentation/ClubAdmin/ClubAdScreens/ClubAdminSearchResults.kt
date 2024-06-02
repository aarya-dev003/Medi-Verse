import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medi_verse.R

@Preview(widthDp = 500, heightDp = 1000)
@Composable
fun ClubAdminSearchResults() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "IEEE", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(18.dp))
            LazyColumn {
                items(15) { index ->
                    SearchLayout(
                        clubImg = R.drawable.medicapslogo,
                        clubName = "Club Name $index",
                        newsTitle = "News Title $index",
                        newsDescription = "This is a description for news item $index."
                    )
                }
            }
        }
    }
}

@Composable
fun SearchLayout(clubImg: Int, clubName: String, newsTitle: String, newsDescription: String) {
    Column {
        Image(
                painter = painterResource(id = clubImg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = newsTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsDescription, style = MaterialTheme.typography.bodyMedium)

    }
}

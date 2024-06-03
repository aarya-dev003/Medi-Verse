import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.medi_verse.R
import com.example.medi_verse.data.remote.model.GetPost
import com.example.medi_verse.data.remote.model.SearchPost
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.Result


@Composable
fun SearchResults(remoteRepo: RemoteRepo,searchText: String) {
    val items = remember { mutableStateOf<List<GetPost>>(emptyList()) }
    val search = SearchPost(
        club_id = searchText
    )
//    LaunchedEffect(search) {
//        val searchResult = remoteRepo.searchPostClub(search)
//        if (searchResult is Result.Success) {
//            items.value = searchResult.data!!
//        }
//    }
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
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(18.dp))
            LazyColumn {
                items(items = items.value) { post ->
                    SearchLayout(post = post)
                }
            }
        }
    }
}

@Composable
fun SearchLayout(post: GetPost) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.medicapslogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = post.username,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
    }
}

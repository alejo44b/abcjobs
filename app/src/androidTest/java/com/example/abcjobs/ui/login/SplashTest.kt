package com.example.abcjobs.ui.login

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.example.abcjobs.R
import org.junit.Rule
import org.junit.Test

/*
fun SplashScreen(navController: NavController, application: Application){
    val context = LocalContext.current
    var progress by remember { mutableFloatStateOf(0f) }
    var textoCarga by remember { mutableStateOf (context.getString(R.string.loading)) }

    LaunchedEffect(Unit){
        val sec = Security(application)
        val specialist = ItSpecialistsAdapter(application)
        val technical = TechnicalTestAdapter(application)
        val interview = InterviewAdapter(application)
        val project = ProjectsAdapter(application)
        val selection = SelectionAdapter(application)
        val company = CompanyAdapter(application)
        val performance = PerformanceAdapter(application)

        textoCarga = context.getString(R.string.splash_checking_servers)
        withContext(Dispatchers.IO) {
            try {
                textoCarga = if (sec.pong()) context.getString(R.string.splash_users_checked) else context.getString(R.string.splash_users_not_checked)
                for (i in 0..12) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (specialist.pong()) context.getString(R.string.splash_itspecialists_checked) else context.getString(R.string.splash_itspecialists_not_checked)
                for (i in 12..24) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (technical.pong()) context.getString(R.string.splash_technicaltests_checked) else context.getString(R.string.splash_technicaltests_not_checked)
                for (i in 24..36) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (interview.pong()) context.getString(R.string.splash_interviews_checked) else context.getString(R.string.splash_interviews_not_checked)
                for (i in 36..48) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (project.pong()) context.getString(R.string.splash_projects_checked) else context.getString(R.string.splash_projects_not_checked)
                for (i in 48..60) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (selection.pong()) context.getString(R.string.splash_selections_checked) else context.getString(R.string.splash_selections_not_checked)
                for (i in 60..72) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (company.pong()) context.getString(R.string.splash_companies_checked) else context.getString(R.string.splash_companies_not_checked)
                for (i in 72..84) {
                    progress = i / 100f
                    delay(10)
                }
                textoCarga = if (performance.pong()) context.getString(R.string.splash_performances_checked) else context.getString(R.string.splash_performances_not_checked)
                for (i in 84..100) {
                    progress = i / 100f
                    delay(10)
                }
            }catch (e: Exception){
                Log.e("SplashScreen", "Error: ${e.message}")
            }
        }
        navController.popBackStack()
        navController.navigate(LoginScreens.Login.route)
    }
    Splash(progress, textoCarga)
}
 */
class SplashTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context: Context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testSplash() {
        composeTestRule.setContent {
            Splash(0f, context.getString(R.string.loading))
        }
        composeTestRule
            .onNodeWithText(context.getString(R.string.loading))
            .assertExists()
    }

}
// ./gradlew boilerplate -PappNamespace=AppName -PfeatureName=FeatureName
tasks.register("boilerplate") {
    doLast {

        // Input prompt for the app namespace (package name)
        val appNamespace = project.findProperty("appNamespace")?.toString()
            ?: throw GradleException("App namespace is required. Run the task with -PappNamespace=com.michael")

        // Input prompt for the feature name
        val featureName = project.findProperty("featureName")?.toString()
            ?: throw GradleException("Feature name is required. Run the task with -PfeatureName=YourFeatureName")

        // Ensure appNamespace is in lowercase
        val normalizedNamespace = appNamespace.lowercase()
        val normalizedFeatureName = featureName.lowercase()

        // Determine string resource name and value
        fun generateStringResource(featureName: String): Pair<String, String> {
            // Check if the featureName is Pascal Case or starts with a single capital letter
            val isPascalCase = featureName.any { it.isUpperCase() } && featureName[0].isUpperCase()
            val stringResourceName = if (isPascalCase) {
                // Convert Pascal Case to snake_case
                featureName.replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()
            } else {
                featureName.lowercase()
            }

            val stringResourceValue = featureName.split(Regex("(?=[A-Z])")).joinToString(" ") { it.capitalize() }

            return Pair(stringResourceName, stringResourceValue)
        }

        val (stringResourceName, stringResourceValue) = generateStringResource(featureName)


        // Define the base project directory
        val appDir = "${projectDir}/app/src/main/java/com/michael"
        val projectRoot = "$appDir/${appNamespace.replace('.', '/')}/feature"

        // Paths for contract and presentation directories
        val contractPath = File("$projectRoot/$normalizedFeatureName/contract")
        val presentationPath = File("$projectRoot/$normalizedFeatureName/presentation")

        // Create directories if they don't exist
        contractPath.mkdirs()
        presentationPath.mkdirs()

        // Define the file content for each component

        // State class
        val stateClass = """
            package com.michael.${normalizedNamespace}.feature.${normalizedFeatureName.lowercase()}.contract

            import com.michael.${normalizedNamespace}.core.base.contract.BaseState
            import com.michael.${normalizedNamespace}.core.base.model.MessageState

            data class ${featureName}State(
                override val isLoading: Boolean,
                override val errorState: MessageState?
            ) : BaseState {
                companion object {
                    val initialState = ${featureName}State(
                        isLoading = false,
                        errorState = null
                    )
                }
            }
        """.trimIndent()

        // ViewAction class
        val viewActionClass = """
            package com.michael.${normalizedNamespace}.feature.${normalizedFeatureName.lowercase()}.contract
                        
            import com.michael.${normalizedNamespace}.core.base.contract.MainViewAction

            interface ${featureName}ViewAction : MainViewAction
        """.trimIndent()

        // ViewModel class
        val viewModelClass = """
            package com.michael.${normalizedNamespace}.feature.${normalizedFeatureName}.presentation

            import com.michael.${normalizedNamespace}.core.base.contract.BaseViewModel
            import com.michael.${normalizedNamespace}.feature.${featureName.lowercase()}.contract.${featureName}State
            import com.michael.${normalizedNamespace}.feature.${featureName.lowercase()}.contract.${featureName}ViewAction
            import dagger.hilt.android.lifecycle.HiltViewModel
            import javax.inject.Inject


            @HiltViewModel
            class ${featureName}ViewModel @Inject constructor() : BaseViewModel<${featureName}State, ${featureName}ViewAction>(
                ${featureName}State.initialState
            ) {
                override fun onViewAction(viewAction: ${featureName}ViewAction) {
                    when (viewAction) {
                        
                    }
                }
            }
        """.trimIndent()

        // Screen class
        val screenClass = """
            package com.michael.${normalizedNamespace}.feature.${normalizedFeatureName.lowercase()}.presentation

            import androidx.compose.runtime.Composable
            import androidx.compose.runtime.collectAsState
            import androidx.compose.runtime.getValue
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.res.stringResource
            import androidx.hilt.navigation.compose.hiltViewModel
            import com.michael.${normalizedNamespace}.feature.components.BaseScreen
            import com.michael.${normalizedNamespace}.feature.components.ToolBarTextComponent
            import com.michael.${normalizedNamespace}.R
            import kotlinx.serialization.Serializable
            import com.michael.${normalizedNamespace}.navigation.NavigationDestination

            
            @Serializable
            object ${featureName}Destination : NavigationDestination

            @Composable
            fun ${featureName}Screen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
                val viewModel = hiltViewModel<${featureName}ViewModel>()
                val state by viewModel.state.collectAsState()

                BaseScreen(
                    state = state,
                    topAppBarCenteredContent = {
                        ToolBarTextComponent(text = stringResource(R.string.${stringResourceName}))
                    },
                    systemOnBack = onBackClick,
                ) { paddingValues ->
                    // UI Content here
                }
            }
        """.trimIndent()

        // Write files to the respective directories
        File(contractPath, "${featureName}State.kt").writeText(stateClass)
        File(contractPath, "${featureName}ViewAction.kt").writeText(viewActionClass)
        File(presentationPath, "${featureName}ViewModel.kt").writeText(viewModelClass)
        File(presentationPath, "${featureName}Screen.kt").writeText(screenClass)

        // Update the Navigator.kt file
        val navigatorFile =
            File("$appDir/${appNamespace.replace('.', '/')}/navigation/Navigator.kt")
        if (navigatorFile.exists()) {
            // Read the current content of the Navigator file
            val navigatorContent = navigatorFile.readText()

            // Define the new destination and composable block
            val newDestinationComposable = """
        composable<${featureName}Destination> { ${featureName}Screen(onBackClick = onBackClick) }
    """.trimIndent()

            // Find the position of the last composable block
            val lastComposableIndex = navigatorContent.lastIndexOf("composable<")

            if (lastComposableIndex != -1) {
                // Find the closing brace of the last composable block
                val closingBraceIndex = navigatorContent.indexOf("}", lastComposableIndex) + 1

                // Insert the new composable block after the closing brace
                val updatedNavigatorContent = StringBuilder(navigatorContent).insert(
                    closingBraceIndex,
                    "\n        $newDestinationComposable"
                ).toString()


                // Write the updated content back to the Navigator file
                navigatorFile.writeText(updatedNavigatorContent)

                println("Navigator.kt updated with ${featureName}Destination successfully!")
            } else {
                println("No composable block found in Navigator.kt. Skipping update.")
            }
        } else {
            println("Navigator.kt not found. Skipping Navigator update.")
        }

        // Update the strings.xml file
        val stringsFile = File("${projectDir}/app/src/main/res/values/strings.xml")
        if (stringsFile.exists()) {
            val stringsContent = stringsFile.readText()

//            // Define the new string resource
//            val newStringResource = """
//        <string name="$stringResourceName">$stringResourceValue</string>
//    """.trimIndent()
//
//            // Insert the new string resource before the closing </resources> tag
//            val updatedStringsContent = StringBuilder(stringsContent).apply {
//                val resourcesEndIndex = lastIndexOf("</resources>")
//                if (resourcesEndIndex != -1) {
//                    insert(resourcesEndIndex, "\n$newStringResource")
//                }
//            }.toString()

            val newStringResource = """
                <string name="${stringResourceName.trim()}">${stringResourceValue.trim()}</string>
            """.trimIndent()

            // Insert the new string resource before the closing </resources> tag
            val updatedStringsContent = StringBuilder(stringsContent).apply {
                val resourcesEndIndex = lastIndexOf("</resources>")
                if (resourcesEndIndex != -1) {
                    insert(resourcesEndIndex - 1, "\n    $newStringResource")  // Insert before the closing tag with proper spacing
                }
            }.toString()

            // Write the updated content back to strings.xml
            stringsFile.writeText(updatedStringsContent)

            println("strings.xml updated with new string resource for $stringResourceName successfully!")
        } else {
            println("strings.xml not found. Skipping string resource update.")
        }




        println("Boilerplate for feature '$featureName' created successfully!")
    }
}

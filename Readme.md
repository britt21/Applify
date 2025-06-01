
Purpose:
A launcher UI that shows installed apps, allows search, and launches selected apps.


Edge Case Handling/Onboarding Flow Improvement:  I introduced a dataManager class what it does it captures the steps completed in a shared preference
to capture the steps completed, even when the app closes by the system the cache will always remember the 
steps completed

The DataManager records progress, retained across app restarts.

App Architecture Design: Fix I introduced an MVVM Architecture by using ViewModel and Repository to separate the business logic from the ui

The viewModel loads all installed apps using the repository.

UI remains simple and only deals with rendering and user input.



LiveData :

Data for installed apps is held in a LiveData<List<AppInfo>>, which the UI observes.
Whenever new data is set in the ViewModel, the UI updates automatically.



Functions
onCreate()
Sets layout.

Sets up RecyclerView and search.

setupRecyclerView()
Prepares the grid to show apps.

Handles app click to launch.

loadApps()
Gets all launchable apps (except itself).

Sorts them by name.

Updates the list on screen.

setupSearch()
Filters apps when typing in search bar.

launchApp(packageName: String)
Launches app using its package name.

onResume()
Reloads the app list every time you return to the screen.

Thought Process:
Uses lifecycleScope for background tasks.

UI update is done on the main thread.

Fast search and app launching.





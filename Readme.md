Purpose:
A launcher UI that shows installed apps, allows search, and launches selected apps.


Functions
onCreate()
Sets layout.

Connects views.

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





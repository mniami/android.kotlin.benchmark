package android.benchmark.ui.activities.main

internal class ActionBarToolImpl(val mainActivity: MainActivity) : IActionBarTool {
    override fun showBackArrow() {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun hideBackArrow() {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false);
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(false);
    }

    override fun setTitle(title: String) {
        mainActivity.supportActionBar?.setTitle(title)
    }
}
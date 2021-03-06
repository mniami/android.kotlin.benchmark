package guideme.volunteers.ui.views.actionbar

interface ActionBarTool {
    fun setTitle(title : String)
    fun showBackArrow()
    fun hideBackArrow()
    var onBackPressed : () -> Boolean
    fun clearOnBackPressed()
    fun backPressed() : Boolean
    fun hideOptions()
    fun showOptions()
    fun dispose()
}
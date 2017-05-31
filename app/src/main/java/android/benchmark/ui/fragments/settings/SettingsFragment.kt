package android.benchmark.ui.fragments.settings

import android.benchmark.R
import android.benchmark.domain.User
import android.benchmark.services.Services
import android.benchmark.ui.activities.main.IMainActivity
import android.benchmark.ui.fragments.base.BaseFragment
import android.benchmark.ui.fragments.base.ToolbarConfiguration
import android.benchmark.ui.utils.AppVersionProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView

class SettingsFragment : BaseFragment(
        R.layout.settings_fragment,
        ToolbarConfiguration(),
        SettingsPresenter(, appVersionProvider, Services.dataService, mainActivity)
        ), ISettingsFragment {
    val appVersionProvider by lazy { AppVersionProvider(activity.packageManager, activity.packageName) }
    val presenter by lazy { SettingsPresenter(this, appVersionProvider, Services.dataService, mainActivity) }
    val tvAppVersion by lazy { view?.findViewById(R.id.tvAppVersion) as TextView }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun setAppVersion(appVersion: String) {
        tvAppVersion.text = appVersion
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter){
            mainActivity.actionBarTool.setTitle(mainActivity.getResourceText(R.string.action_settings))
            mainActivity.actionBarTool.showBackArrow()
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun showUserData(user: User?) {
        user?.let {

        }
    }
}

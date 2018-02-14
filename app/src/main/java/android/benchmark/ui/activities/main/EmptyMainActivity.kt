package android.benchmark.ui.activities.main

import android.benchmark.domain.Person
import android.benchmark.domain.Project
import android.benchmark.domain.Volunteer
import android.benchmark.helpers.dataservices.errors.ErrorMessage
import android.benchmark.ui.views.actionbar.ActionBarTool

class EmptyMainActivity(override val actionBarTool: ActionBarTool) : MainActivity {
    override fun addListener(listener: MainActivityListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeListener(listener: MainActivityListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(errorMessage: ErrorMessage) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openEditUserDetails(volunteer: Volunteer) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProject(project: Project) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSettings() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResourceText(id: Int): String {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ""
    }

    override fun goBack() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openHome() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showVolunteer(volunteer: Volunteer) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openAuthentication() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showVolunteerList() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
package guideme.volunteers.helpers.content

interface ResourceManager {
    fun getString(id: Resources): String
    fun getStringArray(id : Resources.Array) : Array<String>
}
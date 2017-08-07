package android.benchmark.domain

import java.io.Serializable

class Volunteer(val needies: List<Needy> = emptyList(),
                name: String = "",
                surname: String = "",
                avatarImageUri: String = "",
                shortDescription: String = "",
                description: String = "",
                volunteerType: String = "",
                skills: Skills = Skills(),
                activity : Activity = Activity(),
                projects: MutableList<Project> = mutableListOf(),
                addresses: MutableList<Address> = mutableListOf()) :
        Person(name, surname, avatarImageUri, shortDescription, description, volunteerType, skills, activity, projects, addresses)

class Needy(val volunteers: List<Volunteer> = emptyList(),
            name: String = "",
            surname: String = "",
            avatarImageUri: String = "",
            shortDescription: String = "",
            description: String = "",
            volunteerType: String = "",
            skills: Skills = Skills(),
            activity : Activity = Activity(),
            projects: MutableList<Project> = mutableListOf(),
            addresses: MutableList<Address> = mutableListOf()) :
        Person(name, surname, avatarImageUri, shortDescription, description, volunteerType, skills, activity, projects, addresses)

open class Person(
        val name: String = "",
        val surname: String = "",
        val avatarImageUri: String = "",
        val shortDescription: String = "",
        val description: String = "",
        val volunteerType: String = "",
        val skills: Skills = Skills(),
        val activity : Activity = Activity(),
        val projects: MutableList<Project> = mutableListOf(),
        val addresses: MutableList<Address> = mutableListOf()) : Serializable

class User(val volunteers: MutableList<Volunteer> = mutableListOf(),
           name: String = "",
           surname: String = "",
           avatarImageUri: String = "",
           shortDescription: String = "",
           description: String = "",
           volunteerType: String = "",
           skills: Skills = Skills(),
           activity : Activity = Activity(),
           projects: MutableList<Project> = mutableListOf(),
           addresses: MutableList<Address> = mutableListOf()) :
        Person(name, surname, avatarImageUri, shortDescription, description, volunteerType, skills, activity, projects, addresses)

class ActionDate (val value : Long = 0)
class Action (val name : String = "", val date : ActionDate = ActionDate())
class Activity(val actions : MutableList<Action> = mutableListOf())
class Skills(val stamina: Level = Level(),
             val engagement: Level = Level(),
             val humor: Level = Level(),
             val enjoyment: Level = Level(),
             val psychicStrength: Level = Level(),
             val social: Level = Level(),
             val intuition: Level = Level(),
             val marketing: Level = Level(),
             val talkative : Level = Level(),
             val technical : Level = Level(),
             val economic : Level = Level(),
             val laziness : Level = Level(),
             val leading : Level = Level(),
             val introvert : Level = Level(),
             val traveling: Level = Level()) {
}
class Level(val level : Float = 0f) {
    operator fun times (skill2 : Level) : Level {
        return Level(level * skill2.level)
    }
    operator fun times (skillLevel : Float) : Level {
        return Level(level * skillLevel)
    }
    operator fun div (skill2 : Level) : Level {
        return Level(level / skill2.level)
    }
    operator fun div (skillLevel : Float) : Level {
        return Level(level / skillLevel)
    }
    operator fun plus (skillLevel : Float) : Level {
        return Level(level + skillLevel)
    }
    operator fun plus (skill2 : Level) : Level {
        return Level(level + skill2.level)
    }
}
operator fun Float.div (skill : Level) : Level {
    return Level(this / skill.level)
}
operator fun Float.times (skill : Level) : Level {
    return Level(this * skill.level)
}

class VolunteerType {
    companion object {
        const val Admin = "Admin"
        const val Moderator = "Moderator"
        const val Senior = "Senior Volunteer"
        const val Regular = "Regular Volunteer"
        const val Junior = "Junior Volunteer"
    }
}

open class Project(val name: String = "",
                   val description: String = "",
                   val longDescription: String = "",
                   val volunteersInvolved: MutableList<Volunteer> = mutableListOf(),
                   val images: MutableList<ImageMetadata> = mutableListOf()) : Serializable

open class ImageMetadata(val name: String = "",
                         val url: String = "") : Serializable

class Address(
        val city: String = "",
        val zip: String = "",
        val street: String = "",
        val house: String = "",
        val flat: String = "",
        val addressType: LiveAddress = LiveAddress()
) : Serializable

open class AddressType : Serializable
class LiveAddress : AddressType()

class TravelDestination (val name : String, val difficultyLevel : Level){

}
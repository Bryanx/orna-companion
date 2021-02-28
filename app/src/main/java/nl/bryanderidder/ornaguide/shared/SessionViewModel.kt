package nl.bryanderidder.ornaguide.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    var characterClass: MutableLiveData<CharacterClass> = MutableLiveData()
}
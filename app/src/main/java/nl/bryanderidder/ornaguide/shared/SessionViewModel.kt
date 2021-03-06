package nl.bryanderidder.ornaguide.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

// TODO: remove this eventually and use sharedprefs so we can share accross activities
class SessionViewModel(application: Application) : AndroidViewModel(application) {
    var characterClassId: MutableLiveData<Int> = MutableLiveData()
}
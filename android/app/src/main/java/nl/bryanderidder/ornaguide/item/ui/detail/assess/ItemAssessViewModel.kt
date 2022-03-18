package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.view.MenuItem
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.model.ItemAssess
import nl.bryanderidder.ornaguide.item.persistence.ItemAssessRepository
import nl.bryanderidder.ornaguide.item.persistence.ItemAssessRequestBody
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.hideKeyboard

class ItemAssessViewModel(
    private val repository: ItemAssessRepository,
    private val sharedPrefsUtil: SharedPrefsUtil,
) : BindingViewModel() {

    val itemAssess: MutableLiveData<ItemAssess> = MutableLiveData()

    val itemAssessList: MutableLiveData<List<ItemAssess>> = MutableLiveData()

    val itemAssessResult: MutableLiveData<ItemAssess> = MutableLiveData(ItemAssess())

    private val itemAssessBody: ItemAssessRequestBody = ItemAssessRequestBody()

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        private set

    init {
        loadItems()
    }

    fun loadItem() = viewModelScope.launch {
        repository.getItemAssess(
            itemAssessId = sharedPrefsUtil.getItemAssessId(),
            onError = { toastMessage = it }
        ).collect {
            itemAssess.postValue(it)
        }
    }

    private fun loadItems() = viewModelScope.launch {
        repository.getItemAssessList(
            itemId = sharedPrefsUtil.getItemId(),
            onError = { toastMessage = it }
        ).collect {
            itemAssessList.postValue(it)
        }
    }

    fun onClickItemAssessMenuItem(item: MenuItem, dialog: ItemAssessDialogFragment): Boolean {
        return when (item.itemId) {
            R.id.action_delete_item_assess -> {
                onDelete()
                dialog.dismiss()
                true
            }
            else -> false
        }
    }

    fun onDelete() = viewModelScope.launch {
        itemAssess.value?.let { repository.deleteItemAssess(it) }
        loadItems()
    }

    fun updateAttack(value: String) {
        if (inputIsValid(value)) itemAssessBody.attack = value.toInt()
    }

    fun updateDefense(value: String) {
        if (inputIsValid(value)) itemAssessBody.defense = value.toInt()
    }

    fun updateMagic(value: String) {
        if (inputIsValid(value)) itemAssessBody.magic = value.toInt()
    }

    fun updateHp(value: String) {
        if (inputIsValid(value)) itemAssessBody.hp = value.toInt()
    }

    fun updateMana(value: String) {
        if (inputIsValid(value)) itemAssessBody.mana = value.toInt()
    }

    fun updateResistance(value: String) {
        if (inputIsValid(value)) itemAssessBody.resistance = value.toInt()
    }

    fun updateDexterity(value: String) {
        if (inputIsValid(value)) itemAssessBody.dexterity = value.toInt()
    }

    fun updateWard(value: String) {
        if (inputIsValid(value)) itemAssessBody.ward = value.toInt()
    }

    fun updateCrit(value: String) {
        if (inputIsValid(value)) itemAssessBody.crit = value.toInt()
    }

    fun updateLevel(value: String) {
        if (inputIsValid(value)) itemAssessBody.level = value.toInt()
    }

    private fun inputIsValid(value: String) = value.isNotEmpty() && value != "-" && value != "+"

    fun onSubmit(item: Item, stats: Map<String, Int>, view: View): Job {
        view.hideKeyboard()
        return viewModelScope.launch {
            itemAssessBody.id = item.id
            itemAssessBody.image = item.image
            repository.assessItem(
                body = itemAssessBody,
                onError = { toastMessage = it })
                .collect {
                    itemAssessResult.postValue(it)
                    loadItems()
                }
        }
    }
}

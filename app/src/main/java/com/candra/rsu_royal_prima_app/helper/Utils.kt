package com.candra.rsu_royal_prima_app.helper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.LocaleData
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.RecyclerView
import com.candra.rsu_royal_prima_app.data.LoginUser
import com.candra.rsu_royal_prima_app.helper.Constant.GOOD_AFTERNOON
import com.candra.rsu_royal_prima_app.helper.Constant.GOOD_EVENING
import com.candra.rsu_royal_prima_app.helper.Constant.GOOD_MORNING
import com.candra.rsu_royal_prima_app.helper.Constant.GOOD_NIGHT
import com.candra.rsu_royal_prima_app.helper.Constant.NOTHING_DATA
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constant.LOGIN_USER
    )

    private const val FORMAT_DATE = "EEEE,dd-MM-yyyy"
    const val TIME_FORMAT = "HH:mm:ss"


    @SuppressLint("ConstantLocale")
    val formatDate: String = SimpleDateFormat(
        FORMAT_DATE,
        Locale.getDefault()
    ).format(Date())

    fun getUser(context: Context): Flow<LoginUser> =
        context.dataStore.data.map { prefrences ->
            LoginUser(
                username = prefrences[Constant.USERNAME],
                password = prefrences[Constant.PASSWORD],
                isLogin = prefrences[Constant.IS_LOGIN]
            )
        }

    suspend fun updateDataUser(context: Context, user: LoginUser) = context.dataStore.edit { preferences ->
        user.username?.let { preferences[Constant.USERNAME] = it}
        user.password?.let { preferences[Constant.PASSWORD] = it }
        user.isLogin?.let { preferences[Constant.IS_LOGIN] = it}
    }

    fun makeToast(context: Context,message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun setToolbar(titleToolbar: String, subtitleToolbar: String,actionbar: ActionBar?,position: Int){
        actionbar?.apply {
            if (position == 0)
            {
                title = titleToolbar
                subtitle = subtitleToolbar
                setDisplayHomeAsUpEnabled(true)
            }else{
                title = titleToolbar
                subtitle = subtitleToolbar
            }

        }
    }

    fun greetingMessage(username: String): String{
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY))
        {
            in 0..11 -> "$GOOD_MORNING $username"
            in 12..15 -> "$GOOD_AFTERNOON $username"
            in 16..18 -> "$GOOD_EVENING $username"
            in 18..23 -> "$GOOD_NIGHT $username"
            else -> NOTHING_DATA
        }
    }

    fun showTvEmpty(isShow: Boolean,tvShow: MaterialTextView,rvList: RecyclerView){
        tvShow.visibility = if (isShow) View.VISIBLE else View.GONE
        rvList.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    fun showCalendar(tilInputDateBirth: TextInputEditText,context: Context){
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat,Locale.UK)
            tilInputDateBirth.setText(sdf.format(calendar.time))
        }
        tilInputDateBirth.setOnClickListener {
            DatePickerDialog(context,datePicker,calendar[Calendar.YEAR],calendar[Calendar.MONTH],calendar[Calendar.DAY_OF_MONTH])
                .show()
        }
    }



}
package com.example.hci_mobileapp.devicesView

import androidx.lifecycle.ViewModel

class DevicesViewModel: ViewModel(){

    fun getAllDeivces(): Array<Any>? {
        return null
    }

    fun check(id:String): String {
        when(id){
            "c89b94e8581855bc" -> return "Speaker"
            "li6cbv5sdlatti0j" -> return "AC"
            "rnizejqr2di0okho" -> return "Fridge"
            "dbrlsh7o5sn8ur4i" -> return "Faucet"
            "go46xmbqeomjrsjr" -> return "Lamp"
            else -> return "Not found"
        }
    }

}
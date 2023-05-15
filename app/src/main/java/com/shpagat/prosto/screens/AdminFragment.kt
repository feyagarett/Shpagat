package com.shpagat.prosto.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.shpagat.prosto.R
import com.shpagat.prosto.databinding.FragmentAdminBinding
import com.shpagat.prosto.utils.*

class AdminFragment : Fragment() {
    private lateinit var binding: FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFuns()
    }

    private fun initFuns() {
        binding.signinBtn.setOnClickListener {
            trySignin()
        }
    }

    private fun trySignin() {
        val password = myGetText(binding.password)
        if (password.isNotEmpty())
            database.child(ADMIN).get().addOnSuccessListener {
                if (it.exists()) {
                    if (password == it.value.toString()) {
                        val navController = Navigation.findNavController(APP, R.id.main_frame)
                        navController.navigate(R.id.action_admin_to_profile)
                    } else {
                        appToast("Пароль неверный")
                    }
                }
            }
    }
}
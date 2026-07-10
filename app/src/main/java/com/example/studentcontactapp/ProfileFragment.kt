package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var settingsManager: SettingsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        prefManager = PrefManager(requireContext())
        settingsManager = SettingsManager(requireContext())

        val switchDarkMode = view.findViewById<SwitchMaterial>(R.id.switchDarkMode)
        val switchFontSize = view.findViewById<SwitchMaterial>(R.id.switchFontSize)
        val switchNotification = view.findViewById<SwitchMaterial>(R.id.switchNotification)
        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)

        switchDarkMode.isChecked = settingsManager.isDarkMode()
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setDarkMode(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        switchFontSize.isChecked = settingsManager.getFontScale() > 1.0f
        switchFontSize.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setFontScale(if (isChecked) 1.2f else 1.0f)
            requireActivity().recreate()
        }

        switchNotification.isChecked = settingsManager.isNotificationEnabled()
        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setNotificationEnabled(isChecked)
        }

        btnLogout.setOnClickListener {
            prefManager.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}

package kz.qazatracker.main.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import kz.qazatracker.R
import kz.qazatracker.startscreen.StartScreenActivity
import kz.qazatracker.utils.LocaleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val menuViewModel: MenuViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        view.findViewById<TextView>(R.id.language_text_view).setOnClickListener {
            showSelectLanguageDialog()
        }
        view.findViewById<TextView>(R.id.reset_text_view).setOnClickListener {
            showResetDialog()
        }
        view.findViewById<TextView>(R.id.about_qaza_solat_text_view).setOnClickListener {

        }
        view.findViewById<TextView>(R.id.contact_text_view).setOnClickListener {

        }
//        val telegramTextView = view.findViewById<TextView>(R.id.telegram_text_view)
//        Linkify.addLinks(telegramTextView, Linkify.ALL)
    }

    private fun observeViewModel() {
        menuViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleMenuNavigation)
    }

    private fun handleMenuNavigation(menuNavigation: MenuNavigation) {
        when(menuNavigation) {
            MenuNavigation.RestartApp -> {
                val intent = Intent(requireContext(), StartScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun showResetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.reset_message)
        builder.setPositiveButton(R.string.yes) { _, _ -> menuViewModel.onResetData() }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }


    private fun showSelectLanguageDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.select_language))
        val animals = arrayOf(
            getString(R.string.kazakh_lang),
            getString(R.string.russian_lang)
        )
        builder.setItems(animals) { dialog, which ->
            when (which) {
                0 -> {
                    LocaleHelper.setLocale(requireContext(), "kk")
                }
                1 -> {
                    LocaleHelper.setLocale(requireContext(), "en")
                }
            }
            requireActivity().finish();
            requireActivity().startActivity(requireActivity().intent);
        }
        val dialog = builder.create()
        dialog.show()
    }
}
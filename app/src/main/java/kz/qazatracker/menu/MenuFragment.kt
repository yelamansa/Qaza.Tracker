package kz.qazatracker.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import kz.qazatracker.R
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState
import kz.qazatracker.qaza_hand_input.presentation.QazaInputRouter
import kz.qazatracker.remoteconfig.CONTACT_LINK_REMOTE_CONFIG
import kz.qazatracker.remoteconfig.RemoteConfig
import kz.qazatracker.remoteconfig.SOCIAL_NETWORK_REMOTE_CONFIG
import kz.qazatracker.startscreen.StartScreenRouter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val menuViewModel: MenuViewModel by viewModel()
    private val remoteConfig: RemoteConfig by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        view.findViewById<TextView>(R.id.language_text_view).setOnClickListener {
            showSelectLanguageDialog()
        }
        view.findViewById<TextView>(R.id.title_text_view).setOnClickListener {
            requireActivity().onBackPressed()
        }
        view.findViewById<TextView>(R.id.change_qaza_text_view).setOnClickListener {
            startActivity(QazaInputRouter().createIntent(requireContext(), QazaHandInputState.QazaEdit))
        }
        view.findViewById<TextView>(R.id.reset_text_view).setOnClickListener {
            showResetDialog()
        }
        view.findViewById<TextView>(R.id.about_qaza_solat_text_view).setOnClickListener {
            //todo
        }
        val telegramTextView = view.findViewById<TextView>(R.id.contact_text_view)
        telegramTextView.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(remoteConfig.getString(CONTACT_LINK_REMOTE_CONFIG)))
            )
        }
        view.findViewById<TextView>(R.id.social_network_text_view).setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(remoteConfig.getString(SOCIAL_NETWORK_REMOTE_CONFIG))
                )
            )
        }
    }

    private fun observeViewModel() {
        menuViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleMenuNavigation)
    }

    private fun handleMenuNavigation(menuNavigation: MenuNavigation) {
        when (menuNavigation) {
            MenuNavigation.RestartApp -> {
                val intent = StartScreenRouter().createIntent(requireContext())
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
            menuViewModel.langSelected(which)
            requireActivity().finish();
            requireActivity().startActivity(requireActivity().intent);
        }
        val dialog = builder.create()
        dialog.show()
    }
}
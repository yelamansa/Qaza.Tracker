package kz.qazatracker.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kz.qazatracker.R
import kz.qazatracker.utils.LocaleHelper

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.language_text_view).setOnClickListener {
            showSelectLanguageDialog()
        }
        view.findViewById<TextView>(R.id.reset_text_view).setOnClickListener {

        }
        view.findViewById<TextView>(R.id.about_qaza_solat_text_view).setOnClickListener {

        }
        view.findViewById<TextView>(R.id.contact_text_view).setOnClickListener {

        }
//        val telegramTextView = view.findViewById<TextView>(R.id.telegram_text_view)
//        Linkify.addLinks(telegramTextView, Linkify.ALL)
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
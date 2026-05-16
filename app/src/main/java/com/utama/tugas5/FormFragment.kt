package com.utama.tugas5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class FormFragment : Fragment() {

    private lateinit var prefs: DataHujanPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container, false)
        prefs = DataHujanPreferences(requireContext())

        val etTanggal = view.findViewById<TextInputEditText>(R.id.etTanggal)
        val etKondisi = view.findViewById<TextInputEditText>(R.id.etKondisi)
        val etCurah = view.findViewById<TextInputEditText>(R.id.etCurah)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)

        // Ambil argument dari navigasi
        val id = arguments?.getInt("id") ?: -1
        val isEditMode = id != -1

        if (isEditMode) {
            etTanggal.setText(arguments?.getString("tanggal"))
            etKondisi.setText(arguments?.getString("kondisi"))
            etCurah.setText(arguments?.getString("curah"))
        }

        btnSimpan.setOnClickListener {
            val tanggal = etTanggal.text.toString().trim()
            val kondisi = etKondisi.text.toString().trim()
            val curah = etCurah.text.toString().trim()

            if (tanggal.isEmpty() || kondisi.isEmpty() || curah.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = DataHujan(
                id = if (isEditMode) id else 0,
                tanggal = tanggal,
                kondisi = kondisi,
                curahHujan = curah
            )

            if (isEditMode) {
                prefs.update(data)
                Toast.makeText(requireContext(), "Data berhasil diupdate!", Toast.LENGTH_SHORT).show()
            } else {
                prefs.add(data)
                Toast.makeText(requireContext(), "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            }

            // Kembali ke ListFragment
            findNavController().popBackStack()
        }

        return view
    }
}
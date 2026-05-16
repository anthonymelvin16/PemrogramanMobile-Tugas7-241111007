package com.utama.tugas5

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.utama.tugas5.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HujanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = HujanRepository()
        val factory    = HujanViewModelFactory(repository)
        viewModel      = ViewModelProvider(this, factory)[HujanViewModel::class.java]

        binding.rvDataHujan.layoutManager = LinearLayoutManager(requireContext())

        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            binding.rvDataHujan.adapter = DataHujanAdapter(dataList) { item ->
                showDetailDialog(item)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            msg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
        }

        binding.fabTambah.setOnClickListener {
            showTambahDialog()
        }
    }

    private fun showDetailDialog(item: DataHujanApi) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detail Data Hujan")
            .setMessage(
                "Lokasi      : ${item.lokasi}\n" +
                        "Tanggal     : ${item.tanggal}\n" +
                        "Curah Hujan : ${item.curahHujan} mm\n" +
                        "Kategori    : ${item.kategori}\n" +
                        "Keterangan  : ${item.keterangan ?: "-"}"
            )
            .setPositiveButton("Tutup", null)
            .setNegativeButton("Hapus") { _, _ ->
                viewModel.deleteData(item.id)
                Toast.makeText(requireContext(), "Data dihapus!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showTambahDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_form, null)

        val etTanggal = dialogView.findViewById<EditText>(R.id.etTanggal)
        val etKondisi = dialogView.findViewById<EditText>(R.id.etKondisi)
        val etCurah   = dialogView.findViewById<EditText>(R.id.etCurah)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Data Hujan")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val tanggal = etTanggal.text.toString().trim()
                val kondisi = etKondisi.text.toString().trim()
                val curah   = etCurah.text.toString().toDoubleOrNull() ?: 0.0

                if (tanggal.isEmpty() || kondisi.isEmpty()) {
                    Toast.makeText(requireContext(), "Isi semua field!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                viewModel.addData(
                    HujanRequest(
                        lokasi     = "Bandung",
                        tanggal    = tanggal,
                        curahHujan = curah,
                        kategori   = kondisi,
                        keterangan = ""
                    )
                )
                Toast.makeText(requireContext(), "Data ditambahkan!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
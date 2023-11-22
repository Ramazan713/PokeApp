package com.example.pokedexapp.presentation.filter_dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pokedexapp.databinding.FragmentDialogOrderBinding
import com.example.pokedexapp.domain.enums.OrderEnum

class OrderDialog constructor(
    private val listener: Listener,
    private val selectedOrderEnum: OrderEnum?
): DialogFragment() {

    private var _binding: FragmentDialogOrderBinding? = null
    private val binding: FragmentDialogOrderBinding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogOrderBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(selectedOrderEnum){
            OrderEnum.Number -> {
                binding.numberRadio.isChecked = true
            }
            OrderEnum.Name -> {
                binding.nameRadio.isChecked = true
            }
            null -> {}
        }

        binding.radioGroup.setOnCheckedChangeListener { group, i ->
            when(group.id){
                binding.nameRadio.id -> {
                    listener.onSelected(OrderEnum.Name)
                }
                binding.radioGroup.id -> {
                    listener.onSelected(OrderEnum.Number)
                }
            }
            dismiss()
        }
    }

    interface Listener{
        fun onSelected(orderEnum: OrderEnum)
    }
}
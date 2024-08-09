package com.grwal.memoing

import android.os.Bundle
import android.view.View
import com.grwal.memoing.common.TemplateFragment
import com.grwal.memoing.databinding.FragmentMemoBinding
import com.grwal.memoing.viewModel.MemoViewModel
import androidx.fragment.app.viewModels

class MemoFragment: TemplateFragment<FragmentMemoBinding, MemoViewModel>() {
    override val viewModel: MemoViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_memo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onChanged(value: Any) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

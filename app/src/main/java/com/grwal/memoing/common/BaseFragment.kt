package com.grwal.memoing.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.grwal.memoing.common.BaseViewModel

abstract class BaseFragment<V : BaseViewModel?> : Fragment(),
    Observer<Any> {
    protected val TAG by lazy { this::class.simpleName }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun observerViewModel()
    abstract fun setupUI(view: View)

    abstract val viewModel: V
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view: View = inflater.inflate(layoutId, container, false)
        observerViewModel()
        setupUI(view)
        return view
    }

    open fun navigateTo(id: Int) {
        this.findNavController().navigate(id)
    }

    open fun navigateTo(action: NavDirections) {
        this.findNavController().navigate(action)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun setMenuVisibility(isvisible: Boolean) {
        super.setMenuVisibility(isvisible)
    }
}
package com.grwal.memoing.common
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.grwal.memoing.common.extensions.closeApp
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class TemplateFragment<T : ViewDataBinding, VM : TemplateViewModel> : BaseFragment<VM>() {
    var binding by autoCleared<T>()
        private set
    abstract override val viewModel: VM
    open val isClearViewModel = true
    var parentView: View? = null
        private set
    private var lastTimeNavigated = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        resetTimeNavigation()
        return binding.apply { lifecycleOwner = viewLifecycleOwner }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.eventReceiver.collectLatest {
                when (it) {
                    is TemplateViewModel.CommonEvent.OnNavigation -> {
                        if (System.currentTimeMillis() - lastTimeNavigated < TIME_BETWEEN_2_NAVIGATION) {
                            return@collectLatest
                        }
                        lastTimeNavigated = System.currentTimeMillis()
                        navigateToDestination(
                            it.destination,
                            it.bundle
                        )
                        delay(TIME_BETWEEN_2_NAVIGATION)
                        resetTimeNavigation()
                    }
                    TemplateViewModel.CommonEvent.OnCloseApp -> closeApp()
                    TemplateViewModel.CommonEvent.OnBackScreen -> onBackFragment()
                    is TemplateViewModel.CommonEvent.OnOpenAnotherApp -> openAnotherApp(it.packageName)
                    else -> {

                    }
                }
            }
        }
    }

    open fun closeApp() {
        activity?.closeApp()
    }

    open fun clearData(){
        activity?.viewModelStore?.clear()
    }

    open fun navigateToDestination(actionId: Int, bundle: Bundle?) {
        try {
            bundle?.let {
                findNavController().navigate(actionId, it)
            } ?: findNavController().navigate(actionId)
        } catch (e: Exception) {

        }
    }


    open fun openAnotherApp(packageName: String) {
    }

    open fun onBackFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        lifecycle.coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }

    fun onClearViewModelInScopeActivity() {
        activity?.viewModelStore?.clear()
    }

    override fun observerViewModel() {
    }

    override fun setupUI(view: View) {
    }

    private fun resetTimeNavigation() {
        lastTimeNavigated = 0L
    }

    companion object {
        const val TIME_BETWEEN_2_NAVIGATION = 600L
    }
}
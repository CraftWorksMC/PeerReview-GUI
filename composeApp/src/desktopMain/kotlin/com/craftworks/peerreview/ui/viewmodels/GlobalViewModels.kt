package com.craftworks.music.ui.viewmodels

// Interface for ViewModels that can reload
interface ReloadableViewModel {
    fun reloadData()
}

object GlobalViewModels {
    private val viewModels = mutableSetOf<ReloadableViewModel>()

    fun registerViewModel(viewModel: ReloadableViewModel) {
        viewModels.add(viewModel)
    }

    // Refresh functions
    fun refreshAll() {
        viewModels.forEach { it.reloadData() }
    }
}
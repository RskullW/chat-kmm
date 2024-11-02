package com.chatkmm.screen._screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chatkmm.ui.R as A
import com.chatkmm.screen._screen.R as R

class ScreenActivity: AppCompatActivity() {
    private val viewModel: SplashViewModel by inject()
    private val rootViewModel: RootViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
        initialize()
    }

    private fun initialize() {
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel.isUserAuthorized.addObserver { value ->
            if (value == false) {
                rootViewModel.updateScreen(screen = Screen.AUTHORIZATION, isClear = true)
            }
        }

        viewModel.isTakenOrder.addObserver { value ->
            if (value == null) {
                return@addObserver
            }

            val screen: Screen = when (value) {
                CourierStatus.RETURNED_WAITING_FOR_ORDERS -> {
                    Screen.MAIN
                }

                else -> {
                    Screen.DELIVERY
                }
            }

            rootViewModel.updateScreen(screen = screen, isClear = true)
        }

        viewModel.errorText.addObserver { value ->
            if (value == null) {
                return@addObserver
            }

            showDialogError(title = Constants.Strings.retryAgainLater, detail = value, cancelText = Constants.Strings.close) {
                finish()
            }
        }
    }

    private fun showDialogError(title: String, detail: String, cancelText: String, onAction: () -> Unit = {}) {
        val dialog = Dialog(this, A.style.BottomSheetDialogTransparent)

        val dialogError = DialogError(this) {
            dialog.dismiss()
            onAction()
        }

        dialogError.updateTitle(text = title)
        dialogError.updateDescription(text = detail)
        dialogError.updateTextButton(text = cancelText)

        dialog.setContentView(dialogError)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.update()
    }
}
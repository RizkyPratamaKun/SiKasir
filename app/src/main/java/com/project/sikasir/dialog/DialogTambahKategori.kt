package com.project.sikasir.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.sikasir.R
import kotlinx.android.synthetic.main.dialog_tambah.*

/**
 * Dibuat oleh RizkyPratama pada 21-Apr-22.
 */
class DialogTambahKategori : DialogFragment() {

    /**
     * A function that is called when the dialog is created.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     * @return The view of the dialog
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        return inflater.inflate(R.layout.dialog_tambah, container, false)
        edDialogNama.hint = "Nama Kategori"
    }

    /**
     * It sets the width of the dialog to 85% of the screen width and the height to 40% of the screen
     * height.
     */
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
package com.example.todoapp.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.todoapp.R

class BaseActivity:ComponentActivity() {
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

   /* @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String?>?,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }*/

    fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(this)
    }

    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
      //d  progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }
}
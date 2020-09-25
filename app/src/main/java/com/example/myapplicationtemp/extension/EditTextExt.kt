@file:JvmName("EditViewUtils")

package biz.growapp.base.extension


import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


inline fun EditText.doOnTextChanged(
        crossinline beforeAction: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
        crossinline onChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
        crossinline after: (s: Editable?) -> Unit = {}
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeAction(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChanged(s, start, before, count)
        }

        override fun afterTextChanged(s: Editable?) {
            after(s)
        }
    })
}
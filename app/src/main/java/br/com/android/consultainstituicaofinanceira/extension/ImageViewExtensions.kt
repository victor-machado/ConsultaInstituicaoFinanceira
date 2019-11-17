package br.com.android.consultainstituicaofinanceira.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: Any) {
    Glide.with(this)
        .load(image)
        .centerCrop()
        .into(this)
}
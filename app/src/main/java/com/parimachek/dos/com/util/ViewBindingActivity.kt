package com.parimachek.dos.com.util

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivity<VB : ViewBinding>(
  private val bindingInflater: (LayoutInflater) -> VB,
) : AppCompatActivity() {

  protected lateinit var bnd: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    bnd = bindingInflater(layoutInflater)
    setContentView(bnd.root)
  }
}